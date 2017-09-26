package bc.bg.tools.chronos.endpoint.ui.tab.workspace;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.sun.javafx.collections.ObservableListWrapper;

import bc.bg.tools.chronos.endpoint.ui.utils.UIHelper;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBookingRepository;
import javafx.application.Platform;
import javafx.beans.NamedArg;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    // private TableColumn<BookingTableEntry, Date> tableColDuration;
    private TableColumn<BookingTableEntry, String> tableColDuration;

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

	// TODO: REFACTOR
	tableViewBookings.setOnMouseClicked((MouseEvent event) -> {
	    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
		toggleBooking();
	    }
	});
    }

    // TODO: REFACTOR
    private Map<Integer, Node> timerLabels = new HashMap<>();

    protected void toggleBooking() {
	final int selectedIndex = tableViewBookings.getSelectionModel().getSelectedIndex();

	if (timerLabels.containsKey(selectedIndex)) {
	    final Node label = timerLabels.get(selectedIndex);
	    final Object attachedObject = label.getUserData();
	    if (attachedObject instanceof BookingTimer) {
		BookingTimer bookingTimer = (BookingTimer) attachedObject;
		if (bookingTimer.isActive()) {
		    bookingTimer.stop();
		    // label.setStyle("-fx-font-weight: plain");
		    label.setEffect(null);
		} else {
		    if (bookingTimer.isStopped()) {
			bookingTimer = new BookingTimer(label);
			label.setUserData(bookingTimer);
		    }

		    bookingTimer.start();
		    // label.setStyle("-fx-font-weight: bold");
		    label.setEffect(UIHelper.createBlur(2));
		}
	    }
	}
    }

    private void initColumns() {
	// TODO: Refactor/Constants/
	tableColTaskName.setCellFactory(new StringTableCellFactory());
	tableColTaskName.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, String>("taskName"));

	tableColDescription.setCellFactory(new StringTableCellFactory());
	tableColDescription.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, String>("description"));

	tableColStartTime.setCellFactory(new DateTableCellFactory());
	tableColStartTime.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, Date>("startTime"));

	tableColEndTime.setCellFactory(new DateTableCellFactory());
	tableColEndTime.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, Date>("endTime"));

	tableColDuration.setCellFactory(new TimerTableCellFactory());
	tableColDuration.setCellValueFactory(new PropertyValueFactory<BookingTableEntry, String>("durationStr"));

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

	// TODO: Show all or filtered???
	final List<BookingTableEntry> bookingsToShow = filteredBookingStream.map(b -> new BookingTableEntry(b))
		.collect(Collectors.toList());

	tableViewBookings.setItems(new ObservableListWrapper<BookingTableEntry>(bookingsToShow));
    }

    // TODO: Use??
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
		@SuppressWarnings("unused")
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
	    return new StringTableCell(new Label());
	}
    }

    /**
     * @author giliev
     */
    protected class TimerTableCellFactory
	    implements Callback<TableColumn<BookingTableEntry, String>, TableCell<BookingTableEntry, String>> {

	@Override
	public TableCell<BookingTableEntry, String> call(TableColumn<BookingTableEntry, String> param) {
	    return new TimerTableCell(new Label());
	}
    }

    /**
     * @author giliev
     */
    protected class DateTableCellFactory
	    implements Callback<TableColumn<BookingTableEntry, Date>, TableCell<BookingTableEntry, Date>> {

	@Override
	public TableCell<BookingTableEntry, Date> call(TableColumn<BookingTableEntry, Date> param) {
	    return new DateTableCell(new Label());
	}
    }

    /**
     * @author giliev
     */
    protected class DecimalTableCellFactory
	    implements Callback<TableColumn<BookingTableEntry, Double>, TableCell<BookingTableEntry, Double>> {

	@Override
	public TableCell<BookingTableEntry, Double> call(TableColumn<BookingTableEntry, Double> param) {
	    return new DecimalTableCell(new Label());
	}
    }

    /**
     * @author giliev
     */
    protected class IntegerTableCellFactory
	    implements Callback<TableColumn<BookingTableEntry, Long>, TableCell<BookingTableEntry, Long>> {

	@Override
	public TableCell<BookingTableEntry, Long> call(TableColumn<BookingTableEntry, Long> param) {
	    return new IntegerTableCell(new Label());
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
    protected class TimerTableCell extends TableCell<BookingTableEntry, String> {

	private Node rootNode;

	public TimerTableCell(final Object rootNode) {
	    this.rootNode = (Node) rootNode;
	}

	@Override
	protected void updateItem(String item, boolean empty) {
	    super.updateItem(item, empty);
	    if (item == null || empty) {
		setGraphic(null);
		setText(null);
	    } else {
		rootNode.setUserData(new BookingTimer(rootNode));
		timerLabels.put(getIndex(), rootNode);
		setGraphic(rootNode);

		// final LocalTime currTime = LocalTime.parse(item);
		// final LocalTime nextTime = currTime.plusSeconds(1L);

		if (item.equals("00:00")) {
		    ((Label) rootNode).setText(LocalTime.of(0, 0).format(DateTimeFormatter.ofPattern("HH:mm")));
		    // setText(LocalTime.of(0,
		    // 0).format(DateTimeFormatter.ofPattern("HH:mm")));
		} else {
		    ((Label) rootNode).setText(item);
		    // setText(item);
		}
	    }
	}
    }

    public class BookingTimer {
	private int hours;
	private int minutes;
	private int seconds;

	private Timer innerTimer = new Timer();
	private TimerTask innerTask;
	private boolean isActive;
	private boolean isStopped;

	private Node label;

	public BookingTimer(Node label) {
	    this.hours = 0;
	    this.minutes = 0;
	    this.seconds = 0;

	    this.label = label;
	}

	public void start() {
	    innerTask = new TimerTask() {
		@Override
		public void run() {
		    isActive = true;

		    seconds++;

		    if (seconds == 59) {
			minutes += 1;
			seconds = 0;
		    }

		    if (minutes == 59) {
			hours += 1;
			minutes = 0;
		    }

		    Platform.runLater(new Runnable() {
			public void run() {
			    if (label instanceof Label)
				((Label) label).setText(getTime());
			}
		    });
		}
	    };
	    innerTimer.scheduleAtFixedRate(innerTask, 0, 1000);
	}

	public void stop() {
	    isActive = false;
	    isStopped = true;
	    innerTimer.cancel();
	    innerTimer.purge();
	}

	public boolean isActive() {
	    return isActive;
	}

	public boolean isStopped() {
	    return isStopped;
	}

	public String getTime() {
	    return (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":"
		    + (seconds < 10 ? "0" + seconds : seconds);
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

	// private Date duration;

	private String durationStr;

	private String performerHandle;

	private String roleName;

	private String taskName;

	private Double roleBillingRate;

	public BookingTableEntry(final Booking booking) {
	    this.booking = booking;

	    performerHandle = this.booking.getPerformer().getHandle();
	    roleName = this.booking.getRole().getName();
	    taskName = this.booking.getTask().getName();

	    determineDuration();
	    determineBillingRate();
	}

	private void determineDuration() {
	    // if (booking.getEndTime() == null) {
	    // duration = null;
	    // }
	    //
	    final LocalDateTime startTime = LocalDateTime.ofInstant(booking.getStartTime().toInstant(),
		    ZoneId.systemDefault());
	    final LocalDateTime endTime = LocalDateTime.ofInstant(booking.getEndTime().toInstant(),
		    ZoneId.systemDefault());

	    final Duration elapsedTime = Duration.between(startTime, endTime);

	    // final LocalDateTime dTime =
	    // endTime.minusHours(elapsedTime.toHours());
	    // duration =
	    // Date.from(dTime.atZone(ZoneId.systemDefault()).toInstant());

	    // final LocalTime ffzTime = LocalTime.of((int)
	    // elapsedTime.get(ChronoUnit.HOURS),
	    // (int) elapsedTime.get(ChronoUnit.MINUTES), (int)
	    // elapsedTime.get(ChronoUnit.SECONDS));
	    // final LocalTime ffzTime = LocalTime.of(hrs, mins);
	    // durationStr =
	    // ffzTime.format(DateTimeFormatter.ofPattern("hh:mm"));

	    durationStr = DurationFormatUtils.formatDuration(elapsedTime.toMillis(), "HH:mm");
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

	// public Date getDuration() {
	// return duration;
	// }

	public String getDurationStr() {
	    return durationStr;
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
