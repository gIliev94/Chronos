package bc.bg.tools.chronos.endpoint.ui.login;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.endpoint.ui.main.MainViewController;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalPerformerRepository;
import bg.bc.tools.chronos.dataprovider.utilities.EntityHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="user"
    private TextField user; // Value injected by FXMLLoader

    @FXML // fx:id="password"
    private TextField password; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @Autowired
    private LocalPerformerRepository performerRepo;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private Scene scene;

    @FXML // This method is called by the FXMLLoader when initialization is
	  // complete
    void initialize() {
	assert user != null : "fx:id=\"user\" was not injected: check your FXML file 'LoginWindow.fxml'.";
	assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'LoginWindow.fxml'.";
	assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginWindow.fxml'.";
    }

    public void loadTestData() {
	final Iterable<Performer> testDataInserted = transactionTemplate.execute(txStatus -> {
	    return createTestUsers();
	});

	assert (testDataInserted != null && testDataInserted.iterator().hasNext()) : "No test data could be inserted!";
    }

    // TODO: USe this shit...
    // https://gist.github.com/jewelsea/4631319
    private Iterable<Performer> createTestUsers() {
	final Performer userNorm = new Performer();
	userNorm.setSyncKey(UUID.randomUUID().toString());
	userNorm.setName("Georgi Iliev");
	userNorm.setHandle("gil");
	userNorm.setEmail("gil@systec-services.com");
	userNorm.setPassword("1232".toCharArray());
	userNorm.setPrimaryDeviceName(EntityHelper.getComputerName() + "1");
	userNorm.setPriviledges(Arrays.asList(Priviledge.READ, Priviledge.WRITE));
	userNorm.setLogged(false);

	final Performer userAdmin = new Performer();
	userAdmin.setSyncKey(UUID.randomUUID().toString());
	userAdmin.setName("Lachezar Dimitrov");
	userAdmin.setHandle("lad");
	userAdmin.setEmail("lad@systec-services.com");
	userAdmin.setPassword("l".toCharArray());
	userAdmin.setPrimaryDeviceName(EntityHelper.getComputerName() + "2");
	userAdmin.setPriviledges(Arrays.asList(Priviledge.ALL));
	userAdmin.setLogged(false);

	return performerRepo.save(Arrays.asList(userNorm, userAdmin));
    }

    @FXML
    void doLogin(MouseEvent event) {
	if (StringUtils.isBlank(user.getText()) || StringUtils.isBlank(password.getText())) {
	    System.err.println("NO CREDENTIALS INPUT!");
	    return;
	}

	// final Performer userObj = performerRepo.findByHandle(user.getText());
	final Performer userObj = transactionTemplate.execute(txStatus -> {
	    return performerRepo.findByHandle(user.getText());
	});

	if (userObj == null) {
	    System.err.println("NO SUCH USER FOUND IN DB :: " + user.getText());
	    return;
	} else if (ObjectUtils.notEqual(password.getText(), new String(userObj.getPassword()))) {
	    System.err.println("WRONG PASSWORD FOR USER :: " + user.getText());
	    return;
	} else {
	    userObj.setLogged(true);

	    final Performer persistedLoggedUserObj = transactionTemplate.execute(txStatus -> {
		return performerRepo.save(userObj);
	    });

	    final Boolean isLoginSuccessfull = transactionTemplate.execute(txStatus -> {
		return loginPassed(persistedLoggedUserObj);
	    });

	    System.out.println(isLoginSuccessfull ? "LOGIN OK" : "LOGIN ERROR");
	}

	// userNorm.setLogged(isLogged);
    }

    public Boolean loginPassed(final Performer userObj) {
	Parent root = null;
	URL url = null;
	ResourceBundle i18nBundle = null;
	try {
	    url = getClass().getResource("/fxml/MainWindowSandbox.fxml");
	    i18nBundle = ResourceBundle.getBundle("i18n.Bundle", Locale.getDefault());

	    final FXMLLoader l = new FXMLLoader(url, i18nBundle, new JavaFXBuilderFactory(), context::getBean);
	    root = l.load();
	    final MainViewController ctr = l.<MainViewController> getController();
	    ctr.applyLoginStuff(userObj);
	    // primaryStage.setScene(new Scene(root, 900, 600));
	    // // primaryStage.sizeToScene();
	    // primaryStage.setMaximized(true);
	    // primaryStage.show();
	    scene.setRoot(root);

	    return true;
	} catch (Exception ex) {
	    System.out.println("Exception on FXMLLoader.load()");
	    System.out.println("  * url: " + url);
	    System.out.println("  * " + ex);
	    System.out.println("    ----------------------------------------\n");
	    ExceptionUtils.printRootCauseStackTrace(ex);
	    return false;
	}
    }

    public void refScene(Scene refScene) {
	this.scene = refScene;
    }
}
