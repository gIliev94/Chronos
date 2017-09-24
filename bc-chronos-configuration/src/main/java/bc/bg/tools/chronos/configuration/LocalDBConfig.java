package bc.bg.tools.chronos.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedXADataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.bitronix.PoolingDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//TODO: Test w/wo lazy init...
//@Lazy
@Configuration
@PropertySource({ "classpath:local-db.properties" })

// TODO: Old config - does not use XA transactions...
// @EnableJpaRepositories(value =
// "bg.bc.tools.chronos.dataprovider.db.local.repos", entityManagerFactoryRef =
// "localEntityManagerFactory", transactionManagerRef =
// "localTransactionManager")

// TODO: New config - at least attempts to use XA transactions...
@EnableJpaRepositories(value = "bg.bc.tools.chronos.dataprovider.db.local.repos", entityManagerFactoryRef = "localEntityManagerFactory")
public class LocalDBConfig {
    // TODO: Bad attempt of inheritance - remove later...
    // extends CommonDBConfig {

    private static final String LOCAL_PERSISTENCE_UNIT = "localPersistenceUnit";

    @Autowired
    private Environment env;

    @Bean(name = "localEntityManagerFactory")
    @DependsOn("btm")
    @Primary
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	// TODO: Old impl - no XA data src...
	// factoryBean.setDataSource(this.localDataSource());

	// TODO: Set in case XA transactions don`t work - both needed??
	factoryBean.setDataSource(this.localDataSource());
	factoryBean.setJtaDataSource(this.localDataSource());

	factoryBean.setPersistenceUnitName(LOCAL_PERSISTENCE_UNIT);
	factoryBean.setPackagesToScan(env.getProperty("local.entities.lookup"));

	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	vendorAdapter.setDatabase(Database.DERBY);
	vendorAdapter.setDatabasePlatform("org.hibernate.dialect.DerbyDialect");
	vendorAdapter.setShowSql(true);

	factoryBean.setJpaVendorAdapter(vendorAdapter);

	factoryBean.setJpaProperties(this.additionalProperties());

