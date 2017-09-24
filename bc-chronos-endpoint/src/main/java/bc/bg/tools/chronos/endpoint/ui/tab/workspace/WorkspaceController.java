package bc.bg.tools.chronos.endpoint.ui.tab.workspace;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical.CategoricalEntityActionPanelController;
import bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical.CategoryActionPanelController;
import bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical.ICategoricalEntityActionModel;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller for tab - Workspace.
 * 
 * @author giliev
 */
public class WorkspaceController implements Initializable, ICategoricalEntityActionModel {

    private static final Logger LOGGER = Logger.getLogger(WorkspaceController.class);

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblLoggedUser"
    private Label lblLoggedUser;

    @FXML // fx:id="btnBarEntityActions"
    private VBox btnBarEntityActions;

    @FXML // fx:id="datePickerBooking"
    private DatePicker datePickerBooking;

    @FXML // fx:id="btnBarBookingActions"
    private HBox btnBarBookingActions;

    @FXML // fx:id="titlePaneCustomer"
    private TitledPane titlePaneCustomer;

    @FXML // fx:id="treeCustomers"
    private TreeView<Object> treeCustomers;

    @FXML // fx:id="titlePaneProject"
    private TitledPane titlePaneProject;

    @FXML // fx:id="treeProjects"
    private TreeView<Object> treeProjects;

    @FXML // fx:id="titlePaneTask"
    private TitledPane titlePaneTask;

    @FXML // fx:id="treeTasks"
    private TreeView<Object> treeTasks;

    @FXML // fx:id="btnToggleEntityDetails"
    private Button btnToggleEntityDetails;

    @FXML // fx:id="imgViewShowEntityDetails"
    private ImageView imgViewShowEntityDetails;

    @FXML // fx:id="imgViewHideEntityDetails"
    private ImageView imgViewHideEntityDetails;

    @FXML // fx:id="rowEntityTree"
    private RowConstraints rowEntityTree;

    @FXML // fx:id="rowEntityDetails"
    private RowConstraints rowEntityDetails;

    @FXML // fx:id="barEntityDetails"
    private VBox barEntityDetails;

    @FXML // fx:id="btnBookingTabluarPerspective"
    private Button btnBookingTabluarPerspective;

    @FXML // fx:id="btnBookingGraphicalPerspective"
    private Button btnBookingGraphicalPerspective;

    @FXML
    private BookingTabularPerspectiveController bookingsTabularPerspectiveController;

    private Performer loggedPerformer;

    public Performer getLoggedPerformer() {
	return loggedPerformer;
    }

    public void setLoggedPerformer(Performer loggedPerformer) {
	this.loggedPerformer = loggedPerformer;
    }

    private Stage primaryStage;

    public Stage getPrimaryStage() {
	return primaryStage;
    }

    public void setPrimaryStage(Stage refStage) {
	this.primaryStage = refStage;
    }

    private TreeItem<Object> selectedCategoryNode;

    // This method is called by the FXMLLoader when initialization is complete
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	assert lblLoggedUser != null : "fx:id=\"lblLoggedUser\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert btnBarEntityActions != null : "fx:id=\"btnBarEntityActions\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert datePickerBooking != null : "fx:id=\"datePickerBooking\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert btnBarBookingActions != null : "fx:id=\"btnBarBookingActions\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert titlePaneCustomer != null : "fx:id=\"titlePaneCustomer\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert treeCustomers != null : "fx:id=\"treeCustomers\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert titlePaneProject != null : "fx:id=\"titlePaneProject\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert treeProjects != null : "fx:id=\"treeProjects\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert titlePaneTask != null : "fx:id=\"titlePaneTask\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert treeTasks != null : "fx:id=\"treeTasks\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert btnToggleEntityDetails != null : "fx:id=\"btnToggleEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert imgViewShowEntityDetails != null : "fx:id=\"imgViewShowEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert imgViewHideEntityDetails != null : "fx:id=\"imgViewHideEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert rowEntityDetails != null : "fx:id=\"rowEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert rowEntityTree != null : "fx:id=\"rowEntityTree\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert barEntityDetails != null : "fx:id=\"barEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert btnBookingTabluarPerspective != null : "fx:id=\"btnBookingTabluarPerspective\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert btnBookingGraphicalPerspective != null : "fx:id=\"btnBookingGraphicalPerspective\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";

	// TODO: Need???
	this.resources = resources;

