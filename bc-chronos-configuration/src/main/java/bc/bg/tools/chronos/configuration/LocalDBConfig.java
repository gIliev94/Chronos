package bc.bg.tools.chronos.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.bitronix.PoolingDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.lrc.LrcXADataSource;

@Lazy
@Configuration
@PropertySource({ "classpath:local-db.properties" })
// @EnableJpaRepositories(value =
// "bg.bc.tools.chronos.dataprovider.db.local.repos", entityManagerFactoryRef =
// "localEntityManagerFactory", transactionManagerRef =
// "localTransactionManager")
@EnableJpaRepositories(value = "bg.bc.tools.chronos.dataprovider.db.local.repos", entityManagerFactoryRef = "localEntityManagerFactory")
public class LocalDBConfig {

    private static final String LOCAL_PERSISTENCE_UNIT = "localPersistenceUnit";

    @Autowired
    private Environment env;

    @Bean(name = "localEntityManagerFactory")
    @DependsOn("btm")
    @Primary
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	// factoryBean.setDataSource(this.localDataSource());
	// TODO: Set in case XA transactions don`t work
	factoryBean.setJtaDataSource(this.localDataSource());
	factoryBean.setPersistenceUnitName(LOCAL_PERSISTENCE_UNIT);
	factoryBean.setPackagesToScan(env.getProperty("local.entities.lookup"));

	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	factoryBean.setJpaVendorAdapter(vendorAdapter);
	factoryBean.setJpaProperties(this.additionalProperties());

	return factoryBean;
    }

    private Properties additionalProperties() {
	Properties properties = new Properties();
	// TODO: Recreates schema on each run(change to more appropriate later)
	properties.setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty("local.hibernate.hbm2ddl.auto"));
	properties.setProperty(AvailableSettings.DIALECT, env.getProperty("local.hibernate.dialect"));
	// properties.setProperty(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS,
	// "");
	// properties.setProperty(AvailableSettings.SHOW_SQL, "");

	properties.setProperty("javax.persistence.transactionType", "JTA");

	// <entry key="hibernate.transaction.manager_lookup_class"
	// value="org.hibernate.transaction.WebSphereExtendedJTATransactionLookup"/>

	// TODO: ???
	properties.setProperty("hibernate.current_session_context_class", "jta");

	// TODO:
	// properties.setProperty(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,
	// "multitenancyConnectionProvider");
	// properties.setProperty(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
	// "tenantResolver");
	// properties.setProperty(AvailableSettings.MULTI_TENANT, "DATABASE");

	return properties;
    }

    // @Bean(name = "localTransactionManager")
    // @Primary
    // public PlatformTransactionManager localTransactionManager() throws
    // Exception {
    // JpaTransactionManager transactionManager = new JpaTransactionManager();
    // transactionManager.setEntityManagerFactory(this.localEntityManagerFactory().getObject());
    //
    // return transactionManager;
    // }

    @Bean(name = "btmConfig")
    public bitronix.tm.Configuration btmConfig() {
	final bitronix.tm.Configuration btmConfig = TransactionManagerServices.getConfiguration();
	btmConfig.setDisableJmx(true);
	return btmConfig;
    }

    @Bean(name = "btm")
    @DependsOn("btmConfig")
    public BitronixTransactionManager btm() {
	final BitronixTransactionManager btm = TransactionManagerServices.getTransactionManager();
	// TransactionManagerServices.getTaskScheduler()
	// TransactionManagerServices.getResourceLoader().getResources()
	return btm;
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager localTransactionManager() throws Exception {
	// final BitronixTransactionManager btm = btm();
	JtaTransactionManager transactionManager = new JtaTransactionManager();
	transactionManager.setTransactionManager(btm());
	transactionManager.setUserTransaction(btm());
	transactionManager.setAllowCustomIsolationLevels(true);

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
	// FIRST IMPL
	// DriverManagerDataSource sqliteDs = new
	// DriverManagerDataSource(env.getProperty("local.jdbc.url"));
	// sqliteDs.setDriverClassName(env.getProperty("local.jdbc.driverClassName"));
	// sqliteDs.setUsername(env.getProperty("local.jdbc.user"));
	// sqliteDs.setPassword(env.getProperty("local.jdbc.pass"));

	// SECOND IMPL
	// PoolingDataSource src = new PoolingDataSource();
	// src.setUniqueName("localDataSource");
	// src.setMinPoolSize(1);
	// src.setMaxPoolSize(4);
	//
	// Properties properties = new Properties();
	// // properties.setProperty("className",
	// // env.getProperty("local.jdbc.driverClassName"));
	// properties.setProperty("driverClassName",
	// env.getProperty("local.jdbc.driverClassName"));
	// properties.setProperty("url", env.getProperty("local.jdbc.url"));
	// properties.setProperty("user", env.getProperty("local.jdbc.user"));
	// properties.setProperty("password",
	// env.getProperty("local.jdbc.pass"));
	// // properties.setProperty("allowLocalTransactions",
	// // Boolean.toString(true));
	// src.setDriverProperties(properties);
	//
	// final Object prop = src.getDriverProperties().get("className");
	// System.err.println(prop);
	//
	// src.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");

	// THIRD IMPL
	LrcXADataSource src2 = new LrcXADataSource();
	src2.setDriverClassName(env.getProperty("local.jdbc.driverClassName"));
	src2.setUrl(env.getProperty("local.jdbc.url"));
	src2.setUser(env.getProperty("local.jdbc.user"));
	src2.setPassword(env.getProperty("local.jdbc.pass"));

	PoolingDataSourceBean wrp = new PoolingDataSourceBean();
	wrp.setUniqueName("localDataSource");
	wrp.setBeanName("localDataSource");
	// wrp.setClassName(className);
	wrp.setAutomaticEnlistingEnabled(true);
	wrp.setAllowLocalTransactions(true);
	wrp.setDataSource(src2);
	wrp.setMinPoolSize(1);
	wrp.setMaxPoolSize(4);
	// wrp.setShareTransactionConnections(true);
	// wrp.setTwoPcOrderingPosition(2);

	// FOURTH IMPL
	// XADataSourceWrapper wrpr = new BitronixXADataSourceWrapper();
	// return wrpr.wrapDataSource(src2);

	return wrp;

	// return src;

	// return sqliteDs;
    }

    // TODO: Is necessary ???
    // @Bean
    // public Validator entityValidator() {
    // return Validation.buildDefaultValidatorFactory().getValidator();
    // }
    //
}
