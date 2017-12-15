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
import net.sourceforge.jtds.jdbcx.JtdsDataSource;

@Configuration
@PropertySource({ "classpath:remote-db.properties" })
@EnableJpaRepositories(value = "bg.bc.tools.chronos.dataprovider.db.remote.repos", entityManagerFactoryRef = "remoteEntityManagerFactory")
public class RemoteDBConfig {

    private static final String REMOTE_PERSISTENCE_UNIT = "chronosRemotePersistenceUnit";

    protected final String PROP_EM_ENTITY_LOOKUP = "remote.entities.lookup";

    protected final String PROP_HIBERNATE_HBM2DDL_AUTO = "remote.hibernate.hbm2ddl.auto";
    protected final String PROP_HIBERNATE_DIALECT = "remote.hibernate.dialect";

    protected final String KEY_JPA_TX_TYPE = "javax.persistence.transactionType";
    protected final String KEY_JPA_SESSION_CTX_CLASS = "hibernate.current_session_context_class";
    protected final String KEY_JPA_TX_JTA_PLATFORM = "hibernate.transaction.jta.platform";

    // TODO: Extract to configurable properties as well?
    protected final String TX_TYPE_JTA = "JTA";
    protected final String CTX_CLASS_JTA = "jta";
    protected final String JTA_PLATFORM_BITRONIX = "org.hibernate.engine.transaction.jta.platform.internal.BitronixJtaPlatform";

    protected final String PROP_DB_REMOTE_JDBC_DRIVER = "remote.jdbc.driverClassName";
    protected final String PROP_DB_REMOTE_JDBC_USER = "remote.jdbc.user";
    protected final String PROP_DB_REMOTE_JDBC_PASS = "remote.jdbc.pass";
    protected final String PROP_DB_REMOTE_JDBC_URL = "remote.jdbc.url";

    @Autowired
    private Environment env;

    @Bean(name = "remoteEntityManagerFactory")
    @DependsOn("btmConfig")
    public LocalContainerEntityManagerFactoryBean remoteEntityManagerFactory() throws Exception {
	final LocalContainerEntityManagerFactoryBean emFactoryBean = new LocalContainerEntityManagerFactoryBean();
	emFactoryBean.setPersistenceUnitName(REMOTE_PERSISTENCE_UNIT);
	emFactoryBean.setPackagesToScan(env.getProperty(PROP_EM_ENTITY_LOOKUP));
	emFactoryBean.setDataSource(remoteDataSource());
	// emFactoryBean.setJtaDataSource(this.localDataSource());

	final JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
	emFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

	final Properties jpaProperties = new Properties();
	configureJpaProperties(jpaProperties);
	emFactoryBean.setJpaProperties(jpaProperties);

	return emFactoryBean;
    }

    protected void configureJpaVendorAdapter(final JpaVendorAdapter jpaVendorAdapter) {
	// https://stackoverflow.com/a/22742293
	// jpaVendorAdapter.setDatabasePlatform(DB_LOCAL_DIALECT);
	// TODO: Profile this via Spring with DEV/PROD being true/false...
	((HibernateJpaVendorAdapter) jpaVendorAdapter).setShowSql(true);
    }

    protected void configureJpaProperties(final Properties jpaProperties) {
	// TODO: Recreates schema on each run(change to more appropriate later)
	jpaProperties.setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
	jpaProperties.setProperty(AvailableSettings.DIALECT, env.getProperty(PROP_HIBERNATE_DIALECT));
	// TODO: Equivalent to Open-session-in-view
	// properties.setProperty(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS,
	// "");
	// TODO: Debug option...
	// properties.setProperty(AvailableSettings.SHOW_SQL, "");

	jpaProperties.setProperty(KEY_JPA_TX_TYPE, TX_TYPE_JTA);
	jpaProperties.setProperty(KEY_JPA_SESSION_CTX_CLASS, CTX_CLASS_JTA);
	// jpaProperties.setProperty("hibernate.transaction.jta.platform",
	// "org.hibernate.service.jta.platform.internal.BitronixJtaPlatform");
	jpaProperties.setProperty(KEY_JPA_TX_JTA_PLATFORM, JTA_PLATFORM_BITRONIX);
    }

