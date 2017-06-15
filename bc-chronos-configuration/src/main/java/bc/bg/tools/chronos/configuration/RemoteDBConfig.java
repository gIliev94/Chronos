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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import bitronix.tm.resource.jdbc.lrc.LrcXADataSource;

@Lazy
@Configuration
@PropertySource({ "classpath:remote-db.properties" })
// @EnableJpaRepositories(value =
// "bg.bc.tools.chronos.dataprovider.db.remote.repos", entityManagerFactoryRef =
// "remoteEntityManagerFactory", transactionManagerRef =
// "remoteTransactionManager")
@EnableJpaRepositories(value = "bg.bc.tools.chronos.dataprovider.db.remote.repos", entityManagerFactoryRef = "remoteEntityManagerFactory")
public class RemoteDBConfig {

    private static final String REMOTE_PERSISTENCE_UNIT = "remotePersistenceUnit";

    @Autowired
    private Environment env;

    @Bean(name = "remoteEntityManagerFactory")
    @DependsOn("btmConfig")
    public LocalContainerEntityManagerFactoryBean remoteEntityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	// factoryBean.setDataSource(this.remoteDataSource());
	// TODO: Set in case XA transactions don`t work
	factoryBean.setJtaDataSource(this.remoteDataSource());
	factoryBean.setPersistenceUnitName(REMOTE_PERSISTENCE_UNIT);
	factoryBean.setPackagesToScan(env.getProperty("remote.entities.lookup"));

	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	factoryBean.setJpaVendorAdapter(vendorAdapter);
	factoryBean.setJpaProperties(this.additionalProperties());

	return factoryBean;
    }

    private Properties additionalProperties() {
	Properties properties = new Properties();
	// TODO: Recreates schema on each run(change to more appropriate later)
	properties.setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty("remote.hibernate.hbm2ddl.auto"));
	properties.setProperty(AvailableSettings.DIALECT, env.getProperty("remote.hibernate.dialect"));
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

    // TODO: Old
    // @Bean(name = "remoteTransactionManager")
    // public PlatformTransactionManager remoteTransactionManager() throws
    // Exception {
    // JpaTransactionManager transactionManager = new JpaTransactionManager();
    // transactionManager.setEntityManagerFactory(this.remoteEntityManagerFactory().getObject());
    //
    // return transactionManager;
    // }

    // TODO: Need this??
    // @Bean
    // public PersistenceExceptionTranslationPostProcessor
    // exceptionTranslation() {
    // return new PersistenceExceptionTranslationPostProcessor();
    // }

    @Bean
    public DataSource remoteDataSource() throws Exception {
	// FIRST IMPL

	// DriverManagerDataSource mssqlDS = new
	// DriverManagerDataSource(env.getProperty("remote.jdbc.url"));
	// mssqlDS.setDriverClassName(env.getProperty("remote.jdbc.driverClassName"));
	// mssqlDS.setUsername(env.getProperty("remote.jdbc.user"));
	// mssqlDS.setPassword(env.getProperty("remote.jdbc.pass"));

	// SECOND IMPL
	// PoolingDataSource src = new PoolingDataSource();
	// src.setUniqueName("remoteDataSource");
	// src.setMinPoolSize(1);
	// src.setMaxPoolSize(4);
	//
	// Properties properties = new Properties();
	// // properties.setProperty("className",
	// // env.getProperty("remote.jdbc.driverClassName"));
	// properties.setProperty("driverClassName",
	// env.getProperty("remote.jdbc.driverClassName"));
	// properties.setProperty("url", env.getProperty("remote.jdbc.url"));
	// properties.setProperty("user", env.getProperty("remote.jdbc.user"));
	// properties.setProperty("password",
	// env.getProperty("remote.jdbc.pass"));
	// // properties.setProperty("allowLocalTransactions",
	// // Boolean.toString(true));
	// src.setDriverProperties(properties);
	//
	// src.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");

	// THIRD IMPL
	LrcXADataSource src2 = new LrcXADataSource();
	src2.setDriverClassName(env.getProperty("remote.jdbc.driverClassName"));
	src2.setUrl(env.getProperty("remote.jdbc.url"));
	src2.setUser(env.getProperty("remote.jdbc.user"));
	src2.setPassword(env.getProperty("remote.jdbc.pass"));

	PoolingDataSourceBean wrp = new PoolingDataSourceBean();
	wrp.setUniqueName("remoteDataSource");
	wrp.setBeanName("remoteDataSource");
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

	// return mssqlDS;
    }

    // TODO: Is necessary ???
    // @Bean
    // public Validator entityValidator() {
    // return Validation.buildDefaultValidatorFactory().getValidator();
    // }
    //
}
