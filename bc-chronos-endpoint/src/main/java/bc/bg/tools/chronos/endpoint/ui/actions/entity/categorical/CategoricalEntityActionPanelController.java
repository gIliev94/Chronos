package bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.endpoint.ui.actions.EntityActionInfo;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Controller for {@link CategoricalEntity} actions.
 * 
 * @author giliev
 */
public abstract class CategoricalEntityActionPanelController implements Initializable, ICategoricalEntityActions {

    @FXML // ResourceBundle that was given to the FXMLLoader
    protected ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    protected URL location;

    @FXML // fx:id="btnBarEntityActions"
    protected VBox btnBarEntityActions; // Value injected by
					// FXMLLoader

    @FXML // fx:id="btnRefresh "
    protected Button btnRefresh; // Value injected by FXMLLoader

    @FXML // fx:id="btnModify "
    protected Button btnModify; // Value injected by FXMLLoader

    @FXML // fx:id="btnMerge "
    protected Button btnMerge; // Value injected by FXMLLoader

    @FXML // fx:id="btnHide "
    protected Button btnHide; // Value injected by FXMLLoader

    @FXML // fx:id="btnRemove "
    protected Button btnRemove; // Value injected by FXMLLoader

    @FXML // fx:id="btnAddChild "
    protected Button btnAddChild; // Value injected by FXMLLoader

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected LocalCategoryRepository categoryRepo;

    @Autowired
    protected LocalCustomerRepository customerRepo;

    @Autowired
    protected LocalProjectRepository projectRepo;

    @Autowired
    protected LocalTaskRepository taskRepo;

    // @Autowired
    // private LocalChangelogRepository changelogRepo;

    // @Autowired
    // private LocalBookingRepository bookingRepo;

    // @Autowired
    // private LocalPerformerRepository performerRepo;

    protected ICategoricalEntityActionModel actionModel;

    public void setActionModel(ICategoricalEntityActionModel actionModel) {
	this.actionModel = actionModel;
	initActions();
    }

    // This method is called by the FXMLLoader when initialization is complete
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	assert btnBarEntityActions != null : "fx:id=\"btnBarEntityActions\" was not injected: check your FXML file ' CategoricalEntityActionPanel.fxml'.";
	assert btnRefresh != null : "fx:id=\"btnRefresh \" was not injected: check your FXML file ' CategoricalEntityActionPanel.fxml'.";
	assert btnAddChild != null : "fx:id=\"btnAddChild \" was not injected: check your FXML file ' CategoricalEntityActionPanel.fxml'.";
	assert btnModify != null : "fx:id=\"btnModify \" was not injected: check your FXML file ' CategoricalEntityActionPanel.fxml'.";
	assert btnMerge != null : "fx:id=\"btnMerge \" was not injected: check your FXML file ' CategoricalEntityActionPanel.fxml'.";
	assert btnHide != null : "fx:id=\"btnHide \" was not injected: check your FXML file ' CategoricalEntityActionPanel.fxml'.";
	assert btnRemove != null : "fx:id=\"btnRemove \" was not injected: check your FXML file ' CategoricalEntityActionPanel.fxml'.";

	this.resources = resources;

	initTooltips();
    }

    /**
     * Initializes entity action button tooltips
     */
    protected void initTooltips() {
	btnRefresh.setTooltip(
		UIHelper.createTooltip(i18n("view.main.tab.workspace.entity.categorical.action.refresh.description")));
	btnAddChild.setTooltip(
		UIHelper.createTooltip(i18n("view.main.tab.workspace.entity.categorical.action.addchild.description")));
	btnModify.setTooltip(
		UIHelper.createTooltip(i18n("view.main.tab.workspace.entity.categorical.action.modify.description")));
	btnMerge.setTooltip(
		UIHelper.createTooltip(i18n("view.main.tab.workspace.entity.categorical.action.merge.description")));
	btnHide.setTooltip(
		UIHelper.createTooltip(i18n("view.main.tab.workspace.entity.categorical.action.hide.description")));
	btnRemove.setTooltip(
		UIHelper.createTooltip(i18n("view.main.tab.workspace.entity.categorical.action.remove.description")));
    }

    /**
     * Initializes the entity actions.
     */
    protected void initActions() {
	final EntityActionInfo entityActionRefresh = new EntityActionInfo() // nl
		.performer(actionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.READ) // nl
		.actionButton(btnRefresh) // nl
		.action(this::refresh) // nl
		.postActions(this::refreshDetails);

	final EntityActionInfo entityActionAddChild = new EntityActionInfo() // nl
		.performer(actionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.WRITE) // nl
		.actionButton(btnAddChild) // nl
		.preActions(this::refresh, this::refreshDetails) // nl
		.action(this::addChild) // nl
		.postActions(this::refresh, this::refreshDetails);

	final EntityActionInfo entityActionModify = new EntityActionInfo() // nl
		.performer(actionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.WRITE) // nl
		.actionButton(btnModify) // nl
		.preActions(this::refresh, this::refreshDetails) // nl
		.action(this::modify) // nl
		.postActions(this::refresh, this::refreshDetails);

	final EntityActionInfo entityActionMerge = new EntityActionInfo() // nl
		.performer(actionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.WRITE, Priviledge.MERGE) // nl
		.actionButton(btnMerge) // nl
		.preActions(this::refresh, this::refreshDetails) // nl
		.action(this::merge) // nl
		.postActions(this::refresh, this::refreshDetails);

	final EntityActionInfo entityActionHide = new EntityActionInfo() // nl
		.performer(actionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.READ) // nl
		.actionButton(btnHide) // nl
		.preActions(this::refresh, this::refreshDetails) // nl
		.action(this::hide) // nl
		.postActions(this::refresh, this::refreshDetails);

	final EntityActionInfo entityActionRemove = new EntityActionInfo() // nl
		.performer(actionModel.getLoggedUser()) // nl
		.requiredPriviledges(Priviledge.DELETE) // nl
		.actionButton(btnRemove) // nl
		.preActions(this::refresh, this::refreshDetails) // nl
		.action(this::hide) // nl
		.postActions(this::refresh, this::refreshDetails);

	Stream.of(entityActionRefresh, // nl
		entityActionModify, // nl
		entityActionMerge, // nl
		entityActionHide, // nl
		entityActionRemove, // nl
		entityActionAddChild)// nl
		.forEach(UIHelper::wireEntityActionUI);
    }

    /**
     * Refreshes the entity details.
     * 
     * @param dummyArg
     *            - not used argument (consumed by functional interface)
     * @return Not used return value (returned by functional interface)
     */
    protected abstract Void refreshDetails(Void dummyArg);

    @Override
    public abstract Void refresh(Void dummyArg);

    @Override
    public abstract Void addChild(Void dummyArg);

    @Override
    public abstract Void modify(Void dummyArg);

    @Override
    public abstract Void merge(Void dummyArg);

    @Override
    public abstract Void hide(Void dummyArg);

    @Override
    public abstract Void remove(Void dummyArg);

    protected String i18n(String msgId, Object... arguments) {
	return MessageFormat.format(resources.getString(msgId), arguments);
    }
}