    @Bean
    @DependsOn("lrcRemoteDataSource")
    public DataSource remoteDataSource() throws Exception {
	final PoolingDataSourceBean poolingDataSrc = new PoolingDataSourceBean();
	poolingDataSrc.setDataSource(lrcRemoteDataSource());
	poolingDataSrc.setUniqueName("jdbc/remote");
	// TODO: Definitely increase pool size...
	poolingDataSrc.setMinPoolSize(1);
	poolingDataSrc.setMaxPoolSize(4);

	// TODO: Maybe remove...
	// poolingDs.setClassName("net.sourceforge.jtds.jdbcx.JtdsDataSource");

	// TODO: Consider setting this to FALSE along with switching to
	// JtdsDataSource...
	poolingDataSrc.setAllowLocalTransactions(true);
	poolingDataSrc.setAutomaticEnlistingEnabled(true);
	// poolingDataSrcBean.setEnableJdbc4ConnectionTest(false);
	// poolingDs.setTestQuery("SELECT current_timestamp");

	// TODO: Try setting name HERE + BEANS + btm.props to be the same...
	// https://github.com/bitronix/btm/blob/master/btm-docs/src/main/asciidoc/JdbcConfiguration2x.adoc
	// http://web.archive.org/web/20150520175152/https://docs.codehaus.org/display/BTM/Hibernate2x#Hibernate2x-Applicationcode
	// poolingDataSrcBean.setShareTransactionConnections(true);
	// poolingDataSrcBean.setTwoPcOrderingPosition(2);
	// poolingDataSrcBean.setDeferConnectionRelease(true);
	// poolingDataSrcBean.setAcquisitionInterval(0);

	// TODO: Test without Pooling data source(just JTDS for SQL Server)...
	return jtdsRemoteDataSource();
	// OR
	// return new BitronixXADataSourceWrapper(jtdsRemoteDataSource());

	// return poolingDataSrc;

    }

    @Bean
    public LrcXADataSource lrcRemoteDataSource() {
	final LrcXADataSource lrcXaDataSrc = new LrcXADataSource();
	lrcXaDataSrc.setDriverClassName(env.getProperty(PROP_DB_REMOTE_JDBC_DRIVER));
	lrcXaDataSrc.setUrl(env.getProperty(PROP_DB_REMOTE_JDBC_URL));
	lrcXaDataSrc.setUser(env.getProperty(PROP_DB_REMOTE_JDBC_USER));
	lrcXaDataSrc.setPassword(env.getProperty(PROP_DB_REMOTE_JDBC_PASS));

	return lrcXaDataSrc;
    }

    // TODO: Fails for remote - should be done like this thought - 2 Lrc DS is
    // not advised...
    // http://bitronix-transaction-manager.10986.n7.nabble.com/Problem-with-Mysql-and-Mssql-td283.html#a284
    // http://bitronix-transaction-manager.10986.n7.nabble.com/Bitronix-Throws-Unknown-XA-Resource-tp397p398.html
    // https://sourceforge.net/p/jtds/discussion/104389/thread/1de4dd1f/
    // https://gist.github.com/aziz781/1321979
    // https://stackoverflow.com/questions/1080354/how-do-you-configure-a-datasource-in-java-to-connect-to-ms-sql-server
    @Lazy
    @Bean
    public JtdsDataSource jtdsRemoteDataSource() {
	final JtdsDataSource jtdsDataSrc = new JtdsDataSource();
	jtdsDataSrc.setDatabaseName("Chronos");
	jtdsDataSrc.setPortNumber(1433);
	jtdsDataSrc.setServerName("localhost");
	// jtdsDataSrc.setServerName("DESKTOP-HHOD44I\\SQLEXPRESS");
	jtdsDataSrc.setInstance("BC-FSTRUM");

	jtdsDataSrc.setUser(env.getProperty(PROP_DB_REMOTE_JDBC_USER));
	jtdsDataSrc.setPassword(env.getProperty(PROP_DB_REMOTE_JDBC_PASS));

	// TODO: Try out more config props...
	jtdsDataSrc.setLastUpdateCount(true);
	jtdsDataSrc.setAutoCommit(true);
	jtdsDataSrc.setXaEmulation(true);
	jtdsDataSrc.setAppName("Chronos");
	// jtdsDataSrc.set...

	return jtdsDataSrc;
    }
}
