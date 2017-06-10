package bc.bg.tools.chronos.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Lazy
//@Configuration
@PropertySource({ "classpath:remote-db.properties" })
@EnableJpaRepositories(value = "bg.bc.tools.chronos.dataprovider.db.remote.repos", entityManagerFactoryRef = "remoteEntityManagerFactory", transactionManagerRef = "remoteTransactionManager")
public class RemoteDBConfig {

    @Autowired
    private Environment env;

    @Bean(name = "remoteEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean remoteEntityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	factoryBean.setDataSource(this.remoteDataSource());
	factoryBean.setPersistenceUnitName("remotePersistenceUnit");
	factoryBean.setPackagesToScan(env.getProperty("entities.lookup"));

	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	factoryBean.setJpaVendorAdapter(vendorAdapter);
	factoryBean.setJpaProperties(this.additionalProperties());

	return factoryBean;
    }

    private Properties additionalProperties() {
	Properties properties = new Properties();
	// TODO: Recreates schema on each run(change to more appropriate later)
	properties.setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
	properties.setProperty(AvailableSettings.DIALECT, env.getProperty("hibernate.dialect"));

	return properties;
    }

    @Bean(name = "remoteTransactionManager")
    public PlatformTransactionManager remoteTransactionManager() throws Exception {
	JpaTransactionManager transactionManager = new JpaTransactionManager();
	transactionManager.setEntityManagerFactory(this.remoteEntityManagerFactory().getObject());

	return transactionManager;
    }

    // TODO: Need this??
    // @Bean
    // public PersistenceExceptionTranslationPostProcessor
    // exceptionTranslation() {
    // return new PersistenceExceptionTranslationPostProcessor();
    // }

    @Bean
    public DataSource remoteDataSource() throws Exception {
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
