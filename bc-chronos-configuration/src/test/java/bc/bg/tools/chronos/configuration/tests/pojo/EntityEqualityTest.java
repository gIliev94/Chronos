package bc.bg.tools.chronos.configuration.tests.pojo;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import bc.bg.tools.chronos.configuration.tests.runners.RootCauseJUnit4ClassRunner;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier.ModifierAction;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.SynchronizableEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.User;
import bg.bc.tools.chronos.dataprovider.db.entities.UserGroup;

// TODO: Refactor test methods - maybe extract into common helper for all tests...
// TODO: Make same tests with real persistence...
// TODO: Use custom runner only in debug (introduces racing between tests)
@RunWith(RootCauseJUnit4ClassRunner.class)
// @Ignore
public class EntityEqualityTest {

    private static final String ENTITY_DIFF_FORMAT = "Entity << {0} >> :: [{1}] => [{2}]";

    private static final String FIELD_DIFF_FORMAT = "Field << {0} >> :: [{1}] => [{2}]";

    @BeforeClass
    public static void initializeAll() {
	// NOP
    }

    @AfterClass
    public static void deinitializeAll() {
	System.gc();
    }

    @Before
    public void initializeEach() {
    }

    @After
    public void deinitializeEach() {
    }

    @Test
    public void testCategoryEquality() {
	final Category testCategory = getInitCategory();

	final Category copyCategory = getInitCategory();
	assertSameEntity(testCategory, copyCategory);

	final Category diffIdCategory = getInitCategory();
	diffIdCategory.setId(2);
	assertDifferentEntity(testCategory, diffIdCategory, "id", testCategory.getId(), diffIdCategory.getId());

	final Category diffNameCategory = getInitCategory();
	diffNameCategory.setName("OtherCategory");
	assertDifferentEntity(testCategory, diffNameCategory, "name", testCategory.getName(),
		diffNameCategory.getName());
    }

    @Test
    public void testCustomerEquality() {
	final Category testCategory = getInitCategory();

	final Customer testCustomer = getInitCustomer(testCategory);

	final Customer copyCustomer = getInitCustomer(testCategory);
	assertSameEntity(testCustomer, copyCustomer);

	final Customer diffIdCustomer = getInitCustomer(testCategory);
	diffIdCustomer.setId(2);
	assertDifferentEntity(testCustomer, diffIdCustomer, "id", testCustomer.getId(), diffIdCustomer.getId());

	final Customer diffNameCustomer = getInitCustomer(testCategory);
	diffNameCustomer.setName("OtherCustomer");
	assertDifferentEntity(testCustomer, diffNameCustomer, "name", testCustomer.getName(),
		diffNameCustomer.getName());
    }

    @Test
    public void testProjectEquality() {
	final Category testCategory = getInitCategory();
	final Customer testCustomer = getInitCustomer(testCategory);

	final Project testProject = getInitProject(testCustomer, testCategory);

	final Project copyProject = getInitProject(testCustomer, testCategory);
	assertSameEntity(testProject, copyProject);

	final Project diffIdProject = getInitProject(testCustomer, testCategory);
	diffIdProject.setId(2);
	assertDifferentEntity(testProject, diffIdProject, "id", testProject.getId(), diffIdProject.getId());

	final Project diffNameProject = getInitProject(testCustomer, testCategory);
	diffNameProject.setName("OtherProject");
	assertDifferentEntity(testProject, diffNameProject, "name", testProject.getName(), diffNameProject.getName());

	Customer diffCustomer = getInitCustomer(testCategory);
	diffCustomer.setName("DifferentCustomer");

	final Project diffParentCustomerProject = getInitProject(diffCustomer, testCategory);
	assertDifferentEntity(testProject, diffParentCustomerProject, "customer", testProject.getCustomer(),
		diffParentCustomerProject.getCustomer());
    }

    @Test
    public void testTaskEquality() {
	final Category testCategory = getInitCategory();
	final Customer testCustomer = getInitCustomer(testCategory);
	final Project testProject = getInitProject(testCustomer, testCategory);

	final Task testTask = getInitTask(testProject, testCategory);

	final Task copyTask = getInitTask(testProject, testCategory);
	assertSameEntity(testTask, copyTask);

	final Task diffIdTask = getInitTask(testProject, testCategory);
	diffIdTask.setId(2);
	assertDifferentEntity(testTask, diffIdTask, "id", testTask.getId(), diffIdTask.getId());

	final Task diffNameTask = getInitTask(testProject, testCategory);
	diffNameTask.setName("OtherTask");
	assertDifferentEntity(testTask, diffNameTask, "name", testTask.getName(), diffNameTask.getName());

	final Project diffProject = getInitProject(testCustomer, testCategory);
	diffProject.setName("DifferentProject");

	final Task diffParentProjectTask = getInitTask(diffProject, testCategory);
	assertDifferentEntity(testTask, diffParentProjectTask, "project", testTask.getProject(),
		diffParentProjectTask.getProject());
    }

