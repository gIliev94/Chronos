package bc.bg.tools.chronos.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javafx.application.Application;
import javafx.stage.Stage;

//@SpringBootApplication
@EnableTransactionManagement
// @EnableConfigurationProperties
// TODO: Add views package here when implemented
// @ComponentScan
@Import(value = { LocalDBConfig.class, LocalDataProviderConfig.class, RemoteDBConfig.class,
	RemoteDataProviderConfig.class, I18nConfig.class, UIConfig.class })
public class ChronosApplication extends Application {

    public static void main(String[] args) throws Exception {
	// SpringApplication.run(ChronosApplication.class, args);
	launch(args);
    }

    // @Autowired
    // private ChronosView sampleView;

    // @Autowired
    // private ApplicationContext context;

    @Override
    public void start(Stage primaryStage) throws Exception {
	// TODO: register shutdown hook to close this shit...
	ApplicationContext context = new AnnotationConfigApplicationContext(ChronosApplication.class);
	UIConfig uiConfiguration = context.getBean(UIConfig.class);
	uiConfiguration.showStartScreen(primaryStage);
    }
}
