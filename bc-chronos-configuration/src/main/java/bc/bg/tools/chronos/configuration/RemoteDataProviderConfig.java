package bc.bg.tools.chronos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteBillingRateModifierService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteBookingService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteCategoryService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteCustomerService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemotePerformerService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteProjectService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteRoleService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteTaskService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteBillingRateModifierService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteBookingService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteCategoryService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteCustomerService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemotePerformerService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteProjectService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteRoleService;
import bg.bc.tools.chronos.dataprovider.db.remote.services.impl.RemoteTaskService;

@Lazy
//@Configuration
public class RemoteDataProviderConfig {

    @Bean(name = "remoteCategoryService")
    public IRemoteCategoryService categoryService() {
	return new RemoteCategoryService();
    }

    @Bean(name = "remoteCustomerService")
    public IRemoteCustomerService customerService() {
	return new RemoteCustomerService();
    }

    @Bean(name = "remoteProjectService")
    public IRemoteProjectService projectService() {
	return new RemoteProjectService();
    }

    @Bean(name = "remoteTaskService")
    public IRemoteTaskService taskService() {
	return new RemoteTaskService();
    }

    @Bean(name = "remoteBillingRateModifierService")
    public IRemoteBillingRateModifierService billingRateModifierService() {
	return new RemoteBillingRateModifierService();
    }

    @Bean(name = "remotePerformerService")
    public IRemotePerformerService performerService() {
	return new RemotePerformerService();
    }

    @Bean(name = "remoteBookingService")
    public IRemoteBookingService bookingService() {
	return new RemoteBookingService();
    }

    @Bean(name = "remoteRoleService")
    public IRemoteRoleService roleService() {
	return new RemoteRoleService();
    }
}
