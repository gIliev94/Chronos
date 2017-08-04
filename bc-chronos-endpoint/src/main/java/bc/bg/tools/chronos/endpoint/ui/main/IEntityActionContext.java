package bc.bg.tools.chronos.endpoint.ui.main;

import java.io.Serializable;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;

public interface IEntityActionContext {

    TreeItem<Object> getSelectedEntityNode();

    Serializable getSelectedEntity();

    void refreshSelectedEntityNode(Object modifiedEntity);

    Performer getLoggedUser();

    VBox getEntityDetailsPanel();

    // OR

    // void refreshEntityDetailsPanel(Serializable contextEntity, Map<String,
    // String> attributesToDisplay);
}