    @Test
    public void testBillingRoleEquality() {
	final BillingRole testBillingRole = getInitBillingRole();

	final BillingRole copyBillingRole = getInitBillingRole();
	assertSameEntity(testBillingRole, copyBillingRole);

	final BillingRole diffIdBillingRole = getInitBillingRole();
	diffIdBillingRole.setId(2);
	assertDifferentEntity(testBillingRole, diffIdBillingRole, "id", testBillingRole.getId(),
		diffIdBillingRole.getId());

	final BillingRole diffNameBillingRole = getInitBillingRole();
	diffNameBillingRole.setName("OtherTask");
	assertDifferentEntity(testBillingRole, diffNameBillingRole, "name", testBillingRole.getName(),
		diffNameBillingRole.getName());

	final BillingRole diffDescriptionBillingRole = getInitBillingRole();
	diffDescriptionBillingRole.setDescription("OtherTask description");
	assertDifferentEntity(testBillingRole, diffDescriptionBillingRole, "description",
		testBillingRole.getDescription(), diffDescriptionBillingRole.getDescription());

	final BillingRole diffBillingRateBillingRole = getInitBillingRole();
	diffBillingRateBillingRole.setBillingRate(5.5d);
	assertDifferentEntity(testBillingRole, diffBillingRateBillingRole, "billingRate",
		testBillingRole.getBillingRate(), diffBillingRateBillingRole.getBillingRate());
    }

    @Test
    public void testUserEquality() {
	final User testUser = getInitUser();

	final User copyUser = getInitUser();
	assertSameEntity(testUser, copyUser);

	final User diffIdUser = getInitUser();
	diffIdUser.setId(2);
	assertDifferentEntity(testUser, diffIdUser, "id", testUser.getId(), diffIdUser.getId());

	final User diffFirstNameUser = getInitUser();
	diffFirstNameUser.setFirstName("OtherFirstName");
	assertDifferentEntity(testUser, diffFirstNameUser, "firstName", testUser.getFirstName(),
		diffFirstNameUser.getFirstName());

	final User diffLastNameUser = getInitUser();
	diffLastNameUser.setLastName("OtherLastName");
	assertDifferentEntity(testUser, diffLastNameUser, "lastName", testUser.getLastName(),
		diffLastNameUser.getLastName());

	final User diffEmailUser = getInitUser();
	diffEmailUser.setEmail("other@mail");
	assertDifferentEntity(testUser, diffEmailUser, "email", testUser.getEmail(), diffEmailUser.getEmail());

	final User diffAbbreviationUser = getInitUser();
	diffAbbreviationUser.setEmail("otherUsr");
	assertDifferentEntity(testUser, diffAbbreviationUser, "abbreviation", testUser.getAbbreviation(),
		diffAbbreviationUser.getAbbreviation());
    }

    @Test
    public void testUserGroupEquality() {
	final UserGroup testUserGroup = getInitUserGroup();

	final UserGroup copyUserGroup = getInitUserGroup();
	assertSameEntity(testUserGroup, copyUserGroup);

	final UserGroup diffIdUserGroup = getInitUserGroup();
	diffIdUserGroup.setId(2);
	assertDifferentEntity(testUserGroup, diffIdUserGroup, "id", testUserGroup.getId(), diffIdUserGroup.getId());

	final UserGroup diffNameUserGroup = getInitUserGroup();
	diffNameUserGroup.setName("OtherUserGroupName");
	assertDifferentEntity(testUserGroup, diffNameUserGroup, "name", testUserGroup.getName(),
		diffNameUserGroup.getName());

	final UserGroup diffDescriptionUserGroup = getInitUserGroup();
	diffDescriptionUserGroup.setDescription("OtherUserGroupName description");
	assertDifferentEntity(testUserGroup, diffDescriptionUserGroup, "description",
		diffDescriptionUserGroup.getDescription(), diffDescriptionUserGroup.getDescription());
    }

