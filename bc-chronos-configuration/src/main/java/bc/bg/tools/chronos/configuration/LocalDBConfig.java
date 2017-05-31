package bc.bg.tools.chronos.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:local-db.properties" })
@EnableJpaRepositories(value = "bg.bc.tools.chronos.dataprovider.db.local.repos", entityManagerFactoryRef = "localEntityManagerFactory", transactionManagerRef = "localTransactionManager")
public class LocalDBConfig {

    @Autowired
    private Environment env;

    @Bean(name = "localEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	factoryBean.setDataSource(this.localDataSource());
	factoryBean.setPersistenceUnitName("localPersistenceUnit");
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

	// TODO:
	// properties.setProperty(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,
	// "multitenancyConnectionProvider");
	// properties.setProperty(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
	// "tenantResolver");
	// properties.setProperty(AvailableSettings.MULTI_TENANT, "DATABASE");

	return properties;
    }

    @Bean(name = "localTransactionManager")
    @Primary
    public PlatformTransactionManager localTransactionManager() throws Exception {
	JpaTransactionManager transactionManager = new JpaTransactionManager();
	transactionManager.setEntityManagerFactory(this.localEntityManagerFactory().getObject());

	return transactionManager;
    }

    // TODO: Need this??
    // @Bean
    // public PersistenceExceptionTranslationPostProcessor
    // exceptionTranslation() {
    // return new PersistenceExceptionTranslationPostProcessor();
    // }

    @Bean
    @Primary
    public DataSource localDataSource() throws Exception {
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
