package bc.bg.tools.chronos.endpoint.ui.utils;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

public final class UIHelper {

    private UIHelper() {
    }

    public static FXMLLoader getWindowLoaderFor(final String fxml, final String i18n,
	    final Callback<Class<?>, Object> controllerFactory) {
	URL fxmlURL = null;
	ResourceBundle i18nBundle = null;
	try {
	    fxmlURL = UIHelper.class.getResource("/fxml/" + fxml + ".fxml");
	    i18nBundle = ResourceBundle.getBundle(i18n, Locale.getDefault());

	    return new FXMLLoader(fxmlURL, i18nBundle, new JavaFXBuilderFactory(), controllerFactory);
	} catch (Exception e) {
	    System.out.println("Exception on FXMLLoader.load()");
	    System.out.println("  * url: " + fxmlURL);
	    System.out.println("  * " + e);
	    System.out.println("    ----------------------------------------\n");
	    ExceptionUtils.printRootCauseStackTrace(e);

	    throw new RuntimeException(e);
	}
    }

    public static Optional<ButtonType> showErrorDialog(final String errorMessage) {
	final Alert errorDialog = new Alert(AlertType.ERROR, errorMessage, ButtonType.OK);
	return errorDialog.showAndWait();
    }

    public static Optional<ButtonType> showInfoDialog(final String infoMessage) {
	final Alert infoDialog = new Alert(AlertType.INFORMATION, infoMessage, ButtonType.OK);
	return infoDialog.showAndWait();
    }

    public static Optional<ButtonType> showChoiceDialog(final String optionsMessage) {
	final Alert choiceDialog = new Alert(AlertType.CONFIRMATION, optionsMessage, ButtonType.OK, ButtonType.CANCEL);
	return choiceDialog.showAndWait();
    }

    public static Optional<ButtonType> showWarningDialog(final String warningMessage) {
	final Alert warningDialog = new Alert(AlertType.WARNING, warningMessage, ButtonType.OK);
	return warningDialog.showAndWait();
    }
}
