package bc.bg.tools.chronos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalBillingRateModifierService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalBookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalPerformerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalRoleService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalTaskService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalBillingRateModifierService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalBookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalCustomerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalPerformerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalRoleService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalTaskService;

@Lazy
@Configuration
public class LocalDataProviderConfig {

    @Bean(name = "localCategoryService")
    public ILocalCategoryService categoryService() {
	return new LocalCategoryService();
    }

    @Bean(name = "localCustomerService")
    public ILocalCustomerService customerService() {
	return new LocalCustomerService();
    }

    @Bean(name = "localProjectService")
    public ILocalProjectService projectService() {
	return new LocalProjectService();
    }

    @Bean(name = "localTaskService")
    public ILocalTaskService taskService() {
	return new LocalTaskService();
    }

    @Bean(name = "localBillingRateModifierService")
    public ILocalBillingRateModifierService billingRateModiferService() {
	return new LocalBillingRateModifierService();
    }

    @Bean(name = "localPerformerService")
    public ILocalPerformerService performerService() {
	return new LocalPerformerService();
    }

    @Bean(name = "localBookingService")
    public ILocalBookingService bookingService() {
	return new LocalBookingService();
    }

    @Bean(name = "localRoleService")
    public ILocalRoleService roleService() {
	return new LocalRoleService();
    }
}
