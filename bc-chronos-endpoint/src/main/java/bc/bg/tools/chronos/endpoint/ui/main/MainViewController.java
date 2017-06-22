package bc.bg.tools.chronos.endpoint.ui.main;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.NestedRuntimeException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DObject;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalRoleService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalTaskService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteCustomerService;
import bitronix.tm.resource.jdbc.lrc.LrcXADataSource;
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

    // TODO: This is being set in case you want to later do manual i18n...
    @FXML
    private ResourceBundle resources;
    //

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

    // TODO: First manual transaction attempt - UserTransaction...
    // @Autowired
    // private BitronixTransactionManager btm;

    @Autowired
    public PlatformTransactionManager transactionManager;

    @Autowired
    @Qualifier("lrcLocalDataSource")
    private LrcXADataSource lrcLocalDataSource;

    // TODO: Consider swapping remote LRC source with SQL server XA(like below)
    // https://github.com/bitronix/btm/blob/master/btm-docs/src/main/asciidoc/LastResourceCommit2x.adoc
    @Autowired
    @Qualifier("lrcRemoteDataSource")
    private LrcXADataSource lrcRemoteDataSource;

    // https://github.com/bitronix/btm/blob/master/btm-docs/src/main/asciidoc/JdbcXaSupportEvaluation.adoc#msql
    // @Autowired
    // @Qualifier("lrcRemoteDataSource")
    // private JtdsDataSource lrcRemoteDataSource;

    // TODO: Needed for manual transaction approach...
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private LocalTaskRepository taskRepo;

    @Autowired
    private LocalProjectRepository projectRepo;

    @Autowired
    private LocalCustomerRepository customerRepo;

    @Autowired
    private LocalCategoryRepository categoryRepo;
    //

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

	// testPopulateTree(treeCustomers);
	// testPopulateTree(treeProjects);
	// testPopulateTree(treeTasks);
	// testPopulateTree(treeRoles);
	//
    }

    @Transactional("transactionManager")
    private void createTestData() {
	final Category defCat = new Category();
	defCat.setName("DEFAULT");
	defCat.setSortOrder(1);
	//
	defCat.setSyncKey(UUID.randomUUID().toString());
	//

	final Category custCat = new Category();
	custCat.setName("CUSTOM");
	custCat.setSortOrder(2);
	//
	custCat.setSyncKey(UUID.randomUUID().toString());
	//

	final Customer uncategorizedCustomer = new Customer();
	uncategorizedCustomer.setName("PlainCompany");
	uncategorizedCustomer.setDescription("Just a regular company");
	//
	uncategorizedCustomer.setCategory(defCat);
	uncategorizedCustomer.setSyncKey(UUID.randomUUID().toString());
	//
	// defCat.addCategoricalEntity(uncategorizedCustomer);

	final Customer categorizedCustomer = new Customer();
	categorizedCustomer.setName("LegitCompany");
	categorizedCustomer.setDescription("A legitimate company");
	//
	categorizedCustomer.setCategory(custCat);
	categorizedCustomer.setSyncKey(UUID.randomUUID().toString());
	//
	// custCat.addCategoricalEntity(categorizedCustomer);

	// TODO: This might be the way to go when you want to perform
	// transaction manually...
	// transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	// https://github.com/bitronix/btm/blob/master/btm-docs/src/main/asciidoc/JtaBestPractices.adoc
	// final Boolean txPassed = transactionTemplate.execute(a ->
	// localCustomerService.addCustomer(categorizedCustomer)
	// && remoteCustomerService.addCustomer(categorizedCustomer)
	// && localCustomerService.addCustomer(uncategorizedCustomer)
	// && remoteCustomerService.addCustomer(uncategorizedCustomer));
	//
	// System.err.println("\n\n\n\tTX PASSED SUCESSFULLY :: " + txPassed +
	// "\n\n\n");

	// TODO: First manual transaction attempt - UserTransaction...
	// try {
	// System.err.println("\n\n\n\n\n\tBEFORE BEGIN TRANSACTION\n\n\n\n\n");
	// btm.begin();
	// System.err.println("\n\n\n\n\n\tBEFORE CALL DAO\n\n\n\n\n");
	// final boolean lc1_OK =
	// localCustomerService.addCustomer(categorizedCustomer);
	// final boolean rc1_OK =
	// remoteCustomerService.addCustomer(categorizedCustomer);
	// if (!lc1_OK || !rc1_OK) {
	// System.err.println("\n\n\n\n\n\tBEFORE ROLL BACK\n\n\n\n\n");
	// btm.rollback();
	// return;
	// }
	//
	// final boolean lc2_OK =
	// localCustomerService.addCustomer(uncategorizedCustomer);
	// final boolean rc2_OK =
	// remoteCustomerService.addCustomer(uncategorizedCustomer);
	// if (!lc2_OK || !rc2_OK) {
	// System.err.println("\n\n\n\n\n\tBEFORE ROLL BACK\n\n\n\n\n");
	// btm.rollback();
	// return;
	// }
	//
	// System.err.println("\n\n\n\n\n\tBEFORE COMMIT
	// TRANSACTION\n\n\n\n\n");
	// btm.commit();
	// } catch (NotSupportedException | SystemException | SecurityException
	// | IllegalStateException | RollbackException
	// | HeuristicMixedException | HeuristicRollbackException e) {
	// e.printStackTrace();
	// }

	final Project smallProject = new Project();
	smallProject.setName("SmallProject");
	smallProject.setDescription("A small project");
	//
	smallProject.setSyncKey(UUID.randomUUID().toString());
	smallProject.setCategory(custCat);
	smallProject.setCustomer(categorizedCustomer);
	//
	// custCat.addCategoricalEntity(smallProject);
	// categorizedCustomer.addProject(smallProject);

	final Project grandProject = new Project();
	grandProject.setName("GrandProject");
	grandProject.setDescription("A grand project");
	//
	grandProject.setSyncKey(UUID.randomUUID().toString());
	grandProject.setCategory(custCat);
	grandProject.setCustomer(categorizedCustomer);
	//
	// custCat.addCategoricalEntity(grandProject);
	// categorizedCustomer.addProject(grandProject);

	final Task lenientTask = new Task();
	lenientTask.setDescription("A low prior task");
	lenientTask.setName("Lenient task");
	lenientTask.setHoursEstimated(15);
	//
	lenientTask.setSyncKey(UUID.randomUUID().toString());
	lenientTask.setCategory(defCat);
	lenientTask.setProject(smallProject);
	//
	// custCat.addCategoricalEntity(lenientTask);
	// smallProject.addTask(lenientTask);

	final Task urgentTask = new Task();
	urgentTask.setDescription("A high prior task");
	urgentTask.setName("Urgent task");
	urgentTask.setHoursEstimated(5);
	//
	urgentTask.setSyncKey(UUID.randomUUID().toString());
	urgentTask.setCategory(defCat);
	urgentTask.setProject(grandProject);
	//
	// custCat.addCategoricalEntity(urgentTask);
	// grandProject.addTask(urgentTask);

	transactionTemplate.execute(txStatus -> {
	    return persistDomainEntities();
	    // return persistDbEntities();
	});

	// TODO: Return Optional<DCustomer> for all add* service methods...
	// transactionTemplate.execute(a ->
	// localCategoryService.addCategory(defCat) // nl
	// && localCategoryService.addCategory(custCat) // nl
	// );

	// localCategoryService.addCategory(defCat);
	// localCategoryService.addCategory(custCat);

	// transactionTemplate.execute(a ->
	// localCustomerService.addCustomer(categorizedCustomer) // nl
	// && localCustomerService.addCustomer(uncategorizedCustomer) // nl
	// );

	// final DCustomer addedCatCust =
	// localCustomerService.addCustomer(categorizedCustomer);
	// final DCustomer addedUncatCust =
	// localCustomerService.addCustomer(uncategorizedCustomer);

	// addedCatCust.addProject(smallProject);
	// final DCategory theCat =
	// localCategoryService.getCategory(addedCatCust.getCategory().getName());
	// theCat.addCategoricalEntity(smallProject);
	//
	// localCategoryService.updateCategory(theCat);

	// transactionTemplate.execute(a ->
	// localProjectService.addProject(smallProject) // nl
	// && localProjectService.addProject(grandProject) // nl
	// );

	// smallProject.setCategory(custCat);
	// smallProject.setCustomer(addedCatCust);

	// localProjectService.addProject(smallProject);
	// localProjectService.addProject(grandProject);

	// transactionTemplate.execute(a ->
	// localTaskService.addTask(lenientTask) // nl
	// && localTaskService.addTask(urgentTask) // nl
	// );

	// localTaskService.addTask(lenientTask);
	// localTaskService.addTask(urgentTask);
    }

    public Boolean persistDomainEntities() {
	final DCategory defaultCategory = new DCategory();
	defaultCategory.setSyncKey(UUID.randomUUID().toString());
	defaultCategory.setName("DEFAULT");
	defaultCategory.setSortOrder(1);

	final DCategory customCategory = new DCategory();
	customCategory.setSyncKey(UUID.randomUUID().toString());
	customCategory.setName("CUSTOM");
	customCategory.setSortOrder(2);

	final DCustomer uncategorizedCustomer = new DCustomer();
	uncategorizedCustomer.setSyncKey(UUID.randomUUID().toString());
	uncategorizedCustomer.setName("PlainCompany");
	uncategorizedCustomer.setDescription("Just a regular company");
	// uncategorizedCustomer.setCategory(defaultCategory);

	final DCustomer categorizedCustomer = new DCustomer();
	categorizedCustomer.setSyncKey(UUID.randomUUID().toString());
	categorizedCustomer.setName("LegitCompany");
	categorizedCustomer.setDescription("A legitimate company");
	// categorizedCustomer.setCategory(customCategory);

	final DProject smallProject = new DProject();
	smallProject.setSyncKey(UUID.randomUUID().toString());
	smallProject.setName("SmallProject");
	smallProject.setDescription("A small project");
	// smallProject.setCategory(customCategory);
	// smallProject.setCustomer(categorizedCustomer);
	//// categorizedCustomer.addProject(smallProject);

	final DProject grandProject = new DProject();
	grandProject.setSyncKey(UUID.randomUUID().toString());
	grandProject.setName("GrandProject");
	grandProject.setDescription("A grand project");
	// grandProject.setCategory(customCategory);
	// grandProject.setCustomer(categorizedCustomer);
	// categorizedCustomer.addProject(grandProject);

	final DTask lenientTask = new DTask();
	lenientTask.setSyncKey(UUID.randomUUID().toString());
	lenientTask.setDescription("A low prior task");
	lenientTask.setName("Lenient task");
	lenientTask.setHoursEstimated(15);
	// lenientTask.setCategory(defaultCategory);
	// lenientTask.setProject(smallProject);

	final DTask urgentTask = new DTask();
	urgentTask.setSyncKey(UUID.randomUUID().toString());
	urgentTask.setDescription("A high prior task");
	urgentTask.setName("Urgent task");
	urgentTask.setHoursEstimated(5);
	// urgentTask.setCategory(defaultCategory);
	// urgentTask.setProject(grandProject);

	try {
	    // NOT WORKING - no ID maybe...
	    // categoryRepo.save(Arrays.asList(DomainToDbMapper.domainToDbCategory(defaultCategory),
	    // DomainToDbMapper.domainToDbCategory(customCategory)));
	    //
	    // customerRepo.save(Arrays.asList(DomainToDbMapper.domainToDbCustomer(uncategorizedCustomer),
	    // DomainToDbMapper.domainToDbCustomer(categorizedCustomer)));
	    //
	    // projectRepo.save(Arrays.asList(DomainToDbMapper.domainToDbProject(smallProject),
	    // DomainToDbMapper.domainToDbProject(grandProject)));
	    //
	    // taskRepo.save(Arrays.asList(DomainToDbMapper.domainToDbTask(lenientTask),
	    // DomainToDbMapper.domainToDbTask(urgentTask)));

	    // WORKING - ID is set this way...
	    final Category persistedDefaultCategory = DomainToDbMapper.domainToDbCategory(defaultCategory);
	    final Category persistedCustomCategory = DomainToDbMapper.domainToDbCategory(customCategory);
	    categoryRepo.save(Arrays.asList(persistedDefaultCategory, persistedCustomCategory));

	    final Customer persistedUncategorizedCustomer = DomainToDbMapper.domainToDbCustomer(uncategorizedCustomer);
	    persistedUncategorizedCustomer.setCategory(persistedDefaultCategory);
	    final Customer persistedCategorizedCustomer = DomainToDbMapper.domainToDbCustomer(categorizedCustomer);
	    persistedCategorizedCustomer.setCategory(persistedCustomCategory);
	    customerRepo.save(Arrays.asList(persistedUncategorizedCustomer, persistedCategorizedCustomer));

	    final Project persistedSmallProject = DomainToDbMapper.domainToDbProject(smallProject);
	    persistedSmallProject.setCategory(persistedCustomCategory);
	    persistedSmallProject.setCustomer(persistedCategorizedCustomer);
	    final Project persistedGrandProject = DomainToDbMapper.domainToDbProject(grandProject);
	    persistedGrandProject.setCategory(persistedCustomCategory);
	    persistedGrandProject.setCustomer(persistedCategorizedCustomer);
	    projectRepo.save(Arrays.asList(persistedSmallProject, persistedGrandProject));

	    final Task persistedLenientTask = DomainToDbMapper.domainToDbTask(lenientTask);
	    persistedLenientTask.setCategory(persistedCustomCategory);
	    persistedLenientTask.setProject(persistedSmallProject);
	    final Task persistedUrgentTask = DomainToDbMapper.domainToDbTask(urgentTask);
	    persistedUrgentTask.setCategory(persistedCustomCategory);
	    persistedUrgentTask.setProject(persistedGrandProject);
	    taskRepo.save(Arrays.asList(persistedLenientTask, persistedUrgentTask));

	} catch (NestedRuntimeException jpaNestedExc) {
	    ExceptionUtils.printRootCauseStackTrace(jpaNestedExc);
	} catch (Exception ex) {
	    ExceptionUtils.printRootCauseStackTrace(ex);
	    return false;
	}

	return true;
    }

    public Boolean persistDbEntities() {
	final Category defaultCategory = new Category();
	defaultCategory.setSyncKey(UUID.randomUUID().toString());
	defaultCategory.setName("DEFAULT");
	defaultCategory.setSortOrder(1);

	final Category customCategory = new Category();
	customCategory.setSyncKey(UUID.randomUUID().toString());
	customCategory.setName("CUSTOM");
	customCategory.setSortOrder(2);

	final Customer uncategorizedCustomer = new Customer();
	uncategorizedCustomer.setSyncKey(UUID.randomUUID().toString());
	uncategorizedCustomer.setName("PlainCompany");
	uncategorizedCustomer.setDescription("Just a regular company");
	uncategorizedCustomer.setCategory(defaultCategory);

	final Customer categorizedCustomer = new Customer();
	categorizedCustomer.setSyncKey(UUID.randomUUID().toString());
	categorizedCustomer.setName("LegitCompany");
	categorizedCustomer.setDescription("A legitimate company");
	categorizedCustomer.setCategory(customCategory);

	final Project smallProject = new Project();
	smallProject.setSyncKey(UUID.randomUUID().toString());
	smallProject.setName("SmallProject");
	smallProject.setDescription("A small project");
	smallProject.setCategory(customCategory);
	smallProject.setCustomer(categorizedCustomer);

	final Project grandProject = new Project();
	grandProject.setSyncKey(UUID.randomUUID().toString());
	grandProject.setName("GrandProject");
	grandProject.setDescription("A grand project");
	grandProject.setCategory(customCategory);
	grandProject.setCustomer(categorizedCustomer);

	final Task lenientTask = new Task();
	lenientTask.setSyncKey(UUID.randomUUID().toString());
	lenientTask.setDescription("A low prior task");
	lenientTask.setName("Lenient task");
	lenientTask.setHoursEstimated(15);
	lenientTask.setCategory(defaultCategory);
	lenientTask.setProject(smallProject);

	final Task urgentTask = new Task();
	urgentTask.setSyncKey(UUID.randomUUID().toString());
	urgentTask.setDescription("A high prior task");
	urgentTask.setName("Urgent task");
	urgentTask.setHoursEstimated(5);
	urgentTask.setCategory(defaultCategory);
	urgentTask.setProject(grandProject);

	try {
	    categoryRepo.save(Arrays.asList(defaultCategory, customCategory));
	    customerRepo.save(Arrays.asList(uncategorizedCustomer, categorizedCustomer));
	    projectRepo.save(Arrays.asList(smallProject, grandProject));
	    taskRepo.save(Arrays.asList(lenientTask, urgentTask));
	} catch (NestedRuntimeException jpaNestedExc) {
	    ExceptionUtils.printRootCauseStackTrace(jpaNestedExc);
	} catch (Exception ex) {
	    ExceptionUtils.printRootCauseStackTrace(ex);
	    return false;
	}

	return true;
    }

    private void testPopulateTree(final TreeView<DObject> tree) {
	final List<DCategory> categories = localCategoryService.getCategories();
	// TODO; Keep the sorter idea - works nicely...
	categories.sort( // nl
		Comparator.comparing(DCategory::getSortOrder) // nl
			.thenComparing(DCategory::getName) // nl
	);
	//

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

	// TODO: Leverage that to pass something maybe...
	// hBox.setUserData(value);

	hBox.getChildren().addAll(label, textField);

	return hBox;
    }
}
