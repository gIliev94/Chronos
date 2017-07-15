package bc.bg.tools.chronos.endpoint.ui.login;

/**
 * Model methods / constants for LoginController.
 * 
 * @author giliev
 */
public interface ILoginModel {
    String MSG_ID_TOOLTIP_USER_FIELD = "view.login.field.user.tooltip";
    String MSG_ID_TOOLTIP_PASSWORD_FIELD = "view.login.field.password.tooltip";
    String MSG_ID_TOOLTIP_LOGIN_BUTTON = "view.login.button.login.tooltip";

    String MSG_ID_ERR_INVALID_USER = "view.login.error.invalid.user";
    String MSG_ID_ERR_INVALID_PASS = "view.login.error.invalid.password";

    /**
     * @param msgId
     *            - the message translation ID
     * @param arguments
     *            - the arguments to fill in the translated message.
     * @return A translated message for the specified message ID, filled with
     *         context from the specified arguments.
     */
    String i18n(String msgId, Object... arguments);
}
