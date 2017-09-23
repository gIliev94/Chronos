package bc.bg.tools.chronos.endpoint.ui.main;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.derby.jdbc.EmbeddedXADataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import bc.bg.tools.chronos.endpoint.ui.tab.workspace.WorkspaceController;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.i18n.IMessageService;
import bitronix.tm.resource.jdbc.lrc.LrcXADataSource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Controller for Main view of Chronos application.
 * 
 * @author giliev
 */
public class MainViewController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(MainViewController.class);

    private static final String MSG_ID_LABEL_LOGGED_USER = "view.main.tab.workspace.user.label";

    @FXML
    private ResourceBundle resources;

    @SuppressWarnings("unused")
    @Autowired
    private IMessageService messageService;
    //

    // TODO: First manual transaction attempt - UserTransaction...
    // @Autowired
    // private BitronixTransactionManager btm;

    // @Autowired
    // private ApplicationContext applicationContext;
    //
    // @Autowired
    // public PlatformTransactionManager transactionManager;

    // SQLITE
    // @Autowired
    // @Qualifier("lrcLocalDataSource")
    // private LrcXADataSource lrcLocalDataSource;

    // Apache Derby
    @Autowired
    @Qualifier("embeddedLocalDataSource")
    private EmbeddedXADataSource lrcLocalDataSource;

    // MS SQL Server
    // TODO: Consider swapping remote LRC source with SQL server XA(like below)
    // https://github.com/bitronix/btm/blob/master/btm-docs/src/main/asciidoc/LastResourceCommit2x.adoc
    @Autowired
    @Qualifier("lrcRemoteDataSource")
    private LrcXADataSource lrcRemoteDataSource;

    // https://github.com/bitronix/btm/blob/master/btm-docs/src/main/asciidoc/JdbcXaSupportEvaluation.adoc#msql
    // @Autowired
    // @Qualifier("lrcRemoteDataSource")
    // private JtdsDataSource lrcRemoteDataSource;
    //

    // TODO: Need???
    @FXML
    private Menu menuFile;

    @FXML
    private TabPane tabPaneMain;

    @FXML
    private Tab tabPerformers;

    @FXML
    private Tab tabRoles;

    @FXML
    private Tab tabReporting;

    @FXML
    private Tab tabWorkspace;

    @FXML
    private Tab tabStatistics;

    @FXML
    private Tab tabSynchronization;

    @FXML
    private Circle indicatorOffline;

    @FXML
    private Circle indicatorOnline;

    @FXML
    private Label lblLoggedUser;

    @FXML
    private WorkspaceController subformWorkspaceController;

    // TODO: Implement log out function...
    // The primary stage to use when swapping UI windows OR log out...
    private Stage primaryStage;

    private Performer loggedPerformer;

    public Stage getPrimaryStage() {
	return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
	this.primaryStage = primaryStage;
	LOGGER.info("Primary stage component :: " + primaryStage);

	// Set primary stage for every other sub-controller
	subformWorkspaceController.setPrimaryStage(primaryStage);
    }

    public Performer getLoggedPerformer() {
	return loggedPerformer;
    }

    public void setLoggedPerformer(Performer loggedPerformer) {
	this.loggedPerformer = loggedPerformer;
	LOGGER.info("Logged in as :: " + loggedPerformer);

	// Set logged in user for every other sub-controller
	subformWorkspaceController.setLoggedPerformer(loggedPerformer);
    }

    public void loginAs(Performer loggedPerformer) {
	setLoggedPerformer(loggedPerformer);

	// Display login label
	lblLoggedUser.setText(i18n(MSG_ID_LABEL_LOGGED_USER, loggedPerformer));

	hideIncacessibleComponents(loggedPerformer);
    }

    // TODO: Hide inaccessible UI (tabs, menu items, etc...)
    @SuppressWarnings("unchecked")
    protected void hideIncacessibleComponents(Performer loggedPerformer) {
	final Collection<Priviledge> performerPrivileges = loggedPerformer.getPriviledges();
	tabPaneMain.getTabs().forEach(tab -> {
	    tab.setDisable(!(performerPrivileges.containsAll((List<Priviledge>) tab.getUserData())));
	});
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;

	initTabAccess();
	initLoginIndicator();
    }

    protected void initLoginIndicator() {
	indicatorOffline.setEffect(UIHelper.createBlur(indicatorOffline.getRadius()));
	indicatorOnline.setEffect(UIHelper.createBlur(indicatorOnline.getRadius()));
    }

    // TODO: Refactor hidden panes...
    protected void initTabAccess() {
	tabWorkspace.setUserData(Arrays.asList(Priviledge.READ));
	tabPerformers.setUserData(Arrays.asList(Priviledge.READ, Priviledge.WRITE));
	tabRoles.setUserData(Arrays.asList(Priviledge.READ, Priviledge.WRITE));
	tabReporting.setUserData(Arrays.asList(Priviledge.READ));
	tabStatistics.setUserData(Arrays.asList(Priviledge.READ, Priviledge.WRITE));
	tabSynchronization.setUserData(Arrays.asList(Priviledge.READ));
    }

    // https://github.com/tomoTaka01/FileTreeViewSample/blob/master/src/filetreeviewsample/FileTreeViewSample.java
    // http://code.makery.ch/blog/javafx-8-event-handling-examples/
    // https://stackoverflow.com/questions/10518458/javafx-create-custom-button-with-image

    // Example :: replace container`s children approach
    // private Parent currentActionPanel;

    private String i18n(String msgId, Object... arguments) {
	return MessageFormat.format(resources.getString(msgId), arguments);
    }
}
