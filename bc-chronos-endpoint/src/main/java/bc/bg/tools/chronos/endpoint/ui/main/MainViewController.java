package bc.bg.tools.chronos.endpoint.ui.main;

import java.io.Serializable;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.NestedRuntimeException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.endpoint.ui.actions.EntityAction;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalPerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import bg.bc.tools.chronos.dataprovider.i18n.IMessageService;
import bg.bc.tools.chronos.dataprovider.utilities.EntityHelper;
import bitronix.tm.resource.jdbc.lrc.LrcXADataSource;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

    @FXML
    private Menu menuFile;

    @FXML
    private TreeView<Object> treeCustomers;

    @FXML
    private TreeView<Object> treeProjects;

    @FXML
    private TreeView<Object> treeTasks;

    @FXML
    private TreeView<Object> treeRoles;

    @FXML
    private TitledPane titlePaneCustomer;

    @FXML
    private TitledPane titlePaneProject;

    @FXML
    private TitledPane titlePaneTask;

    @FXML
    private TitledPane titlePaneRole;

    @FXML
    private VBox stackEntityAttributes;

    // TODO: This is being set in case you want to later do manual i18n...
    @FXML
    private ResourceBundle resources;
    //

    @SuppressWarnings("unused")
    @Autowired
    private IMessageService messageService;

    // @Autowired
    // @Qualifier("localCategoryService")
    // private ILocalCategoryService localCategoryService;
    //
    // @Autowired
    // @Qualifier("localCustomerService")
    // private ILocalCustomerService localCustomerService;
    //
    // @Autowired
    // @Qualifier("localProjectService")
    // private ILocalProjectService localProjectService;
    //
    // @Autowired
    // @Qualifier("localTaskService")
    // private ILocalTaskService localTaskService;
    //
    // @Autowired
    // @Qualifier("localRoleService")
    // private ILocalRoleService localRoleService;
    //
    // @Autowired
    // @Qualifier("remoteCustomerService")
    // private IRemoteCustomerService remoteCustomerService;

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

    @Autowired
    private LocalChangelogRepository changelogRepo;

    @Autowired
    private LocalRoleRepository roleRepo;

    @Autowired
    private LocalBookingRepository bookingRepo;

    @Autowired
    private LocalPerformerRepository performerRepo;
    //

    //
    // private Map<EntityAction<Category, ?>, Button> categoryActions;

    private List<EntityAction<Category>> categoryActions;
    //

    @FXML
    private Label loggedUserLabel;

    @FXML
    private Tab tabPerformers;

    @FXML
    private Tab tabReporting;

    @FXML
    private TabPane tabPaneMain;

    private Performer loggedPerformer;

    private TreeItem<Object> selectedCategoryNode;

    @FXML
    private VBox actionButtonBar;

    public Performer getLoggedPerformer() {
	return loggedPerformer;
    }

    public void setLoggedPerformer(Performer loggedPerformer) {
	this.loggedPerformer = loggedPerformer;
    }

    public void loginAs(Performer loggedPerformer) {
	setLoggedPerformer(loggedPerformer);

	loggedUserLabel.setText(loggedUserLabel.getText() + " " + loggedPerformer);

	if (loggedPerformer.getPriviledges().contains(Priviledge.ALL)) {
	    tabPaneMain.getTabs().remove(tabPerformers);
	    tabPaneMain.getTabs().remove(tabReporting);
	}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;

	// Initialize tree with 'empty' ROOT item...
	treeCustomers.setRoot(new TreeItem<Object>());
	treeProjects.setRoot(new TreeItem<Object>());
	treeTasks.setRoot(new TreeItem<Object>());
	treeRoles.setRoot(new TreeItem<Object>());

	// TODO: Test code - clean up later...
	createTestData();
	//

	testPopulateTree(treeCustomers);
	testPopulateTree(treeProjects);
	testPopulateTree(treeTasks);
	testPopulateTree(treeRoles);
	//

	loadCategoryActions();
    }

    @Transactional("transactionManager")
    private void createTestData() {
	transactionTemplate.execute(txStatus -> {
	    return persistDbEntities();
	});

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

	// Also works - only displays weird in DB...
	// transactionTemplate.execute(txStatus -> {
	// final Customer cu =
	// customerRepo.findByName(categorizedCustomer.getName());
	//
	// if (cu != null) {
	// final Collection<Changelog> ffs =
	// changelogRepo.findByUpdatedEntityKey(cu.getSyncKey());
	// return (!ffs.isEmpty() && ffs.size() == 1);
	// }
	//
	// return false;
	// });

	// TODO: Return Optional<DCustomer> for all add* service methods...

	// try {
	// Statement statement =
	// lrcLocalDataSource.getXAConnection().getConnection().createStatement();
	// statement.execute("PRAGMA journal_mode = WAL;");
	// statement.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
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

	final DCustomer categorizedCustomer = new DCustomer();
	categorizedCustomer.setSyncKey(UUID.randomUUID().toString());
	categorizedCustomer.setName("LegitCompany");
	categorizedCustomer.setDescription("A legitimate company");

	final DProject smallProject = new DProject();
	smallProject.setSyncKey(UUID.randomUUID().toString());
	smallProject.setName("SmallProject");
	smallProject.setDescription("A small project");

	final DProject grandProject = new DProject();
	grandProject.setSyncKey(UUID.randomUUID().toString());
	grandProject.setName("GrandProject");
	grandProject.setDescription("A grand project");

	final DTask lenientTask = new DTask();
	lenientTask.setSyncKey(UUID.randomUUID().toString());
	lenientTask.setDescription("A low prior task");
	lenientTask.setName("Lenient task");
	lenientTask.setHoursEstimated(15);

	final DTask urgentTask = new DTask();
	urgentTask.setSyncKey(UUID.randomUUID().toString());
	urgentTask.setDescription("A high prior task");
	urgentTask.setName("Urgent task");
	urgentTask.setHoursEstimated(5);

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

	final Customer categorizedCustomer = new Customer();
	categorizedCustomer.setSyncKey(UUID.randomUUID().toString());
	categorizedCustomer.setName("LegitCompany");
	categorizedCustomer.setDescription("A legitimate company");

	final Project smallProject = new Project();
	smallProject.setSyncKey(UUID.randomUUID().toString());
	smallProject.setName("SmallProject");
	smallProject.setDescription("A small project");

	final Project grandProject = new Project();
	grandProject.setSyncKey(UUID.randomUUID().toString());
	grandProject.setName("GrandProject");
	grandProject.setDescription("A grand project");

	final Task lenientTask = new Task();
	lenientTask.setSyncKey(UUID.randomUUID().toString());
	lenientTask.setDescription("A low prior task");
	lenientTask.setName("Lenient task");
	lenientTask.setHoursEstimated(15);

	final Task urgentTask = new Task();
	urgentTask.setSyncKey(UUID.randomUUID().toString());
	urgentTask.setDescription("A high prior task");
	urgentTask.setName("Urgent task");
	urgentTask.setHoursEstimated(5);

	try {
	    final Category mDefCat = categoryRepo.save(defaultCategory);
	    appendChangelog(mDefCat.getSyncKey());

	    final Category mCustCat = categoryRepo.save(customCategory);
	    appendChangelog(mCustCat.getSyncKey());

	    uncategorizedCustomer.setCategory(mDefCat);
	    categorizedCustomer.setCategory(mCustCat);

	    final Customer mUncatCust = customerRepo.save(uncategorizedCustomer);
	    appendChangelog(mUncatCust.getSyncKey());

	    final Customer mCatCust = customerRepo.save(categorizedCustomer);
	    appendChangelog(mCatCust.getSyncKey());

	    smallProject.setCategory(mCustCat);
	    smallProject.setCustomer(mCatCust);

	    grandProject.setCategory(mCustCat);
	    grandProject.setCustomer(mCatCust);

	    final Project mSmallProj = projectRepo.save(smallProject);
	    appendChangelog(mSmallProj.getSyncKey());

	    final Project mGrandProj = projectRepo.save(grandProject);
	    appendChangelog(mGrandProj.getSyncKey());

	    lenientTask.setCategory(mDefCat);
	    lenientTask.setProject(mSmallProj);

	    urgentTask.setCategory(mDefCat);
	    urgentTask.setProject(mGrandProj);

	    final Task mLenTask = taskRepo.save(lenientTask);
	    appendChangelog(mLenTask.getSyncKey());

	    final Task mUrgeTask = taskRepo.save(urgentTask);
	    appendChangelog(mUrgeTask.getSyncKey());

	} catch (NestedRuntimeException jpaNestedExc) {
	    ExceptionUtils.printRootCauseStackTrace(jpaNestedExc);
	} catch (Exception ex) {
	    ExceptionUtils.printRootCauseStackTrace(ex);
	    return false;
	}

	return true;
    }

    private void appendChangelog(String entitySyncKey) {
	final Changelog changeLog = new Changelog();
	changeLog.setChangeTime(Calendar.getInstance().getTime());
	changeLog.setDeviceName(EntityHelper.getComputerName());
	changeLog.setUpdatedEntityKey(entitySyncKey);

	changelogRepo.save(changeLog);
    }

    // https://github.com/tomoTaka01/FileTreeViewSample/blob/master/src/filetreeviewsample/FileTreeViewSample.java
    private void testPopulateTree(final TreeView<Object> tree) {
	final List<Category> categories = ((List<Category>) categoryRepo.findAll());
	// TODO; Keep the sorter idea - works nicely...
	categories.sort( // nl
		Comparator.comparing(Category::getSortOrder) // nl
			.thenComparing(Category::getName) // nl
	);
	//

	final ObservableList<TreeItem<Object>> categoryLevel = tree.getRoot().getChildren();
	final boolean categoriesAppended = categoryLevel.addAll(categories.stream() // nl
		.map(TreeItem<Object>::new) // nl
		.collect(Collectors.toList()));
	if (categoriesAppended) {
	    for (TreeItem<Object> catNode : categoryLevel) {
		catNode.setExpanded(true);

		final Category catObj = (Category) catNode.getValue();

		Collection<?> subEntities = null;
		if (Objects.equals(tree.getId(), "treeCustomers")) {
		    subEntities = customerRepo.findByCategory(catObj);
		} else if (Objects.equals(tree.getId(), "treeProjects")) {
		    subEntities = projectRepo.findByCategory(catObj);
		} else if (Objects.equals(tree.getId(), "treeTasks")) {
		    subEntities = taskRepo.findByCategory(catObj);
		} else if (Objects.equals(tree.getId(), "treeRoles")) {
		    subEntities = roleRepo.findByCategory(catObj);
		}
		subEntities.stream() // nl
			.map(TreeItem<Object>::new) // nl
			.forEach(catNode.getChildren()::add);
	    }
	}

	tree.getSelectionModel().selectedItemProperty().addListener(this::showEntityAttributes);
    }

    private void showEntityAttributes(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> old_val,
	    TreeItem<Object> new_val) {
	final ObservableList<Node> entityAttrList = stackEntityAttributes.getChildren();
	entityAttrList.clear();

	final Object newValueObj = new_val.getValue();
	if (newValueObj instanceof Category) {
	    Category catObj = (Category) newValueObj;
	    entityAttrList.add(createHBox(new Label("Name: "), new TextField(catObj.getName())));
	    entityAttrList
		    .add(createHBox(new Label("Sort order: "), new TextField(String.valueOf(catObj.getSortOrder()))));

	    selectedCategoryNode = new_val;
	    showCategoryActions();
	    // titlePaneCustomer.setText(MessageFormat
	    // .format(resources.getString("view.main.tab.workspace.entity.customer.title"),
	    // custObj.getName()));

	} else if (newValueObj instanceof Customer) {
	    Customer custObj = (Customer) newValueObj;
	    entityAttrList.add(createHBox(new Label("Name: "), new TextField(custObj.getName())));
	    entityAttrList.add(createHBox(new Label("Description: "), new TextField(custObj.getDescription())));

	    titlePaneCustomer.setText(MessageFormat
		    .format(resources.getString("view.main.tab.workspace.entity.customer.title"), custObj.getName()));

	} else if (newValueObj instanceof Project) {
	    Project projObj = (Project) newValueObj;
	    entityAttrList.add(new HBox(new Label("Name: "), new TextField(projObj.getName())));
	    entityAttrList.add(new HBox(new Label("Description: "), new TextField(projObj.getDescription())));

	    titlePaneProject.setText(MessageFormat
		    .format(resources.getString("view.main.tab.workspace.entity.project.title"), projObj.getName()));
	} else if (newValueObj instanceof Task) {
	    Task taskObj = (Task) newValueObj;
	    entityAttrList.add(new HBox(new Label("Name: "), new TextField(taskObj.getName())));
	    entityAttrList.add(new HBox(new Label("Description: "), new TextField(taskObj.getDescription())));
	    entityAttrList.add(new HBox(new Label("Hours estimated: "),
		    new TextField(String.valueOf(taskObj.getHoursEstimated()))));

	    titlePaneTask.setText(MessageFormat.format(resources.getString("view.main.tab.workspace.entity.task.title"),
		    taskObj.getName()));
	} else if (newValueObj instanceof Role) {
	    Role roleObj = (Role) newValueObj;
	    entityAttrList.add(new HBox(new Label("Name: "), new TextField(roleObj.getName())));
	    entityAttrList.add(new HBox(new Label("Description: "), new TextField(roleObj.getDescription())));
	    entityAttrList.add(new HBox(new Label("Rate: "), new TextField(String.valueOf(roleObj.getBillingRate()))));

	    titlePaneRole.setText(MessageFormat.format(resources.getString("view.main.tab.workspace.entity.role.title"),
		    roleObj.getName()));
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

    private Button generateButtonForAction(EntityAction<? extends Serializable> action) {
	Button actionButton = new Button();
	actionButton.setGraphic(new ImageView(new Image("/images/" + action.getActionIconName())));
	actionButton.setMnemonicParsing(false);
	actionButton.setOnMouseClicked(evt -> {
	    action.execute(selectedCategoryNode.getValue());
	});

	return actionButton;
    }

    // http://code.makery.ch/blog/javafx-8-event-handling-examples/
    // https://stackoverflow.com/questions/10518458/javafx-create-custom-button-with-image
    private void loadCategoryActions() {
	final Class<Category> cls = Category.class;

	categoryActions = new ArrayList<EntityAction<Category>>();
	categoryActions.add(new EntityAction<Category>(cls, "refresh_icon.png") // nl
		.requiredPriviledges(Arrays.asList(Priviledge.READ)) // nl
		.action(this::refreshCategory));

	// categoryActions.add(new EntityAction<Category>(cls,
	// "add_icon").requiredPriviledge(Priviledge.WRITE));
	// categoryActions.add(new EntityAction<Category>(cls,
	// "update_icon").requiredPriviledge(Priviledge.WRITE));
	// categoryActions.add(new EntityAction<Category>(cls,
	// "remove_icon").requiredPriviledge(Priviledge.DELETE));
	// categoryActions.add(new EntityAction<Category>(cls,
	// "upDownOrder_icon").requiredPriviledge(Priviledge.WRITE));
    }

    public void showCategoryActions() {
	categoryActions.forEach(a -> {
	    // Move to somewhere else...
	    actionButtonBar.getChildren().clear();
	    //

	    // TODO: DISABLE / ENABLE THIS...
	    // transactionTemplate.execute(tx -> {
	    // final Customer sneakyCust = new Customer();
	    // sneakyCust.setName("Sneaky Company");
	    // sneakyCust.setSyncKey(UUID.randomUUID().toString());
	    // sneakyCust.setDescription("A sneaky company appears...");
	    // sneakyCust.setCategory((Category)
	    // selectedCategoryNode.getValue());
	    //
	    // customerRepo.save(sneakyCust);
	    // return 0;
	    // });
	    //

	    if (a.isVisibleToUser(loggedPerformer)) {
		actionButtonBar.getChildren().add(generateButtonForAction(a));
	    }
	});
    }

    private void refreshCategory(Category category) {
	transactionTemplate.execute(tx -> {
	    final Category refreshedCategory = categoryRepo.findOne(category.getId());
	    selectedCategoryNode.setValue(refreshedCategory);

	    // Update property display
	    final ObservableList<Node> entityAttrList = stackEntityAttributes.getChildren();
	    entityAttrList.clear();
	    entityAttrList.add(createHBox(new Label("Name: "), new TextField(refreshedCategory.getName())));
	    entityAttrList.add(createHBox(new Label("Sort order: "),
		    new TextField(String.valueOf(refreshedCategory.getSortOrder()))));

	    // Update sub-entities
	    Optional<Collection<?>> subEntities = Optional.empty();
	    if (!treeCustomers.getSelectionModel().isEmpty()) {
		// final FilteredList<TreeItem<Object>> filtered =
		// selectedCategoryNode.getChildren()
		// .filtered(n -> n.getValue() instanceof Customer)
		// .filtered(n -> ObjectUtils.equals(((Customer)
		// n.getValue()).getCategory().getName(),
		// refreshedCategory.getName()));
		// System.out.println(filtered);
		// treeCustomers.getRoot().getChildren().removeIf(n ->
		// n.getValue() instanceof Customer && ObjectUtils
		// .equals(((Customer) n.getValue()).getCategory().getName(),
		// refreshedCategory.getName()));
		selectedCategoryNode.getChildren().clear();
		subEntities = Optional.ofNullable(customerRepo.findByCategory(refreshedCategory));
	    } else if (!treeRoles.getSelectionModel().isEmpty()) {
		subEntities = Optional.ofNullable(projectRepo.findByCategory(refreshedCategory));
	    } else if (!treeProjects.getSelectionModel().isEmpty()) {
		subEntities = Optional.ofNullable(taskRepo.findByCategory(refreshedCategory));
	    } else if (!treeTasks.getSelectionModel().isEmpty()) {
		subEntities = Optional.ofNullable(roleRepo.findByCategory(refreshedCategory));
	    }
	    subEntities.ifPresent(list -> {
		list.stream().map(TreeItem<Object>::new) // nl
			.forEach(selectedCategoryNode.getChildren()::add);
	    });

	    // ffs???
	    return 0;
	});
    }

}
