package bc.bg.tools.chronos.endpoint.ui.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import bc.bg.tools.chronos.endpoint.ui.ChronosRoot;
import bc.bg.tools.chronos.endpoint.ui.ChronosView;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import javafx.scene.control.Button;

@Controller
public class SampleView extends ChronosView {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private LocalCustomerRepository custRepo;

    @Autowired
    @Qualifier("localCustomerService")
    private ILocalCustomerService localCustomerService;
    {
	// TODO; not werking
	System.err.println(localCustomerService != null ? "HAS VALUE" : "N/A");
	System.err.println(context != null ? "HAS VALUE" : "N/A");
	System.err.println(custRepo != null ? "HAS VALUE" : "N/A");
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
