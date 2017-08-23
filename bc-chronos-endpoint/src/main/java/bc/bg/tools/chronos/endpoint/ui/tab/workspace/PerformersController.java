package bc.bg.tools.chronos.endpoint.ui.tab.workspace;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * Controller for Performers tab.
 * 
 * @author giliev
 */
public class PerformersController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="barFilterPerformers"
    private HBox barFilterPerformers; // Value injected by FXMLLoader

    @FXML // fx:id="comboBoxFilterCriteria"
    private ComboBox<?> comboBoxFilterCriteria; // Value injected by FXMLLoader

    @FXML // fx:id="stackFilterValueFields"
    private StackPane stackFilterValueFields; // Value injected by FXMLLoader

    @FXML // fx:id="texFieldFilterValue"
    private TextField texFieldFilterValue; // Value injected by FXMLLoader

    @FXML // fx:id="comboBoxFilterValue"
    private ComboBox<?> comboBoxFilterValue; // Value injected by FXMLLoader

    @FXML // fx:id="btnAppendFilter"
    private Button btnAppendFilter; // Value injected by FXMLLoader

    @FXML // fx:id="listViewAppendedFilters"
    private ListView<?> listViewAppendedFilters; // Value injected by FXMLLoader

    @FXML // fx:id="tableViewPerformers"
    private TableView<?> tableViewPerformers; // Value injected by FXMLLoader

    @FXML // fx:id="tableColHandle"
    private TableColumn<?, ?> tableColHandle; // Value injected by FXMLLoader

    @FXML // fx:id="tableColName"
    private TableColumn<?, ?> tableColName; // Value injected by FXMLLoader

    @FXML // fx:id="tableColEmail"
    private TableColumn<?, ?> tableColEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tableColPrivileges"
    private TableColumn<?, ?> tableColPrivileges; // Value injected by
						  // FXMLLoader

    @FXML // fx:id="btnToggleLastBookingsTable"
    private Button btnToggleLastBookingsTable; // Value injected by FXMLLoader

    @FXML // fx:id="imgViewShowLastBookingsTable"
    private ImageView imgViewShowLastBookingsTable; // Value injected by
						    // FXMLLoader

    @FXML // fx:id="imgViewHideLastBookingsTable"
    private ImageView imgViewHideLastBookingsTable; // Value injected by
						    // FXMLLoader

    @FXML // fx:id="tableViewLastBookings"
    private TableView<?> tableViewLastBookings; // Value injected by FXMLLoader

    @FXML // fx:id="tableColTask"
    private TableColumn<?, ?> tableColTask; // Value injected by FXMLLoader

    @FXML // fx:id="tableColDescription"
    private TableColumn<?, ?> tableColDescription; // Value injected by
						   // FXMLLoader

    @FXML // fx:id="tableColStartTime"
    private TableColumn<?, ?> tableColStartTime; // Value injected by FXMLLoader

    @FXML // fx:id="tableColEndTime"
    private TableColumn<?, ?> tableColEndTime; // Value injected by FXMLLoader

    @FXML // fx:id="listViewAvailablePrivileges"
    private ListView<?> listViewAvailablePrivileges; // Value injected by
						     // FXMLLoader

    @FXML // fx:id="btnAssignPrivilege"
    private Button btnAssignPrivilege; // Value injected by FXMLLoader

    @FXML // fx:id="btnRemovePrivilege"
    private Button btnRemovePrivilege; // Value injected by FXMLLoader

    @FXML // fx:id="listViewPerformerPrivileges"
    private ListView<?> listViewPerformerPrivileges; // Value injected by
						     // FXMLLoader

    @FXML // fx:id="btnCancelModifications"
    private Button btnCancelModifications; // Value injected by FXMLLoader

    @FXML // fx:id="btnCommitModifications"
    private Button btnCommitModifications; // Value injected by FXMLLoader

    @FXML
    void cancelModifications(ActionEvent event) {

    }

    @FXML
    void appendFilter(ActionEvent event) {

    }

    @FXML
    void assignPrivilege(ActionEvent event) {

    }

    @FXML
    void commitModifications(ActionEvent event) {

    }

    @FXML
    void removePrivilege(ActionEvent event) {

    }

    @FXML
    void toggleLastBookingsTableVisible(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is
	  // complete
    void initialize() {
	assert barFilterPerformers != null : "fx:id=\"barFilterPerformers\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert comboBoxFilterCriteria != null : "fx:id=\"comboBoxFilterCriteria\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert stackFilterValueFields != null : "fx:id=\"stackFilterValueFields\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert texFieldFilterValue != null : "fx:id=\"texFieldFilterValue\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert comboBoxFilterValue != null : "fx:id=\"comboBoxFilterValue\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert btnAppendFilter != null : "fx:id=\"btnAppendFilter\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert listViewAppendedFilters != null : "fx:id=\"listViewAppendedFilters\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableViewPerformers != null : "fx:id=\"tableViewPerformers\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableColHandle != null : "fx:id=\"tableColHandle\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableColName != null : "fx:id=\"tableColName\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableColEmail != null : "fx:id=\"tableColEmail\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableColPrivileges != null : "fx:id=\"tableColPrivileges\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert btnToggleLastBookingsTable != null : "fx:id=\"btnToggleLastBookingsTable\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert imgViewShowLastBookingsTable != null : "fx:id=\"imgViewShowLastBookingsTable\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert imgViewHideLastBookingsTable != null : "fx:id=\"imgViewHideLastBookingsTable\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableViewLastBookings != null : "fx:id=\"tableViewLastBookings\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableColTask != null : "fx:id=\"tableColTask\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableColDescription != null : "fx:id=\"tableColDescription\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableColStartTime != null : "fx:id=\"tableColStartTime\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert tableColEndTime != null : "fx:id=\"tableColEndTime\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert listViewAvailablePrivileges != null : "fx:id=\"listViewAvailablePrivileges\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert btnAssignPrivilege != null : "fx:id=\"btnAssignPrivilege\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert btnRemovePrivilege != null : "fx:id=\"btnRemovePrivilege\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert listViewPerformerPrivileges != null : "fx:id=\"listViewPerformerPrivileges\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert btnCancelModifications != null : "fx:id=\"btnCancelModifications\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
	assert btnCommitModifications != null : "fx:id=\"btnCommitModifications\" was not injected: check your FXML file 'PerformerTabSkeleton.fxml'.";
    }
}