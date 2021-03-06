package bg.bc.tools.chronos.dataprovider.utilities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier.ModifierAction;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBillingRateModifierRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBillingRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalPerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteChangelogRepository;

/**
 * Creator of sample data for Chronos.
 * 
 * @author giliev
 */
public class DataCreator {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private LocalChangelogRepository localChangelogRepo;

    @Autowired
    private LocalCategoryRepository localCategoryRepo;

    @Autowired
    private LocalCustomerRepository localCustomerRepo;

    @Autowired
    private LocalProjectRepository localProjectRepo;

    @Autowired
    private LocalTaskRepository localTaskRepo;

    @Autowired
    private LocalBookingRepository localBookingRepo;

    @Autowired
    private LocalBillingRateModifierRepository localBillingRateModifierRepo;

    @Autowired
    private LocalPerformerRepository localPerformerRepo;

    @Autowired
    private LocalBillingRoleRepository localRoleRepo;

    @Autowired
    private RemoteChangelogRepository remoteChangelogRepo;

    @Autowired
    private RemoteCategoryRepository remoteCategoryRepo;
    //
    // @Autowired
    // private RemoteCustomerRepository remoteCustomerRepo;
    //
    // @Autowired
    // private RemoteBookingRepository remoteBookingRepo;

    public void createSampleUserData() {
	transactionTemplate.execute(transactionStatus -> {
	    return createUserData();
	});
    }

    public void createSampleWorkspaceData() throws Exception {
	transactionTemplate.execute(transactionStatus -> {
	    return createWorkspaceData();
	});
    }

    private String generateSyncKey() {
	return UUID.randomUUID().toString();
    }

    public void createRemoteData() {
	// (GETDATE(), HOST_NAME() + '10', @category1Id, 'Category'),
	// (GETDATE(), HOST_NAME () + '20', @category2Id, 'Category');

	final Category dl1 = new Category();
	dl1.setName("[CATEGORY] DOWNLOADED 1");
	dl1.setSortOrder(4);
	// dl1.setSyncKey(generateSyncKey());

	final Category dl2 = new Category();
	dl2.setName("[CATEGORY] DOWNLOADED 2");
	dl2.setSortOrder(4);
	// dl2.setSyncKey(generateSyncKey());

	final Category sCatDefault = addCategoryR(dl1);
	final Category sCatLowPriority = addCategoryR(dl2);
    }

    /**
     * Creates sample data to be used everywhere across Chronos, i.e:
     * <ul>
     * <li>Performers</li>
     * <li>Roles</li>
     * <ul>
     * 
     * <br>
     * 
     * @return Empty(null) value - convenience for transaction template.
     */
    private Void createUserData() {
	final Performer userDeveloper = new Performer();
	// userDeveloper.setSyncKey(generateSyncKey());
	// userDeveloper.setName("Georgi Iliev");
	// userDeveloper.setHandle("gil");
	// userDeveloper.setEmail("gil@company.com");
	// userDeveloper.setPassword("1232".toCharArray());
	// userDeveloper.setPrimaryDeviceName("GIL-COMPANY-PC");
	// userDeveloper.setPriviledges(Arrays.asList(Priviledge.READ,
	// Priviledge.WRITE));
	// userDeveloper.setLogged(false);

	final Performer userTester = new Performer();
	// userTester.setSyncKey(generateSyncKey());
	// userTester.setName("Maximilian Helmut");
	// userTester.setHandle("mhe");
	// userTester.setEmail("mhe@company.com");
	// userTester.setPassword("9999".toCharArray());
	// userTester.setPrimaryDeviceName("MHE-COMPANY-PC");
	// userTester.setPriviledges(Arrays.asList(Priviledge.READ,
	// Priviledge.WRITE));
	// userTester.setLogged(false);

	final Performer userManager = new Performer();
	// userManager.setSyncKey(generateSyncKey());
	// userManager.setName("Ilyia Karamazov");
	// userManager.setHandle("ilk");
	// userManager.setEmail("ilk@company.com");
	// userManager.setPassword("0000".toCharArray());
	// userManager.setPrimaryDeviceName("ILK-COMPANY-PC");
	// userManager.setPriviledges(Arrays.asList(Priviledge.ALL));
	// userManager.setLogged(false);

	addPerformer(userDeveloper);
	addPerformer(userTester);
	addPerformer(userManager);

	final BillingRole roleDeveloper = new BillingRole();
	// roleDeveloper.setSyncKey(generateSyncKey());
	roleDeveloper.setName("Software Developer");
	roleDeveloper.setDescription("Software developer");
	roleDeveloper.setBillingRate(15);
	// roleDeveloper.setCategory(.);

	final BillingRole roleTester = new BillingRole();
	// roleTester.setSyncKey(generateSyncKey());
	roleTester.setName("QA");
	roleTester.setDescription("Software QA");
	roleTester.setBillingRate(10);
	// roleDeveloper.setCategory(.);

	final BillingRole roleManager = new BillingRole();
	// roleManager.setSyncKey(generateSyncKey());
	roleManager.setName("Team Leader");
	roleManager.setDescription("Software team leader");
	roleManager.setBillingRate(30);
	// roleDeveloper.setCategory(.);

	addRole(roleDeveloper);
	addRole(roleTester);
	addRole(roleManager);

	return (Void) null;
    }

