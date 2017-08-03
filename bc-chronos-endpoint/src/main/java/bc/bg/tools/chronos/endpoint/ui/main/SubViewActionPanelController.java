package bc.bg.tools.chronos.endpoint.ui.main;

import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import bc.bg.tools.chronos.endpoint.ui.actions.EntityAction;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SubViewActionPanelController implements Initializable {

    private List<EntityAction<? extends Serializable>> actions;

    @FXML
    private VBox actionButtonBar;

    @FXML
    private ResourceBundle resources;

    // @Autowired
    // private TransactionTemplate transactionTemplate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;

	actions = new LinkedList<EntityAction<? extends Serializable>>();
    }

    private void addActions(Class<? extends Serializable> entityClass) {
	final String entityName = entityClass.getSimpleName();

	switch (entityName) {
	case "Category":
	    final EntityAction<Category> actionRefreshCategory = new EntityAction<Category>(Category.class) // nl
		    .requiredPriviledges(Arrays.asList(Priviledge.READ)) // nl
		    .actionIconName("refresh_icon") // nl
		    .action(this::refreshCategory) // nl
		    .postAction(this::postCategory);
	    actions.add(actionRefreshCategory);

	    final EntityAction<Category> actionHideCategory = new EntityAction<Category>(Category.class) // nl
		    .requiredPriviledges(Arrays.asList(Priviledge.READ)) // nl
		    .actionIconName("delete_icon") // nl
		    .action(this::hideCategory);
	    actions.add(actionHideCategory);
	    break;

	default:
	    break;
	}
    }

    protected void refreshCategory(Object ctx) {
	System.out.println();
	System.out.println();

	System.out.println("REFRESH CATEGORY");

	System.out.println();
	System.out.println();
    }

    protected void postCategory(Object ctx) {
	System.out.println();
	System.out.println();

	System.out.println("POST ACTION CATEGORY");

	System.out.println();
	System.out.println();
    }

    protected void hideCategory(Object ctx) {
	System.out.println();
	System.out.println();

	System.out.println("HIDE CATEGORY");

	System.out.println();
	System.out.println();
    }

    public void displayActions(Class<? extends Serializable> entityClass, Performer user, Object ctx) {
	addActions(entityClass);

	clearActions();

	actions.stream() // nl
		.filter(a -> a.isVisibleToUser(user)) // nl
		.forEach(a -> { // nl
		    final Button actionButton = UIHelper.createButtonForAction(a, ctx);
		    // actionButton.widthProperty().add(actionButtonBar.getWidth());
		    // Region r = ((Region) actionButtonBar);
		    // actionButton.prefWidthProperty().bind(r.widthProperty());

		    actionButtonBar.getChildren().add(actionButton);
		});
    }

    public void clearActions() {
	actionButtonBar.getChildren().clear();
    }
}
