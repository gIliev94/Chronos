package bc.bg.tools.chronos.configuration;

import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import bg.bc.tools.chronos.dataprovider.i18n.IMessageService;
import bg.bc.tools.chronos.dataprovider.i18n.MessageService;

/**
 * Configuration class for I18n related beans.
 * 
 * @author giliev
 */
@Lazy
@Configuration
public class I18nConfig {

    // @Bean
    // public MessageSource messageSource() throws Exception {
    // ResourceBundleMessageSource messageSource = new
    // ResourceBundleMessageSource();
    // messageSource.setBasename("messages");
    // messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
    //
    // return messageSource;
    // }

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public IMessageService messageService() {
	return new MessageService(applicationContext.getBeansOfType(Locale.class));
    }

    @Bean("EN-US")
    public Locale localeUSA() throws Exception {
	return tryFindInstalledLocale(Locale.US);
    }

    @Bean("DE")
    public Locale localeGermany() throws Exception {
	return tryFindInstalledLocale(Locale.GERMANY);
    }

    /**
     * Attempts to find the specified locale in the system`s list of installed
     * locales and returns it(default locale if not found).
     * 
     * @param locale
     *            - the locale to check for in all installed.
     * @return The locale if installed, else the default system locale.
     */
    protected Locale tryFindInstalledLocale(Locale locale) {
	final Locale[] installedLocales = Locale.getAvailableLocales();
	for (Locale installedLocale : installedLocales) {
	    if (Objects.equals(installedLocale.getCountry(), locale.getCountry())) {
		return installedLocale;
	    }
	}

	return Locale.getDefault();
    }

}
