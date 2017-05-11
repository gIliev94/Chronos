package bc.bg.tools.chronos.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableAutoConfiguration
@SpringBootApplication
// @EnableConfigurationProperties
//
@EnableTransactionManagement
// @EnableJpaRepositories("bg.bc.tools.chronos.dataprovider.db.repos")
//
// @ComponentScan
@Import(value = { DBConfig.class })
// @Import(value = { DBConfig.class, DataProviderConfig.class })
public class ChronosApplication {

    public static void main(String[] args) throws Exception {
	SpringApplication.run(ChronosApplication.class, args);
    }

}
