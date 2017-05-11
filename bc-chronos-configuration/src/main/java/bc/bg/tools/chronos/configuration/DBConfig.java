package bc.bg.tools.chronos.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.SQLServerDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DBConfig {

    private static final String HOST = "localhost";
    private static final int PORT = 1433;
    private static final String DB_NAME = "Chronos";
    private static final String USER = "sa-boehringer";
    private static final String PASSWORD = "1232";

    private static final String[] ENTITIES_LOOKUP = { "bg.bc.tools.chronos.dataprovider.db.entities" };

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	factoryBean.setDataSource(this.dataSource());
	factoryBean.setPackagesToScan(ENTITIES_LOOKUP);

	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	factoryBean.setJpaVendorAdapter(vendorAdapter);
	factoryBean.setJpaProperties(this.additionalProperties());

	return factoryBean;
    }

    private Properties additionalProperties() {
	Properties properties = new Properties();
	// TODO: Recreates schema on each run(change to more appropriate later)
	// properties.setProperty("hibernate.hbm2ddl.auto", "update");
	properties.setProperty("hibernate.hbm2ddl.auto", "create");
	properties.setProperty("hibernate.dialect", SQLServerDialect.class.getName());

	return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
	JpaTransactionManager transactionManager = new JpaTransactionManager();
	transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());

	return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DataSource dataSource() throws Exception {
	DriverManagerDataSource mssqlDS = new DriverManagerDataSource(
		"jdbc:jtds:sqlserver://" + HOST + ":" + PORT + ";databaseName=" + DB_NAME);
	mssqlDS.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
	mssqlDS.setUsername(USER);
	mssqlDS.setPassword(PASSWORD);

	return mssqlDS;
    }

    // TODO: Is necessary ???
    // @Bean
    // public Validator entityValidator() {
    // return Validation.buildDefaultValidatorFactory().getValidator();
    // }
    //
}
