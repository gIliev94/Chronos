package bc.bg.tools.chronos.endpoint.ui.actions.entity.category;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.endpoint.ui.actions.EntityActionInfo;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class CategoryActionPanelController implements Initializable, ICategoryEntityActions {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="actionButtonGeneratedContainer"
    private VBox actionButtonGeneratedContainer; // Value injected by FXMLLoader

    @FXML // fx:id="btnRefreshCategory"
    private Button btnRefreshCategory; // Value injected by FXMLLoader

    @FXML // fx:id="btnModifyCategory"
    private Button btnModifyCategory; // Value injected by FXMLLoader

    @FXML // fx:id="btnMergeCategory"
    private Button btnMergeCategory; // Value injected by FXMLLoader

    @FXML // fx:id="btnHideCategory"
    private Button btnHideCategory; // Value injected by FXMLLoader

    @FXML // fx:id="btnRemoveCategory"
    private Button btnRemoveCategory; // Value injected by FXMLLoader

    @FXML // fx:id="btnAddChildForCategory"
    private Button btnAddChildForCategory; // Value injected by FXMLLoader

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

    // @Autowired
    // private LocalChangelogRepository changelogRepo;

    @Autowired
    private LocalRoleRepository roleRepo;

    // @Autowired
    // private LocalBookingRepository bookingRepo;

    // @Autowired
    // private LocalPerformerRepository performerRepo;

    // TODO: Abstract parent for EntityActionController OR NOT...
    private ICategoryActionModel categoryActionModel;

    public void setCategoryActionModel(ICategoryActionModel categoryActionModel) {
	this.categoryActionModel = categoryActionModel;
	initializeActions();
	UIHelper.removeHiddenNodesFromContainer(actionButtonGeneratedContainer);
    }
    //

    // This method is called by the FXMLLoader when initialization is complete
    @FXML
    void initialize() {
	assert actionButtonGeneratedContainer != null : "fx:id=\"actionButtonGeneratedContainer\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnRefreshCategory != null : "fx:id=\"btnRefreshCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnModifyCategory != null : "fx:id=\"btnModifyCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnMergeCategory != null : "fx:id=\"btnMergeCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnHideCategory != null : "fx:id=\"btnHideCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnRemoveCategory != null : "fx:id=\"btnRemoveCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnAddChildForCategory != null : "fx:id=\"btnAddChildForCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
    }

    // TODO: Do you need this as well as the standard init method - or just
    // merge them into one???
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;
    }

    protected void initializeActions() {
	final EntityActionInfo actionInfoRefreshCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.READ) // nl
		.actionButton(btnRefreshCategory) // nl
		// .preActions(this::merge) // nl
		.action(this::refresh) // nl
		.postActions(this::refreshDetails);
	// .postActions(this::refreshDetails, this::hide);

	// TODO: Add to parent Action controller because add will not depend on
	// whether the entity node is clicked or not...
	// final EntityActionInfo actionAddCategory = new EntityActionInfo() //
	// nl
	// .performer(categoryActionModel.getLoggedUser()) // nl
	// .requiredPriviledges(Priviledge.WRITE) // nl
	// .actionButton(btnAddCategory) // nl
	// .preActions(this::merge) // nl
	// .action(this::modify) // nl
	// .postActions(this::refreshDetails, this::hide);
	//

	final EntityActionInfo actionInfoModifyCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.WRITE) // nl
		.actionButton(btnModifyCategory) // nl
		// .preActions(this::hide) // nl
		.action(this::modify) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo actionInfoMergeCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.WRITE, Priviledge.MERGE) // nl
		.actionButton(btnMergeCategory) // nl
		// .preActions(this::validate) // nl
		.action(this::merge) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo actionInfoHideCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.READ) // nl
		.actionButton(btnHideCategory) // nl
		// .preActions(this::hide) // nl
		.action(this::hide) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo actionInfoRemoveCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.DELETE) // nl
		.actionButton(btnRemoveCategory) // nl
		// .preActions(this::hide) // nl
		.action(this::hide) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo actionInfoAddChildForCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.DELETE) // nl
		.actionButton(btnAddChildForCategory) // nl
		// .preActions(this::hide) // nl
		.action(this::addChild) // nl
		.postActions(this::refreshDetails);

	Stream.of(actionInfoRefreshCategory, // nl
		actionInfoModifyCategory, // nl
		actionInfoMergeCategory, // nl
		actionInfoHideCategory, // nl
		actionInfoRemoveCategory, // nl
		actionInfoAddChildForCategory)// nl
		.forEach(UIHelper::wireEntityActionUI);
    }

    @Override
    // @FXML
    // https://stackoverflow.com/a/37052850
    public Void refresh(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("REFRESH CATEGORY");

	System.out.println();
	System.out.println();

	transactionTemplate.execute(txStatus -> {
	    final TreeItem<Object> selectedEntityNode = categoryActionModel.getSelectedEntityNode();

	    final Category selectedCategory = (Category) selectedEntityNode.getValue();
	    final Category refreshedCategory = categoryRepo.findOne(selectedCategory.getId());
	    categoryActionModel.refreshSelectedEntityNode(refreshedCategory);

	    final Pair<Class<? extends Serializable>, TreeView<Object>> selectedTreeCtx = categoryActionModel
		    .getSelectedTreeContext();

	    Optional<Collection<?>> childNodeEntities = Optional.empty();

	    final Class<? extends Serializable> selectedTreeClass = selectedTreeCtx.getKey();
	    if (Customer.class.isAssignableFrom(selectedTreeClass)) {
		childNodeEntities = Optional.ofNullable(customerRepo.findByCategory(refreshedCategory));
	    } else if (Project.class.isAssignableFrom(selectedTreeClass)) {
		childNodeEntities = Optional.ofNullable(projectRepo.findByCategory(refreshedCategory));
	    } else if (Task.class.isAssignableFrom(selectedTreeClass)) {
		childNodeEntities = Optional.ofNullable(taskRepo.findByCategory(refreshedCategory));
	    } else if (Role.class.isAssignableFrom(selectedTreeClass)) {
		childNodeEntities = Optional.ofNullable(roleRepo.findByCategory(refreshedCategory));
	    }

	    childNodeEntities.ifPresent(children -> {
		selectedEntityNode.getChildren().clear();
		children.stream().map(TreeItem<Object>::new) // nl
			.forEach(selectedEntityNode.getChildren()::add);
	    });

	    return (Void) null;
	});

	return (Void) null;
    }

    // TODO: Implement the rest of the actions...
    @Override
    public Void modify(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("MODIFY CATEGORY");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void merge(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("MERGE CATEGORY");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void hide(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("HIDE CATEGORY");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void remove(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("REMOVE CATEGORY");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void addChild(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("ADD CHILD FOR CATEGORY");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void refreshDetails(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("REFRESH DETAILS");

	System.out.println();
	System.out.println();

	return (Void) null;
    }
}