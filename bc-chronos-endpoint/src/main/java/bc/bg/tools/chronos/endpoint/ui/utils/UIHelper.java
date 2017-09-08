package bc.bg.tools.chronos.endpoint.ui.utils;

import java.net.URL;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import bc.bg.tools.chronos.endpoint.ui.actions.EntityActionInfo;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * A helper class for common UI actions done via Java FX`s API.
 * 
 * @author giliev
 */
public final class UIHelper {

    public static class Defaults {
	public static final JavaFXBuilderFactory FX_BUILDER_FACTORY = new JavaFXBuilderFactory();

	public static final String APP_TITLE = "Chronos";
	public static final String APP_ICON = "chronos_icon";
	public static final String APP_START_WINDOW = "LoginWindow";
	public static final String APP_MAIN_WINDOW = "MainWindowSandbox";
	public static final String APP_MSG_ID_ERR_WINDOW_NOT_LOADED = "view.error.could.not.be.loaded";
	public static final String APP_I18N_EN = "i18n.Bundle";
	public static final String APP_STYLE_SHEET_BASE = "dark";
    }

    private static class Paths {
	private static final String REL_PATH_FXML = "/fxml/";
	private static final String REL_PATH_STYLE_SHEET = "/css/";
	private static final String REL_PATH_IMAGE_ICON = "/images/";

	private static final String EXT_FXML = ".fxml";
	private static final String EXT_STYLE_SHEET = ".css";
	private static final String[] EXT_IMAGE_ICON = { ".ico", ".png", ".jpg" };

	private static String getFxnlPath(String fxmlName) {
	    return (REL_PATH_FXML + fxmlName + EXT_FXML);
	}

	private static String getStyleSheetPath(String styleSheetName) {
	    return (REL_PATH_STYLE_SHEET + styleSheetName + EXT_STYLE_SHEET);
	}
    }

    // TODO: Add to defaults maybe...
    private static final String STYLE_SHEET_ALERTS = "dialogs";
    private static final String STYLE_CLASS_DIALOGS = "myDialog";
    //

    private UIHelper() {
    }

    public static Tooltip createTooltip(final String tooltipText) {
	final Tooltip tooltip = new Tooltip(tooltipText);
	// TODO: Style maybe???
	return tooltip;
    }

    public static Image createImageIcon(final String imageName) {
	Image imgIcon = null;

	for (String ext : Paths.EXT_IMAGE_ICON) {
	    try {
		imgIcon = new Image(Paths.REL_PATH_IMAGE_ICON + imageName + ext, -1, 50, true, true);
		break;
	    } catch (Exception e) {
		continue;
	    }
	}

	return imgIcon;
    }

    public static void wireEntityActionUI(final EntityActionInfo entityActionInfo) {
	final Button actionButton = entityActionInfo.getActionButton();
	// TODO: Test...
	makeToggleVisibilityCapable(actionButton);
	actionButton.setVisible(entityActionInfo.isVisibleToUser());
	//

	actionButton.setOnMouseClicked(clickEvent -> {
	    try {
		entityActionInfo.executeActionSequence((Void) null);
	    } catch (Exception genericException) {
		// TODO: This or allow proceeding with Q dialog...
		showErrorDialog(genericException.getMessage());
	    }
	});

	actionButton.hoverProperty().addListener((observedProperty, oldValue, newValue) -> {
	    actionButton.setEffect(createGlow(newValue.booleanValue()));
	});
    }

    private static final double ON_GLOW_INTENSITY = 0.5d;

    private static final double OFF_GLOW_INTENSITY = 0.0d;

    public static Effect createGlow(boolean glowOn) {
	final Glow glowEffect = new Glow();

	glowEffect.setLevel(glowOn ? ON_GLOW_INTENSITY : OFF_GLOW_INTENSITY);

	return glowEffect;
    }

    public static Effect createBlur(double radius) {
	// final Distant light = new Distant();
	// light.setAzimuth(-135.0);
	//
	// final Lighting lightEffect = new Lighting();
	// lightEffect.setLight(light);
	// lightEffect.setSurfaceScale(5.0);
	//
	// return lightEffect;

	final GaussianBlur blurEffect = new GaussianBlur();
	blurEffect.setRadius(radius);

	// return blurEffect;

	final Glow glowEffect = new Glow();
	glowEffect.setLevel(1.0);
	glowEffect.setInput(blurEffect);

	return glowEffect;
    }

    // https://stackoverflow.com/a/28318394
    public static void makeToggleVisibilityCapable(final Node toggleNode) {
	toggleNode.managedProperty().bind(toggleNode.visibleProperty());
    }

    public static void removeHiddenNodesFromContainer(final Pane parentContainer) {
	parentContainer.getChildren().removeIf(node -> !node.isVisible());
    }
    //

    public static FXMLLoader getWindowLoaderFor(final String fxml, final String i18n,
	    final Callback<Class<?>, Object> controllerFactory) {
	final ResourceBundle i18nBundle = ResourceBundle.getBundle(i18n != null ? i18n : Defaults.APP_I18N_EN,
		Locale.getDefault());

	URL fxmlURL = null;
	try {
	    fxmlURL = UIHelper.class.getResource(Paths.getFxnlPath(fxml));
	    return new FXMLLoader(fxmlURL, i18nBundle, Defaults.FX_BUILDER_FACTORY, controllerFactory);
	} catch (Exception e) {
	    // TODO: Remove prints & decide if you`ll hande or re-throw...
	    System.out.println("Exception on FXMLLoader.load()");
	    System.out.println("  * url: " + fxmlURL);
	    System.out.println("  * " + e);
	    System.out.println("    ----------------------------------------\n");
	    ExceptionUtils.printRootCauseStackTrace(e);

	    throw new RuntimeException(e);
	}
    }

    private static void styleDialog(final Dialog<?> dialog) {
	String styleSheet = Defaults.APP_STYLE_SHEET_BASE;

	// TODO: Distinguish styles for types dialog/s
	// https://stackoverflow.com/a/28421229
	if (dialog instanceof Alert) {
	    styleSheet = STYLE_SHEET_ALERTS;

	    final AlertType alertType = ((Alert) dialog).getAlertType();
	    switch (alertType) {
	    case ERROR:
		styleSheet = "PLACEHOLDER";
		break;
	    case WARNING:
		styleSheet = "PLACEHOLDER";
		break;
	    case INFORMATION:
		styleSheet = "PLACEHOLDER";
		break;
	    case CONFIRMATION:
		styleSheet = "PLACEHOLDER";
		break;
	    case NONE:
	    default:
		break;
	    }
	} else if (dialog instanceof ChoiceDialog) {
	    styleSheet = "PLACEHOLDER";
	} else if (dialog instanceof TextInputDialog) {
	    styleSheet = "PLACEHOLDER";
	}

	dialog.getDialogPane().getStylesheets().add(Paths.getStyleSheetPath(styleSheet));
	dialog.getDialogPane().getStyleClass().add(STYLE_CLASS_DIALOGS);
    }

    // TODO: com.sun.deploy.uitoolkit.impl.fx.ui.FXUIFactory => consider for
    // dialogs...
    public static Optional<ButtonType> showErrorDialog(final String errorMessage) {
	final Alert errorDialog = new Alert(AlertType.ERROR, errorMessage, ButtonType.OK);
	styleDialog(errorDialog);

	return errorDialog.showAndWait();
    }

    public static Optional<ButtonType> showWarningDialog(final String warningMessage) {
	final Alert warningDialog = new Alert(AlertType.WARNING, warningMessage, ButtonType.OK);
	styleDialog(warningDialog);

	return warningDialog.showAndWait();
    }

    public static Optional<ButtonType> showInfoDialog(final String infoMessage) {
	final Alert infoDialog = new Alert(AlertType.INFORMATION, infoMessage, ButtonType.OK);
	styleDialog(infoDialog);

	return infoDialog.showAndWait();
    }

    public static Optional<ButtonType> showQuestionDialog(final String questionMessage) {
	final Alert questionDialog = new Alert(AlertType.CONFIRMATION, questionMessage, ButtonType.OK,
		ButtonType.CANCEL);
	styleDialog(questionDialog);

	return questionDialog.showAndWait();
    }

    public static <T> Optional<T> showChoiceDialog(final T defaultChoice, final Collection<T> choices) {
	final ChoiceDialog<T> choiceDialog = new ChoiceDialog<T>(defaultChoice, choices);
	styleDialog(choiceDialog);

	return choiceDialog.showAndWait();
    }

    public static Optional<String> showInputDialog(final String defaultValue) {
	final TextInputDialog inputDialog = new TextInputDialog(defaultValue);
	styleDialog(inputDialog);

	return inputDialog.showAndWait();
    }
}
