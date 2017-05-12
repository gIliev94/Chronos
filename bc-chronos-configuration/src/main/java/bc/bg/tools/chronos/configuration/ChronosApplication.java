package bc.bg.tools.chronos.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// @EnableConfigurationProperties
@EnableTransactionManagement
// TODO: Move to db configs...
@EnableJpaRepositories("bg.bc.tools.chronos.dataprovider.db.repos")
// TODO: Add views package here when implemented
// @ComponentScan
@Import(value = { LocalDBConfig.class, RemoteDBConfig.class, DataProviderConfig.class })
public class ChronosApplication {

    public static void main(String[] args) throws Exception {
	SpringApplication.run(ChronosApplication.class, args);
    }

}