    @Test
    public void testPerformerEquality() {
	final User testUser = getInitUser();
	final BillingRole testBillingRole = getInitBillingRole();

	final Performer testPerformer = getInitPerformer(testUser, testBillingRole);

	final Performer copyPerformer = getInitPerformer(testUser, testBillingRole);
	assertSameEntity(testPerformer, copyPerformer);

	final Performer diffIdPerformer = getInitPerformer(testUser, testBillingRole);
	diffIdPerformer.setId(2);
	assertDifferentEntity(testPerformer, diffIdPerformer, "id", testPerformer.getId(), diffIdPerformer.getId());

	final User diffUser = getInitUser();
	diffUser.setFirstName("DifferentFistName");

	final Performer diffParentUserPerformer = getInitPerformer(diffUser, testBillingRole);
	assertDifferentEntity(testPerformer, diffParentUserPerformer, "user", testPerformer.getUser(),
		diffParentUserPerformer.getUser());

	final BillingRole diffBillingRole = getInitBillingRole();
	diffBillingRole.setName("DifferentBillingRole");

	final Performer diffParentBillingRolePerformer = getInitPerformer(testUser, diffBillingRole);
	assertDifferentEntity(testPerformer, diffParentBillingRolePerformer, "billingRole",
		testPerformer.getBillingRole(), diffParentBillingRolePerformer.getBillingRole());
    }

    @Test
    public void testBookingEquality() {
	final Category testCategory = getInitCategory();
	final Customer testCustomer = getInitCustomer(testCategory);
	final Project testProject = getInitProject(testCustomer, testCategory);
	final Task testTask = getInitTask(testProject, testCategory);

	final User testUser = getInitUser();
	final BillingRole testBillingRole = getInitBillingRole();
	final Performer testPerformer = getInitPerformer(testUser, testBillingRole);

	final Booking testBooking = getInitBooking(testTask, testPerformer, testBillingRole);

	final Booking copyBooking = getInitBooking(testTask, testPerformer, testBillingRole);
	assertSameEntity(testBooking, copyBooking);

	final Booking diffIdBooking = getInitBooking(testTask, testPerformer, testBillingRole);
	diffIdBooking.setId(2);
	assertDifferentEntity(testBooking, diffIdBooking, "id", testBooking.getId(), diffIdBooking.getId());

	final Task diffTask = getInitTask(testProject, testCategory);
	diffTask.setName("DifferentTask");

	final Booking diffParentTaskBooking = getInitBooking(diffTask, testPerformer, testBillingRole);
	assertDifferentEntity(testBooking, diffParentTaskBooking, "task", testBooking.getTask(),
		diffParentTaskBooking.getTask());

	final Performer diffPerformer = getInitPerformer(testUser, testBillingRole);
	diffPerformer.setId(diffPerformer.getId() + 1);

	final Booking diffParentPerformerBooking = getInitBooking(testTask, diffPerformer, testBillingRole);
	assertDifferentEntity(testBooking, diffParentPerformerBooking, "performer", testBooking.getPerformer(),
		diffParentPerformerBooking.getPerformer());

	final BillingRole diffBillingRole = getInitBillingRole();
	diffBillingRole.setName("DifferentBillingRole");

	final Booking diffParentBillingRoleBooking = getInitBooking(testTask, testPerformer, diffBillingRole);
	assertDifferentEntity(testBooking, diffParentBillingRoleBooking, "billingRole", testBooking.getBillingRole(),
		diffParentBillingRoleBooking.getBillingRole());
    }

    @Test
    public void testBillingRateModifierEquality() {
	final Category testCategory = getInitCategory();
	final Customer testCustomer = getInitCustomer(testCategory);
	final Project testProject = getInitProject(testCustomer, testCategory);
	final Task testTask = getInitTask(testProject, testCategory);

	final User testUser = getInitUser();
	final BillingRole testBillingRole = getInitBillingRole();
	final Performer testPerformer = getInitPerformer(testUser, testBillingRole);

	final Booking testBooking = getInitBooking(testTask, testPerformer, testBillingRole);

	final BillingRateModifier testBillingRateModifier = getInitBillingRateModifier(testBooking);

	final BillingRateModifier copyBillingRateModifier = getInitBillingRateModifier(testBooking);
	assertSameEntity(testBillingRateModifier, copyBillingRateModifier);

	final BillingRateModifier diffIdBillingRateModifier = getInitBillingRateModifier(testBooking);
	diffIdBillingRateModifier.setId(2);
	assertDifferentEntity(testBillingRateModifier, diffIdBillingRateModifier, "id", testBillingRateModifier.getId(),
		diffIdBillingRateModifier.getId());

	final Booking diffBooking = getInitBooking(testTask, testPerformer, testBillingRole);
	diffBooking.setId(diffBooking.getId() + 1);

	final BillingRateModifier diffParentBookingBillingRateModifier = getInitBillingRateModifier(diffBooking);
	assertDifferentEntity(testBillingRateModifier, diffParentBookingBillingRateModifier, "booking",
		testBillingRateModifier.getBooking(), diffParentBookingBillingRateModifier.getBooking());
    }

