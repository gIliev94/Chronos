package bc.bg.tools.chronos.endpoint.ui.actions;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;

public interface IEntityActionModel {

    TreeItem<Object> getSelectedEntityNode();

    // <T extends Serializable> T getSelectedEntity();

    void refreshSelectedEntityNode(Object modifiedEntity);

    Performer getLoggedUser();

    VBox getEntityDetailsPanel();

    // OR

    // void refreshEntityDetailsPanel(Serializable contextEntity, Map<String,
    // String> attributesToDisplay);
}
