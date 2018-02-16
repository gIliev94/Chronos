package bc.bg.tools.chronos.endpoint.ui.login;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.endpoint.ui.main.MainViewController;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.User;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalPerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalUserRepository;
import bg.bc.tools.chronos.dataprovider.utilities.DataCreator;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for login view.
 * 
 * @author giliev
 */
public class LoginController implements ILoginModel {

    // TODO: Use these links to refactor...
    // https://gist.github.com/jewelsea/4631319
    // https://stackoverflow.com/questions/17226948/switching-scene-in-javafx
    // http://www.javafxtutorials.com/tutorials/switching-to-different-screens-in-javafx-and-fxml/
    // https://www.google.bg/search?q=javafx+swap+scene&oq=javafx+swap+sc&aqs=chrome.0.0j69i57.8207j0j7&sourceid=chrome&ie=UTF-8

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userField"
    private TextField userField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private PasswordField passwordField; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @Autowired
    private LocalPerformerRepository performerRepo;

    @Autowired
    private LocalUserRepository userRepo;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TransactionTemplate transactionTemplate;

    // The primary stage to use when swapping UI windows
    private Stage primaryStage;

    public void setPrimaryStage(Stage refStage) {
	this.primaryStage = refStage;
    }

    // This method is called by the FXMLLoader when initialization is complete
    @FXML
    void initialize() {
	assert userField != null : "fx:id=\"userField\" was not injected: check your FXML file 'LoginWindow.fxml'.";
	assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginWindow.fxml'.";
	assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginWindow.fxml'.";

	initTooltips();
	toggleLoginButtonOnInput();
    }

    protected void initTooltips() {
	userField.tooltipProperty()
		.set(UIHelper.createTooltip(resources.getString(ILoginModel.MSG_ID_TOOLTIP_USER_FIELD)));
	passwordField.tooltipProperty()
		.set(UIHelper.createTooltip(resources.getString(ILoginModel.MSG_ID_TOOLTIP_PASSWORD_FIELD)));
	loginButton.tooltipProperty()
		.set(UIHelper.createTooltip(resources.getString(ILoginModel.MSG_ID_TOOLTIP_LOGIN_BUTTON)));
    }

    // TODO: Refactor (maybe use colors to highlight)...
    // https://stackoverflow.com/a/29616567
    // https://www.javacodegeeks.com/2012/06/in-this-tutorial-i-will-design-nice.html
    // http://zoranpavlovic.blogspot.bg/2012/05/javafx-2-create-nice-login-form.html
    protected void toggleLoginButtonOnInput() {
	loginButton.disableProperty().set(true);

	// TODO: One at a time binding via Listeners OR multiple bindings

	// userField.textProperty().addListener((obs, oldVal, newVal) -> {
	// if (!newVal.isEmpty() && !passwordField.getText().isEmpty()) {
	// loginButton.disableProperty().set(false);
	// } else {
	// loginButton.disableProperty().set(true);
	// }
	// });
	//
	// passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
	// if (!newVal.isEmpty() && !userField.getText().isEmpty()) {
	// loginButton.disableProperty().set(false);
	// } else {
	// loginButton.disableProperty().set(true);
	// }
	// });

	loginButton.disableProperty()
		.bind(Bindings.or(userField.textProperty().isEmpty(), passwordField.textProperty().isEmpty()));
    }

    @FXML
    void performLogin(ActionEvent loginBtnClickedEvt) {
	final String username = userField.getText();

	final User user = transactionTemplate.execute(txStatus -> {
	    return userRepo.findByAbbreviation(username);
	});

	if (user == null) {
	    UIHelper.showErrorDialog(i18n(MSG_ID_ERR_INVALID_USER, username));
	    return;
	} else if (ObjectUtils.notEqual(passwordField.getText(), new String(user.getPassword()))) {
	    UIHelper.showErrorDialog(i18n(MSG_ID_ERR_INVALID_PASS, username));
	    return;
	} else {
	    final Boolean wasMainViewDisplayed = transactionTemplate.execute(txStatus -> {
		return displayMainWindow(user);
	    });
	    if (!wasMainViewDisplayed) {
		UIHelper.showErrorDialog(
			i18n(UIHelper.Defaults.APP_MSG_ID_ERR_WINDOW_NOT_LOADED, UIHelper.Defaults.APP_MAIN_WINDOW));
	    }
	}
    }

    protected Boolean displayMainWindow(final User user) {
	final FXMLLoader uiLoader = UIHelper.getWindowLoaderFor(UIHelper.Defaults.APP_MAIN_WINDOW,
		UIHelper.Defaults.APP_I18N_EN, context::getBean);

	Parent rootContainer;
	try {
	    rootContainer = uiLoader.load();
	} catch (IOException e) {
	    // TODO: Remove println
	    System.out.println("Exception on FXMLLoader.load()");
	    System.out.println(" * url: " + uiLoader.getLocation());
	    System.out.println(" * " + e);
	    System.out.println(" ----------------------------------------\n");
	    ExceptionUtils.printRootCauseStackTrace(e);
	    return false;
	}

	final MainViewController mainViewController = uiLoader.<MainViewController> getController();
	mainViewController.loginAs(user);
	mainViewController.setPrimaryStage(primaryStage);

	primaryStage.getScene().setRoot(rootContainer);

	// TODO: Either maximize window or set size!
	// https://stackoverflow.com/a/22686642
	primaryStage.setResizable(true);
	primaryStage.setMaximized(true);

	// https://stackoverflow.com/a/36982552
	primaryStage.setMinHeight(800.0d);
	primaryStage.setMinWidth(800.0d);

	// https://stackoverflow.com/a/8791691
	// primaryStage.setHeight(800.0d);
	// primaryStage.setWidth(1000.0d);

	// primaryStage.setResizable(true);
	// primaryStage.sizeToScene();

	return true;
    }

    @Autowired
    private DataCreator localDataCreator;

    public void loadLoginTestData() {
	final Iterable<Performer> testDataInserted = transactionTemplate.execute(txStatus -> {
	    // return createTestUsers();
	    try {
		localDataCreator.createSampleUserData();
		localDataCreator.createSampleWorkspaceData();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	    }
	    return null;
	});

	assert (testDataInserted != null && testDataInserted.iterator().hasNext()) : "No test data could be inserted!";
    }

    @Override
    public String i18n(String msgId, Object... arguments) {
	return MessageFormat.format(resources.getString(msgId), arguments);
    }
}
