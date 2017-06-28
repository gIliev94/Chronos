package bc.bg.tools.chronos.endpoint.ui.actions;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;

public class EntityAction<E extends Serializable> {

    // private Button button;
    //
    // private Image icon;

    private String actionIconName;

    private Class<E> entityClass;

    private List<Priviledge> requiredPriviledges;

    private boolean isVisibleToUser;

    private Consumer<E> action;

    private Consumer<E> postAction;

    public EntityAction(Class<E> entityClass, String actionIconPrefix) {
	// this.icon = new Image("/images/action_" + actionName + "_icon.jpg");
	// // icon.set
	//
	// button = new Button();
	// button.setGraphic(new ImageView(this.icon));

	// Hidden by default - show if user has privileges.
	// button.setVisible(false);
	// button.setMnemonicParsing(false);
	// button.set
	this(entityClass);
	this.actionIconName = actionIconPrefix;
    }

    public EntityAction(Class<E> entityClass) {
	this.entityClass = entityClass;
	// Hidden by default - show if user has privileges.
	this.isVisibleToUser = false;
    }

    public void executeWith(E input) throws RuntimeException {
	if (action == null) {
	    throw new IllegalArgumentException("NO ACTION SPECIFIED!!!");
	}

	if (postAction != null) {
	    action.andThen(postAction).accept(input);
	} else {
	    action.accept(input);
	}
    }

    @SuppressWarnings("unchecked")
    public void execute(Object input) {
	executeWith((E) input);
    }

    public EntityAction<E> entityClass(Class<E> entityClass) {
	this.entityClass = entityClass;
	return this;
    }

    public EntityAction<E> requiredPriviledges(List<Priviledge> requiredPriviledges) {
	this.requiredPriviledges = requiredPriviledges;
	return this;
    }

    public EntityAction<E> action(Consumer<E> action) {
	this.action = action;
	return this;
    }

    public EntityAction<E> postAction(Consumer<E> postAction) {
	this.postAction = postAction;
	return this;
    }

    public String getActionIconName() {
	return actionIconName;
    }

    public Class<E> getEntityClass() {
	return this.entityClass;
    }

    public List<Priviledge> getRequiredPriviledges() {
	return this.requiredPriviledges;
    }

    public boolean isVisibleToUser(Performer performer) {
	if (performer == null) {
	    throw new RuntimeException("NO PERFORMER SET!!!");
	}

	this.isVisibleToUser = performer.getPriviledges().contains(Priviledge.ALL)
		|| requiredPriviledges.stream().allMatch(performer.getPriviledges()::contains);
	return this.isVisibleToUser;
    }
}
