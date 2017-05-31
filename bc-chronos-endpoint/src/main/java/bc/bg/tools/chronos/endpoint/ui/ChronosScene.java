package bc.bg.tools.chronos.endpoint.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ChronosScene extends Scene {

    private ChronosView primaryStage;

    public ChronosScene(Parent root) {
	super(root, 777, 500);
    }
    
    public ChronosScene(ChronosView primaryStage) {
	super(primaryStage.getRoot(), 777, 500);
	this.setPrimaryStage(primaryStage);
    }

    public void setPrimaryStage(ChronosView primaryStage) {
	this.primaryStage = primaryStage;
	this.primaryStage.setScene(this);
    }
}
