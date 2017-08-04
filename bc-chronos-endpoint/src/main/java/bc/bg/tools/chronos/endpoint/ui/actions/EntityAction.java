package bc.bg.tools.chronos.endpoint.ui.actions;

import java.util.List;
import java.util.function.Function;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import javafx.scene.control.Button;

public class EntityAction {

    private Performer performer;

    private List<Priviledge> requiredPriviledges;

    private Function<Void, Void> action;

    private Function<Void, Void> postAction;

    private Button actionButton;

    public EntityAction performer(final Performer performer) {
	this.performer = performer;
	return this;
    }

    public EntityAction requiredPriviledges(final List<Priviledge> requiredPriviledges) {
	this.requiredPriviledges = requiredPriviledges;
	return this;
    }

    public EntityAction actionButton(final Button actionButton) {
	this.actionButton = actionButton;
	return this;
    }

    public EntityAction action(final Function<Void, Void> action) {
	this.action = action;
	return this;
    }

    public EntityAction postAction(final Function<Void, Void> postAction) {
	this.postAction = postAction;
	return this;
    }

    public List<Priviledge> getRequiredPriviledges() {
	return this.requiredPriviledges;
    }

    public Button getActionButton() {
	return actionButton;
    }

    public boolean isVisibleToUser() {
	if (performer == null) {
	    throw new RuntimeException("NO PERFORMER SET!!!");
	}

	final boolean isVisibleToUser = performer.getPriviledges().contains(Priviledge.ALL)
		|| requiredPriviledges.stream().allMatch(performer.getPriviledges()::contains);
	return isVisibleToUser;
    }

    public void execute(final Void dummyArg) {
	if (action == null) {
	    throw new IllegalArgumentException("NO ACTION SPECIFIED!!!");
	}

	if (postAction != null) {
	    action.andThen(postAction).apply(dummyArg);
	} else {
	    action.apply(dummyArg);
	}
    }
}
