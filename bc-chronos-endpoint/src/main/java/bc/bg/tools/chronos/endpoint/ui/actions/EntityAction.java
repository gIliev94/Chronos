package bc.bg.tools.chronos.endpoint.ui.actions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import javafx.scene.control.Button;

/**
 * Builder class for entity actions.
 * 
 * @author giliev
 */
public class EntityAction {
    
    private static final Logger LOGGER = Logger.getLogger(EntityAction.class);

    /**
     * This action`s performer(user).
     */
    private Performer performer;

    /**
     * This action`s required privileges(required of its {@link #performer}).
     */
    private Set<Priviledge> requiredPrivileges;

    /**
     * The main action.
     */
    private Function<Void, Void> action;

    /**
     * The follow-up / post actions(applied AFTER {@link #action}).
     */
    private List<Function<Void, Void>> postActions;

    /**
     * This action`s UI button.
     */
    private Button actionButton;

    public EntityAction() {
	requiredPrivileges = new HashSet<>();
	postActions = new LinkedList<>();
    }

    /**
     * Builder method, sets up {@link #performer}
     * 
     * @param performer
     *            - the performer(user) to set.
     * @return This entity action instance (used for builder method chaining).
     */
    public final EntityAction performer(final Performer performer) {
	this.performer = performer;
	return this;
    }

    /**
     * Builder method, sets up {@link #requiredPrivileges}
     * 
     * @param requiredPrivileges
     *            - the privileges to set as required.
     * @return This entity action instance (used for builder method chaining).
     */
    public final EntityAction requiredPriviledges(final Priviledge... requiredPrivileges) {
	this.requiredPrivileges.addAll(Stream.of(requiredPrivileges).collect(Collectors.toList()));
	return this;
    }

    /**
     * Builder method, sets up {@link #actionButton}
     * 
     * @param actionButton
     *            - the UI button to set.
     * @return This entity action instance (used for builder method chaining).
     */
    public final EntityAction actionButton(final Button actionButton) {
	this.actionButton = actionButton;
	return this;
    }

    /**
     * Builder method, sets up {@link #action}
     *
     * @param action
     *            - the main action to set.
     * @return This entity action instance (used for builder method chaining).
     */
    public final EntityAction action(final Function<Void, Void> action) {
	this.action = action;
	return this;
    }

    /**
     * Builder method, sets up {@link #postActions}
     * 
     * @param postActions
     *            - the follow-up / post actions to set.
     * @return This entity action instance (used for builder method chaining).
     */
    @SafeVarargs
    public final EntityAction postActions(final Function<Void, Void>... postActions) {
	this.postActions.addAll(Stream.of(postActions).collect(Collectors.toList()));
	return this;
    }

    /**
     * @return - this entity action`s UI button.
     */
    public Button getActionButton() {
	return actionButton;
    }

    /**
     * Determines whether this entity action is applicable(visible) to the
     * currently logged user, by matching user privileges to required action
     * privileges.
     * 
     * @return - TRUE if this action`s UI button should be visible, FALSE
     *         otherwise.
     */
    public boolean isVisibleToUser() {
	if (performer == null) {
	    logIncompleteEntityAction();
	    return false;
	}

	final boolean isVisibleToUser = performer.getPriviledges().contains(Priviledge.ALL)
		|| requiredPrivileges.stream().allMatch(performer.getPriviledges()::contains);
	return isVisibleToUser;
    }

    /**
     * Initiates this entity action`s execution sequence / chain - the main
     * action itself + multiple follow-up / post actions(like clean up / refresh
     * / etc.. ), if applicable.
     * 
     * @param dummyArg
     *            - dummy argument because of functional interface(not actually
     *            used).
     */
    public void executeActionSequence(final Void dummyArg) {
	if (action == null) {
	    logIncompleteEntityAction();
	    return;
	}

	Function<Void, Void> entityActionSequence = action;

	if (!(postActions.isEmpty())) {
	    for (final Function<Void, Void> postAction : postActions) {
		entityActionSequence = entityActionSequence.andThen(postAction);
	    }
	}

	entityActionSequence.apply(dummyArg);
    }

    /**
     * Logs the current entity action state - used for debugging.
     */
    protected void logIncompleteEntityAction() {
	LOGGER.error("Incorrectly set / incomplete entity action:\n" + this.toString());
    }

    /*
     * Overridden for the purpose of providing debug info about the action
     * instance.
     */
    @Override
    public String toString() {
	final String notAssigned = "N / A";

	final StringBuilder actionInfoBuilder = new StringBuilder();
	actionInfoBuilder // nl
		.append("Button name :: " + (actionButton != null ? actionButton.getId() : notAssigned)) // nl
		.append('\n') // nl
		.append("Performer :: " + (performer != null ? performer : notAssigned)) // nl
		.append('\n') // nl
		.append("Requires privileges :: "
			+ (!(requiredPrivileges.isEmpty()) ? requiredPrivileges : notAssigned)) // nl
		.append('\n') // nl
		.append("Is main action set :: " + (action != null)) // nl
		.append('\n') // nl
		.append("Has post actions set :: " + !(postActions.isEmpty())) // nl
		.append('\n') // nl
		.append("Action exection sequence length :: " + postActions.size() + 1);

	return actionInfoBuilder.toString();
    }
}
