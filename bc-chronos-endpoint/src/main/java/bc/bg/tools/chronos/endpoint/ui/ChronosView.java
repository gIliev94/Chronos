package bc.bg.tools.chronos.endpoint.ui;

import javafx.scene.Parent;
import javafx.stage.Stage;

public abstract class ChronosView extends Stage implements IChronosView {

    protected Parent root;

    @Override
    public Parent getRoot() {
	return this.root;
    }

    @Override
    public void showOnScreen() {
	if (this.getScene() != null) {
	    this.show();
	}
    }
}
