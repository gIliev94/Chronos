package bg.bc.tools.chronos.dataprovider.i18n;

import java.util.Locale;
import java.util.Map;

/**
 * Implementation of an I18n service.
 * 
 * @author giliev
 */
public class MessageService implements IMessageService {

    // @Autowired
    // private MessageSource messageSource;

    private Map<String, Locale> availableLocales;

    public MessageService(Map<String, Locale> availableLocales) {
	this.availableLocales = availableLocales;
    }

    @Override
    public Map<String, Locale> getAvailableLocales() {
	return availableLocales;
    }

    // public static class LocaleChoice {
    // private String name;
    // private Locale locale;
    // }

    // @Override
    // public String i18n(String msgId, Object[] args) {
    // return messageSource.getMessage(msgId, args, locale);
    // }
    //
    // @Override
    // public String i18n(String msgId) {
    // return messageSource.getMessage(msgId, null, locale);
    // }
}
