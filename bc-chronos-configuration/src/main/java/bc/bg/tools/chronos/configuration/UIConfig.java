package bc.bg.tools.chronos.configuration;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import bc.bg.tools.chronos.endpoint.ui.actions.entity.categorical.CategoryActionPanelController;
import bc.bg.tools.chronos.endpoint.ui.login.LoginController;
import bc.bg.tools.chronos.endpoint.ui.main.MainViewController;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleController;
import bc.bg.tools.chronos.endpoint.ui.tab.performers.PerformersController;
import bc.bg.tools.chronos.endpoint.ui.tab.reporting.RecentReportActionPanelController;
import bc.bg.tools.chronos.endpoint.ui.tab.reporting.ReportActionPanelController;
import bc.bg.tools.chronos.endpoint.ui.tab.reporting.ReportingController;
import bc.bg.tools.chronos.endpoint.ui.tab.workspace.BookingActionPanelController;
import bc.bg.tools.chronos.endpoint.ui.tab.workspace.BookingTabularPerspectiveController;
import bc.bg.tools.chronos.endpoint.ui.tab.workspace.WorkspaceController;
import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Configuration class for UI related beans.
 * 
 * @author giliev
 */
@Lazy
@Configuration
public class UIConfig {

    /**
     * @param primaryStage
     *            - the JavaFx primary stage
     * @param context
     *            - the Spring application context
     */
    public void showStartScreen(final Stage primaryStage, final ConfigurableApplicationContext context) {
	final FXMLLoader uiLoader = UIHelper.getWindowLoaderFor(UIHelper.Defaults.APP_START_WINDOW,
		UIHelper.Defaults.APP_I18N_EN, context::getBean);

	Parent rootContainer;
	try {
	    rootContainer = uiLoader.load();
	} catch (IOException e) {
	    ExceptionUtils.printRootCauseStackTrace(e);
	    return;
	}

	final LoginController loginController = uiLoader.<LoginController> getController();
	loginController.setPrimaryStage(primaryStage);
	// TODO: Remove test data...
	loginController.loadLoginTestData();
	//

	primaryStage.setResizable(false);
	primaryStage.setTitle(UIHelper.Defaults.APP_TITLE);
	primaryStage.getIcons().add(UIHelper.createImageIcon(UIHelper.Defaults.APP_ICON));

	primaryStage.setScene(new Scene(rootContainer));
	// primaryStage.sizeToScene();
	// primaryStage.setMaximized(true);
	primaryStage.show();

	// TODO: Remove later - JavaFx demo(private method below as well)...
	// testSample(primaryStage);
    }

    @SuppressWarnings("unused")
    private void testSample(Stage primaryStage) {
	try {
	    final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Sample.fxml"));
	    final BorderPane root = loader.load();
	    final SampleController controller = loader.getController();

	    final Scene scene = new Scene(root, 300, controller.getWindowHeight());
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch (Exception e) {
	    ExceptionUtils.printRootCauseStackTrace(e);
	}
    }
    //

    @Bean
    public MainViewController mainViewController() {
	return new MainViewController();
    }

    @Bean
    public LoginController loginController() {
	return new LoginController();
    }

    @Bean
    public WorkspaceController workspaceController() {
	return new WorkspaceController();
    }

    @Bean
    public CategoryActionPanelController categoryActionController() {
	return new CategoryActionPanelController();
    }

    @Bean
    public BookingActionPanelController bookingActionController() {
	return new BookingActionPanelController();
    }

    @Bean
    public BookingTabularPerspectiveController bookingTabularPerspectiveController() {
	return new BookingTabularPerspectiveController();
    }

    @Bean
    public PerformersController performersController() {
	return new PerformersController();
    }

    @Bean
    public ReportingController reportingController() {
	return new ReportingController();
    }

    @Bean
    public RecentReportActionPanelController recentReportActionPanelController() {
	return new RecentReportActionPanelController();
    }

    @Bean
    public ReportActionPanelController reportActionPanelController() {
	return new ReportActionPanelController();
    }
}
