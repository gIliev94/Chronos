package bg.bc.tools.chronos.dataprovider.i18n;

public interface IMessageService {
    String i18n(String msgId, Object[] args);

    String i18n(String msgId);
}
