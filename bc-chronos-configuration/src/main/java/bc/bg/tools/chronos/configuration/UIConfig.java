package bc.bg.tools.chronos.configuration;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import bc.bg.tools.chronos.endpoint.ui.main.MainViewController;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@Configuration
//TODO: Need?
@Lazy
public class UIConfig {
    
    //TODO: Add beans for each view`s Model/Controller instances...
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

    @Bean(name = "mainViewController")
    public MainViewController mainViewController() {
	return new MainViewController();
    }

    public void showStartScreen(Stage primaryStage, ConfigurableApplicationContext context) {
	Parent root = null;
	URL url = null;
	ResourceBundle i18nBundle = null;
	try {
	    url = getClass().getResource("/fxml/MainWindow.fxml");
	    i18nBundle = ResourceBundle.getBundle("i18n.Bundle", Locale.getDefault());
	    root = FXMLLoader.load(url, i18nBundle, new JavaFXBuilderFactory(), context::getBean);
	} catch (Exception ex) {
	    System.out.println("Exception on FXMLLoader.load()");
	    System.out.println("  * url: " + url);
	    System.out.println("  * " + ex);
	    System.out.println("    ----------------------------------------\n");
	    return;
	}

	primaryStage.setTitle("Chronos");
	primaryStage.setScene(new Scene(root, 900, 600));
	primaryStage.show();

	// TODO: Remove later...
	// testSample(primaryStage);
    }

    @SuppressWarnings("unused")
    private void testSample(Stage primaryStage) {
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BasicTestUI.fxml"));
	    BorderPane root = loader.load();
	    SampleController controller = loader.getController();

	    Scene scene = new Scene(root, 300, controller.getWindowHeight());
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
