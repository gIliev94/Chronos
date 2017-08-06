package bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical;

import java.net.URL;
import java.util.ResourceBundle;

import bc.bg.tools.chronos.endpoint.ui.actions.ICommonEntityActions;
import bc.bg.tools.chronos.endpoint.ui.actions.IEntityActionModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public abstract class CategoricalEntityActionPanelController implements Initializable, ICommonEntityActions {

    @FXML // ResourceBundle that was given to the FXMLLoader
    protected ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    protected URL location;

    @FXML // fx:id="actionButtonGeneratedContainer"
    protected VBox actionButtonGeneratedContainer; // Value injected by
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

    protected IEntityActionModel actionModel;

    public void setActionModel(IEntityActionModel actionModel) {
	this.actionModel = actionModel;
	initializeActions();
    }

    // This method is called by the FXMLLoader when initialization is complete
    @FXML
    void initialize() {
	assert actionButtonGeneratedContainer != null : "fx:id=\"actionButtonGeneratedContainer\" was not injected: check your FXML file ' EntityActionPanel.fxml'.";
	assert btnRefresh != null : "fx:id=\"btnRefresh \" was not injected: check your FXML file ' EntityActionPanel.fxml'.";
	assert btnModify != null : "fx:id=\"btnModify \" was not injected: check your FXML file ' EntityActionPanel.fxml'.";
	assert btnMerge != null : "fx:id=\"btnMerge \" was not injected: check your FXML file ' EntityActionPanel.fxml'.";
	assert btnHide != null : "fx:id=\"btnHide \" was not injected: check your FXML file ' EntityActionPanel.fxml'.";
	assert btnRemove != null : "fx:id=\"btnRemove \" was not injected: check your FXML file ' EntityActionPanel.fxml'.";
	assert btnAddChild != null : "fx:id=\"btnAddChild \" was not injected: check your FXML file ' EntityActionPanel.fxml'.";
    }

    // TODO: Do you need this as well as the standard init method - or just
    // merge them into one???
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;
    }

    protected abstract void initializeActions();

    @Override
    public abstract Void refresh(Void dummyArg);

    @Override
    public abstract Void modify(Void dummyArg);

    @Override
    public abstract Void merge(Void dummyArg);

    @Override
    public abstract Void hide(Void dummyArg);

    @Override
    public abstract Void remove(Void dummyArg);

    @Override
    public abstract Void addChild(Void dummyArg);
}