    /**
     * Creates sample data for Workspace, i.e:
     * <ul>
     * <li>Categories</li>
     * <li>Customers</li>
     * <li>Projects</li>
     * <li>Tasks</li>
     * <li>Bookings</li>
     * <li>Billing rate modifiers</li>
     * <ul>
     * 
     * <br>
     * 
     * @return Empty(null) value - convenience for transaction template.
     */
    private Void createWorkspaceData() {
	// Categories
	// TODO: i18n
	final Category catDefault = new Category();
	// catDefault.setSyncKey(generateSyncKey());
	catDefault.setName("[CATEGORY] DEFAULT");
	catDefault.setSortOrder(1);

	final Category catLowPriority = new Category();
	// catLowPriority.setSyncKey(generateSyncKey());
	catLowPriority.setName("[CATEGORY] LOW PRIORITY");
	catLowPriority.setSortOrder(2);

	final Category catHighPriority = new Category();
	// catHighPriority.setSyncKey(generateSyncKey());
	catHighPriority.setName("[CATEGORY] HIGH PRIORITY");
	catHighPriority.setSortOrder(3);

	// Customers
	final Customer custNegotiationInProgress = new Customer();
	// custNegotiationInProgress.setSyncKey(generateSyncKey());
	custNegotiationInProgress.setName("'Still In Negotiation' Inc.");
	custNegotiationInProgress.setDescription("Potential new customer... Not yet booked.");

	final Customer custLawFirmComission = new Customer();
	// custLawFirmComission.setSyncKey(generateSyncKey());
	custLawFirmComission.setName("Johnson, Johnson & Wesley");
	custLawFirmComission.setDescription("Up-and-coming legal firm.");

	final Customer custLogisticsComission = new Customer();
	// custLogisticsComission.setSyncKey(generateSyncKey());
	custLogisticsComission.setName("Atlantic United Transportation");
	custLogisticsComission.setDescription("Logistics company.");

	final Customer custGovernmentCommission = new Customer();
	// custGovernmentCommission.setSyncKey(generateSyncKey());
	custGovernmentCommission.setName("Ministry Of Culture");
	custGovernmentCommission.setDescription("Government comissioner.");

	final Customer custOrganizationCommission = new Customer();
	// custOrganizationCommission.setSyncKey(generateSyncKey());
	custOrganizationCommission.setName("Writers`s Guild Of America");
	custOrganizationCommission.setDescription("A syndicate type of organization.");

	// Projects
	final Project projLawFirmClericalAssistant = new Project();
	// projLawFirmClericalAssistant.setSyncKey(generateSyncKey());
	projLawFirmClericalAssistant.setName("iClerk");
	projLawFirmClericalAssistant.setDescription("Software assisting clerical duties.");

	final Project projLogisticsRoutePicker = new Project();
	// projLogisticsRoutePicker.setSyncKey(generateSyncKey());
	projLogisticsRoutePicker.setName("EasyShortcut");
	projLogisticsRoutePicker.setDescription("A shortest transport route type of application.");

	final Project projWritersMessageBoard = new Project();
	// projWritersMessageBoard.setSyncKey(generateSyncKey());
	projWritersMessageBoard.setName("Den Of Ink & Pen");
	projWritersMessageBoard.setDescription("A message board for writers.");

	final Project projMinistryEmployeeRegistry = new Project();
	// projMinistryEmployeeRegistry.setSyncKey(generateSyncKey());
	projMinistryEmployeeRegistry.setName("Employee Simple Registry");
	projMinistryEmployeeRegistry.setDescription("A registry for ministry of culture employees.");

	final Project projMinistrySightsSurveilance = new Project();
	// projMinistrySightsSurveilance.setSyncKey(generateSyncKey());
	projMinistrySightsSurveilance.setName("SafeSights");
	projMinistrySightsSurveilance.setDescription("A surveilance system for cultural sights.");

	// Tasks
	final Task taskClericalAssistantTests = new Task();
	// taskClericalAssistantTests.setSyncKey(generateSyncKey());
	taskClericalAssistantTests.setDescription("Write automated tests for integration of iClerk into new systems.");
	taskClericalAssistantTests.setName("Creation of integration tests");
	taskClericalAssistantTests.setHoursEstimated(24);

	final Task taskEasyShortuctUpdateMaps = new Task();
	// taskEasyShortuctUpdateMaps.setSyncKey(generateSyncKey());
	taskEasyShortuctUpdateMaps.setDescription("Updates used maps for the application.");
	taskEasyShortuctUpdateMaps.setName("Maps update");
	taskEasyShortuctUpdateMaps.setHoursEstimated(3);

	final Task taskMessageBoardAddSignature = new Task();
	// taskMessageBoardAddSignature.setSyncKey(generateSyncKey());
	taskMessageBoardAddSignature.setDescription("Add electronic signature to every post on the message boards.");
	taskMessageBoardAddSignature.setName("Add signature to board posts");
	taskMessageBoardAddSignature.setHoursEstimated(16);

	final Task taskEmployeeRegistryPurge = new Task();
	// taskEmployeeRegistryPurge.setSyncKey(generateSyncKey());
	taskEmployeeRegistryPurge.setDescription("Write & Schedule a purge script for data of laid off employees.");
	taskEmployeeRegistryPurge.setName("Add purging for laid off employees");
	taskEmployeeRegistryPurge.setHoursEstimated(16);

	final Task taskSurveilanceExtendRecordingCapacity = new Task();
	// taskSurveilanceExtendRecordingCapacity.setSyncKey(generateSyncKey());
	taskSurveilanceExtendRecordingCapacity.setDescription("Extend the provided storage space for recordings.");
	taskSurveilanceExtendRecordingCapacity.setName("Extend recordings storage capacity");
	taskSurveilanceExtendRecordingCapacity.setHoursEstimated(5);

	final Task taskSurveilanceAutomaticSnapshots = new Task();
	// taskSurveilanceAutomaticSnapshots.setSyncKey(generateSyncKey());
	taskSurveilanceAutomaticSnapshots.setDescription("Take automatic snap shots upon sudden movement on camera.");
	taskSurveilanceAutomaticSnapshots.setName("Take snap shots on motion");
	taskSurveilanceAutomaticSnapshots.setHoursEstimated(32);

	// Bookings
	final Booking bookingFull = new Booking();
	// bookingFull.setSyncKey(generateSyncKey());
	bookingFull.setDescription("Adding signature for every post on the message board.");
	final LocalDateTime startTime1 = LocalDateTime.of(2017, 9, 9, 14, 25, 10);
	bookingFull.setStartTime(Date.from(startTime1.atZone(ZoneId.systemDefault()).toInstant()));
	final LocalDateTime endTime1 = LocalDateTime.of(2017, 9, 9, 17, 25, 42);
	bookingFull.setEndTime(Date.from(endTime1.atZone(ZoneId.systemDefault()).toInstant()));
	bookingFull.setHoursSpent(Duration.between(startTime1, endTime1).toHours());

	final Booking bookingPartial = new Booking();
	// bookingPartial.setSyncKey(generateSyncKey());
	bookingPartial.setDescription("Write purge script for laid off employees.");
	final LocalDateTime startTime2 = LocalDateTime.of(2017, 9, 14, 10, 8, 11);
	bookingPartial.setStartTime(Date.from(startTime2.atZone(ZoneId.systemDefault()).toInstant()));
	final LocalDateTime endTime2 = LocalDateTime.of(2017, 9, 14, 15, 10, 3);
	bookingPartial.setEndTime(Date.from(endTime2.atZone(ZoneId.systemDefault()).toInstant()));
	bookingPartial.setHoursSpent(Duration.between(startTime2, endTime2).toHours());

	// Billing Rate Modifiers
	final BillingRateModifier modRateGovernmentDiscount = new BillingRateModifier();
	// modRateGovernmentDiscount.setSyncKey(generateSyncKey());
	modRateGovernmentDiscount.setModifierAction(ModifierAction.PERCENT);
	modRateGovernmentDiscount.setModifierValue(0.75d);

	final BillingRateModifier modRateLoyalCustomerDiscount = new BillingRateModifier();
	// modRateLoyalCustomerDiscount.setSyncKey(generateSyncKey());
	modRateLoyalCustomerDiscount.setModifierAction(ModifierAction.PERCENT);
	modRateLoyalCustomerDiscount.setModifierValue(0.85d);

	final BillingRateModifier modRateOvertime = new BillingRateModifier();
	// modRateOvertime.setSyncKey(generateSyncKey());
	modRateOvertime.setModifierAction(ModifierAction.MULTIPLY);
	modRateOvertime.setModifierValue(2.0d);

	try {
	    // Categories
	    final Category sCatDefault = addCategory(catDefault);
	    final Category sCatLowPriority = addCategory(catLowPriority);
	    final Category sCatHighPriority = addCategory(catHighPriority);

	    // final Category rCatDefault = new Category();
	    // rCatDefault.setSyncKey(generateSyncKey());
	    // rCatDefault.setName("[CATEGORY] DEFAULT");
	    // rCatDefault.setSortOrder(1);
	    // final Category rsCatDefault =
	    // remoteCategoryRepo.save(rCatDefault);
	    //
	    // final Customer rCustNegotiationInProgress = new Customer();
	    // rCustNegotiationInProgress.setSyncKey(generateSyncKey());
	    // rCustNegotiationInProgress.setName("'Still In Negotiation'
	    // Inc.");
	    // rCustNegotiationInProgress.setDescription("Potential new
	    // customer... Not yet booked.");
	    // rCustNegotiationInProgress.setCategory(rsCatDefault);
	    //
	    // final Customer rsCustNegotiationInProgress =
	    // remoteCustomerRepo.save(rCustNegotiationInProgress);
	    // //

	    // Customers
	    final Customer sCustNegotiationInProgress = addCustomer(custNegotiationInProgress, sCatDefault);
	    final Customer sCustLawFirmComission = addCustomer(custLawFirmComission, sCatLowPriority);
	    final Customer sCustLogisticsComission = addCustomer(custLogisticsComission, sCatLowPriority);
	    final Customer sCustOrganizationCommission = addCustomer(custOrganizationCommission, sCatHighPriority);
	    final Customer sCustGovernmentCommission = addCustomer(custGovernmentCommission, sCatHighPriority);

	    // Projects
	    final Project sProjLawFirmClericalAssistant = addProject(projLawFirmClericalAssistant, sCatDefault,
		    sCustLawFirmComission);
	    final Project sProjLogisticsRoutePicker = addProject(projLogisticsRoutePicker, sCatDefault,
		    sCustLogisticsComission);
	    final Project sProjMinistryEmployeeRegistry = addProject(projMinistryEmployeeRegistry, sCatDefault,
		    sCustGovernmentCommission);
	    final Project sProjMinistrySightsSurveilance = addProject(projMinistrySightsSurveilance, sCatDefault,
		    sCustGovernmentCommission);
	    final Project sProjWritersMessageBoard = addProject(projWritersMessageBoard, sCatDefault,
		    sCustOrganizationCommission);

	    // Tasks
	    final Task sTaskClericalAssistantTests = addTask(taskClericalAssistantTests, sCatDefault,
		    sProjLawFirmClericalAssistant);
	    final Task sTaskEasyShortuctUpdateMaps = addTask(taskEasyShortuctUpdateMaps, sCatDefault,
		    sProjLogisticsRoutePicker);
	    final Task sTaskEmployeeRegistryPurge = addTask(taskEmployeeRegistryPurge, sCatDefault,
		    sProjMinistryEmployeeRegistry);
	    final Task sTaskSurveilanceExtendRecordingCapacity = addTask(taskSurveilanceExtendRecordingCapacity,
		    sCatDefault, sProjMinistrySightsSurveilance);
	    final Task sTaskSurveilanceAutomaticSnapshots = addTask(taskSurveilanceAutomaticSnapshots, sCatDefault,
		    sProjMinistrySightsSurveilance);
	    final Task sTaskMessageBoardAddSignature = addTask(taskMessageBoardAddSignature, sCatDefault,
		    sProjWritersMessageBoard);

	    // Bookings
	    // final Booking sBookingFull = addBooking(bookingFull,
	    // sTaskEasyShortuctUpdateMaps,
	    // localPerformerRepo.findByHandle("gil"),
	    // localRoleRepo.findByName("Software Developer"));

	    // final Booking sBookingPartial = addBooking(bookingPartial,
	    // sTaskEmployeeRegistryPurge,
	    // localPerformerRepo.findByHandle("ilk"),
	    // localRoleRepo.findByName("Team Leader"));

	    // Billing Rate Modifiers
	    // final BillingRateModifier sModRateGovernmentDiscount =
	    // addBillingRateModifier(modRateGovernmentDiscount,
	    // sBookingPartial);
	    //
	    // final BillingRateModifier sModRateLoyalCustomerDiscount =
	    // addBillingRateModifier(
	    // modRateLoyalCustomerDiscount, sBookingFull);

	    // final BillingRateModifier sModRateOvertime =
	    // addBillingRateModifier(modRateOvertime, sBookingFull);

	} catch (Exception exc) {
	    ExceptionUtils.printRootCauseStackTrace(exc);
	    throw exc;
	}

	return (Void) null;
    }

