package bc.bg.tools.chronos.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IBillingRateService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IBookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ICategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IClientService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IPerformerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ITaskService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.BillingRateService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.BookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.CategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.ClientService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.PerformerService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.ProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.TaskService;

@Configuration
public class DataProviderConfig {

    @Bean
    public ICategoryService categoryService() {
	return new CategoryService();
    }

    @Bean
    public IClientService clientService() {
	return new ClientService();
    }

    @Bean
    public IProjectService projectService() {
	return new ProjectService();
    }

    @Bean
    public ITaskService taskService() {
	return new TaskService();
    }

    @Bean
    public IBillingRateService billingRateService() {
	return new BillingRateService();
    }

    @Bean
    public IPerformerService performerService() {
	return new PerformerService();
    }

    @Bean
    public IBookingService bookingService() {
	return new BookingService();
    }

    @Bean
    public MessageSource messageSource() throws Exception {
	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	messageSource.setBasename("messages");
	messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());

	return messageSource;
    }

    // // TODO: Predefined locales for DEV-MODE(remove later)
    // // @Bean("DE")
    // // public Locale localeGermany() throws Exception {
    // // Locale[] availableLocales = Locale.getAvailableLocales();
    // // for (Locale locale : availableLocales) {
    // // if (locale.getCountry().equals(Locale.GERMANY.getCountry())) {
    // // return locale;
    // // }
    // // }
    // //
    // // return Locale.getDefault();
    // // }
    //
    @Bean("EN-US")
    public Locale localeUSA() throws Exception {
	Locale[] availableLocales = Locale.getAvailableLocales();
	for (Locale locale : availableLocales) {
	    if (locale.getCountry().equals(Locale.US.getCountry())) {
		return locale;
	    }
	}

	return Locale.getDefault();
    }
    //
}
