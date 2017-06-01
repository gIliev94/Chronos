package bc.bg.tools.chronos.endpoint.ui.sample;

import javax.inject.Inject;

import bc.bg.tools.chronos.endpoint.ui.ChronosRoot;
import bc.bg.tools.chronos.endpoint.ui.ChronosView;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import javafx.scene.control.Button;

public class SampleView extends ChronosView {

    @Inject
    private ILocalCustomerService localCustomerService;
    {
	// TODO; not werking
	System.err.println(localCustomerService != null ? "HAS VALUE" : "N/A");
    }

    private Button btn;

    public SampleView(ChronosRoot root) {
	super(root, 777, 500);

	btn = new Button();
	btn.setText("Say 'Hello World'");
	btn.setOnAction(evt -> {
	    System.out.println("Hello World!");
	});

	root.add(btn);

	// TODO: need??
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
