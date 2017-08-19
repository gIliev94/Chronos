package bc.bg.tools.chronos.endpoint.ui.tab.workspace;

import java.net.URL;
import java.util.ResourceBundle;

import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * FXML Controller for tab - Workspace.
 * 
 * @author giliev
 */
public class WorkspaceController implements Initializable {

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
    private TreeView<?> treeCustomers;

    @FXML // fx:id="titlePaneProject"
    private TitledPane titlePaneProject;

    @FXML // fx:id="treeProjects"
    private TreeView<?> treeProjects;

    @FXML // fx:id="titlePaneTask"
    private TitledPane titlePaneTask;

    @FXML // fx:id="treeTasks"
    private TreeView<?> treeTasks;

    @FXML // fx:id="titlePaneRole"
    private TitledPane titlePaneRole;

    @FXML // fx:id="treeRoles"
    private TreeView<?> treeRoles;

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
	assert titlePaneRole != null : "fx:id=\"titlePaneRole\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert treeRoles != null : "fx:id=\"treeRoles\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert btnToggleEntityDetails != null : "fx:id=\"btnToggleEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert imgViewShowEntityDetails != null : "fx:id=\"imgViewShowEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert imgViewHideEntityDetails != null : "fx:id=\"imgViewHideEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert rowEntityDetails != null : "fx:id=\"rowEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert rowEntityTree != null : "fx:id=\"rowEntityTree\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert barEntityDetails != null : "fx:id=\"barEntityDetails\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert btnBookingTabluarPerspective != null : "fx:id=\"btnBookingTabluarPerspective\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";
	assert btnBookingGraphicalPerspective != null : "fx:id=\"btnBookingGraphicalPerspective\" was not injected: check your FXML file 'WorkspaceTabSkeleton.fxml'.";

	this.resources = resources;

	initToggleEntityDetails();

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
	    break;

	case IMG_VIEW_SHOW_DETAILS_ID:
	    imageViewToHide = imgViewShowEntityDetails;
	    imageViewToShow = imgViewHideEntityDetails;
	    entityTreeHeightNew = (rowEntityTree.getPercentHeight() - rowEntityDetailsOrigHeight);
	    entityDetailsHeightNew = rowEntityDetailsOrigHeight;
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
	// ...
    }
}
