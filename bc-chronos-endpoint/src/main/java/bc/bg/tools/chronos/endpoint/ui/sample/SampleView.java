package bc.bg.tools.chronos.endpoint.ui.sample;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import bc.bg.tools.chronos.endpoint.ui.ChronosRoot;
import bc.bg.tools.chronos.endpoint.ui.ChronosView;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import bg.bc.tools.chronos.dataprovider.i18n.IMessageService;
import javafx.scene.control.Button;

@Component
public class SampleView extends ChronosView {

    // TODO: TEST - for experimental usage
    @Autowired
    private ApplicationContext context;

    // TODO: TEST - for experimental usage
    @Autowired
    private LocalCustomerRepository custRepo;

    // TODO: TEST - for experimental usage
    @Autowired
    @Qualifier("localCategoryService")
    private ILocalCategoryService localCategoryService;

    @Autowired
    @Qualifier("localCustomerService")
    private ILocalCustomerService localCustomerService;
    
    {
	// // TODO; not working
	// System.err.println(localCustomerService != null ? "HAS VALUE" :
	// "N/A");
	// System.err.println(context != null ? "HAS VALUE" : "N/A");
	// System.err.println(custRepo != null ? "HAS VALUE" : "N/A");
    }

    // TODO: TEST - for experimental usage
    @Autowired
    @Qualifier("msgService")
    private IMessageService msgService;

    private Button btn;

    public SampleView(ChronosRoot root) {
	super(root, 777, 500);

	btn = new Button();
	btn.setOnAction(evt -> {
	    DCustomer ffsCustomer = new DCustomer();
	    ffsCustomer.setName("FFS customer");
	    ffsCustomer.setDescription("dang descr");

	    DCategory ffsCat = new DCategory();
	    ffsCat.setName("DEFAULT");
	    ffsCat.setSortOrder(1);
	    ffsCat.addCategoricalEntity(ffsCustomer);

	    final boolean ffsAdd = localCustomerService.addCustomer(ffsCustomer);
	    final DCustomer obtainedCust = localCustomerService.getCustomer(ffsCustomer.getName());
	    if (ffsAdd && obtainedCust != null) {
		System.out.println(obtainedCust.getName());
	    }
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

    @PostConstruct
    public void initialize() {
	btn.setText(msgService.i18n("sample.view.button"));
    }

    public void ffs() {
	// TODO; not werking
	System.err.println(localCustomerService != null ? "HAS VALUE" : "N/A");
	System.err.println(context != null ? "HAS VALUE" : "N/A");
	System.err.println(custRepo != null ? "HAS VALUE" : "N/A");
    }
}
