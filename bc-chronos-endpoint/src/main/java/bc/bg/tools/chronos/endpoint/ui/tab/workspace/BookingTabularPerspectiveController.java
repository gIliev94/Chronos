package bc.bg.tools.chronos.endpoint.ui.tab.workspace;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class BookingTabularPerspectiveController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tableViewBookings"
    private TableView<Booking> tableViewBookings;

    @FXML // fx:id="tableColTaskName"
    private TableColumn<Booking, String> tableColTaskName;

    @FXML // fx:id="tableColDescription"
    private TableColumn<Booking, String> tableColDescription;

    @FXML // fx:id="tableColStartTime"
    private TableColumn<Booking, Date> tableColStartTime;

    @FXML // fx:id="tableColEndTime"
    private TableColumn<Booking, Date> tableColEndTime;

    @FXML // fx:id="tableColDuration"
    private TableColumn<Booking, Date> tableColDuration;

    @FXML // fx:id="tableColHoursSpent"
    private TableColumn<Booking, Double> tableColHoursSpent;

    @FXML // fx:id="tableColPerformerHandle"
    private TableColumn<Booking, String> tableColPerformerHandle;

    @FXML // fx:id="tableColPerformerRoleName"
    private TableColumn<Booking, String> tableColRoleName;

    @FXML // fx:id="tableColBillingRate"
    private TableColumn<Booking, Double> tableColRoleBillingRate;

    @Autowired
    private ApplicationContext applicationContext;

    // This method is called by the FXMLLoader when initialization is complete
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	// this.resources = resources;
	initColumns();

	initTestData();

	// TODO:
	// tableColBillingRate.DEFAULT_CELL_FACTORY

    }

    private void initColumns() {
	// TODO Auto-generated method stub
	tableColTaskName.setCellFactory(new StringTableCellFactory());
	tableColTaskName.setCellValueFactory(new PropertyValueFactory<Booking, String>("task.name"));

	tableColDescription.setCellFactory(new StringTableCellFactory());
	tableColDescription.setCellValueFactory(new PropertyValueFactory<Booking, String>("description"));

	tableColStartTime.setCellFactory(new DateTableCellFactory());
	tableColStartTime.setCellValueFactory(new PropertyValueFactory<Booking, Date>("startTime"));

	tableColEndTime.setCellFactory(new DateTableCellFactory());
	tableColEndTime.setCellValueFactory(new PropertyValueFactory<Booking, Date>("endTime"));

	tableColHoursSpent.setCellFactory(new DecimalTableCellFactory());
	tableColHoursSpent.setCellValueFactory(new PropertyValueFactory<Booking, Double>("hoursSpent"));

	tableColPerformerHandle.setCellFactory(new StringTableCellFactory());
	tableColPerformerHandle.setCellValueFactory(new PropertyValueFactory<Booking, String>("performer.handle"));

	tableColRoleName.setCellFactory(new StringTableCellFactory());
	tableColRoleName.setCellValueFactory(new PropertyValueFactory<Booking, String>("role.name"));

	tableColRoleBillingRate.setCellFactory(new DecimalTableCellFactory());
	tableColRoleBillingRate.setCellValueFactory(new PropertyValueFactory<Booking, Double>("role.billingRate"));
    }

    private void initTestData() {
	// TODO Auto-generated method stub

    }

    /**
     * @author giliev
     */
    protected class GenericTableCellFactory
	    implements Callback<TableColumn<Booking, Object>, TableCell<Booking, Object>> {

	private final String fxmlSource;

	protected GenericTableCellFactory(@NamedArg("fxmlSource") String fxmlSource) throws MalformedURLException {
	    this.fxmlSource = fxmlSource;
	}

	@Override
	public TableCell<Booking, Object> call(TableColumn<Booking, Object> param) {
	    final FXMLLoader loader = UIHelper.getWindowLoaderFor(fxmlSource, UIHelper.Defaults.APP_I18N_EN,
		    applicationContext::getBean);
	    try {
		final Parent rootNode = loader.load();

		// TODO: Set field properly in Abstract or Concrete
		// controller...
		// https://stackoverflow.com/a/40049810
		final Object controller = loader.getController();
		// controller.setValue

		return new GenericTableCell(rootNode);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return new TableCell<Booking, Object>();
	    }

	    // return new TableCell<Booking, Object>() {
	    //
	    // @Override
	    // protected void updateItem(Object item, boolean empty) {
	    // super.updateItem(item, empty);
	    // if (item == null || empty) {
	    // setGraphic(null);
	    // setText(null);
	    // } else {
	    // try {
	    // final FXMLLoader loader = UIHelper.getWindowLoaderFor(fxmlSource,
	    // UIHelper.Defaults.APP_I18N_EN, applicationContext::getBean);
	    // // TODO: Set field properly in Abstract or Concrete
	    // // controller...
	    // // https://stackoverflow.com/a/40049810
	    //
	    // loader.getNamespace().put("item", item);
	    // setGraphic(loader.load());
	    // setText(StringUtils.EMPTY);
	    // } catch (IOException e) {
	    // // TODO:
	    // e.printStackTrace();
	    // setGraphic(null);
	    // setText(null);
	    // }
	    // }
	    // }
	    // };
	}
    }

    /**
     * @author giliev
     */
    protected class StringTableCellFactory
	    implements Callback<TableColumn<Booking, String>, TableCell<Booking, String>> {

	@Override
	public TableCell<Booking, String> call(TableColumn<Booking, String> param) {
	    final FXMLLoader loader = UIHelper.getWindowLoaderFor("StringTableCol", UIHelper.Defaults.APP_I18N_EN,
		    applicationContext::getBean);
	    try {
		final Parent rootNode = loader.load();

		// TODO: Set field properly in Abstract or Concrete
		// controller...
		// https://stackoverflow.com/a/40049810
		final Object controller = loader.getController();
		// controller.setValue

		return new StringTableCell(rootNode);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return new TableCell<Booking, String>();
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class DateTableCellFactory implements Callback<TableColumn<Booking, Date>, TableCell<Booking, Date>> {

	@Override
	public TableCell<Booking, Date> call(TableColumn<Booking, Date> param) {
	    final FXMLLoader loader = UIHelper.getWindowLoaderFor("DateTableCol", null, applicationContext::getBean);
	    try {
		final Parent rootNode = loader.load();

		// TODO: Set field properly in Abstract or Concrete
		// controller...
		// https://stackoverflow.com/a/40049810
		final Object controller = loader.getController();
		// controller.setValue

		return new DateTableCell(rootNode);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return new TableCell<Booking, Date>();
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class DecimalTableCellFactory
	    implements Callback<TableColumn<Booking, Double>, TableCell<Booking, Double>> {

	@Override
	public TableCell<Booking, Double> call(TableColumn<Booking, Double> param) {
	    final FXMLLoader loader = UIHelper.getWindowLoaderFor("StringTableCol", null, applicationContext::getBean);
	    try {
		final Parent rootNode = loader.load();

		// TODO: Set field properly in Abstract or Concrete
		// controller...
		// https://stackoverflow.com/a/40049810
		final Object controller = loader.getController();
		// controller.setValue

		return new DecimalTableCell(rootNode);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return new TableCell<Booking, Double>();
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class GenericTableCell extends TableCell<Booking, Object> {

	private Node rootNode;

	public GenericTableCell(final Object rootNode) {
	    this.rootNode = (Node) rootNode;
	}

	@Override
	protected void updateItem(Object item, boolean empty) {
	    super.updateItem(item, empty);
	    if (item == null || empty) {
		setGraphic(null);
		setText(null);
	    } else {
		// TODO: Set field properly in Abstract or Concrete
		// controller...
		// https://stackoverflow.com/a/40049810

		// loader.getNamespace().put("item", item);
		setGraphic(rootNode);
		setText(StringUtils.EMPTY);
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class StringTableCell extends TableCell<Booking, String> {

	private Node rootNode;

	public StringTableCell(final Object rootNode) {
	    this.rootNode = (Node) rootNode;
	}

	@Override
	protected void updateItem(String item, boolean empty) {
	    super.updateItem(item, empty);
	    if (item == null || empty) {
		setGraphic(null);
		setText(null);
	    } else {
		// TODO: Set field properly in Abstract or Concrete
		// controller...
		// https://stackoverflow.com/a/40049810

		// loader.getNamespace().put("item", item);
		setGraphic(rootNode);
		setText(StringUtils.EMPTY);
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class DateTableCell extends TableCell<Booking, Date> {

	private Node rootNode;

	public DateTableCell(final Object rootNode) {
	    this.rootNode = (Node) rootNode;
	}

	@Override
	protected void updateItem(Date item, boolean empty) {
	    super.updateItem(item, empty);
	    if (item == null || empty) {
		setGraphic(null);
		setText(null);
	    } else {
		// TODO: Set field properly in Abstract or Concrete
		// controller...
		// https://stackoverflow.com/a/40049810

		// loader.getNamespace().put("item", item);
		setGraphic(rootNode);
		setText(StringUtils.EMPTY);
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class DecimalTableCell extends TableCell<Booking, Double> {

	private Node rootNode;

	public DecimalTableCell(final Object rootNode) {
	    this.rootNode = (Node) rootNode;
	}

	@Override
	protected void updateItem(Double item, boolean empty) {
	    super.updateItem(item, empty);
	    if (item == null || empty) {
		setGraphic(null);
		setText(null);
	    } else {
		// TODO: Set field properly in Abstract or Concrete
		// controller...
		// https://stackoverflow.com/a/40049810

		// loader.getNamespace().put("item", item);
		setGraphic(rootNode);
		setText(StringUtils.EMPTY);
	    }
	}
    }
}
