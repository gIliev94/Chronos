package bc.bg.tools.chronos.endpoint.ui.login;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.endpoint.ui.main.MainViewController;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalPerformerRepository;
import bg.bc.tools.chronos.dataprovider.utilities.EntityHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller for login view.
 * 
 * @author giliev
 */
public class LoginController {

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
    }

    // TODO: Extract constants and apply I18n(also refactor UI)
    @FXML
    void performLogin(MouseEvent loginBtnClickedEvt) {
	if (StringUtils.isBlank(userField.getText()) || StringUtils.isBlank(passwordField.getText())) {
	    UIHelper.showErrorDialog("NO CREDENTIALS INPUT!");
	    return;
	}

	final Performer user = transactionTemplate.execute(txStatus -> {
	    return performerRepo.findByHandle(userField.getText());
	});

	if (user == null) {
	    UIHelper.showErrorDialog("NO SUCH USER FOUND IN DB :: " + userField.getText());
	    return;
	} else if (ObjectUtils.notEqual(passwordField.getText(), new String(user.getPassword()))) {
	    UIHelper.showErrorDialog("WRONG PASSWORD FOR USER :: " + userField.getText());
	    return;
	} else {
	    final Performer persistedUser = transactionTemplate.execute(txStatus -> {
		user.setLogged(true);
		return performerRepo.save(user);
	    });

	    final Boolean wasMainViewDisplayed = transactionTemplate.execute(txStatus -> {
		return displayMainWindow(persistedUser);
	    });
	    if (!wasMainViewDisplayed) {
		UIHelper.showErrorDialog("THE MAIN WINDOW COULD NOT BE LOADED! ASK ADMINISTRATOR FOR HELP!");
	    }
	}
    }

    protected Boolean displayMainWindow(final Performer user) {
	final FXMLLoader uiLoader = UIHelper.getWindowLoaderFor("MainWindowSandbox", "i18n.Bundle", context::getBean);

	Parent rootContainerUI;
	try {
	    rootContainerUI = uiLoader.load();
	} catch (IOException e) {
	    System.out.println("Exception on FXMLLoader.load()");
	    System.out.println(" * url: " + uiLoader.getLocation());
	    System.out.println(" * " + e);
	    System.out.println(" ----------------------------------------\n");
	    ExceptionUtils.printRootCauseStackTrace(e);
	    return false;
	}

	final MainViewController mainViewController = uiLoader.<MainViewController> getController();
	mainViewController.loginAs(user);

	// TODO: Either set size or maximize window!
	// primaryStage.setHeight(600.0d);
	// primaryStage.setWidth(900.0d);
	primaryStage.setMaximized(true);
	// primaryStage.sizeToScene();
	primaryStage.getScene().setRoot(rootContainerUI);

	return true;
    }

    public void loadLoginTestData() {
	final Iterable<Performer> testDataInserted = transactionTemplate.execute(txStatus -> {
	    return createTestUsers();
	});

	assert (testDataInserted != null && testDataInserted.iterator().hasNext()) : "No test data could be inserted!";
    }

    // TODO: Use these links to refactor...
    // https://gist.github.com/jewelsea/4631319
    // https://stackoverflow.com/questions/17226948/switching-scene-in-javafx
    // http://www.javafxtutorials.com/tutorials/switching-to-different-screens-in-javafx-and-fxml/
    // https://www.google.bg/search?q=javafx+swap+scene&oq=javafx+swap+sc&aqs=chrome.0.0j69i57.8207j0j7&sourceid=chrome&ie=UTF-8
    protected Iterable<Performer> createTestUsers() {
	final Performer userNorm = new Performer();
	userNorm.setSyncKey(UUID.randomUUID().toString());
	userNorm.setName("Georgi Iliev");
	userNorm.setHandle("gil");
	userNorm.setEmail("gil@abv.bg");
	userNorm.setPassword("1232".toCharArray());
	userNorm.setPrimaryDeviceName(EntityHelper.getComputerName() + "1");
	userNorm.setPriviledges(Arrays.asList(Priviledge.READ, Priviledge.WRITE));
	userNorm.setLogged(false);

	final Performer userAdmin = new Performer();
	userAdmin.setSyncKey(UUID.randomUUID().toString());
	userAdmin.setName("Lachezar Dimitrov");
	userAdmin.setHandle("lad");
	userAdmin.setEmail("lad@abv.bg");
	userAdmin.setPassword("l".toCharArray());
	userAdmin.setPrimaryDeviceName(EntityHelper.getComputerName() + "2");
	userAdmin.setPriviledges(Arrays.asList(Priviledge.ALL));
	userAdmin.setLogged(false);

	return performerRepo.save(Arrays.asList(userNorm, userAdmin));
    }
}
