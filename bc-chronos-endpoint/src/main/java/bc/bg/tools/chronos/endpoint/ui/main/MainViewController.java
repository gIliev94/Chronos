package bc.bg.tools.chronos.endpoint.ui.main;

import java.net.URL;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DObject;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MainViewController implements Initializable {

    @FXML
    private Menu menuFile;

    @FXML
    private TreeView<DObject> treeCustomers;

    @Autowired
    @Qualifier("localCustomerService")
    private ILocalCustomerService localCustomerService;

    @Autowired
    @Qualifier("localCategoryService")
    private ILocalCategoryService localCategoryService;

    // TODO: Maybe use a separate project for REMOTE DB pushing - like a service
    // API
    // OR
    // Extend dependencies of this project to create an internal Spring app with
    // remote conf...

    // @Autowired
    // @Qualifier("remoteCategoryService")
    // private IRemoteCategoryService remoteCategoryService;

    // TODO: Set if you want to dynamically use i18n...
    @FXML
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;

	// Initialize tree with 'empty' ROOT item...
	treeCustomers.setRoot(new TreeItem<DObject>());

	// TODO: Test code - clean up later...
	final TreeItem<DObject> root = treeCustomers.getRoot();

	createTestData();

	testPopulateCustomerTree(root);
    }

    private void createTestData() {
	final DCategory defCat = new DCategory();
	defCat.setName("DEFAULT");
	defCat.setSortOrder(1);

	final DCategory custCat = new DCategory();
	custCat.setName("CUSTOM");
	custCat.setSortOrder(2);

	final DCustomer uncategorizedCustomer = new DCustomer();
	uncategorizedCustomer.setName("PlainCompany");
	uncategorizedCustomer.setDescription("Just a regular company");
	defCat.addCategoricalEntity(uncategorizedCustomer);

	final DCustomer categorizedCustomer = new DCustomer();
	categorizedCustomer.setName("LegitCompany");
	categorizedCustomer.setDescription("A legitimate company");
	custCat.addCategoricalEntity(categorizedCustomer);

	// TODO: Return Optional<DCustomer> for all add methods...
	localCustomerService.addCustomer(categorizedCustomer);
	localCustomerService.addCustomer(uncategorizedCustomer);
    }

    private void testPopulateCustomerTree(final TreeItem<DObject> root) {
	// Keep the sorter idea - works nicely...
	final List<DCategory> categories = localCategoryService.getCategories();
	categories.sort(Comparator.comparing(DCategory::getSortOrder).thenComparing(DCategory::getName));

	final ObservableList<TreeItem<DObject>> categoryLevel = root.getChildren();
	final boolean categoriesAppended = categoryLevel.addAll(categories.stream() // nl
		.map(TreeItem<DObject>::new) // nl
		.collect(Collectors.toList()));
	if (categoriesAppended) {
	    for (TreeItem<DObject> catNode : categoryLevel) {
		catNode.setExpanded(true);

		final DCategory catObj = (DCategory) catNode.getValue();
		final Collection<DCustomer> customers = localCustomerService.getCustomers(catObj);
		customers.stream() // nl
			.map(TreeItem<DObject>::new) // nl
			.forEach(catNode.getChildren()::add);
	    }
	}
    }
}
