package bc.bg.tools.chronos.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import bg.bc.tools.chronos.dataprovider.i18n.IMessageService;
import bg.bc.tools.chronos.dataprovider.i18n.MessageService;

//TODO: May be redundant - i18n is actually done in the UI module...
@Configuration
public class I18nConfig {

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

    @Bean("msgService")
    public IMessageService messageService() {
	return new MessageService();
    }
}
