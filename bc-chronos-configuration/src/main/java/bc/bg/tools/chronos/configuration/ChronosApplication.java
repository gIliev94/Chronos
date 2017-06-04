package bc.bg.tools.chronos.configuration;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
// TODO: Add views package here when implemented
@ComponentScan({ "bg.bc.tools.chronos.dataprovider.db.local.services",
	"bg.bc.tools.chronos.dataprovider.db.local.repos", "bc.bg.tools.chronos.endpoint.ui" })
@Import(value = { LocalDBConfig.class, LocalDataProviderConfig.class, RemoteDBConfig.class,
	RemoteDataProviderConfig.class, I18nConfig.class, UIConfig.class })
public class ChronosApplication extends Application {

    public static void main(String[] args) throws Exception {
	// SpringApplication.run(ChronosApplication.class, args);
	// launch(args);
	launch(ChronosApplication.class, args);
    }

    // @Autowired
    // private ChronosView sampleView;

    // @Autowired
    // private ApplicationContext context;

    @Override
    public void start(Stage primaryStage) throws Exception {
	// TODO: register shutdown hook to close this shit...

	final ConfigurableApplicationContext context = SpringApplication.run(ChronosApplication.class, new String[0]);
	System.err.println("CONTEXT :: " + context);

	// ApplicationContext context = new
	// AnnotationConfigApplicationContext(ChronosApplication.class);
	UIConfig uiConfiguration = context.getBean(UIConfig.class);
	uiConfiguration.showStartScreen(primaryStage);
    }

    @PreDestroy
    public void destroy() {
	System.err.println("Im inside destroy...");
    }
}