    private Performer addPerformer(final Performer performer) {
	return saveEntity(performer);
    }

    private Category addCategory(final Category category) {
	return saveEntity(category);
    }

    private Category addCategoryR(final Category category) {
	return saveEntityR(category);
    }

    private Customer addCustomer(final Customer customer, final Category savedCategory) {
	// customer.setCategory(savedCategory);

	return saveEntity(customer);
    }

    private Project addProject(final Project project, final Category savedCategory, final Customer savedCustomer) {
	// project.setCategory(savedCategory);
	project.setCustomer(savedCustomer);

	return saveEntity(project);
    }

    private Task addTask(final Task task, final Category savedCategory, final Project savedProject) {
	// task.setCategory(savedCategory);
	task.setProject(savedProject);

	return saveEntity(task);
    }

    private Booking addBooking(final Booking booking, final Task savedTask, final Performer savedPerformer,
	    final BillingRole savedRole) {
	booking.setTask(savedTask);
	booking.setPerformer(savedPerformer);
	booking.setBillingRole(savedRole);// TODO ???

	return saveEntity(booking);
    }

    private BillingRateModifier addBillingRateModifier(final BillingRateModifier billingRateModifier,
	    final Booking savedBooking) {
	billingRateModifier.setBooking(savedBooking);

	return saveEntity(billingRateModifier);
    }

