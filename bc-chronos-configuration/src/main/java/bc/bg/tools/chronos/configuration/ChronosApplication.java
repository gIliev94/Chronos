package bc.bg.tools.chronos.configuration;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import bc.bg.tools.chronos.endpoint.ui.sample.SampleView;
import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement

// TODO: NEED? Add views package here when implemented
// @ComponentScan({"bc.bg.tools.chronos.endpoint.ui"})

@Import(value = { LocalDBConfig.class, LocalDataProviderConfig.class, I18nConfig.class, UIConfig.class })

// TODO: NEED? Full configuration of DBs +
// bc.bg.tools.chronos.configuration.ChronosMultitenancyResolver
// @Import(value = { LocalDBConfig.class, LocalDataProviderConfig.class,
// RemoteDBConfig.class,
// RemoteDataProviderConfig.class, I18nConfig.class, UIConfig.class })
public class ChronosApplication extends Application {

    public static void main(String[] args) throws Exception {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
	// TODO: NEED? Register shutdown hook to close this shit...
	final ConfigurableApplicationContext context = SpringApplication.run(ChronosApplication.class, new String[0]);

	UIConfig uiConfiguration = context.getBean(UIConfig.class);
	uiConfiguration.showStartScreen(primaryStage, context);
    }

    @PreDestroy
    public void destroy() {
	System.err.println("Pre-destroy beans...");
    }
}