    @Test
    public void testCollectionOfEntitiesEquality() {
	final Category testCategory = getInitCategory();
	final Category copyCategory = getInitCategory();
	final Category diffCategory = getInitCategory();
	diffCategory.setName("DifferentCategory");

	testEntityInList(testCategory, copyCategory, diffCategory);
	testEntityInSet(testCategory, copyCategory, diffCategory);

	final Customer testCustomer = getInitCustomer();
	final Customer copyCustomer = getInitCustomer();
	final Customer diffCustomer = getInitCustomer();
	diffCustomer.setName("DifferentCustomer");

	testEntityInList(testCustomer, copyCustomer, diffCustomer);
	testEntityInSet(testCustomer, copyCustomer, diffCustomer);

	final Project testProject = getInitProject(testCustomer);
	final Project copyProject = getInitProject(copyCustomer);
	final Project diffProject = getInitProject(diffCustomer);
	diffProject.setName("DifferentProject");

	testEntityInList(testProject, copyProject, diffProject);
	testEntityInSet(testProject, copyProject, diffProject);

	final Task testTask = getInitTask(testProject);
	final Task copyTask = getInitTask(copyProject);
	final Task diffTask = getInitTask(diffProject);
	diffTask.setName("DifferentTask");

	testEntityInList(testTask, copyTask, diffTask);
	testEntityInSet(testTask, copyTask, diffTask);

	final BillingRole testBillingRole = getInitBillingRole();
	final BillingRole copyBillingRole = getInitBillingRole();
	final BillingRole diffBillingRole = getInitBillingRole();
	diffBillingRole.setName("DifferentBillingRole");

	testEntityInList(testBillingRole, copyBillingRole, diffBillingRole);
	testEntityInSet(testBillingRole, copyBillingRole, diffBillingRole);

	final User testUser = getInitUser();
	final User copyUser = getInitUser();
	final User diffUser = getInitUser();
	diffUser.setFirstName("DifferentFirstName");

	testEntityInList(testUser, copyUser, diffUser);
	testEntityInSet(testUser, copyUser, diffUser);

	final UserGroup testUserGroup = getInitUserGroup();
	final UserGroup copyUserGroup = getInitUserGroup();
	final UserGroup diffUserGroup = getInitUserGroup();
	diffUserGroup.setName("DifferentUserGroup");

	testEntityInList(testUserGroup, copyUserGroup, diffUserGroup);
	testEntityInSet(testUserGroup, copyUserGroup, diffUserGroup);

	final Performer testPerformer = getInitPerformer(testUser, testBillingRole);
	final Performer copyPerformer = getInitPerformer(copyUser, copyBillingRole);
	final Performer diffPerformer = getInitPerformer(diffUser, diffBillingRole);
	diffPerformer.setId(65);

	testEntityInList(testPerformer, copyPerformer, diffPerformer);
	testEntityInSet(testPerformer, copyPerformer, diffPerformer);

	final Booking testBooking = getInitBooking(testTask, testPerformer, testBillingRole);
	final Booking copyBooking = getInitBooking(copyTask, copyPerformer, copyBillingRole);
	final Booking diffBooking = getInitBooking(diffTask, diffPerformer, diffBillingRole);
	diffBooking.setId(65);

	testEntityInList(testBooking, copyBooking, diffBooking);
	testEntityInSet(testBooking, copyBooking, diffBooking);

	final BillingRateModifier testBillingRateModifier = getInitBillingRateModifier(testBooking);
	final BillingRateModifier copyBillingRateModifier = getInitBillingRateModifier(copyBooking);
	final BillingRateModifier diffBillingRateModifier = getInitBillingRateModifier(diffBooking);
	diffBillingRateModifier.setModifierValue(7.72d);

	testEntityInList(testBillingRateModifier, copyBillingRateModifier, diffBillingRateModifier);
	testEntityInSet(testBillingRateModifier, copyBillingRateModifier, diffBillingRateModifier);
    }

