package bc.bg.tools.chronos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalBillingRateService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalBookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalClientService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalPerformerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalTaskService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalBillingRateService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalBookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalClientService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalPerformerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.LocalTaskService;

@Configuration
public class LocalDataProviderConfig {

    @Bean(name = "localCategoryService")
    public ILocalCategoryService categoryService() {
	return new LocalCategoryService();
    }

    @Bean(name = "localClientService")
    public ILocalClientService clientService() {
	return new LocalClientService();
    }

    @Bean(name = "localProjectService")
    public ILocalProjectService projectService() {
	return new LocalProjectService();
    }

    @Bean(name = "localTaskService")
    public ILocalTaskService taskService() {
	return new LocalTaskService();
    }

    @Bean(name = "localBillingRateService")
    public ILocalBillingRateService billingRateService() {
	return new LocalBillingRateService();
    }

    @Bean(name = "localPerformerService")
    public ILocalPerformerService performerService() {
	return new LocalPerformerService();
    }

    @Bean(name = "localBookingService")
    public ILocalBookingService bookingService() {
	return new LocalBookingService();
    }

}