    // private Role addRole(final Role role, final Category savedCategory, final
    // Booking savedBooking) {
    // // role.setCategory(savedCategory);
    // role.setBooking(savedBooking);// TODO ???
    //
    // return saveEntity(role);
    // }

    private BillingRole addRole(final BillingRole role) {
	return saveEntity(role);
    }

    @SuppressWarnings({ "unchecked" })
    private <E extends Serializable> E saveEntity(final E entity) {
	if (Customer.class.isAssignableFrom(entity.getClass())) {
	    final Customer customer = localCustomerRepo.save((Customer) entity);
	    // appendChangelog(customer.getSyncCounter(), Customer.class);

	    return (E) customer;

	} else if (Project.class.isAssignableFrom(entity.getClass())) {
	    final Project project = localProjectRepo.save((Project) entity);
	    // appendChangelog(project.getSyncCounter(), Project.class);

	    return (E) project;

	} else if (Task.class.isAssignableFrom(entity.getClass())) {
	    final Task task = localTaskRepo.save((Task) entity);
	    // appendChangelog(task.getSyncCounter(), Task.class);

	    return (E) task;

	} else if (Booking.class.isAssignableFrom(entity.getClass())) {
	    final Booking booking = localBookingRepo.save((Booking) entity);
	    // appendChangelog(booking.getSyncKey(), Booking.class);

	    return (E) booking;

	} else if (Performer.class.isAssignableFrom(entity.getClass())) {
	    final Performer performer = localPerformerRepo.save((Performer) entity);
	    // appendChangelog(performer.getSyncKey(), Performer.class);

	    return (E) performer;

	} else if (BillingRole.class.isAssignableFrom(entity.getClass())) {
	    final BillingRole role = localRoleRepo.save((BillingRole) entity);
	    // appendChangelog(role.getSyncKey(), Role.class);

	    return (E) role;

	} else if (BillingRateModifier.class.isAssignableFrom(entity.getClass())) {
	    final BillingRateModifier billingRateModifier = localBillingRateModifierRepo
		    .save((BillingRateModifier) entity);
	    // appendChangelog(billingRateModifier.getSyncKey(),
	    // BillingRateModifier.class);

	    return (E) billingRateModifier;

	}

	throw new RuntimeException("NO ENTITY SAVED!!!");
    }

