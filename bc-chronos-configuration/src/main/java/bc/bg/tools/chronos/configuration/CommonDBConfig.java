package bc.bg.tools.chronos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;

@Configuration
public class CommonDBConfig {

    private static final String TX_INSTANCE_ID = "ChronosTM";

    @Bean(name = "btmConfig")
    public bitronix.tm.Configuration btmConfig() {
	final bitronix.tm.Configuration bitronixTxManagerConfig = TransactionManagerServices.getConfiguration();

	bitronixTxManagerConfig.setServerId(TX_INSTANCE_ID);
	bitronixTxManagerConfig.setAsynchronous2Pc(false);

	// TODO: Test with/without(suspecting they always register - msg beans)
	bitronixTxManagerConfig.setDisableJmx(true);
	// TODO: Two LRC sources are prohibited usually - disable this when you
	// fix impl...
	bitronixTxManagerConfig.setAllowMultipleLrc(true);

	// TODO: For debugging purposes, may be expensive on production -
	// consider removing...
	bitronixTxManagerConfig.setWarnAboutZeroResourceTransaction(true);
	bitronixTxManagerConfig.setDebugZeroResourceTransaction(true);

	return bitronixTxManagerConfig;
    }

    @Bean(name = "btm")
    @DependsOn("btmConfig")
    public BitronixTransactionManager btm() {
	final BitronixTransactionManager bitronixTxManager = TransactionManagerServices.getTransactionManager();
	return bitronixTxManager;
    }

    // Translates JPA Exceptions to Spring data access runtime exceptions
    // (https://stackoverflow.com/a/43585486)
    @Bean
    public PersistenceExceptionTranslationPostProcessor jpaExceptionTranslator() {
	final PersistenceExceptionTranslationPostProcessor excTranslator = new PersistenceExceptionTranslationPostProcessor();
	excTranslator.setFrozen(true);
	excTranslator.setOpaque(true);
	// excTranslator.setProxyTargetClass(proxyTargetClass);

	return excTranslator;
    }

    // TODO: Consider using this validation technique:
    // http://www.baeldung.com/javax-validation
    // https://stackoverflow.com/questions/23604540/spring-boot-how-to-properly-inject-javax-validation-validator
    // @Bean
    // public Validator entityValidator() {
    // return Validation.buildDefaultValidatorFactory().getValidator();
    // }

    // TODO: Common transaction manager - figure out how to autouse OR get
    // UserTransaction impl...
    // Does not seem to make a difference whether I intialize it or not...

    // @Bean(name = "transactionManager")
    // @Primary
    // public PlatformTransactionManager localTransactionManager() {
    // JtaTransactionManager transactionManager = new JtaTransactionManager();
    // transactionManager.setTransactionManager(btm());
    // transactionManager.setUserTransaction(btm());
    // // transactionManager.setAllowCustomIsolationLevels(true);
    //
    // return transactionManager;
    // }
}
