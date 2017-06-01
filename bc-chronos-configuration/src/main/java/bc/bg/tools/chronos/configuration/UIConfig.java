package bc.bg.tools.chronos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import bc.bg.tools.chronos.endpoint.ui.ChronosRoot;
import bc.bg.tools.chronos.endpoint.ui.ChronosView;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleRoot;
import bc.bg.tools.chronos.endpoint.ui.sample.SampleView;
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
	primaryStage.setTitle("Hello World!");
	primaryStage.setScene(sampleView());
	primaryStage.show();
    }
}
