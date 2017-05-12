package bc.bg.tools.chronos.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:remote-db.properties" })
public class RemoteDBConfig {

    // private static final String HOST = "localhost";
    // private static final int PORT = 1433;
    // private static final String DB_NAME = "Chronos";
    // private static final String USER = "sa-boehringer";
    // private static final String PASSWORD = "1232";
    //
    // private static final String[] ENTITIES_LOOKUP = {
    // "bg.bc.tools.chronos.dataprovider.db.entities" };

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	factoryBean.setDataSource(this.dataSource());
	// factoryBean.setPackagesToScan(ENTITIES_LOOKUP);
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
	// SQLServerDialect.class.getName());
	properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));

	return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
	JpaTransactionManager transactionManager = new JpaTransactionManager();
	transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());

	return transactionManager;
    }

    // @Bean
    // public PersistenceExceptionTranslationPostProcessor
    // exceptionTranslation() {
    // return new PersistenceExceptionTranslationPostProcessor();
    // }

    @Bean
    public DataSource dataSource() throws Exception {
	// DriverManagerDataSource mssqlDS = new DriverManagerDataSource(
	// "jdbc:jtds:sqlserver://" + HOST + ":" + PORT + ";databaseName=" +
	// DB_NAME);
	// mssqlDS.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
	// mssqlDS.setUsername(USER);
	// mssqlDS.setPassword(PASSWORD);
	DriverManagerDataSource mssqlDS = new DriverManagerDataSource(env.getProperty("jdbc.url"));
	mssqlDS.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	mssqlDS.setUsername(env.getProperty("jdbc.user"));
	mssqlDS.setPassword(env.getProperty("jdbc.pass"));

	return mssqlDS;
    }

    // TODO: Is necessary ???
    // @Bean
    // public Validator entityValidator() {
    // return Validation.buildDefaultValidatorFactory().getValidator();
    // }
    //
}
