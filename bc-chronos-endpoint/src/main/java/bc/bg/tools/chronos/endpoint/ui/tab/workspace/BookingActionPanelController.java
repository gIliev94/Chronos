package bc.bg.tools.chronos.endpoint.ui.tab.workspace;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BookingActionPanelController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnRefreshBooking"
    private Button btnRefreshBooking; // Value injected by FXMLLoader

    @FXML // fx:id="btnStartBooking"
    private Button btnStartBooking; // Value injected by FXMLLoader

    @FXML // fx:id="btnContinueBooking"
    private Button btnContinueBooking; // Value injected by FXMLLoader

    @FXML // fx:id="btnStopBooking"
    private Button btnStopBooking; // Value injected by FXMLLoader

    @FXML // fx:id="btnPauseBooking"
    private Button btnPauseBooking; // Value injected by FXMLLoader

    @FXML // fx:id="btnModifyBooking"
    private Button btnModifyBooking; // Value injected by FXMLLoader

    @FXML // fx:id="btnRemoveBooking"
    private Button btnRemoveBooking; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is
	  // complete
    void initialize() {
	assert btnRefreshBooking != null : "fx:id=\"btnRefreshBooking\" was not injected: check your FXML file 'BookingActionPanel.fxml'.";
	assert btnStartBooking != null : "fx:id=\"btnStartBooking\" was not injected: check your FXML file 'BookingActionPanel.fxml'.";
	assert btnContinueBooking != null : "fx:id=\"btnContinueBooking\" was not injected: check your FXML file 'BookingActionPanel.fxml'.";
	assert btnStopBooking != null : "fx:id=\"btnStopBooking\" was not injected: check your FXML file 'BookingActionPanel.fxml'.";
	assert btnPauseBooking != null : "fx:id=\"btnPauseBooking\" was not injected: check your FXML file 'BookingActionPanel.fxml'.";
	assert btnModifyBooking != null : "fx:id=\"btnModifyBooking\" was not injected: check your FXML file 'BookingActionPanel.fxml'.";
	assert btnRemoveBooking != null : "fx:id=\"btnRemoveBooking\" was not injected: check your FXML file 'BookingActionPanel.fxml'.";
    }

    @FXML
    void refreshBooking(ActionEvent event) {

    }

    @FXML
    void startBooking(ActionEvent event) {

    }

    @FXML
    void continueBooking(ActionEvent event) {

    }

    @FXML
    void stopBooking(ActionEvent event) {

    }

    @FXML
    void pauseBooking(ActionEvent event) {

    }

    @FXML
    void modifyBooking(ActionEvent event) {

    }

    @FXML
    void removeBooking(ActionEvent event) {

    }
}