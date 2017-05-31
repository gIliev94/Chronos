package bc.bg.tools.chronos.endpoint.ui;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class SampleView extends ChronosView {

    private Button btn;

    public SampleView() {
	this.setTitle("Hello World!");

	btn = new Button();
	btn.setText("Say 'Hello World'");
	btn.setOnAction(evt -> {
	    System.out.println("Hello World!");
	});

	root = new StackPane();
	((StackPane) root).getChildren().add(btn);

	// Platform.runLater(new Runnable() {
	// @Override
	// public void run() {
	// btn = new Button();
	// btn.setText("Say 'Hello World'");
	// btn.setOnAction(evt -> {
	// System.out.println("Hello World!");
	// });
	//
	// root = new StackPane();
	// ((StackPane) root).getChildren().add(btn);
	// }
	// });
    }
}
