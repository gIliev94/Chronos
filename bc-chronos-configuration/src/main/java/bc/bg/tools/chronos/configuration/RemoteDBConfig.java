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
//import net.sourceforge.jtds.jdbcx.JtdsDataSource;

//TODO: Test w/wo lazy init...
@Lazy
@Configuration
@PropertySource({ "classpath:remote-db.properties" })

// TODO: Old config - does not use XA transactions...
// @EnableJpaRepositories(value =
// "bg.bc.tools.chronos.dataprovider.db.remote.repos", entityManagerFactoryRef =
// "remoteEntityManagerFactory", transactionManagerRef =
// "remoteTransactionManager")

// TODO: New config - at least attempts to use XA transactions...
@EnableJpaRepositories(value = "bg.bc.tools.chronos.dataprovider.db.remote.repos", entityManagerFactoryRef = "remoteEntityManagerFactory")
public class RemoteDBConfig {
    // TODO: Bad attempt of inheritance - remove later...
    // extends CommonDBConfig {

    private static final String REMOTE_PERSISTENCE_UNIT = "remotePersistenceUnit";

    @Autowired
    private Environment env;

    @Bean(name = "remoteEntityManagerFactory")
    @DependsOn("btmConfig")
    public LocalContainerEntityManagerFactoryBean remoteEntityManagerFactory() throws Exception {
	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

	// TODO: Set in case XA transactions don`t work - both needed??
	factoryBean.setDataSource(this.remoteDataSource());
	factoryBean.setJtaDataSource(this.remoteDataSource());
	// factoryBean.setDataSource(this.lrcRemoteDataSource());
	// factoryBean.setJtaDataSource(this.lrcRemoteDataSource());

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
	// TODO: Equivalent to Open-session-in-view
	// properties.setProperty(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS,
	// "");
	// TODO: Debug option...
	// properties.setProperty(AvailableSettings.SHOW_SQL, "");

	// TODO: Somewhat ok JTA config - can`t tell if it works - try w/wo...
	properties.setProperty("javax.persistence.transactionType", "JTA");
	properties.setProperty("hibernate.current_session_context_class", "jta");
	// <entry key="hibernate.transaction.manager_lookup_class"
	// value="org.hibernate.transaction.WebSphereExtendedJTATransactionLookup"/>

	// TODO: DB multitenancy - irrevant in non-web app I think...
	// properties.setProperty(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,
	// "multitenancyConnectionProvider");
	// properties.setProperty(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
	// "tenantResolver");
	// properties.setProperty(AvailableSettings.MULTI_TENANT, "DATABASE");

	return properties;
    }

    // TODO: Old impl - simple Hibernate/Jpa transactional manager(no XA)...
    // @Bean(name = "remoteTransactionManager")
    // public PlatformTransactionManager remoteTransactionManager() throws
    // Exception {
    // JpaTransactionManager transactionManager = new JpaTransactionManager();
    // transactionManager.setEntityManagerFactory(this.remoteEntityManagerFactory().getObject());
    //
    // return transactionManager;
    // }

    @Bean
    public LrcXADataSource lrcRemoteDataSource() {
	LrcXADataSource lrcXaDs = new LrcXADataSource();
	lrcXaDs.setDriverClassName(env.getProperty("remote.jdbc.driverClassName"));
	lrcXaDs.setUrl(env.getProperty("remote.jdbc.url"));
	lrcXaDs.setUser(env.getProperty("remote.jdbc.user"));
	lrcXaDs.setPassword(env.getProperty("remote.jdbc.pass"));

	return lrcXaDs;
    }

    // TODO: Fails for remote - should be done like this thought - 2 Lrc DS is
    // not advised...
    // http://bitronix-transaction-manager.10986.n7.nabble.com/Problem-with-Mysql-and-Mssql-td283.html#a284
    // http://bitronix-transaction-manager.10986.n7.nabble.com/Bitronix-Throws-Unknown-XA-Resource-tp397p398.html
    // @Bean
    // public JtdsDataSource lrcRemoteDataSource() {
    // JtdsDataSource src3 = new JtdsDataSource();
    // src3.setDatabaseName("Chronos");
    // src3.setPortNumber(1433);
    // src3.setServerName("localhost");
    // src3.setInstance("DESKTOP-HHOD44I\\SQLEXPRESS");;
    // // src3.setServerName("DESKTOP-HHOD44I\\SQLEXPRESS");
    // src3.setUser(env.getProperty("remote.jdbc.user"));
    // src3.setPassword(env.getProperty("remote.jdbc.pass"));
    // src3.setXaEmulation(true);
    //
    // return src3;
    // }

    @Bean
    @DependsOn("lrcRemoteDataSource")
    public DataSource remoteDataSource() throws Exception {
	PoolingDataSourceBean poolingDs = new PoolingDataSourceBean();
	poolingDs.setDataSource(lrcRemoteDataSource());
	poolingDs.setMinPoolSize(1);
	poolingDs.setMaxPoolSize(4);

	// TODO: Try setting name HERE+BEANS+btm.props to be the same...
	// poolingDs.setUniqueName("remoteDataSource");
	// poolingDs.setBeanName("remoteDataSource");
	poolingDs.setUniqueName("jdbc/remote");

	// TODO: Maybe remove...
	// poolingDs.setClassName("net.sourceforge.jtds.jdbcx.JtdsDataSource");

	// TODO: These 2 don`t seem to be doing shit...
	poolingDs.setAutomaticEnlistingEnabled(true);

	// TODO: Consider setting this to FALSE along with switching to
	// JtdsDataSource...
	poolingDs.setAllowLocalTransactions(true);

	// TODO: Play with these 2 if it ever gets to optimizing...
	// poolingDs.setShareTransactionConnections(true);
	// poolingDs.setTwoPcOrderingPosition(2);

	// TODO: Is this the more correct approach to use???
	// BitronixXADataSourceWrapper xaDsWrapper = new
	// BitronixXADataSourceWrapper();
	// final PoolingDataSourceBean poolingDs =
	// xaDsWrapper.wrapDataSource(lrcRemoteDataSource());
	// poolingDs.set
	// return poolingDs;

	return poolingDs;

    }

    // TODO: 2nd attempt - pooling DS( not very successful attempt)
    // @Bean
    // public DataSource remoteDataSource() throws Exception {
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

    // return src;
    // }

    // TODO: Original impl - no XA
    // @Bean
    // public DataSource remoteDataSource() throws Exception {
    // DriverManagerDataSource mssqlDS = new
    // DriverManagerDataSource(env.getProperty("remote.jdbc.url"));
    // mssqlDS.setDriverClassName(env.getProperty("remote.jdbc.driverClassName"));
    // mssqlDS.setUsername(env.getProperty("remote.jdbc.user"));
    // mssqlDS.setPassword(env.getProperty("remote.jdbc.pass"));
    // return mssqlDS;
    // }
}
