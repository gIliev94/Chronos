package bc.bg.tools.chronos.endpoint.ui.sample;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MainViewController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Menu menuFile;

    @FXML
    private TreeView<Object> treeCustomers;

    @Autowired
    @Qualifier("localCustomerService")
    private ILocalCustomerService localCustomerService;

    @Autowired
    @Qualifier("localCategoryService")
    private ILocalCategoryService localCategoryService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;

	System.err.println(localCustomerService != null ? "HAS VALUE" : "N/A");

	DCategory defCat = new DCategory();
	defCat.setName("DEFAULT");
	defCat.setSortOrder(1);

	DCategory custCat = new DCategory();
	custCat.setName("CUSTOM");
	custCat.setSortOrder(2);

	DCustomer uncategorizedCustomer = new DCustomer();
	uncategorizedCustomer.setName("PlainCompany");
	uncategorizedCustomer.setDescription("Just a regular company");
	defCat.addCategoricalEntity(uncategorizedCustomer);

	DCustomer categorizedCustomer = new DCustomer();
	categorizedCustomer.setName("LegitCompany");
	categorizedCustomer.setDescription("A legitimate company");
	custCat.addCategoricalEntity(categorizedCustomer);

	// localCustomerService.addCustomer(uncategorizedCustomer);
	// localCustomerService.addCustomer(categorizedCustomer);

	// final List<DCategory> categories =
	// localCategoryService.getCategories();

	TreeItem<Object> catNode1 = new TreeItem<>(defCat.getName());
	TreeItem<Object> catNode2 = new TreeItem<>(custCat.getName());

	treeCustomers.setRoot(new TreeItem<Object>());
	final TreeItem<Object> root = treeCustomers.getRoot();

	@SuppressWarnings("unchecked")
	final boolean addAll = root.getChildren().addAll(catNode1, catNode2);
	if (addAll) {
	    catNode2.setExpanded(true);
	    treeCustomers.getSelectionModel().select(catNode2);

	    custCat.getCategoricalEntities().stream().map(TreeItem<Object>::new).forEach(catNode2.getChildren()::add);
	}

	// InputStream stream =
	// getClass().getResourceAsStream("/i18n/Bundle.properties");
	//
	// try {
	// ResourceBundle r = new PropertyResourceBundle(stream);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// menuFile.get
    }
}
