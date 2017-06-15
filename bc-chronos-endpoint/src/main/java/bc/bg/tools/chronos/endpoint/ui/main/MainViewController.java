package bc.bg.tools.chronos.endpoint.ui.main;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DObject;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalRoleService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalTaskService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteCustomerService;
import bitronix.tm.BitronixTransaction;
import bitronix.tm.BitronixTransactionManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

    @FXML
    private Menu menuFile;

    @FXML
    private TreeView<DObject> treeCustomers;

    @FXML
    private TreeView<DObject> treeProjects;

    @FXML
    private TreeView<DObject> treeTasks;

    @FXML
    private TreeView<DObject> treeRoles;

    @FXML
    private VBox stackEntityAttributes;

    @Autowired
    @Qualifier("localCategoryService")
    private ILocalCategoryService localCategoryService;

    @Autowired
    @Qualifier("localCustomerService")
    private ILocalCustomerService localCustomerService;

    @Autowired
    @Qualifier("localProjectService")
    private ILocalProjectService localProjectService;

    @Autowired
    @Qualifier("localTaskService")
    private ILocalTaskService localTaskService;

    @Autowired
    @Qualifier("localRoleService")
    private ILocalRoleService localRoleService;

    @Autowired
    @Qualifier("remoteCustomerService")
    private IRemoteCustomerService remoteCustomerService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private BitronixTransactionManager btm;

    @Autowired
    @Qualifier("localDataSource")
    private DataSource localDataSource;

    // TODO: Maybe use a separate project for REMOTE DB pushing - like a service
    // API
    // OR
    // Extend dependencies of this project to create an internal Spring app with
    // remote conf...

    // @Autowired
    // @Qualifier("remoteCategoryService")
    // private IRemoteCategoryService remoteCategoryService;

    // TODO: Set if you want to dynamically use i18n...
    @FXML
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;

	// Initialize tree with 'empty' ROOT item...
	treeCustomers.setRoot(new TreeItem<DObject>());
	treeProjects.setRoot(new TreeItem<DObject>());
	treeTasks.setRoot(new TreeItem<DObject>());
	treeRoles.setRoot(new TreeItem<DObject>());

	// TODO: Test code - clean up later...
	createTestData();

	testPopulateTree(treeCustomers);
	testPopulateTree(treeProjects);
	testPopulateTree(treeTasks);
	testPopulateTree(treeRoles);
    }

    @Transactional("transactionManager")
    private void createTestData() {
	final DCategory defCat = new DCategory();
	defCat.setName("DEFAULT");
	defCat.setSortOrder(1);

	final DCategory custCat = new DCategory();
	custCat.setName("CUSTOM");
	custCat.setSortOrder(2);

	final DCustomer uncategorizedCustomer = new DCustomer();
	uncategorizedCustomer.setName("PlainCompany");
	uncategorizedCustomer.setDescription("Just a regular company");
	defCat.addCategoricalEntity(uncategorizedCustomer);

	final DCustomer categorizedCustomer = new DCustomer();
	categorizedCustomer.setName("LegitCompany");
	categorizedCustomer.setDescription("A legitimate company");
	custCat.addCategoricalEntity(categorizedCustomer);

	// TODO: Return Optional<DCustomer> for all add methods...

	// TODO: Does not do shit... no resource enlisted for tracking...
	try {
	    BitronixTransaction currentTransaction = btm.getCurrentTransaction();
	    currentTransaction = currentTransaction != null ? currentTransaction
		    : (BitronixTransaction) btm.getTransaction();

	    // currentTransaction.enlist(localDataSource);

	    btm.begin();

	    // final TransactionStatus t =
	    // transactionManager.getTransaction(null);
	    // final Object savePoint = t.createSavepoint();
	    // if (t.isNewTransaction() || !t.isCompleted()) {
	    final boolean lc1_OK = localCustomerService.addCustomer(categorizedCustomer);
	    final boolean rc1_OK = remoteCustomerService.addCustomer(categorizedCustomer);
	    if (!lc1_OK || !rc1_OK) {
		btm.rollback();
		// transactionManager.rollback(t);
		// t.rollbackToSavepoint(savePoint);
		// t.releaseSavepoint(savePoint);
		return;
	    }

	    final boolean lc2_OK = localCustomerService.addCustomer(uncategorizedCustomer);
	    final boolean rc2_OK = remoteCustomerService.addCustomer(uncategorizedCustomer);
	    if (!lc2_OK || !rc2_OK) {
		btm.rollback();
		// transactionManager.rollback(t);
		// t.rollbackToSavepoint(savePoint);
		// t.releaseSavepoint(savePoint);
		return;
	    }

	    // transactionManager.commit(t);
	    // }

	    btm.commit();
	} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
		| HeuristicMixedException | HeuristicRollbackException e) {
	    e.printStackTrace();
	}

	// TODO: OLD but working...
	// final boolean lc1_OK =
	// localCustomerService.addCustomer(categorizedCustomer);
	// final boolean rc1_OK =
	// remoteCustomerService.addCustomer(categorizedCustomer);
	//
	// final boolean lc2_OK =
	// localCustomerService.addCustomer(uncategorizedCustomer);
	// final boolean rc2_OK =
	// remoteCustomerService.addCustomer(uncategorizedCustomer);
    }

    private void testPopulateTree(final TreeView<DObject> tree) {
	// Keep the sorter idea - works nicely...
	final List<DCategory> categories = localCategoryService.getCategories();
	categories.sort(Comparator.comparing(DCategory::getSortOrder).thenComparing(DCategory::getName));

	final ObservableList<TreeItem<DObject>> categoryLevel = tree.getRoot().getChildren();
	final boolean categoriesAppended = categoryLevel.addAll(categories.stream() // nl
		.map(TreeItem<DObject>::new) // nl
		.collect(Collectors.toList()));
	if (categoriesAppended) {
	    for (TreeItem<DObject> catNode : categoryLevel) {
		catNode.setExpanded(true);

		final DCategory catObj = (DCategory) catNode.getValue();

		List<? extends DObject> subEntities = null;
		if (Objects.equals(tree.getId(), "treeCustomers")) {
		    subEntities = localCustomerService.getCustomers(catObj);
		} else if (Objects.equals(tree.getId(), "treeProjects")) {
		    subEntities = localProjectService.getProjects(catObj);
		} else if (Objects.equals(tree.getId(), "treeTasks")) {
		    subEntities = localTaskService.getTasks(catObj);
		} else if (Objects.equals(tree.getId(), "treeRoles")) {
		    subEntities = localRoleService.getRoles(catObj);
		}
		subEntities.stream() // nl
			.map(TreeItem<DObject>::new) // nl
			.forEach(catNode.getChildren()::add);
	    }
	}

	tree.getSelectionModel().selectedItemProperty().addListener(this::showEntityAttributes);
    }

    private void showEntityAttributes(ObservableValue<? extends TreeItem<DObject>> observable,
	    TreeItem<DObject> old_val, TreeItem<DObject> new_val) {
	final ObservableList<Node> entityAttrList = stackEntityAttributes.getChildren();
	entityAttrList.clear();

	final DObject newValueObj = new_val.getValue();
	if (newValueObj instanceof DCustomer) {
	    DCustomer custObj = (DCustomer) newValueObj;
	    entityAttrList.add(createHBox(new Label("Name: "), new TextField(custObj.getName())));
	    entityAttrList.add(createHBox(new Label("Description: "), new TextField(custObj.getDescription())));
	} else if (newValueObj instanceof DProject) {
	    DProject projObj = (DProject) newValueObj;
	    entityAttrList.add(new HBox(new Label("Name: "), new TextField(projObj.getName())));
	    entityAttrList.add(new HBox(new Label("Description: "), new TextField(projObj.getDescription())));
	} else if (newValueObj instanceof DTask) {
	    DTask taskObj = (DTask) newValueObj;
	    entityAttrList.add(new HBox(new Label("Name: "), new TextField(taskObj.getName())));
	    entityAttrList.add(new HBox(new Label("Description: "), new TextField(taskObj.getDescription())));
	    entityAttrList.add(new HBox(new Label("Hours estimated: "),
		    new TextField(String.valueOf(taskObj.getHoursEstimated()))));
	} else if (newValueObj instanceof DRole) {
	    DRole roleObj = (DRole) newValueObj;
	    entityAttrList.add(new HBox(new Label("Name: "), new TextField(roleObj.getName())));
	    entityAttrList.add(new HBox(new Label("Description: "), new TextField(roleObj.getDescription())));
	    entityAttrList.add(new HBox(new Label("Rate: "), new TextField(String.valueOf(roleObj.getBillingRate()))));
	}
    }

    private HBox createHBox(Label label, TextField textField) {
	final HBox hBox = new HBox();
	hBox.setFillHeight(true);
	hBox.setManaged(true);
	hBox.setPadding(new Insets(5));
	hBox.setSpacing(5);
	// hBox.setUserData(value);

	hBox.getChildren().addAll(label, textField);

	return hBox;
    }
}
