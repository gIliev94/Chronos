package bc.bg.tools.chronos.endpoint.ui.main;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.endpoint.ui.actions.EntityAction;
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
import javafx.util.Pair;

public class CategoryActionPanelController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRefreshCategory;

    @FXML
    private Button btnAddCategory;

    @FXML
    private Button btnEditCategory;

    @FXML
    private Button btnRemoveCategory;

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
    }
    //

    @FXML
    void initialize() {
	// injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnRefreshCategory != null : "fx:id=\"btnRefreshCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnAddCategory != null : "fx:id=\"btnAddCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnEditCategory != null : "fx:id=\"btnEditCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
	assert btnRemoveCategory != null : "fx:id=\"btnRemoveCategory\" was not injected: check your FXML file 'CategoryActionPanel.fxml'.";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;
    }

    protected void initializeActions() {
	final EntityAction actionRefreshCategory = new EntityAction() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.READ) // nl
		.actionButton(btnRefreshCategory) // nl
		.action(this::refreshCategory) // nl
		.postActions(this::refreshEntityDetails);

	// TODO: Add to parent Action controller because add will not depend on
	// whether the entity node is clicked or not...
	final EntityAction actionAddCategory = new EntityAction() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.WRITE) // nl
		.actionButton(btnAddCategory) // nl
		.action(this::addEditCategory) // nl
		.postActions(this::refreshEntityDetails, this::hideCategory);
	//

	final EntityAction actionEditCategory = new EntityAction() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.WRITE) // nl
		.actionButton(btnEditCategory) // nl
		.action(this::addEditCategory) // nl
		.postActions(this::refreshEntityDetails);

	final EntityAction actionRemoveCategory = new EntityAction() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.DELETE) // nl
		.actionButton(btnRemoveCategory) // nl
		.action(this::hideCategory) // nl
		.postActions(this::refreshEntityDetails);

	Stream.of(actionRefreshCategory, actionAddCategory, actionEditCategory, actionRemoveCategory)
		.forEach(UIHelper::wireEntityActionUI);
    }

    protected Void refreshCategory(Void dummyArg) {
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

    protected Void addEditCategory(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("REFRESH CATEGORY");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    protected Void hideCategory(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("HIDE CATEGORY");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    protected Void refreshEntityDetails(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("POST ACTION CATEGORY");

	System.out.println();
	System.out.println();

	return (Void) null;
    }
}