    // private void appendChangelog(final String entitySyncKey, final Class<?
    // extends Serializable> entityClass) {
    // final Changelog changeLog = new Changelog();
    // changeLog.setChangeTime(Calendar.getInstance().getTime());
    // changeLog.setDeviceName(EntityHelper.getDeviceName());
    // changeLog.setUpdatedEntityType(entityClass.getSimpleName());
    // changeLog.setUpdatedEntityKey(entitySyncKey);
    //
    // localChangelogRepo.save(changeLog);
    // }

    @SuppressWarnings({ "unchecked" })
    private <E extends Serializable> E saveEntityR(final E entity) {
	if (Category.class.isAssignableFrom(entity.getClass())) {
	    final Category category = remoteCategoryRepo.save((Category) entity);
	    // appendChangelogR(category.getSyncKey(), Category.class);

	    return (E) category;

	}

	throw new RuntimeException("NO ENTITY SAVED!!!");
    }

    // private void appendChangelogR(final String entitySyncKey, final Class<?
    // extends Serializable> entityClass) {
    // final Changelog changeLog = new Changelog();
    // changeLog.setChangeTime(Calendar.getInstance().getTime());
    // changeLog.setDeviceName(EntityHelper.getDeviceName());
    // changeLog.setUpdatedEntityType(entityClass.getSimpleName());
    // changeLog.setUpdatedEntityKey(entitySyncKey);
    //
    // remoteChangelogRepo.save(changeLog);
    // }

}