	return factoryBean;
    }

    private Properties additionalProperties() {
	Properties properties = new Properties();

	// TODO: Recreates schema on each run(change to more appropriate later)
	properties.setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty("local.hibernate.hbm2ddl.auto"));
	properties.setProperty(AvailableSettings.DIALECT, env.getProperty("local.hibernate.dialect"));
	// TODO: Equivalent to Open-session-in-view
	// properties.setProperty(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS,
	// "");
	// TODO: Debug option...
	// properties.setProperty(AvailableSettings.SHOW_SQL, "");

	// TODO: Somewhat ok JTA config - can`t tell if it works - try w/wo...
	properties.setProperty("javax.persistence.transactionType", "JTA");
	properties.setProperty("hibernate.current_session_context_class", "jta");
	properties.setProperty("hibernate.transaction.jta.platform",
		"org.hibernate.service.jta.platform.internal.BitronixJtaPlatform");

	// TODO: DB multitenancy - irrevant in non-web app I think...
	// properties.setProperty(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,
	// "multitenancyConnectionProvider");
	// properties.setProperty(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
	// "tenantResolver");
	// properties.setProperty(AvailableSettings.MULTI_TENANT, "DATABASE");

	return properties;
    }

    // TODO: Old impl - simple Hibernate/Jpa transactional manager(no XA)...
    // @Bean(name = "localTransactionManager")
    // @Primary
    // public PlatformTransactionManager localTransactionManager() throws
    // Exception {
    // JpaTransactionManager transactionManager = new JpaTransactionManager();
    // transactionManager.setEntityManagerFactory(this.localEntityManagerFactory().getObject());
    //
    // return transactionManager;
    // }

    // TODO: What is this - do you need it???
    // @Bean
    // public PersistenceExceptionTranslationPostProcessor
    // exceptionTranslation() {
    // return new PersistenceExceptionTranslationPostProcessor();
    // }

    // @Bean
    // public LrcXADataSource lrcLocalDataSource() {
    // LrcXADataSource lrcXaDs = new LrcXADataSource();
    // lrcXaDs.setDriverClassName(env.getProperty("local.jdbc.driverClassName"));
    // lrcXaDs.setUrl(env.getProperty("local.jdbc.url"));
    // lrcXaDs.setUser(env.getProperty("local.jdbc.user"));
    // lrcXaDs.setPassword(env.getProperty("local.jdbc.pass"));
    //
    // return lrcXaDs;
    // }

    // TODO: APACHE DERBY TEST
    @Bean
    public EmbeddedXADataSource embeddedLocalDataSource() {
	EmbeddedXADataSource embeddedXaDs = new EmbeddedXADataSource();
	embeddedXaDs.setDatabaseName("C:/Users/aswor/Desktop/squirrelsql-3.8.0-standard/ChronosLocal");
	embeddedXaDs.setCreateDatabase("create");
	embeddedXaDs.setUser(env.getProperty("local.jdbc.user"));
	embeddedXaDs.setPassword(env.getProperty("local.jdbc.pass"));

	return embeddedXaDs;
    }
    //

    @Bean
    // @DependsOn("lrcLocalDataSource")
    @DependsOn("embeddedLocalDataSource")
    @Primary
    public DataSource localDataSource() throws Exception {
	// TODO: APACHE DERBY TEST
	PoolingDataSourceBean poolingDs = new PoolingDataSourceBean();
	poolingDs.setDataSource(embeddedLocalDataSource());
	poolingDs.setMaxPoolSize(5);
	poolingDs.setUniqueName("jdbc/local");
	poolingDs.setAutomaticEnlistingEnabled(true);
	poolingDs.setAllowLocalTransactions(true);
	//

	// PoolingDataSourceBean poolingDs = new PoolingDataSourceBean();
	// poolingDs.setDataSource(lrcLocalDataSource());
	// // TODO: No more than 1 connection for an SQLITE database...
	// poolingDs.setMinPoolSize(0);
	// poolingDs.setMaxPoolSize(1);
	//
	// // TODO: Try setting name HERE+BEANS+btm.props to be the same...
	// //
	// https://github.com/bitronix/btm/blob/master/btm-docs/src/main/asciidoc/JdbcConfiguration2x.adoc
	// //
	// http://web.archive.org/web/20150520175152/https://docs.codehaus.org/display/BTM/Hibernate2x#Hibernate2x-Applicationcode
	// // poolingDs.setUniqueName("localDataSource");
	// // poolingDs.setBeanName("localDataSource");
	// poolingDs.setUniqueName("jdbc/local");
	//
	// // TODO: Maybe remove...
	// //
	// poolingDs.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
	//
	// // TODO: These 2 don`t seem to be doing shit...
	// poolingDs.setAutomaticEnlistingEnabled(true);
	// // poolingDs.setAllowLocalTransactions(true);
	//
	// poolingDs.setEnableJdbc4ConnectionTest(false);
	// poolingDs.setTestQuery("SELECT current_timestamp");
	//
	// // final Properties p = new Properties();
	// // p.setProperty("journal_mode", "WAL");
	// // poolingDs.setDriverProperties(p);
	//
	// // TODO: Play with these 2 if it ever gets to optimizing...
	// // poolingDs.setShareTransactionConnections(true);
	// // poolingDs.setTwoPcOrderingPosition(2);
	// // poolingDs.setDeferConnectionRelease(true);
	// // poolingDs.setAcquisitionInterval(0);
	//
	// // TODO: Is this the more correct approach to use???
	// // BitronixXADataSourceWrapper xaDsWrapper = new
	// // BitronixXADataSourceWrapper();
	// // final PoolingDataSourceBean poolingDs =
	// // xaDsWrapper.wrapDataSource(lrcLocalDataSource());
	// // poolingDs.set
	// // return poolingDs;

	return poolingDs;
    }

    // TODO: 2nd attempt - pooling DS( not very successful attempt)
    // @Bean
    // @Primary
    // public DataSource localDataSource() throws Exception {
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
    // return src;
    // }

    // TODO: Original impl - no XA
    // @Bean
    // @Primary
    // public DataSource localDataSource() throws Exception {
    // // DriverManagerDataSource sqliteDs = new
    // // DriverManagerDataSource(env.getProperty("local.jdbc.url"));
    // //
    // sqliteDs.setDriverClassName(env.getProperty("local.jdbc.driverClassName"));
    // // sqliteDs.setUsername(env.getProperty("local.jdbc.user"));
    // // sqliteDs.setPassword(env.getProperty("local.jdbc.pass"));
    // // return sqliteDs;
    // }

    // TODO: What is this - do you really need it???
    // @Bean
    // public Validator entityValidator() {
    // return Validation.buildDefaultValidatorFactory().getValidator();
    // }
    //
}
