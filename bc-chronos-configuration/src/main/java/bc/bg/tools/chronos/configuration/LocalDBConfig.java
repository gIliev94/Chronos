package bc.bg.tools.chronos.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:local-db.properties" })
public class LocalDBConfig {

    // private static final String DB_NAME = "ChronosLocalDB";
    // private static final String USER = "admin";
    // private static final String PASSWORD = "admin";
    //
    // private static final String[] ENTITIES_LOOKUP = {
    // "bg.bc.tools.chronos.dataprovider.db.entities" };

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	factoryBean.setDataSource(this.dataSource());
	factoryBean.setPackagesToScan(env.getProperty("entities.lookup"));

	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	factoryBean.setJpaVendorAdapter(vendorAdapter);
	factoryBean.setJpaProperties(this.additionalProperties());

	return factoryBean;
    }

    private Properties additionalProperties() {
	Properties properties = new Properties();
	// TODO: Recreates schema on each run(change to more appropriate later)
	// properties.setProperty("hibernate.hbm2ddl.auto", "update");
	// properties.setProperty("hibernate.hbm2ddl.auto", "create");
	// properties.setProperty("hibernate.dialect",
	// SQLiteDialect.class.getName());
	properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));

	return properties;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() throws Exception {
	JpaTransactionManager transactionManager = new JpaTransactionManager();
	transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());

	return transactionManager;
    }

    // @Bean(name = "localDbSrc")
    // @Qualifier("localDbSrc")
    @Bean
    @Primary
    public DataSource dataSource() throws Exception {
	DriverManagerDataSource sqliteDs = new DriverManagerDataSource(env.getProperty("jdbc.url"));
	sqliteDs.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	sqliteDs.setUsername(env.getProperty("jdbc.user"));
	sqliteDs.setPassword(env.getProperty("jdbc.pass"));

	return sqliteDs;
    }

    // TODO: Is necessary ???
    // @Bean
    // public Validator entityValidator() {
    // return Validation.buildDefaultValidatorFactory().getValidator();
    // }
    //
}
