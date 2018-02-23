package bc.bg.tools.chronos.configuration.tests.local.crud;

import java.util.Collection;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.util.Pair;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.configuration.CommonDBConfig;
import bc.bg.tools.chronos.configuration.LocalDBConfig;
import bc.bg.tools.chronos.configuration.LocalDataProviderConfig;
import bc.bg.tools.chronos.configuration.tests.runners.RootCauseSpringJUnit4ClassRunner;
import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.GenericEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.SynchronizableEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;

// TODO: Refactor test methods...
@SpringBootApplication
@RunWith(RootCauseSpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { // nl
	CommonDBConfig.class, // nl
	LocalDBConfig.class, // nl
	LocalDataProviderConfig.class // nl
})
// @Ignore
public class EntityEqualityTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private TransactionTemplate transactionTemplate;

    // TODO: Replace repo calls with service calls once properly implemented...
    // @Autowired
    // private ILocalCategoryService localCategoryService;

    // @Autowired
    // private ILocalCustomerService localCustomerService;

    // @Autowired
    // private ILocalProjectService localProjectService;

    // @Autowired
    // private ILocalTaskService localTaskService;

    @Autowired
    private LocalCategoryRepository localCategoryRepository;

    @Autowired
    private LocalCustomerRepository localCustomerRepository;

    @Autowired
    private LocalProjectRepository localProjectRepository;

    @Autowired
    private LocalTaskRepository localTaskRepository;

    private Category testCategory;

    private Customer testCustomer;

    private Project testProject = null;

    private Task testTask = null;

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
	// Update counter for all set to default ==
	// SynchronizableEntity.NEW_ENTITY_UPDATE_COUNT (entity just
	// created locally)

	// Sync counter for all set to default ==
	// SynchronizableEntity.NEW_ENTITY_SYNC_COUNT (entity not yet
	// synced)

	testCustomer = new Customer();
	testCustomer.setName("RAID");
	testCustomer.setDescription("Bug-expellent solutions");
	// testCustomer.setUpdateCounter(updateCounter);
	// testCustomer.setSyncCounter(syncCounter);

	testProject = new Project();
	testProject.setName("SCION");
	testProject.setDescription("Motion detection trigger for expellents");
	// testProject.setUpdateCounter(updateCounter);
	// testProject.setSyncCounter(syncCounter);

	testTask = new Task();
	testTask.setName("Design motion detector script");
	testTask.setDescription("Provide specifics to build the motion detector script");
	testTask.setHoursEstimated(3);
	// testTask.setUpdateCounter(updateCounter);
	// testTask.setSyncCounter(syncCounter);

	testCategory = new Category();
	testCategory.setName("NON-HAZARDOUS");
	// testCategory.setUpdateCounter(updateCounter);
	// testCategory.setSyncCounter(syncCounter);

	// default == Category.SORT_ORDER_UNSORTED_APPENDED (add to the END of
	// the displayed list of categories)
	// testCategory.setSortOrder(sortOrder);
    }

    @After
    public void deinitializeEach() {
	testTask = null;
	testProject = null;
	testCustomer = null;
	testCategory = null;
    }

    @Test
    @Ignore
    public void testLocalCategoryCRUD() {
	// 1. Create
	// ---------
	final Category createdCategory = transactionTemplate.execute(txStat -> {
	    return localCategoryRepository.save(testCategory);
	});
	assertEntityExists(createdCategory, testCategory);

	// 2. Read
	// ---------
	final Category readCategory = transactionTemplate.execute(txStat -> {
	    return localCategoryRepository.findByName(testCategory.getName());
	});
	assertEntityExists(readCategory, testCategory);

	// 3. Update
	// ---------
	// Properties before the change (update)
	final long originalUpdateCount = readCategory.getUpdateCounter();
	// Original sort order is the default ==
	// Category.SORT_ORDER_UNSORTED_APPENDED
	final int originalSortOrder = readCategory.getSortOrder();

	// Change(update) properties
	readCategory.setSortOrder(Category.SORT_ORDER_UNSORTED_PREPENDED);
	readCategory.markUpdated();

	final Category updatedCategory = transactionTemplate.execute(txStat -> {
	    return localCategoryRepository.save(readCategory);
	});
	assertEntityUpdated(updatedCategory, readCategory, originalUpdateCount, originalSortOrder,
		updatedCategory.getSortOrder());

	// 4. Delete
	// ---------
	// Perform deletion by fetched object...
	transactionTemplate.execute(txStat -> {
	    localCategoryRepository.delete(readCategory);
	    return null;
	});

	// Confirm deletion
	final Category stillExistingCategory = transactionTemplate.execute(txStat -> {
	    return localCategoryRepository.findByName(testCategory.getName());
	});
	assertEntityDeleted(stillExistingCategory, testCategory);
    }

    @Test
    @Ignore
    public void testLocalCustomerCRUD() {
	// 1. Create
	// ---------
	testCustomer.addCategory(testCategory);

	final Customer createdCustomer = transactionTemplate.execute(txStat -> {
	    return localCustomerRepository.save(testCustomer);
	});
	assertEntityAndReferencesExist(createdCustomer, testCustomer, testCategory, createdCustomer.getCategories());

	// 2. Read
	// ---------
	final Customer readCustomer = transactionTemplate.execute(txStat -> {
	    return localCustomerRepository.findByName(testCustomer.getName());
	});
	assertEntityExists(readCustomer, testCustomer);

	// 3. Update
	// ---------
	// Properties before the change (update)
	final long originalUpdateCount = readCustomer.getUpdateCounter();
	final String originalDescription = readCustomer.getDescription();

	// Change(update) properties
	readCustomer.setDescription("UPDATED DESC");
	readCustomer.markUpdated();

	final Customer updatedCustomer = transactionTemplate.execute(txStat -> {
	    return localCustomerRepository.save(readCustomer);
	});
	assertEntityUpdated(updatedCustomer, testCustomer, originalUpdateCount, originalDescription,
		updatedCustomer.getDescription());

	// 4. Delete
	// ---------
	// Perform deletion by fetched object...

	// Remove links
	final Category referencedCategory = readCustomer.getCategories().iterator().next();
	referencedCategory.removeCategoricalEntity(readCustomer);

	final Pair<Category, Customer> unlinkedEntities = transactionTemplate.execute(txStat -> {
	    final Category category = localCategoryRepository.save(referencedCategory);
	    final Customer customer = localCustomerRepository.save(readCustomer);
	    return Pair.of(category, customer);
	});

	transactionTemplate.execute(txStat -> {
	    localCustomerRepository.delete(unlinkedEntities.getSecond());
	    return null;
	});

	final Customer stillExistingCustomer = transactionTemplate.execute(txStat -> {
	    return localCustomerRepository.findByName(readCustomer.getName());
	});
	assertEntityDeleted(stillExistingCustomer, testCustomer);
    }

    @Test
    @Ignore
    public void testLocalProjectCRUD() {
	// 1. Create
	// ---------
	// TODO: opt 1 => not working
	// testCustomer.addCategory(testCategory);

	// final Customer createdCustomer = transactionTemplate.execute(txStat
	// -> {
	// return localCustomerRepository.save(testCustomer);
	// });
	// Assert.assertNotNull("No Customer created in local DB for Java
	// object: " + testCustomer, createdCustomer);
	// Assert.assertNotEquals("The created Customer in local DB is not
	// managed(no PK)!", 0L, createdCustomer.getId());
	// Assert.assertFalse("The created Customer does not contain the
	// Catergories referenced(Category " + testCategory + ")!",
	// createdCustomer.getCategories().isEmpty());

	// final Category preCreatedCategory =
	// createdCustomer.getCategories().iterator().next();

	// testProject.addCategory(preCreatedCategory);
	// testProject.setCustomer(createdCustomer);

	// final Project createdProject = transactionTemplate.execute(txStat ->
	// {
	// return localProjectRepository.save(testProject);
	// });
	// Assert.assertNotNull("No Project created in local DB for Java
	// object: " + testProject, createdProject);
	// Assert.assertNotEquals("The created Project in local DB is not
	// managed(no PK)!", 0L, createdProject.getId());
	// Assert.assertFalse(
	// "The created Project does not contain the Catergories
	// referenced(Category " + testCategory + ")!",
	// createdProject.getCategories().isEmpty());

	// TODO: opt 2 => working
	testCustomer.addCategory(testCategory);
	testProject.addCategory(testCategory);
	testProject.setCustomer(testCustomer);

	final Project createdProject = transactionTemplate.execute(txStat -> {
	    return localProjectRepository.save(testProject);
	});
	assertEntityAndReferencesExist(createdProject, testProject, testCategory, createdProject.getCategories());

	final Customer referencedCustomer = createdProject.getCustomer();
	assertEntityAndReferencesExist(referencedCustomer, testCustomer, testCategory,
		referencedCustomer.getCategories());

	// TODO: opt 3 => working
	// testCustomer.addCategory(testCategory);
	// testProject.addCategory(testCategory);
	// testProject.setCustomer(testCustomer);

	// final Pair<Customer, Project> persistedEntities =
	// transactionTemplate.execute(txStat -> {
	// final Customer customer =
	// localCustomerRepository.save(testCustomer);
	// final Project project = localProjectRepository.save(testProject);
	// return Pair.of(customer, project);
	// });

	// Assert.assertNotNull("No Customer created in local DB for Java
	// object: " + testCustomer,
	// persistedEntities.getFirst());
	// Assert.assertNotEquals("The created Customer in local DB is not
	// managed(no PK)!", 0L,
	// persistedEntities.getFirst().getId());
	// Assert.assertFalse(
	// "The created Customer does not contain the Catergories
	// referenced(Category " + testCategory + ")!",
	// persistedEntities.getFirst().getCategories().isEmpty());

	// Assert.assertNotNull("No Project created in local DB for Java
	// object: " + testProject,
	// persistedEntities.getSecond());
	// Assert.assertNotEquals("The created Project in local DB is not
	// managed(no PK)!", 0L,
	// persistedEntities.getSecond().getId());
	// Assert.assertFalse(
	// "The created Project does not contain the Catergories
	// referenced(Category " + testCategory + ")!",
	// persistedEntities.getSecond().getCategories().isEmpty());

	// 2. Read
	// ---------
	final Project readProject = transactionTemplate.execute(txStat -> {
	    return localProjectRepository.findByName(testProject.getName());
	});
	assertEntityExists(readProject, testProject);

	// 3. Update
	// ---------
	// Properties before the change (update)
	final long originalUpdateCount = readProject.getUpdateCounter();
	final String originalDescription = readProject.getDescription();

	// Change(update) properties
	readProject.setDescription("UPDATED DESC");
	readProject.markUpdated();

	final Project updatedProject = transactionTemplate.execute(txStat -> {
	    return localProjectRepository.save(readProject);
	});
	assertEntityUpdated(updatedProject, testProject, originalUpdateCount, originalDescription,
		updatedProject.getDescription());

	// 4. Delete
	// ---------
	// Perform deletion by fetched object...

	// Remove links
	final Category referencedCategory = readProject.getCategories().iterator().next();
	referencedCategory.removeCategoricalEntity(readProject);

	final Pair<Category, Project> unlinkedEntities = transactionTemplate.execute(txStat -> {
	    final Category category = localCategoryRepository.save(referencedCategory);
	    final Project project = localProjectRepository.save(readProject);
	    return Pair.of(category, project);
	});

	transactionTemplate.execute(txStat -> {
	    localProjectRepository.delete(unlinkedEntities.getSecond());
	    return null;
	});

	final Project stillExistingProject = transactionTemplate.execute(txStat -> {
	    return localProjectRepository.findByName(readProject.getName());
	});
	assertEntityDeleted(stillExistingProject, testProject);
    }

    @Test
    @Ignore
    public void testLocalTaskCRUD() {
	// 1. Create
	// ---------
	testCustomer.addCategory(testCategory);
	testProject.addCategory(testCategory);
	testTask.addCategory(testCategory);

	testProject.setCustomer(testCustomer);
	testTask.setProject(testProject);

	final Task createdTask = transactionTemplate.execute(txStat -> {
	    return localTaskRepository.save(testTask);
	});
	assertEntityAndReferencesExist(createdTask, testTask, testCategory, createdTask.getCategories());

	final Project referencedProject = createdTask.getProject();
	assertEntityAndReferencesExist(referencedProject, testProject, testCategory, referencedProject.getCategories());

	final Customer referencedCustomer = referencedProject.getCustomer();
	assertEntityAndReferencesExist(referencedCustomer, testCustomer, testCategory,
		referencedCustomer.getCategories());

	// 2. Read
	// ---------
	final Task readTask = transactionTemplate.execute(txStat -> {
	    return localTaskRepository.findByName(testTask.getName());
	});
	assertEntityExists(readTask, testTask);

	// 3. Update
	// ---------
	// Properties before the change (update)
	final long originalUpdateCount = readTask.getUpdateCounter();
	final String originalDescription = readTask.getDescription();

	// Change(update) properties
	readTask.setDescription("UPDATED DESC");
	readTask.markUpdated();

	final Task updatedTask = transactionTemplate.execute(txStat -> {
	    return localTaskRepository.save(readTask);
	});
	assertEntityUpdated(updatedTask, testTask, originalUpdateCount, originalDescription,
		updatedTask.getDescription());

	// 4. Delete
	// ---------

	// Remove links
	final Category referencedCategory = readTask.getCategories().iterator().next();
	referencedCategory.removeCategoricalEntity(readTask);

	final Pair<Category, Task> unlinkedEntities = transactionTemplate.execute(txStat -> {
	    final Category category = localCategoryRepository.save(referencedCategory);
	    final Task task = localTaskRepository.save(readTask);
	    return Pair.of(category, task);
	});

	transactionTemplate.execute(txStat -> {
	    localTaskRepository.delete(unlinkedEntities.getSecond());
	    return null;
	});

	final Task stillExistingTask = transactionTemplate.execute(txStat -> {
	    return localTaskRepository.findByName(readTask.getName());
	});
	assertEntityDeleted(stillExistingTask, testTask);
    }

    @Test
    @Ignore
    public void testLocalBidirectionalCascade() {
	// 1. Standard(owner) cascade insert: Category (OWNER) => Customer
	// (INVERSE)
	// ---------
	testCategory.addCategoricalEntity(testCustomer);

	final Category createdCategory = transactionTemplate.execute(txStat -> {
	    return localCategoryRepository.save(testCategory);
	});
	assertEntityAndReferencesExist(createdCategory, testCategory, testCustomer,
		createdCategory.getCategoricalEntities());

	// 2. Delete created entities before trying the inverse cascade
	// ---------
	final Customer referencedCustomer = (Customer) createdCategory.getCategoricalEntities().iterator().next();
	deleteCategoricalEntity(createdCategory, testCategory, referencedCustomer, testCustomer, true);

	// // 2.1 Remove links
	// createdCategory.removeCategoricalEntity(referencedCustomer);
	// referencedCustomer.removeCategory(createdCategory);
	//
	// final Pair<Category, Customer> unlinkedEntities =
	// transactionTemplate.execute(txStat -> {
	// final Category category =
	// localCategoryRepository.save(createdCategory);
	// final Customer customer =
	// localCustomerRepository.save(referencedCustomer);
	// return Pair.of(category, customer);
	// });
	//
	// // 2.2 Delete Customer (NON-OWNER side of the relationship)
	// transactionTemplate.execute(txStat -> {
	// localCustomerRepository.delete(unlinkedEntities.getSecond());
	// return null;
	// });
	//
	// final Customer stillExistingCustomer =
	// transactionTemplate.execute(txStat -> {
	// return
	// localCustomerRepository.findByName(referencedCustomer.getName());
	// });
	// assertEntityDeleted(stillExistingCustomer, testCustomer);
	//
	// // 2.3 Delete Category (OWNER side of the relationship)
	// transactionTemplate.execute(txStat -> {
	// localCategoryRepository.delete(unlinkedEntities.getFirst());
	// return null;
	// });
	// final Category stillExistingCategory =
	// transactionTemplate.execute(txStat -> {
	// return localCategoryRepository.findByName(testCategory.getName());
	// });
	// assertEntityDeleted(stillExistingCategory, testCategory);

	// 3. Inverse cascade insert: Customer (INVERSE) => Category (OWNER)
	// ---------
	testCustomer.addCategory(testCategory);

	final Customer createdCustomer = transactionTemplate.execute(txStat -> {
	    return localCustomerRepository.save(testCustomer);
	});
	assertEntityAndReferencesExist(createdCustomer, testCustomer, testCategory, createdCustomer.getCategories());

	// Cleanup created entities - possibly will interfere with next running
	// test/s
	final Category referencedCategory = createdCustomer.getCategories().iterator().next();
	deleteCategoricalEntity(referencedCategory, testCategory, createdCustomer, testCustomer, true);
    }

    @Test
    // @Ignore
    public void testLocalHierirachicalFetch() {
	// 1. Create
	// ---------
	testCustomer.addCategory(testCategory);
	testProject.addCategory(testCategory);
	testTask.addCategory(testCategory);

	testProject.setCustomer(testCustomer);
	testTask.setProject(testProject);

	final Task createdTask = transactionTemplate.execute(txStat -> {
	    return localTaskRepository.save(testTask);
	});
	assertEntityAndReferencesExist(createdTask, testTask, testCategory, createdTask.getCategories());

	final Project referencedProject = createdTask.getProject();
	assertEntityAndReferencesExist(referencedProject, testProject, testCategory, referencedProject.getCategories());

	final Customer referencedCustomer = referencedProject.getCustomer();
	assertEntityAndReferencesExist(referencedCustomer, testCustomer, testCategory,
		referencedCustomer.getCategories());

	// 5. Try fetch all entities from Category
	final Category resultingCategory = transactionTemplate.execute(txStat -> {
	    return localCategoryRepository.findByName(testCategory.getName());
	});
	assertEntityExists(resultingCategory, testCategory);

	final Set<CategoricalEntity> categoricalEntities = resultingCategory.getCategoricalEntities();
	Assert.assertFalse(
		"No Categorical entities found in local DB for Category  with name: " + testCategory.getName(),
		categoricalEntities.isEmpty());
	Assert.assertTrue("No Customer found in local DB for Category  with name: " + testCategory.getName(),
		categoricalEntities.stream().anyMatch(ce -> ce instanceof Customer));
	Assert.assertTrue("No Project found in local DB for Category  with name: " + testCategory.getName(),
		categoricalEntities.stream().anyMatch(ce -> ce instanceof Project));
	Assert.assertTrue("No Task found in local DB for Category  with name: " + testCategory.getName(),
		categoricalEntities.stream().anyMatch(ce -> ce instanceof Task));

	System.out.println("CATEGORICAL ENTITIES:");
	categoricalEntities.forEach(ce -> System.out.println(ce.getClass().getSimpleName() + " => " + ce));

	// TODO: still not working
	// Cleanup created entities - possibly will interfere with next running
	// test/s
	deleteCategoricalEntity(resultingCategory, testCategory, createdTask, testTask, false);
	// deleteCategoricalEntity(resultingCategory, testCategory,
	// referencedProject, testProject, false);
	// deleteCategoricalEntity(resultingCategory, testCategory,
	// referencedCustomer, testCustomer, true);
    }

    private void assertEntityExists(final SynchronizableEntity persistedEntity, final SynchronizableEntity testEntity) {
	final String testEntityClass = testEntity.getClass().getSimpleName();
	Assert.assertNotNull(
		"No " + testEntityClass + " found/created/updated in local DB for Java object: " + testEntity,
		persistedEntity);
	// Assert.assertNotEquals("The found/created/updated " + testEntityClass
	// + " in local DB is not managed(no PK)!",
	// 0L, persistedEntity.getId());
	final long id = persistedEntity instanceof GenericEntity ? ((GenericEntity) persistedEntity).getId()
		: ((CategoricalEntity) persistedEntity).getId();
	Assert.assertNotEquals("The found/created/updated " + testEntityClass + " in local DB is not managed(no PK)!",
		0L, id);
    }

    private void assertEntityAndReferencesExist(final SynchronizableEntity persistedEntity,
	    final SynchronizableEntity testEntity, final SynchronizableEntity referencedEntity,
	    final Collection<? extends SynchronizableEntity> references) {
	assertEntityExists(persistedEntity, testEntity);

	final String testEntityClass = testEntity.getClass().getSimpleName();
	final String referencedEntityClass = referencedEntity.getClass().getSimpleName();
	Assert.assertFalse("The created " + testEntityClass + " does not contain the " + referencedEntityClass
		+ "/s referenced:" + referencedEntityClass + " (" + referencedEntity + ")", references.isEmpty());
    }

    private void assertEntityUpdated(final SynchronizableEntity persistedEntity, final SynchronizableEntity testEntity,
	    final long originalUpdateCount, final Object originalPropertyValue, final Object updatedPropertyValue) {
	assertEntityExists(persistedEntity, testEntity);

	final String testEntityClass = testEntity.getClass().getSimpleName();
	Assert.assertTrue("The attempted update of " + testEntityClass + " in local DB failed!",
		persistedEntity.getUpdateCounter() > originalUpdateCount);
	Assert.assertNotEquals(
		"The changed propery failed to be applied as update of " + testEntityClass + " in local DB!",
		originalPropertyValue, updatedPropertyValue);
    }

    private void assertEntityDeleted(final SynchronizableEntity persistedEntity,
	    final SynchronizableEntity testEntity) {
	final String testEntityClass = testEntity.getClass().getSimpleName();
	Assert.assertNull("The attempted deletion of " + testEntityClass + " from local DB failed!", persistedEntity);
    }

    private void deleteCategoricalEntity(final Category createdCategory, final Category testCategory,
	    final CategoricalEntity referencedCategoricalEntity, final CategoricalEntity testCategoricalEntity,
	    boolean deleteCategory) {
	// 1. Remove links + update entities (foreach)
	// createdCategory.removeCategoricalEntity(referencedCategoricalEntity);
	referencedCategoricalEntity.removeCategory(createdCategory);

	final Pair<Category, CategoricalEntity> unlinkedEntities = transactionTemplate.execute(txStat -> {
	    return sMethod(createdCategory, referencedCategoricalEntity);
	});

	// 2. Delete Categorical Entity (NON-OWNER side of the relationship)
	transactionTemplate.execute(txStat -> {
	    return dMethod(referencedCategoricalEntity, unlinkedEntities);
	});

	final CategoricalEntity stillExistingCategoricalEntity = transactionTemplate.execute(txStat -> {
	    final String entityName = referencedCategoricalEntity.getName();

	    CategoricalEntity categoricalEntity = null;
	    if (referencedCategoricalEntity instanceof Customer) {
		categoricalEntity = localCustomerRepository.findByName(entityName);
	    } else if (referencedCategoricalEntity instanceof Project) {
		categoricalEntity = localProjectRepository.findByName(entityName);
	    } else if (referencedCategoricalEntity instanceof Task) {
		categoricalEntity = localTaskRepository.findByName(entityName);
	    }

	    return categoricalEntity;
	});
	assertEntityDeleted(stillExistingCategoricalEntity, testCategoricalEntity);

	if (deleteCategory) {
	    // 3. Delete Category (OWNER side of the relationship)
	    transactionTemplate.execute(txStat -> {
		localCategoryRepository.delete(unlinkedEntities.getFirst());
		return null;
	    });

	    final Category stillExistingCategory = transactionTemplate.execute(txStat -> {
		return localCategoryRepository.findByName(testCategory.getName());
	    });
	    assertEntityDeleted(stillExistingCategory, testCategory);
	}
    }

    private Object dMethod(final CategoricalEntity referencedCategoricalEntity,
	    final Pair<Category, CategoricalEntity> unlinkedEntities) {
	if (referencedCategoricalEntity instanceof Customer) {
	    localCustomerRepository.delete((Customer) unlinkedEntities.getSecond());
	} else if (referencedCategoricalEntity instanceof Project) {
	    localProjectRepository.delete((Project) unlinkedEntities.getSecond());
	} else if (referencedCategoricalEntity instanceof Task) {
	    localTaskRepository.delete((Task) unlinkedEntities.getSecond());
	}

	return null;
    }

    private Pair<Category, CategoricalEntity> sMethod(final Category createdCategory,
	    final CategoricalEntity referencedCategoricalEntity) {
	final Category category = localCategoryRepository.save(createdCategory);

	CategoricalEntity categoricalEntity = null;
	if (referencedCategoricalEntity instanceof Customer) {
	    categoricalEntity = localCustomerRepository.save((Customer) referencedCategoricalEntity);
	} else if (referencedCategoricalEntity instanceof Project) {
	    categoricalEntity = localProjectRepository.save((Project) referencedCategoricalEntity);
	} else if (referencedCategoricalEntity instanceof Task) {
	    categoricalEntity = localTaskRepository.save((Task) referencedCategoricalEntity);
	}

	return Pair.of(category, categoricalEntity);
    }
}
