package bc.bg.tools.chronos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteBillingRateService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteBookingService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteCategoryService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteClientService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemotePerformerService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteProjectService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteTaskService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteBillingRateService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteBookingService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteCategoryService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteClientService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemotePerformerService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteProjectService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteTaskService;

@Configuration
public class RemoteDataProviderConfig {

    @Bean(name = "remoteCategoryService")
    public IRemoteCategoryService categoryService() {
	return new RemoteCategoryService();
    }

    @Bean(name = "remoteClientService")
    public IRemoteClientService clientService() {
	return new RemoteClientService();
    }

    @Bean(name = "remoteProjectService")
    public IRemoteProjectService projectService() {
	return new RemoteProjectService();
    }

    @Bean(name = "remoteTaskService")
    public IRemoteTaskService taskService() {
	return new RemoteTaskService();
    }

    @Bean(name = "remoteBillingRateService")
    public IRemoteBillingRateService billingRateService() {
	return new RemoteBillingRateService();
    }

    @Bean(name = "remotePerformerService")
    public IRemotePerformerService performerService() {
	return new RemotePerformerService();
    }

    @Bean(name = "remoteBookingService")
    public IRemoteBookingService bookingService() {
	return new RemoteBookingService();
    }

}
