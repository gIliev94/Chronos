package bc.bg.tools.chronos.configuration;

import java.io.IOException;
import java.net.URL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import bc.bg.tools.chronos.endpoint.ui.ChronosRoot;
import bc.bg.tools.chronos.endpoint.ui.ChronosView;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleRoot;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void showStartScreen(Stage primaryStage) {
	Parent root = null;
	URL url = null;
	try {
	    // root = FXMLLoader.load(getClass().getClassLoader().getResource(
	    // "C:/Users/aswor/Documents/
	    // EclipseWorkspace/chronos/bc-chronos-endpoint/src/main/resources/DiplTestUI.fxml"));

	    // root =
	    // FXMLLoader.load(getClass().getClassLoader().getResource("/fxml/DiplTestUI.fxml"));

	    url = getClass().getResource("/fxml/BasicTestUI.fxml");
	    root = FXMLLoader.load(url);
	} catch (Exception ex) {
	    System.out.println("Exception on FXMLLoader.load()");
	    System.out.println("  * url: " + url);
	    System.out.println("  * " + ex);
	    System.out.println("    ----------------------------------------\n");
	    return;
	}

	primaryStage.setTitle("Hello World!");
	// primaryStage.setScene(sampleView());
	primaryStage.setScene(new Scene(root, 900, 600));
	primaryStage.show();
    }
}
