package bc.bg.tools.chronos.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
// @EnableConfigurationProperties
// TODO: Add views package here when implemented
// @ComponentScan
@Import(value = { LocalDBConfig.class, LocalDataProviderConfig.class, RemoteDBConfig.class,
	RemoteDataProviderConfig.class, I18nConfig.class })
public class ChronosApplication {

    public static void main(String[] args) throws Exception {
	SpringApplication.run(ChronosApplication.class, args);
    }
}
