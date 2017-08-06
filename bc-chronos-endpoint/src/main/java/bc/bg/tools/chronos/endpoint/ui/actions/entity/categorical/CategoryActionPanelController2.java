package bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.endpoint.ui.actions.EntityActionInfo;
import bc.bg.tools.chronos.endpoint.ui.actions.IEntityActionModel;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Pair;

public class CategoryActionPanelController2 extends CategoricalEntityActionPanelController
	implements ICategoryEntityActions {

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

    // TODO:
    // Abstract parent for
    // EntityActionController OR NOT...
    private ICategoryActionModel categoryActionModel;

    @Override
    public void setActionModel(IEntityActionModel actionModel) {
	this.categoryActionModel = (ICategoryActionModel) actionModel;
	super.setActionModel(categoryActionModel);
    }

    protected void initializeActions() {
	final EntityActionInfo actionInfoRefreshCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.READ) // nl
		.actionButton(btnRefresh) // nl
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
		.actionButton(btnModify) // nl
		// .preActions(this::hide) // nl
		.action(this::modify) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo actionInfoMergeCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.WRITE, Priviledge.MERGE) // nl
		.actionButton(btnMerge) // nl
		// .preActions(this::validate) // nl
		.action(this::merge) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo actionInfoHideCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.READ) // nl
		.actionButton(btnHide) // nl
		// .preActions(this::hide) // nl
		.action(this::hide) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo actionInfoRemoveCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.DELETE) // nl
		.actionButton(btnRemove) // nl
		// .preActions(this::hide) // nl
		.action(this::hide) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo actionInfoAddChildForCategory = new EntityActionInfo() // nl
		.performer(categoryActionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.DELETE) // nl
		.actionButton(btnAddChild) // nl
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

	UIHelper.removeHiddenNodesFromContainer(actionButtonGeneratedContainer);
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