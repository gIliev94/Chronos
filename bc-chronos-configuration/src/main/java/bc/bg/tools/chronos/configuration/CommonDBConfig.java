package bc.bg.tools.chronos.configuration;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.common.XAResourceProducer;

@Configuration
public class CommonDBConfig {

    @Bean(name = "btmConfig")
    public bitronix.tm.Configuration btmConfig() {
	final bitronix.tm.Configuration btmConfig = TransactionManagerServices.getConfiguration();
	// TODO: Test with/without(suspecting they always register - msg beans)
	btmConfig.setDisableJmx(true);

	// TODO: Explicit resource loader - does not work properly(path-wise
	// even)...
	// btmConfig.setResourceConfigurationFilename(
	// "C:/Users/aswor/Documents/EclipseWorkspace/chronos/bc-chronos-configuration/src/main/resources/btm.properties");

	// TODO: Two LRC sources are prohibited usually - disable this when you
	// fix impl...
	btmConfig.setAllowMultipleLrc(true);

	// TODO: Debug wise - remove later...
	btmConfig.setWarnAboutZeroResourceTransaction(true);
	btmConfig.setDebugZeroResourceTransaction(true);

	return btmConfig;
    }

    @Bean(name = "btm")
    // TODO: Require config bean to be processed beforehand...
    @DependsOn("btmConfig")
    public BitronixTransactionManager btm() {
	final BitronixTransactionManager btm = TransactionManagerServices.getTransactionManager();

	// TODO: Debug print - remove later...
	final Map<String, XAResourceProducer> initialResources = TransactionManagerServices.getResourceLoader()
		.getResources();
	System.err.println("INIT RESOURCES: " + initialResources);

	return btm;
    }

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
