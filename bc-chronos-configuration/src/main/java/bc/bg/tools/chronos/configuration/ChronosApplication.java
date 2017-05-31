package bc.bg.tools.chronos.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import bc.bg.tools.chronos.endpoint.ui.ChronosScene;
import bc.bg.tools.chronos.endpoint.ui.ChronosView;
import bc.bg.tools.chronos.endpoint.ui.SampleView;
import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
@EnableTransactionManagement
// @EnableConfigurationProperties
// TODO: Add views package here when implemented
// @ComponentScan
@Import(value = { LocalDBConfig.class, LocalDataProviderConfig.class, RemoteDBConfig.class,
	RemoteDataProviderConfig.class, I18nConfig.class, UIConfig.class })
public class ChronosApplication extends Application {

    public static void main(String[] args) throws Exception {
	SpringApplication.run(ChronosApplication.class, args);
	launch(args);
    }

    // @Autowired
    // private ChronosView sampleView;

    @Override
    public void start(Stage primaryStage) throws Exception {
	ChronosView v = new SampleView();
	ChronosScene s = new ChronosScene(v);
	primaryStage = v;
	primaryStage.show();
    }
}
