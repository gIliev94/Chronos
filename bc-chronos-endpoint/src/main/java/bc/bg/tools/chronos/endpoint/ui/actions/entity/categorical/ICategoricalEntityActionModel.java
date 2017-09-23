package bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical;

import java.io.Serializable;

import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * Blueprint for {@link CategoricalEntity} action model.}
 * 
 * @author giliev
 */
public interface ICategoricalEntityActionModel {

    /**
     * @return A key-value pair of Entity class(KEY) and Selected Entity
     *         Tree(VALUE).
     */
    Pair<Class<? extends Serializable>, TreeView<Object>> getSelectedTreeContext();

    /**
     * @return The selected Entity node(branch/leaf)
     */
    TreeItem<Object> getSelectedEntityNode();

    /**
     * Visually refreshes an entity.
     * 
     * @param modifiedEntity
     *            - the modified entity.
     */
    void refreshSelectedEntityNode(Object modifiedEntity);

    /**
     * @return The currently logged in user.
     */
    Performer getLoggedUser();

    // TODO: User or not ???

    /**
     * @return The entity details pane.
     */
    VBox getEntityDetailsPanel();

    // boolean isLocked
}
