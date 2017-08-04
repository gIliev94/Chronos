package bc.bg.tools.chronos.endpoint.ui.main;

import java.io.Serializable;

import javafx.scene.control.TreeView;
import javafx.util.Pair;

public interface ICategoryActionModel extends IEntityActionContext {

    // TODO: Maybe specific
    // TreeView<Customer> getTreeCustomers();

    // OR

    // TreeView<Object> getTreeCustomers();
    //
    // TreeView<Object> getTreeProjects();
    //
    // TreeView<Object> getTreeTasks();
    //
    // TreeView<Object> getTreeRoles();

    // OR

    // TreeView<? extends Serializable> getSelectedTree();
    Pair<Class<? extends Serializable>, TreeView<Object>> getSelectedTreeContext();
}
