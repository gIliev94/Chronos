package bc.bg.tools.chronos.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IBookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IClientService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IProjectService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.BookingService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.ClientService;
import bg.bc.tools.chronos.dataprovider.db.local.services.impl.ProjectService;
import bg.bc.tools.chronos.dataprovider.i18n.IMessageService;
import bg.bc.tools.chronos.dataprovider.i18n.MessageService;

@Configuration
public class DataProviderConfig {

    @Bean
    public IClientService clientService() {
	return new ClientService();
    }

    @Bean
    public IProjectService projectService() {
	return new ProjectService();
    }

    @Bean
    public IBookingService bookingService() {
	return new BookingService();
    }

    @Bean
    public IMessageService messageService() {
	return new MessageService();
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