	initToggleEntityDetails();
	initTreeContents();
    }

    private void initTreeContents() {
	// Initialize tree with 'empty' ROOT item...
	treeCustomers.setRoot(new TreeItem<Object>());
	treeProjects.setRoot(new TreeItem<Object>());
	treeTasks.setRoot(new TreeItem<Object>());

	// Populate tree
	populateTree(treeCustomers);
	populateTree(treeProjects);
	populateTree(treeTasks);

	treeTasks.getSelectionModel().selectedItemProperty().addListener((obs, newVal, oldVal) -> {
	    final Object oSelectedTask = newVal.getValue();
	    if (oSelectedTask instanceof Task) {
		bookingsTabularPerspectiveController.filterBookingsForParent((Task) oSelectedTask);
	    }
	});
    }

    /**
     * Initializes settings/fields relevant to entity details panel/toggle
     * button.
     */
    private void initToggleEntityDetails() {
	// Make components visibility toggle
	UIHelper.makeToggleVisibilityCapable(imgViewHideEntityDetails);
	UIHelper.makeToggleVisibilityCapable(imgViewShowEntityDetails);
	// UIHelper.makeToggleVisibilityCapable(barEntityDetails);

	// Default toggle image is HIDE...
	// https://stackoverflow.com/a/24616203
	btnToggleEntityDetails.setUserData(imgViewHideEntityDetails.getId());

	// Store original height for calculations(reallocation of UI space)
	rowEntityDetailsOrigHeight = rowEntityDetails.getPercentHeight();
    }

    /** The FXML id of 'hide details' image */
    private static final String IMG_VIEW_HIDE_DETAILS_ID = "imgViewHideEntityDetails";

    /** The FXML id of 'show details' image */
    private static final String IMG_VIEW_SHOW_DETAILS_ID = "imgViewShowEntityDetails";

    /** The height for which the node becomes invisible - in percentage. */
    private static final double HEIGHT_INVISIBLE = 0.0d;

    /**
     * The original height of the entity details grid row(as configured in the
     * FXML) - in percentage.
     */
    private transient double rowEntityDetailsOrigHeight;

    /**
     * Shows/hides entity details panel. Also swaps button images for visual
     * aid.
     */
    public void toggleEntityDetailsVisible() {
	final String lastImageViewId = (String) btnToggleEntityDetails.getUserData();

	ImageView imageViewToHide = null;
	ImageView imageViewToShow = null;
	Double entityTreeHeightNew = null;
	Double entityDetailsHeightNew = null;

	switch (lastImageViewId) {
	case IMG_VIEW_HIDE_DETAILS_ID:
	    imageViewToHide = imgViewHideEntityDetails;
	    imageViewToShow = imgViewShowEntityDetails;
	    entityTreeHeightNew = (rowEntityTree.getPercentHeight() + rowEntityDetailsOrigHeight);
	    entityDetailsHeightNew = HEIGHT_INVISIBLE;
	    // barEntityDetails.getChildren().stream().forEach(c -> {
	    // c.setVisible(false);
	    // c.setManaged(false);
	    // });
	    barEntityDetails.visibleProperty().set(false);
	    barEntityDetails.managedProperty().set(false);
	    break;

	case IMG_VIEW_SHOW_DETAILS_ID:
	    imageViewToHide = imgViewShowEntityDetails;
	    imageViewToShow = imgViewHideEntityDetails;
	    entityTreeHeightNew = (rowEntityTree.getPercentHeight() - rowEntityDetailsOrigHeight);
	    entityDetailsHeightNew = rowEntityDetailsOrigHeight;
	    // barEntityDetails.getChildren().stream().forEach(c -> {
	    // c.setVisible(true);
	    // c.setManaged(true);
	    // });
	    barEntityDetails.visibleProperty().set(true);
	    barEntityDetails.managedProperty().set(true);
	    break;

	default:
	    // TODO: log , i18n and/or custom exception
	    throw new RuntimeException("Inexistent image/panel ID referenced!");
	}

	performToggleDetailsVisibility(imageViewToShow, imageViewToHide, entityTreeHeightNew, entityDetailsHeightNew);
    }

    /**
     * Toggles the visibility of the entity details panel as well as the image
     * for the toggle button itself.
     * 
     * @param imgViewVisible
     *            - the image to show.
     * @param imgViewInvisible
     *            - the image to hide.
     * @param heightEntityTree
     *            - the new allocated height for entity tree(in percentage)
     * @param heightEntityDetails
     *            - the new allocated height for entity details(in percentage)
     */
    protected void performToggleDetailsVisibility(final ImageView imgViewVisible, final ImageView imgViewInvisible,
	    final double heightEntityTree, final double heightEntityDetails) {
	// Trigger visibility/managed state...
	imgViewInvisible.setVisible(false);
	imgViewVisible.setVisible(true);

	// Reallocate percent heights...
	rowEntityTree.setPercentHeight(heightEntityTree);
	rowEntityDetails.setPercentHeight(heightEntityDetails);

	// Store the active image(maybe move to model class if applicable)
	btnToggleEntityDetails.setUserData(imgViewVisible.getId());
    }

    public void toggleEntityPanelVisible() {
	// TODO: Implement
	// ...
    }

    // TODO: Need commented out repos???
    // @Autowired
    // private TransactionTemplate transactionTemplate;

    @Autowired
    private LocalTaskRepository taskRepo;

    @Autowired
    private LocalProjectRepository projectRepo;

    @Autowired
    private LocalCustomerRepository customerRepo;

    @Autowired
    private LocalCategoryRepository categoryRepo;

    // @Autowired
    // private LocalChangelogRepository changelogRepo;
    //
    // @Autowired
    // private LocalBookingRepository bookingRepo;
    //
    // @Autowired
    // private LocalPerformerRepository performerRepo;

    // https://github.com/tomoTaka01/FileTreeViewSample/blob/master/src/filetreeviewsample/FileTreeViewSample.java
    private void populateTree(final TreeView<Object> tree) {
	final List<Category> categories = ((List<Category>) categoryRepo.findAll());
	// TODO; Keep the sorter idea - works nicely...
	categories.sort( // nl
		Comparator.comparing(Category::getSortOrder) // nl
			.thenComparing(Category::getName) // nl
	);
	//

	final ObservableList<TreeItem<Object>> categoryLevel = tree.getRoot().getChildren();
	final boolean categoriesAppended = categoryLevel.addAll(categories.stream() // nl
		.map(TreeItem<Object>::new) // nl
		.collect(Collectors.toList()));
	if (categoriesAppended) {
	    for (TreeItem<Object> catNode : categoryLevel) {
		catNode.setExpanded(true);

		final Category catObj = (Category) catNode.getValue();

		// TODO: Refactor
		Collection<?> subEntities = null;
		if (Objects.equals(tree.getId(), "treeCustomers")) {
		    subEntities = customerRepo.findByCategory(catObj);
		} else if (Objects.equals(tree.getId(), "treeProjects")) {
		    subEntities = projectRepo.findByCategory(catObj);
		} else if (Objects.equals(tree.getId(), "treeTasks")) {
		    subEntities = taskRepo.findByCategory(catObj);
		}
		subEntities.stream() // nl
			.map(TreeItem<Object>::new) // nl
			.forEach(catNode.getChildren()::add);
	    }
	}

	tree.getSelectionModel().selectedItemProperty().addListener(this::showEntityAttributes);
    }

    private void showEntityAttributes(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> old_val,
	    TreeItem<Object> new_val) {
	final ObservableList<Node> entityAttrList = barEntityDetails.getChildren();
	entityAttrList.stream().forEach(n -> {
	    n.visibleProperty().bind(barEntityDetails.visibleProperty());
	    n.managedProperty().bind(barEntityDetails.managedProperty());
	});

	entityAttrList.clear();
	selectedCategoryNode = new_val;

	Class<? extends Serializable> entityClass = null;

	final Object newValueObj = new_val.getValue();
	if (newValueObj instanceof Category) {
	    final Category catObj = (Category) newValueObj;
	    entityAttrList.add(createHBox(new Label("Name: "), new TextField(catObj.getName())));
	    entityAttrList
		    .add(createHBox(new Label("Sort order: "), new TextField(String.valueOf(catObj.getSortOrder()))));

	    entityClass = Category.class;

	} else if (newValueObj instanceof Customer) {
	    final Customer custObj = (Customer) newValueObj;
	    entityAttrList.add(createHBox(new Label("Name: "), new TextField(custObj.getName())));
	    entityAttrList.add(createHBox(new Label("Description: "), new TextField(custObj.getDescription())));

	    entityClass = Customer.class;

	    titlePaneCustomer.setText(MessageFormat
		    .format(resources.getString("view.main.tab.workspace.entity.customer.title"), custObj.getName()));

	} else if (newValueObj instanceof Project) {
	    final Project projObj = (Project) newValueObj;
	    entityAttrList.add(new HBox(new Label("Name: "), new TextField(projObj.getName())));
	    entityAttrList.add(new HBox(new Label("Description: "), new TextField(projObj.getDescription())));

	    entityClass = Project.class;

	    titlePaneProject.setText(MessageFormat
		    .format(resources.getString("view.main.tab.workspace.entity.project.title"), projObj.getName()));
	    titlePaneCustomer
		    .setText(MessageFormat.format(resources.getString("view.main.tab.workspace.entity.customer.title"),
			    projObj.getCustomer().getName()));

	} else if (newValueObj instanceof Task) {
	    final Task taskObj = (Task) newValueObj;
	    entityAttrList.add(new HBox(new Label("Name: "), new TextField(taskObj.getName())));
	    entityAttrList.add(new HBox(new Label("Description: "), new TextField(taskObj.getDescription())));
	    entityAttrList.add(new HBox(new Label("Hours estimated: "),
		    new TextField(String.valueOf(taskObj.getHoursEstimated()))));

	    entityClass = Task.class;

	    titlePaneTask.setText(MessageFormat.format(resources.getString("view.main.tab.workspace.entity.task.title"),
		    taskObj.getName()));

	    titlePaneProject
		    .setText(MessageFormat.format(resources.getString("view.main.tab.workspace.entity.project.title"),
			    taskObj.getProject().getName()));
	    titlePaneCustomer
		    .setText(MessageFormat.format(resources.getString("view.main.tab.workspace.entity.customer.title"),
			    taskObj.getProject().getCustomer().getName()));
	}

	showCategoricalEntityActions(entityClass);
    }

    private HBox createHBox(Label label, TextField textField) {
	final HBox hBox = new HBox();
	hBox.setFillHeight(true);
	hBox.setManaged(true);
	hBox.setPadding(new Insets(5));
	hBox.setSpacing(5);

	// TODO: Leverage that to pass something maybe...
	// hBox.setUserData(value);

	hBox.getChildren().addAll(label, textField);

	return hBox;
    }

    private static final String ACTION_PANEL_CATEGORICAL = "CategoricalEntityActionPanel";

    @Autowired
    private CategoryActionPanelController categoryActionController;

    @Autowired
    private ApplicationContext applicationContext;

    @FXML
    private AnchorPane paneCategoricalActions;

    @FXML
    private AnchorPane paneShowEntityPanel;

    // @FXML
    // private CategoricalEntityActionPanelController
    // subformCategoricalActionPanelController;

    // @FXML
    // private Parent subformCategoricalActionPanel;

    protected void showCategoricalEntityActions(final Class<? extends Serializable> entityClass) {
	final FXMLLoader actionPanel = UIHelper.getWindowLoaderFor(ACTION_PANEL_CATEGORICAL,
		UIHelper.Defaults.APP_I18N_EN, applicationContext::getBean);

	Object specificEntityController = null;
	if (Category.class.isAssignableFrom(entityClass)) {
	    specificEntityController = categoryActionController;
	} else {
	    // TODO: Implement specific controllers for other entities...
	    // paneCategoricalActions.getChildren().forEach(n ->
	    // n.setVisible(false));
	    // paneCategoricalActions.getChildren().clear();
	    return;
	}
	actionPanel.setController(specificEntityController);

	try {
	    final Parent actionPanelRoot = actionPanel.load();

	    final CategoricalEntityActionPanelController actionController = actionPanel
		    .<CategoricalEntityActionPanelController> getController();
	    actionController.setActionModel(this);

	    // paneCategoricalActions.getChildren().clear();
	    paneCategoricalActions.getChildren().add(actionPanelRoot);
	    // TODO:
	    paneShowEntityPanel.toBack();

	} catch (IOException e) {
	    LOGGER.error(e);
	    UIHelper.showErrorDialog(
		    i18n(UIHelper.Defaults.APP_MSG_ID_ERR_WINDOW_NOT_LOADED, ACTION_PANEL_CATEGORICAL));
	}
    }

    @Override
    public void refreshSelectedEntityNode(Object modifiedEntity) {
	if (modifiedEntity instanceof Serializable) {
	    selectedCategoryNode.setValue(modifiedEntity);
	} else {
	    // LOG...
	}
    }

    @Override
    public Performer getLoggedUser() {
	return loggedPerformer;
    }

    @Override
    public VBox getEntityDetailsPanel() {
	return barEntityDetails;
    }

    @Override
    public Pair<Class<? extends Serializable>, TreeView<Object>> getSelectedTreeContext() {
	if (!treeCustomers.getSelectionModel().isEmpty()) {
	    return new Pair<>(Customer.class, treeCustomers);
	} else if (!treeProjects.getSelectionModel().isEmpty()) {
	    return new Pair<>(Project.class, treeProjects);
	} else if (!treeTasks.getSelectionModel().isEmpty()) {
	    return new Pair<>(Task.class, treeTasks);
	}

	return null;
    }

    @Override
    public TreeItem<Object> getSelectedEntityNode() {
	return selectedCategoryNode;
    }

    private String i18n(String msgId, Object... arguments) {
	return MessageFormat.format(resources.getString(msgId), arguments);
    }
}
