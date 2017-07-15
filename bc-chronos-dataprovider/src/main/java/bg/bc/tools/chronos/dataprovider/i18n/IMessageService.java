package bg.bc.tools.chronos.dataprovider.i18n;

import java.util.Locale;
import java.util.Map;

/**
 * Interface for I18n service.
 * 
 * @author giliev
 */
public interface IMessageService {
    // String i18n(String msgId, Object[] args);
    //
    // String i18n(String msgId);

    /**
     * @return A map of available locales as values(bean names / display names
     *         being the keys).
     */
    Map<String, Locale> getAvailableLocales();
}
