package bc.bg.tools.chronos.endpoint.ui.actions.entity;

import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.javafx.collections.ObservableListWrapper;

import bc.bg.tools.chronos.endpoint.ui.actions.EntityActionInfo;
import bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical.ICategoricalEntityActionModel;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Privilege.UserPrivilege;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddEntityController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblCaption"
    private Label lblCaption; // Value injected by FXMLLoader

    @FXML // fx:id="addEntityGeneratedContainer"
    private VBox addEntityGeneratedContainer; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @Autowired
    private LocalCategoryRepository categoryRepo;

    private transient Class<?> entityClass;

    private Object parentEntity;

    public void setParentEntity(Object parentEntity) {
	this.parentEntity = parentEntity;
	initCaption();
	initFields();
	initActions();
    }

    private ICategoricalEntityActionModel actionModel;

    public void setActionModel(ICategoricalEntityActionModel actionModel) {
	this.actionModel = actionModel;
    }

    @Override // This method is called by the FXMLLoader when initialization is
	      // complete
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;

	assert lblCaption != null : "fx:id=\"lblCaption\" was not injected: check your FXML file 'AddEntity.fxml'.";
	assert addEntityGeneratedContainer != null : "fx:id=\"addEntityGeneratedContainer\" was not injected: check your FXML file 'AddEntity.fxml'.";
	assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'AddEntity.fxml'.";
	assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'AddEntity.fxml'.";
    }

    protected void initCaption() {
	final String parentEntityName = getEntityDisplayString(parentEntity, true);
	final String childEntityClass = getEntityDisplayString(parentEntity, false);
	lblCaption.setText(MessageFormat.format(resources.getString("view.add.entity.caption"), childEntityClass,
		parentEntityName));
    }

    private String getEntityDisplayString(final Object entity, final boolean getParent) {
	final String notAssigned = "N/A";

	// Optional.of(entity.toString()).orElse(notAssigned)
	return (getParent // nl
		? (String) getParentEntity(entity).orElse(notAssigned)// nl
		: Optional.of(entity.getClass().getSimpleName()).orElse(notAssigned));

	// return (getParent // nl
	// ? ObjectUtils.<String>
	// defaultIfNull(getParentEntity(entity).toString(), notAssigned)// nl
	// : ObjectUtils.<String>
	// defaultIfNull(entity.getClass().getSimpleName(), notAssigned));
    }

    protected Optional<Object> getParentEntity(final Object childEntity) {
	// final Class<? extends Object> childEntityClass =
	// childEntity.getClass();
	entityClass = childEntity.getClass();

	Optional<Object> parentEntity = Optional.empty();
	if (Customer.class.isAssignableFrom(entityClass)) {
	    // NOP - no parent for customer...
	} else if (Project.class.isAssignableFrom(entityClass)) {
	    parentEntity = Optional.ofNullable(((Project) childEntity).getCustomer());
	} else if (Task.class.isAssignableFrom(entityClass)) {
	    parentEntity = Optional.ofNullable(((Task) childEntity).getProject());
	} else if (Booking.class.isAssignableFrom(entityClass)) {
	    parentEntity = Optional.ofNullable(((Booking) childEntity).getTask());
	} else if (BillingRole.class.isAssignableFrom(entityClass)) {
	    // TODO: Not sure about this relation...
	    // parentEntity = Optional.ofNullable(((Role)
	    // childEntity).getBooking());
	} else if (BillingRateModifier.class.isAssignableFrom(entityClass)) {
	    parentEntity = Optional.ofNullable(((BillingRateModifier) childEntity).getBooking());
	}

	return parentEntity;
    }

    protected void initFields() {
	Label editLabel;
	Control editControl;

	if (CategoricalEntity.class.isAssignableFrom(entityClass)) {
	    editLabel = new Label("Name:");
	    editLabel.setPrefSize(50.0d, 30.0d);

	    editControl = new TextField();
	    editControl.setPrefSize(200.0d, 30.0d);

	    addEntityGeneratedContainer.getChildren().add(createHBox(editLabel, editControl));

	    editLabel = new Label("Description:");
	    editLabel.setPrefSize(50.0d, 30.0d);

	    editControl = new TextField();
	    editControl.setPrefSize(200.0d, 30.0d);

	    addEntityGeneratedContainer.getChildren().add(createHBox(editLabel, editControl));

	    editLabel = new Label("Category:");
	    editLabel.setPrefSize(50.0d, 30.0d);

	    editControl = new ComboBox<Category>(new ObservableListWrapper((List) categoryRepo.findAll()));
	    editControl.setPrefSize(200.0d, 30.0d);

	    addEntityGeneratedContainer.getChildren().add(createHBox(editLabel, editControl));

	    if (Customer.class.isAssignableFrom(entityClass)) {
		// NOP - nothing additional
	    } else if (Project.class.isAssignableFrom(entityClass)) {
		// NOP - nothing additional
	    } else if (Task.class.isAssignableFrom(entityClass)) {
		//
	    } else if (BillingRole.class.isAssignableFrom(entityClass)) {
		//
	    }

	} else if (Booking.class.isAssignableFrom(entityClass)) {
	    // editLabel = new Label("Name:");
	    // editLabel.setPrefSize(50.0d, 30.0d);
	    //
	    // editControl = new TextField();
	    // editControl.setPrefSize(200.0d, 30.0d);
	} else if (BillingRateModifier.class.isAssignableFrom(entityClass)) {
	    // editLabel = new Label("Name:");
	    // editLabel.setPrefSize(50.0d, 30.0d);
	    //
	    // editControl = new TextField();
	    // editControl.setPrefSize(200.0d, 30.0d);
	}

    }

    private HBox createHBox(final Label instructionLabel, final Control editControl) {
	final HBox hBox = new HBox();
	hBox.setFillHeight(true);
	hBox.setManaged(true);
	hBox.setPadding(new Insets(5));
	hBox.setSpacing(5);

	// TODO: Leverage that to pass something maybe...
	// hBox.setUserData(value);

	hBox.getChildren().addAll(instructionLabel, editControl);

	return hBox;
    }

    private void initActions() {
	final EntityActionInfo actionInfoAddEntity = new EntityActionInfo() // nl
		.performer(actionModel.getLoggedUser()) // nl
		.requiredPriviledges(UserPrivilege.READ) // nl
		.actionButton(btnAdd) // nl
		// .preActions(this::merge) // nl
		.action(this::add); // nl
	// .postActions(this::refreshDetails);

	final EntityActionInfo actionInfoCancelEntity = new EntityActionInfo() // nl
		.performer(actionModel.getLoggedUser()) // nl
		.requiredPriviledges(UserPrivilege.READ) // nl
		.actionButton(btnCancel) // nl
		// .preActions(this::merge) // nl
		.action(this::cancel); // nl
	// .postActions(this::refreshDetails);

	Stream.of(actionInfoAddEntity, // nl
		actionInfoCancelEntity // nl
	).forEach(UIHelper::wireEntityActionUI);

	// UIHelper.removeHiddenNodesFromContainer(addEntityGeneratedContainer);
    }

    protected Void add(Void dummyArg) {

	return (Void) null;
    }

    protected Void cancel(Void dummyArg) {

	return (Void) null;
    }
}
