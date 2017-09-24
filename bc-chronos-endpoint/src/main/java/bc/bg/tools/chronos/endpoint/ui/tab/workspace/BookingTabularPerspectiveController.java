package bc.bg.tools.chronos.endpoint.ui.tab.workspace;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.sun.javafx.collections.ObservableListWrapper;

import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBookingRepository;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    private TableView<BookingTableEntry> tableViewBookings;

    @FXML // fx:id="tableColTaskName"
    private TableColumn<BookingTableEntry, String> tableColTaskName;

    @FXML // fx:id="tableColDescription"
    private TableColumn<BookingTableEntry, String> tableColDescription;

    @FXML // fx:id="tableColStartTime"
    private TableColumn<BookingTableEntry, Date> tableColStartTime;

    @FXML // fx:id="tableColEndTime"
    private TableColumn<BookingTableEntry, Date> tableColEndTime;

    @FXML // fx:id="tableColDuration"
    private TableColumn<BookingTableEntry, Date> tableColDuration;

    @FXML // fx:id="tableColHoursSpent"
    private TableColumn<BookingTableEntry, Long> tableColHoursSpent;

    @FXML // fx:id="tableColPerformerHandle"
    private TableColumn<BookingTableEntry, String> tableColPerformerHandle;

    @FXML // fx:id="tableColPerformerRoleName"
    private TableColumn<BookingTableEntry, String> tableColRoleName;

    @FXML // fx:id="tableColBillingRate"
    private TableColumn<BookingTableEntry, Double> tableColRoleBillingRate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private LocalBookingRepository bookingRepo;

    // This method is called by the FXMLLoader when initialization is complete
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;

	initColumns();
	clearBookingFilters();
    }

    private void initColumns() {
	tableColTaskName.setCellFactory(new StringTableCellFactory());
	tableColTaskName.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, String>("taskName"));

	tableColDescription.setCellFactory(new StringTableCellFactory());
	tableColDescription.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, String>("description"));

	tableColStartTime.setCellFactory(new DateTableCellFactory());
	tableColStartTime.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, Date>("startTime"));

	tableColEndTime.setCellFactory(new DateTableCellFactory());
	tableColEndTime.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, Date>("endTime"));

	tableColDuration.setCellFactory(new DateTableCellFactory());
	tableColDuration.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, Date>("duration"));

	tableColHoursSpent.setCellFactory(new IntegerTableCellFactory());
	tableColHoursSpent.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, Long>("hoursSpent"));

	tableColPerformerHandle.setCellFactory(new StringTableCellFactory());
	tableColPerformerHandle
		.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, String>("performerHandle"));

	tableColRoleName.setCellFactory(new StringTableCellFactory());
	tableColRoleName.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, String>("roleName"));

	tableColRoleBillingRate.setCellFactory(new DecimalTableCellFactory());
	tableColRoleBillingRate
		.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, Double>("roleBillingRate"));
    }

    public void filterBookingsForParent(final CategoricalEntity categoricalEntity) {
	final Class<? extends CategoricalEntity> parentClass = categoricalEntity.getClass();

	final Stream<Booking> allBookingStream = StreamSupport // nl
		.stream(bookingRepo.findAll().spliterator(), false);

	Stream<Booking> filteredBookingStream = Stream.empty();
	if (Customer.class.isAssignableFrom(parentClass)) {
	    filteredBookingStream = allBookingStream
		    .filter(b -> categoricalEntity.getName().equals(b.getTask().getProject().getCustomer().getName()));
	} else if (Project.class.isAssignableFrom(parentClass)) {
	    filteredBookingStream = allBookingStream
		    .filter(b -> categoricalEntity.getName().equals(b.getTask().getProject().getName()));
	} else if (Task.class.isAssignableFrom(parentClass)) {
	    filteredBookingStream = bookingRepo.findByTask((Task) categoricalEntity).stream();
	}

	showBookings(filteredBookingStream);
    }

    private void clearBookingFilters() {
	showAllBookings();
    }

    protected void showAllBookings() {
	final Stream<Booking> allBookingStream = StreamSupport // nl
		.stream(bookingRepo.findAll().spliterator(), false);
	showBookings(allBookingStream);
    }

    private void showBookings(final Stream<Booking> filteredBookingStream) {
	// Remove previously filtered items...
	tableViewBookings.getItems().clear();

	final List<BookingTableEntry> bs = filteredBookingStream.map(b -> new BookingTableEntry(b))
		.collect(Collectors.toList());
	System.err.println(bs);

	// final List<Booking> bookingsToShow =
	// filteredBookingStream.collect(Collectors.toList());
	//
	tableViewBookings.setItems(new ObservableListWrapper<BookingTableEntry>(bs));
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
	    implements Callback<TableColumn<BookingTableEntry, String>, TableCell<BookingTableEntry, String>> {

	@Override
	public TableCell<BookingTableEntry, String> call(TableColumn<BookingTableEntry, String> param) {
	    // final FXMLLoader loader =
	    // UIHelper.getWindowLoaderFor("StringTableCol",
	    // UIHelper.Defaults.APP_I18N_EN,
	    // applicationContext::getBean);
	    // try {
	    // final Parent rootNode = loader.load();
	    //
	    // // TODO: Set field properly in Abstract or Concrete
	    // // controller...
	    // // https://stackoverflow.com/a/40049810
	    // final Object controller = loader.getController();
	    // // controller.setValue
	    //
	    // return new StringTableCell(rootNode);

	    return new StringTableCell(new Label());
	    // } catch (IOException e) {
	    // // TODO Auto-generated catch block
	    // e.printStackTrace();
	    // return new TableCell<Booking, String>();
	    // }
	}
    }

    /**
     * @author giliev
     */
    protected class DateTableCellFactory
	    implements Callback<TableColumn<BookingTableEntry, Date>, TableCell<BookingTableEntry, Date>> {

	@Override
	public TableCell<BookingTableEntry, Date> call(TableColumn<BookingTableEntry, Date> param) {
	    // final FXMLLoader loader =
	    // UIHelper.getWindowLoaderFor("DateTableCol", null,
	    // applicationContext::getBean);
	    // try {
	    // final Parent rootNode = loader.load();
	    //
	    // // TODO: Set field properly in Abstract or Concrete
	    // // controller...
	    // // https://stackoverflow.com/a/40049810
	    // final Object controller = loader.getController();
	    // // controller.setValue
	    //
	    // return new DateTableCell(rootNode);
	    return new DateTableCell(new Label());
	    // } catch (IOException e) {
	    // // TODO Auto-generated catch block
	    // e.printStackTrace();
	    // return new TableCell<Booking, Date>();
	    // }
	}
    }

    /**
     * @author giliev
     */
    protected class DecimalTableCellFactory
	    implements Callback<TableColumn<BookingTableEntry, Double>, TableCell<BookingTableEntry, Double>> {

	@Override
	public TableCell<BookingTableEntry, Double> call(TableColumn<BookingTableEntry, Double> param) {
	    // final FXMLLoader loader =
	    // UIHelper.getWindowLoaderFor("StringTableCol", null,
	    // applicationContext::getBean);
	    // try {
	    // final Parent rootNode = loader.load();
	    //
	    // // TODO: Set field properly in Abstract or Concrete
	    // // controller...
	    // // https://stackoverflow.com/a/40049810
	    // final Object controller = loader.getController();
	    // // controller.setValue
	    //
	    // return new DecimalTableCell(rootNode);
	    return new DecimalTableCell(new Label());
	    // } catch (IOException e) {
	    // // TODO Auto-generated catch block
	    // e.printStackTrace();
	    // return new TableCell<Booking, Double>();
	    // }
	}
    }

    /**
     * @author giliev
     */
    protected class IntegerTableCellFactory
	    implements Callback<TableColumn<BookingTableEntry, Long>, TableCell<BookingTableEntry, Long>> {

	@Override
	public TableCell<BookingTableEntry, Long> call(TableColumn<BookingTableEntry, Long> param) {
	    // final FXMLLoader loader =
	    // UIHelper.getWindowLoaderFor("StringTableCol", null,
	    // applicationContext::getBean);
	    // try {
	    // final Parent rootNode = loader.load();
	    //
	    // // TODO: Set field properly in Abstract or Concrete
	    // // controller...
	    // // https://stackoverflow.com/a/40049810
	    // final Object controller = loader.getController();
	    // // controller.setValue
	    //
	    // return new DecimalTableCell(rootNode);
	    return new IntegerTableCell(new Label());
	    // } catch (IOException e) {
	    // // TODO Auto-generated catch block
	    // e.printStackTrace();
	    // return new TableCell<Booking, Double>();
	    // }
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
    protected class StringTableCell extends TableCell<BookingTableEntry, String> {

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
		// setText(StringUtils.EMPTY);
		setText(item);
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class DateTableCell extends TableCell<BookingTableEntry, Date> {

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
		// setText(StringUtils.EMPTY);
		setText(LocalDateTime.ofInstant(item.toInstant(), ZoneId.systemDefault())
			.format(DateTimeFormatter.ofPattern("h:mm a")));
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class IntegerTableCell extends TableCell<BookingTableEntry, Long> {

	private Node rootNode;

	public IntegerTableCell(final Object rootNode) {
	    this.rootNode = (Node) rootNode;
	}

	@Override
	protected void updateItem(Long item, boolean empty) {
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
		// setText(StringUtils.EMPTY);
		setText(String.valueOf(item));
	    }
	}
    }

    /**
     * @author giliev
     */
    protected class DecimalTableCell extends TableCell<BookingTableEntry, Double> {

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
		// setText(StringUtils.EMPTY);
		setText(String.valueOf(item));
	    }
	}
    }

    public class BookingTableEntry {

	private Booking booking;

	// private String description;
	//
	// private Date startTime;
	//
	// private Date endTime;

	private Date duration;

	// TODO: Change to DOUBLE
	// private long hoursSpent;

	private String performerHandle;

	private String roleName;

	private String taskName;

	private Double roleBillingRate;

	// private Collection<BillingRateModifier> billingRateModifiers;

	public BookingTableEntry(final Booking booking) {
	    this.booking = booking;

	    performerHandle = this.booking.getPerformer().getHandle();
	    roleName = this.booking.getRole().getName();
	    taskName = this.booking.getTask().getName();

	    determineDuration();
	    determineBillingRate();
	}

	private void determineDuration() {
	    final LocalDateTime startTime = LocalDateTime.ofInstant(booking.getStartTime().toInstant(),
		    ZoneId.systemDefault());
	    final LocalDateTime endTime = LocalDateTime.ofInstant(booking.getEndTime().toInstant(),
		    ZoneId.systemDefault());

	    final Duration elapsedTime = Duration.between(startTime, endTime);
	    final LocalDateTime dTime = endTime.minusHours(elapsedTime.toHours());

	    duration = Date.from(dTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	private void determineBillingRate() {
	    double billingRate = booking.getRole().getBillingRate();

	    final Collection<BillingRateModifier> billingRateModifiers = booking.getBillingRateModifiers();
	    for (BillingRateModifier mod : billingRateModifiers) {
		switch (mod.getModifierAction()) {
		case ADD:
		    billingRate += mod.getModifierValue();
		    break;
		case SUBTRACT:
		    billingRate -= mod.getModifierValue();
		    break;
		case DIVIDE:
		    billingRate /= mod.getModifierValue();
		    break;
		case MULTIPLY:
		case PERCENT:
		    billingRate *= mod.getModifierValue();
		    break;

		default:
		    break;
		}
	    }

	    this.roleBillingRate = billingRate;
	}

	public String getDescription() {
	    return booking.getDescription();
	}

	public Date getStartTime() {
	    return booking.getStartTime();
	}

	public Date getEndTime() {
	    return booking.getEndTime();
	}

	public Date getDuration() {
	    return duration;
	}

	public long getHoursSpent() {
	    return booking.getHoursSpent();
	}

	public String getTaskName() {
	    return taskName;
	}

	public String getPerformerHandle() {
	    return performerHandle;
	}

	public String getRoleName() {
	    return roleName;
	}

	public Double getRoleBillingRate() {
	    return roleBillingRate;
	}

	@Override
	public String toString() {
	    return booking.toString();
	}
    }
}
