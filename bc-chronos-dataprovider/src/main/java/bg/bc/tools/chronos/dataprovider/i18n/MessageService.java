package bg.bc.tools.chronos.dataprovider.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;

public class MessageService implements IMessageService {

    @Autowired
    private MessageSource messages;

    @Autowired
    @Qualifier("EN-US")
    private Locale locale;

    @Override
    public String i18n(String msgId, Object[] args) {
	return messages.getMessage(msgId, args, locale);
    }

    @Override
    public String i18n(String msgId) {
	return messages.getMessage(msgId, null, locale);
    }
}
