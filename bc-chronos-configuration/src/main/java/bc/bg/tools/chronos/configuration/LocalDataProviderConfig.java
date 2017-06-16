package bc.bg.tools.chronos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;

import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalBillingRateModifierService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalBookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalChangelogService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalPerformerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalRoleService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalTaskService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalBillingRateModifierService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalBookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalChangelogService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalCustomerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalPerformerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalRoleService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalTaskService;

//TODO: Test w/wo lazy init...
@Lazy
@Configuration
public class LocalDataProviderConfig {
    // TODO: Bad attempt of inheritance - remove later...
    // extends LocalDBConfig {

    @Bean(name = "localCategoryService")
    @DependsOn("transactionManager")
    public ILocalCategoryService categoryService() {
	return new LocalCategoryService();
    }

    @Bean(name = "localCustomerService")
    @DependsOn("transactionManager")
    public ILocalCustomerService customerService() {
	return new LocalCustomerService();
    }

    @Bean(name = "localProjectService")
    @DependsOn("transactionManager")
    public ILocalProjectService projectService() {
	return new LocalProjectService();
    }

    @Bean(name = "localTaskService")
    @DependsOn("transactionManager")
    public ILocalTaskService taskService() {
	return new LocalTaskService();
    }

    @Bean(name = "localBillingRateModifierService")
    @DependsOn("transactionManager")
    public ILocalBillingRateModifierService billingRateModiferService() {
	return new LocalBillingRateModifierService();
    }

    @Bean(name = "localPerformerService")
    @DependsOn("transactionManager")
    public ILocalPerformerService performerService() {
	return new LocalPerformerService();
    }

    @Bean(name = "localBookingService")
    @DependsOn("transactionManager")
    public ILocalBookingService bookingService() {
	return new LocalBookingService();
    }

    @Bean(name = "localRoleService")
    @DependsOn("transactionManager")
    public ILocalRoleService roleService() {
	return new LocalRoleService();
    }

    @Bean(name = "localChangelogService")
    @DependsOn("transactionManager")
    public ILocalChangelogService changelogService() {
	return new LocalChangelogService();
    }

    // TODO: Configure this properly
    // http://www.javaworld.com/article/2077714/java-web-development/xa-transactions-using-spring.html?page=3

    // @Bean(name = "localCustTransactionProxy")
    // @DependsOn("transactionManager")
    // @Scope(proxyMode = ScopedProxyMode.INTERFACES)
    // public TransactionProxyFactoryBean localCustTransactionProxy() {
    // TransactionProxyFactoryBean abstractProxy = new
    // TransactionProxyFactoryBean();
    // abstractProxy.setTransactionManager(localTransactionManager());
    // abstractProxy.setTarget(customerService());
    // // abstractProxy.setProxyTargetClass(true);
    //
    // Properties properties = new Properties();
    // properties.setProperty("get", "PROPAGATION_SUPPORTS,readOnly");
    // properties.setProperty("find", "PROPAGATION_SUPPORTS,readOnly");
    // properties.setProperty("is", "PROPAGATION_SUPPORTS,readOnly");
    // properties.setProperty("*", "PROPAGATION_REQUIRED");
    // abstractProxy.setTransactionAttributes(properties);
    //
    // return abstractProxy;
    // }
}
