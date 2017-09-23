package bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Pair;

/**
 * Controller for {@link Category} actions.}
 * 
 * @author giliev
 */
public class CategoryActionPanelController extends CategoricalEntityActionPanelController {

    // TODO: Save link...
    // https://stackoverflow.com/a/37052850
    @Override
    public Void refresh(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("REFRESH CATEGORY");

	System.out.println();
	System.out.println();

	transactionTemplate.execute(txStatus -> {
	    final TreeItem<Object> selectedCategoryNode = actionModel.getSelectedEntityNode();

	    final Category selectedCategory = (Category) selectedCategoryNode.getValue();
	    final Category refreshedCategory = categoryRepo.findOne(selectedCategory.getId());
	    actionModel.refreshSelectedEntityNode(refreshedCategory);

	    final Pair<Class<? extends Serializable>, TreeView<Object>> selectedTreeCtx = actionModel
		    .getSelectedTreeContext();

	    Optional<Collection<?>> childNodeEntities = Optional.empty();

	    final Class<? extends Serializable> selectedTreeClass = selectedTreeCtx.getKey();
	    if (Customer.class.isAssignableFrom(selectedTreeClass)) {
		childNodeEntities = Optional.ofNullable(customerRepo.findByCategory(refreshedCategory));
	    } else if (Project.class.isAssignableFrom(selectedTreeClass)) {
		childNodeEntities = Optional.ofNullable(projectRepo.findByCategory(refreshedCategory));
	    } else if (Task.class.isAssignableFrom(selectedTreeClass)) {
		childNodeEntities = Optional.ofNullable(taskRepo.findByCategory(refreshedCategory));
	    }

	    childNodeEntities.ifPresent(children -> {
		selectedCategoryNode.getChildren().clear();
		children.stream().map(TreeItem<Object>::new) // nl
			.forEach(selectedCategoryNode.getChildren()::add);
	    });

	    return (Void) null;
	});

	return (Void) null;
    }

    // TODO: Implement the rest of the actions...
    @Override
    public Void refreshDetails(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("REFRESH DETAILS");

	System.out.println();
	System.out.println();

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

}