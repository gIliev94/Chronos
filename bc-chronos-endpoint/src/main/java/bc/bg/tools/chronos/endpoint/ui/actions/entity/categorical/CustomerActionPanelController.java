package bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical;

import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import javafx.scene.control.TreeItem;

/**
 * Controller for {@link Customer} actions.}
 * 
 * @author giliev
 */
public class CustomerActionPanelController extends CategoricalEntityActionPanelController {

    @Override
    public Void refresh(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("REFRESH CUSTOMER");

	System.out.println();
	System.out.println();

	transactionTemplate.execute(txStatus -> {
	    final TreeItem<Object> selectedCustomerNode = actionModel.getSelectedEntityNode();

	    final Customer selectedCustomer = (Customer) selectedCustomerNode.getValue();
	    final Customer refreshedCustomer = customerRepo.findOne(selectedCustomer.getId());
	    actionModel.refreshSelectedEntityNode(refreshedCustomer);

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

    @Override
    public Void addChild(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("ADD CHILD FOR CUSTOMER");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void modify(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("MODIFY CUSTOMER");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void merge(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("MERGE CUSTOMER");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void hide(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("HIDE CUSTOMER");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

    @Override
    public Void remove(Void dummyArg) {
	System.out.println();
	System.out.println();

	System.out.println("REMOVE CUSTOMER");

	System.out.println();
	System.out.println();

	return (Void) null;
    }

}