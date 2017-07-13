package bc.bg.tools.chronos.configuration;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;

import bc.bg.tools.chronos.endpoint.ui.login.LoginController;
import bc.bg.tools.chronos.endpoint.ui.main.MainViewController;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@Configuration
// TODO: Test w/wo lazy init...
@Lazy
public class UIConfig {

    // TODO: Add beans for each view`s Model/Controller instances...
    // @Bean
    // public AdminModel adminModel() {
    // return new AdminModel();
    // }
    //
    // @Bean
    // public CkaModel ckaModel() {
    // return new CkaModel();
    // }
    //
    // @Bean
    // public IAdminPresenter adminPresenter() {
    // return new AdminPresenter();
    // }
    //
    // @Bean
    // public ICkaPresenter ckaPresenter() {
    // return new CkaPresenter();
    // }

    // TODO: Ensure TM is created before referenced in controller...
    @DependsOn("transactionManager")
    @Bean(name = "mainViewController")
    public MainViewController mainViewController() {
	return new MainViewController();
    }

    @DependsOn("transactionManager")
    @Bean(name = "loginController")
    public LoginController loginController() {
	return new LoginController();
    }

    // TODO: Refactor paths/resources obtaining method when you get to JavaFx...
    public void showStartScreen(Stage primaryStage, ConfigurableApplicationContext context) {
	primaryStage.setResizable(false);

	Parent root = null;
	URL url = null;
	ResourceBundle i18nBundle = null;
	Scene theScene = null;
	try {
	    url = getClass().getResource("/fxml/LoginWindow.fxml");
	    i18nBundle = ResourceBundle.getBundle("i18n.Bundle", Locale.getDefault());

	    final FXMLLoader l = new FXMLLoader(url, i18nBundle, new JavaFXBuilderFactory(), context::getBean);
	    root = l.load();

	    final LoginController ctr = l.<LoginController> getController();
	    // theScene = new Scene(root, 400, 150);
	    theScene = new Scene(root);

	    ctr.setPrimaryStage(primaryStage);
	    ctr.loadLoginTestData();

	} catch (Exception ex) {
	    System.out.println("Exception on FXMLLoader.load()");
	    System.out.println("  * url: " + url);
	    System.out.println("  * " + ex);
	    System.out.println("    ----------------------------------------\n");
	    ExceptionUtils.printRootCauseStackTrace(ex);
	    return;
	}

	primaryStage.setTitle("Chronos");
	primaryStage.getIcons().add(new Image("/images/chronos_icon.jpg"));

	primaryStage.setScene(theScene);
	// primaryStage.sizeToScene();
	// primaryStage.setMaximized(true);
	primaryStage.show();

	// TODO: Remove later - JavaFx demo...
	// testSample(primaryStage);
    }

    // TODO: Refactor paths/resources obtaining method when you get to JavaFx...
    // public void showStartScreen(Stage primaryStage,
    // ConfigurableApplicationContext context) {
    // Parent root = null;
    // URL url = null;
    // ResourceBundle i18nBundle = null;
    // try {
    // url = getClass().getResource("/fxml/MainWindowSandbox.fxml");
    // i18nBundle = ResourceBundle.getBundle("i18n.Bundle",
    // Locale.getDefault());
    // root = FXMLLoader.load(url, i18nBundle, new JavaFXBuilderFactory(),
    // context::getBean);
    // // TODO: Load subviews like this... Use the controller to initialize
    // // forms post-construct
    // // final FXMLLoader l = new FXMLLoader(url, i18nBundle, new
    // // JavaFXBuilderFactory(), context::getBean);
    // // root = l.load();
    // // final Object ctr = l.getController();
    // // System.out.println(ctr);
    // } catch (Exception ex) {
    // System.out.println("Exception on FXMLLoader.load()");
    // System.out.println(" * url: " + url);
    // System.out.println(" * " + ex);
    // System.out.println(" ----------------------------------------\n");
    // ExceptionUtils.printRootCauseStackTrace(ex);
    // return;
    // }
    //
    // primaryStage.setTitle("Chronos");
    // primaryStage.getIcons().add(new Image("/images/chronos_icon.jpg"));
    // primaryStage.setScene(new Scene(root, 900, 600));
    // // primaryStage.sizeToScene();
    // primaryStage.setMaximized(true);
    // primaryStage.show();
    //
    // // TODO: Remove later - JavaFx demo...
    // // testSample(primaryStage);
    // }

    // TODO: Remove later - JavaFx demo...
    @SuppressWarnings("unused")
    private void testSample(Stage primaryStage) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Sample.fxml"));
	    BorderPane root = loader.load();
	    SampleController controller = loader.getController();

	    Scene scene = new Scene(root, 300, controller.getWindowHeight());
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    //
}
