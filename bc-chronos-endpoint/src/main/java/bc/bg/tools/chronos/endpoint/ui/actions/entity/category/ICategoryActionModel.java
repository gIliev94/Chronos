package bc.bg.tools.chronos.endpoint.ui.actions.entity.category;

import java.io.Serializable;

import bc.bg.tools.chronos.endpoint.ui.actions.IEntityActionModel;
import javafx.scene.control.TreeView;
import javafx.util.Pair;

public interface ICategoryActionModel extends IEntityActionModel {

    Pair<Class<? extends Serializable>, TreeView<Object>> getSelectedTreeContext();
}