    private <T extends SynchronizableEntity> void testEntityInList(final T testEntity, final T copyEntity,
	    final T diffEntity) {
	final List<T> testEntitiesList = new ArrayList<>();
	testEntitiesList.add(testEntity);
	testEntitiesList.add(copyEntity);
	testEntitiesList.add(diffEntity);

	final String entityClassName = testEntity.getClass().getSimpleName();

	Assert.assertTrue("Test " + entityClassName + " not present in List!", testEntitiesList.contains(testEntity));
	Assert.assertTrue("Copy " + entityClassName + " not present in List!", testEntitiesList.contains(copyEntity));
	Assert.assertTrue("Different " + entityClassName + " not present in List!",
		testEntitiesList.contains(diffEntity));

	Assert.assertEquals("Test " + entityClassName + " not present at expected index in List!", 0,
		testEntitiesList.indexOf(testEntity));
	Assert.assertEquals("Copy " + entityClassName + " is not matched as first occurence with Test "
		+ entityClassName + " in List!", 0, testEntitiesList.indexOf(copyEntity));
	Assert.assertEquals("Copy " + entityClassName + " not present at expected index in List!", 1,
		testEntitiesList.lastIndexOf(copyEntity));
	Assert.assertEquals("Different " + entityClassName + " not present at expected index in List!", 2,
		testEntitiesList.indexOf(diffEntity));

	Assert.assertEquals("Test " + entityClassName + " not equal to Copy " + entityClassName + "!",
		testEntitiesList.get(0), testEntitiesList.get(1));
	Assert.assertNotEquals("Test " + entityClassName + " equal to Different " + entityClassName + "!",
		testEntitiesList.get(0), testEntitiesList.get(2));
	Assert.assertNotEquals("Copy " + entityClassName + " not equal to Different " + entityClassName + "!",
		testEntitiesList.get(1), testEntitiesList.get(2));
    }

    private <T extends SynchronizableEntity> void testEntityInSet(final T testEntity, final T copyEntity,
	    final T diffEntity) {
	final Set<T> testEntitiesSet = new HashSet<>();
	testEntitiesSet.add(testEntity);
	testEntitiesSet.add(copyEntity);
	testEntitiesSet.add(diffEntity);

	final String entityClassName = testEntity.getClass().getSimpleName();

	Assert.assertTrue("Test " + entityClassName + " not present in Set!", testEntitiesSet.contains(testEntity));
	Assert.assertTrue("Copy " + entityClassName + " not present in Set!", testEntitiesSet.contains(copyEntity));
	Assert.assertTrue("Different " + entityClassName + " not present in Set!",
		testEntitiesSet.contains(diffEntity));

	Assert.assertTrue("Test AND Copy " + entityClassName + " both present in Set!", testEntitiesSet.size() == 2);
    }

    private Category getInitCategory(final CategoricalEntity... categoricalEntities) {
	final Category initialCategory = new Category();
	initialCategory.setId(1);
	initialCategory.setName("TestCategory");
	initialCategory.setSortOrder(Category.SORT_ORDER_UNSORTED_APPENDED);
	initialCategory.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialCategory.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);

	for (CategoricalEntity categoricalEntity : categoricalEntities) {
	    initialCategory.addCategoricalEntity(categoricalEntity);
	}

