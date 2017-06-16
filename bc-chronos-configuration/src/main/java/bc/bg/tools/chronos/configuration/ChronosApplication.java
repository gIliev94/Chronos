package bc.bg.tools.chronos.configuration;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import bitronix.tm.TransactionManagerServices;
import javafx.application.Application;
import javafx.stage.Stage;

//@SpringBootApplication(exclude = { RemoteDBConfig.class, RemoteDataProviderConfig.class })
//@EnableAutoConfiguration(exclude = { RemoteDBConfig.class, RemoteDataProviderConfig.class })
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
// TODO: NEED? Add views package here when implemented
// @ComponentScan({"bc.bg.tools.chronos.endpoint.ui"})

// TODO: NEED? Full configuration of DBs +
// bc.bg.tools.chronos.configuration.ChronosMultitenancyResolver

// @Import(value = { LocalDBConfig.class, LocalDataProviderConfig.class,
// RemoteDBConfig.class,
// RemoteDataProviderConfig.class, I18nConfig.class, UIConfig.class })
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
	// TODO: NEED? Register shutdown hook to close this shit...
	final ConfigurableApplicationContext context = SpringApplication.run(ChronosApplication.class, new String[0]);
	final UIConfig uiConfiguration = context.getBean(UIConfig.class);

	uiConfiguration.showStartScreen(primaryStage, context);
    }

    @PreDestroy
    public void destroy() {
	// TODO: Maybe close transaction manager here - BTM or other??
	TransactionManagerServices.getTransactionManager().shutdown();

	System.err.println("Pre-destroy beans - do something before garbage collection?");
	LOGGER.info("Pre-destroy beans - do something before garbage collection?");
    }
}
