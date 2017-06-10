package bc.bg.tools.chronos.configuration;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import bc.bg.tools.chronos.endpoint.ui.ChronosRoot;
import bc.bg.tools.chronos.endpoint.ui.ChronosView;
import bc.bg.tools.chronos.endpoint.ui.sample.MainViewController;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleController;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleRoot;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@Configuration
@Lazy
public class UIConfig {
    //
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

    @Bean(name = "sampleRoot")
    public ChronosRoot sampleRoot() {
	return new SampleRoot();
    }

    @Bean(name = "sampleView")
    public ChronosView sampleView() {
	return new SampleView(sampleRoot());
    }

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

	//TODO: Remove later...
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