	return initialCategory;
    }

    private Customer getInitCustomer(final Category... categories) {
	final Customer initialCustomer = new Customer();
	initialCustomer.setId(1);
	initialCustomer.setName("TestCustomer");
	initialCustomer.setDescription("TestCustomer description");
	initialCustomer.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialCustomer.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);

	for (final Category category : categories) {
	    initialCustomer.addCategory(category);
	}

	return initialCustomer;
    }

    private Project getInitProject(final Customer customer, final Category... categories) {
	final Project initialProject = new Project();
	initialProject.setId(1);
	initialProject.setName("TestProject");
	initialProject.setDescription("TestProject description");
	initialProject.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialProject.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);

	initialProject.setCustomer(customer);

	for (final Category category : categories) {
	    initialProject.addCategory(category);
	}

	return initialProject;
    }

    private Task getInitTask(final Project project, final Category... categories) {
	final Task initialTask = new Task();
	initialTask.setId(1);
	initialTask.setName("TestTask");
	initialTask.setDescription("TestTask description");
	initialTask.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialTask.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);
	initialTask.setHoursEstimated(1);

	initialTask.setProject(project);

	for (final Category category : categories) {
	    initialTask.addCategory(category);
	}

	return initialTask;
    }

    private BillingRole getInitBillingRole() {
	final BillingRole initialBillingRole = new BillingRole();
	initialBillingRole.setId(1);
	initialBillingRole.setName("TestBillingRole");
	initialBillingRole.setDescription("TestBillingRole description");
	initialBillingRole.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialBillingRole.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);
	initialBillingRole.setBillingRate(15.5d);

	return initialBillingRole;
    }

    private User getInitUser(final UserGroup... userGroups) {
	final User initialUser = new User();
	initialUser.setId(1);
	initialUser.setFirstName("InitFirstName");
	initialUser.setLastName("InitLastName");
	initialUser.setPassword("initPW".toCharArray());
	initialUser.setEmail("init@email");
	initialUser.setAbbreviation("initUsr");
	initialUser.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialUser.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);

	for (final UserGroup userGroup : userGroups) {
	    initialUser.addUserGroup(userGroup);
	}

	return initialUser;
    }

    private UserGroup getInitUserGroup(final User... users) {
	final UserGroup initialUserGroup = new UserGroup();
	initialUserGroup.setId(1);
	initialUserGroup.setName("InitialUserGroup");
	// initialUserGroup.setPrivileges("InitLastName");
	initialUserGroup.setDescription("InitialUserGroup description");
	initialUserGroup.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialUserGroup.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);

	for (final User user : users) {
	    initialUserGroup.addUser(user);
	}

	return initialUserGroup;
    }

    private Performer getInitPerformer(final User user, final BillingRole billingRole, final Booking... bookings) {
	final Performer initialUser = new Performer();
	initialUser.setId(1);
	initialUser.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialUser.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);

	initialUser.setUser(user);
	initialUser.setBillingRole(billingRole);

	for (final Booking booking : bookings) {
	    initialUser.addBooking(booking);
	}

	return initialUser;
    }

    private Booking getInitBooking(final Task task, final Performer performer, final BillingRole billingRole,
	    final BillingRateModifier... billingRateModifiers) {
	final Booking initialBooking = new Booking();
	initialBooking.setId(1);
	initialBooking.setDescription("InitialBooking description");
	initialBooking.setDeviceName("InitialPC");
	initialBooking.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	initialBooking.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);

	final LocalDateTime sampleDate = LocalDateTime.of(2018, 2, 22, 10, 30);
	initialBooking.setStartTime(Date.from(sampleDate.plusHours(0).atZone(ZoneId.systemDefault()).toInstant()));
	initialBooking.setEndTime(Date.from(sampleDate.plusHours(8).atZone(ZoneId.systemDefault()).toInstant()));
	initialBooking.setHoursSpent(8.0d);

	initialBooking.setTask(task);
	initialBooking.setPerformer(performer);
	initialBooking.setBillingRole(billingRole);

	for (final BillingRateModifier billingRateModifier : billingRateModifiers) {
	    initialBooking.addBillingRateModifier(billingRateModifier);
	}

	return initialBooking;
    }

    private BillingRateModifier getInitBillingRateModifier(final Booking booking) {
	final BillingRateModifier billingRateModifier = new BillingRateModifier();
	billingRateModifier.setId(1);
	billingRateModifier.setModifierAction(ModifierAction.ADD);
	billingRateModifier.setModifierValue(4.5d);
	billingRateModifier.setUpdateCounter(SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT);
	billingRateModifier.setSyncCounter(SynchronizableEntity.NEW_ENTITY_SYNC_COUNT);

	billingRateModifier.setBooking(booking);

	return billingRateModifier;
    }

    private void assertSameEntity(final SynchronizableEntity testEntity, final SynchronizableEntity otherEntity) {
	final String entityClassName = testEntity.getClass().getSimpleName();
	Assert.assertEquals(MessageFormat.format(ENTITY_DIFF_FORMAT, entityClassName, testEntity, otherEntity),
		testEntity, otherEntity);
    }

    private void assertDifferentEntity(final SynchronizableEntity testEntity, final SynchronizableEntity otherEntity,
	    final String diffProperty, final Object oldPropValue, final Object newPropValue) {
	Assert.assertNotEquals(MessageFormat.format(FIELD_DIFF_FORMAT, diffProperty, oldPropValue, newPropValue),
		testEntity, otherEntity);
    }
}
