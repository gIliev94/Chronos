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
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@PropertySource({ "classpath:local-db.properties" })
@EnableJpaRepositories(value = "bg.bc.tools.chronos.dataprovider.db.local.repos", entityManagerFactoryRef = "localEntityManagerFactory")
public class LocalDBConfig {

    private static final String LOCAL_PERSISTENCE_UNIT = "chronosLocalPersistenceUnit";

    protected final String PROP_EM_ENTITY_LOOKUP = "local.entities.lookup";

    protected final String PROP_HIBERNATE_HBM2DDL_AUTO = "local.hibernate.hbm2ddl.auto";
    protected final String PROP_HIBERNATE_DIALECT = "local.hibernate.dialect";

    protected final String KEY_JPA_TX_TYPE = "javax.persistence.transactionType";
    protected final String KEY_JPA_SESSION_CTX_CLASS = "hibernate.current_session_context_class";
    protected final String KEY_JPA_TX_JTA_PLATFORM = "hibernate.transaction.jta.platform";

    // TODO: Extract to configurable properties as well?
    protected final String TX_TYPE_JTA = "JTA";
    protected final String CTX_CLASS_JTA = "jta";
    protected final String JTA_PLATFORM_BITRONIX = "org.hibernate.engine.transaction.jta.platform.internal.BitronixJtaPlatform";

    protected final String PROP_DB_LOCAL_JDBC_USER = "local.jdbc.user";
    protected final String PROP_DB_LOCAL_JDBC_PASS = "local.jdbc.pass";

    // protected final String DB_LOCAL_DIALECT =
    // "org.hibernate.dialect.DerbyDialect";
    // protected final String DB_LOCAL_DIALECT =
    // "org.hibernate.dialect.DerbyTenSevenDialect";
    protected final String DB_LOCAL_URL = "C:/Users/giliev/Desktop/derby";
    // protected final String DB_LOCAL_URL =
    // "C:/Users/aswor/Desktop/squirrelsql-3.8.0-standard/ChronosLocal";
    protected final String DB_LOCAL_DDL_AUTO = "create";

    // TODO: Lazy OR not???
    @Lazy
    @Autowired
    protected Environment env;

    @Bean(name = "localEntityManagerFactory")
    @DependsOn("btm")
    @Primary
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() throws Exception {
	final LocalContainerEntityManagerFactoryBean emFactoryBean = new LocalContainerEntityManagerFactoryBean();
	emFactoryBean.setPersistenceUnitName(LOCAL_PERSISTENCE_UNIT);
	emFactoryBean.setPackagesToScan(env.getProperty(PROP_EM_ENTITY_LOOKUP));
	emFactoryBean.setDataSource(localDataSource());
	// emFactoryBean.setJtaDataSource(this.localDataSource());

	final JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
	configureJpaVendorAdapter(jpaVendorAdapter);
	emFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

	final Properties jpaProperties = new Properties();
	configureJpaProperties(jpaProperties);
	emFactoryBean.setJpaProperties(jpaProperties);

	// emFactoryBean.setJpaDialect(JpaDialect);

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

	// TODO: Somewhat ok JTA config - can`t tell if it works - try w/wo...
	// http://www.adam-bien.com/roller/abien/entry/don_t_use_jpa_s
	jpaProperties.setProperty(KEY_JPA_TX_TYPE, TX_TYPE_JTA);
	jpaProperties.setProperty(KEY_JPA_SESSION_CTX_CLASS, CTX_CLASS_JTA);
	// jpaProperties.setProperty("hibernate.transaction.jta.platform",
	// "org.hibernate.service.jta.platform.internal.BitronixJtaPlatform");
	jpaProperties.setProperty(KEY_JPA_TX_JTA_PLATFORM, JTA_PLATFORM_BITRONIX);

	// TODO: DB multitenancy - irrevant in non-web app I think...
	// properties.setProperty(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,
	// "multitenancyConnectionProvider");
	// properties.setProperty(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
	// "tenantResolver");
	// properties.setProperty(AvailableSettings.MULTI_TENANT, "DATABASE");
    }

    @Bean(destroyMethod = "close") // https://stackoverflow.com/a/44757112
    @DependsOn("embeddedLocalDataSource")
    @Primary
    public DataSource localDataSource() throws Exception {
	// TODO: Try to close on Spring cleanup...
	final PoolingDataSourceBean poolingDataSrcBean = new PoolingDataSourceBean();
	poolingDataSrcBean.setDataSource(embeddedLocalDataSource());
	poolingDataSrcBean.setUniqueName("jdbc/local");
	// TODO: Pool only 1 - its local???
	poolingDataSrcBean.setMaxPoolSize(5);
	// TODO: These 2 don`t seem to be doing shit...

	poolingDataSrcBean.setAllowLocalTransactions(true);
	poolingDataSrcBean.setAutomaticEnlistingEnabled(true);
	// poolingDataSrcBean.setEnableJdbc4ConnectionTest(false);
	// poolingDs.setTestQuery("SELECT current_timestamp");

	// TODO: Try setting name HERE + BEANS + btm.props to be the same...
	// https://github.com/bitronix/btm/blob/master/btm-docs/src/main/asciidoc/JdbcConfiguration2x.adoc
	// http://web.archive.org/web/20150520175152/https://docs.codehaus.org/display/BTM/Hibernate2x#Hibernate2x-Applicationcode
	// poolingDataSrcBean.setShareTransactionConnections(true);
	// poolingDataSrcBean.setTwoPcOrderingPosition(2);
	// poolingDataSrcBean.setDeferConnectionRelease(true);
	// poolingDataSrcBean.setAcquisitionInterval(0);

	// TODO: Test without Pooling data source(just embedded for Derby)...
	// return embeddedLocalDataSource();
	// OR
	// return new BitronixXADataSourceWrapper(embeddedLocalDataSource());

	return poolingDataSrcBean;
    }

    @Bean
    public EmbeddedXADataSource embeddedLocalDataSource() {
	final EmbeddedXADataSource embeddedXaDataSrc = new EmbeddedXADataSource();
	embeddedXaDataSrc.setDatabaseName(DB_LOCAL_URL);
	embeddedXaDataSrc.setCreateDatabase(DB_LOCAL_DDL_AUTO);
	embeddedXaDataSrc.setUser(env.getProperty(PROP_DB_LOCAL_JDBC_USER));
	embeddedXaDataSrc.setPassword(env.getProperty(PROP_DB_LOCAL_JDBC_PASS));

	return embeddedXaDataSrc;
    }
}
