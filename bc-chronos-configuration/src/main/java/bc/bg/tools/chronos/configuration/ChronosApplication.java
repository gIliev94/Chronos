package bc.bg.tools.chronos.configuration;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javafx.application.Application;
import javafx.stage.Stage;

//TODO: NEED? Add views package here when implemented
//@ComponentScan({"bc.bg.tools.chronos.endpoint.ui"})

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@Import(value = { CommonDBConfig.class, // nl
	LocalDBConfig.class, LocalDataProviderConfig.class, // nl
	RemoteDBConfig.class, RemoteDataProviderConfig.class, // nl
	I18nConfig.class, UIConfig.class })
public class ChronosApplication extends Application {

    private static final Logger LOGGER = Logger.getLogger(ChronosApplication.class);

    public static void main(String[] args) throws Exception {
	launch(args);
    }

    // Entry point of the JavaFX app - starts the UI endless loop/thread => the
    // place to initialize Spring app context...
    @Override
    public void start(Stage primaryStage) throws Exception {
	final ConfigurableApplicationContext appContext = SpringApplication.run(ChronosApplication.class);
	// TODO: NEED? Register shutdown hook to close this shit...
	appContext.registerShutdownHook();

	final UIConfig uiConfiguration = appContext.getBean(UIConfig.class);
	uiConfiguration.showStartScreen(primaryStage, appContext);
    }

    @PreDestroy
    public void destroy() {
	// TODO: Do something here or???
	System.err.println("Pre-destroy beans - do something before garbage collection?");
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info("Pre-destroy beans - do something before garbage collection?");
	}
    }
}
