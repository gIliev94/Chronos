package bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical;

import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;

/**
 * Blueprint for {@link CategoricalEntity} actions.
 * 
 * @author giliev
 */
public interface ICategoricalEntityActions {

    /**
     * Refreshes this entity`s data as well as its children`s.
     * 
     * @param dummyArg
     *            - not used argument (consumed by functional interface)
     * @return Not used return value (returned by functional interface)
     */
    Void refresh(Void dummyArg);

    /**
     * Creates a child entity for this entity.
     * 
     * @param dummyArg
     *            - not used argument (consumed by functional interface)
     * @return Not used return value (returned by functional interface)
     */
    Void addChild(Void dummyArg);

    /**
     * Modifies this entity`s data.
     * 
     * @param dummyArg
     *            - not used argument (consumed by functional interface)
     * @return Not used return value (returned by functional interface)
     */
    Void modify(Void dummyArg);

    /**
     * Merges a specified entity and its children into this entity.
     * 
     * @param dummyArg
     *            - not used argument (consumed by functional interface)
     * @return Not used return value (returned by functional interface)
     */
    Void merge(Void dummyArg);

    /**
     * Hides this entity and its children from the user interface.
     * 
     * @param dummyArg
     *            - not used argument (consumed by functional interface)
     * @return Not used return value (returned by functional interface)
     */
    Void hide(Void dummyArg);

    /**
     * Removes this entity and its children from the system.
     * 
     * @param dummyArg
     *            - not used argument (consumed by functional interface)
     * @return Not used return value (returned by functional interface)
     */
    Void remove(Void dummyArg);
}
