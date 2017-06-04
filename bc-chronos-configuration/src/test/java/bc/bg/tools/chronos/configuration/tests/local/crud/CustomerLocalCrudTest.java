package bc.bg.tools.chronos.configuration.tests.local.crud;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import bc.bg.tools.chronos.configuration.tests.local.LocalTestConfiguration;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;

@SpringBootApplication
@ContextConfiguration(classes = { LocalTestConfiguration.class })
public class CustomerLocalCrudTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private ILocalCustomerService localCustomerService;

    // @Autowired
    // private ILocalCategoryService localCategoryService;

    private static Customer TEST_CUSTOMER;
    private static Category DEFAULT_CATEGORY;

    @BeforeClass
    public static void initialize() {
	TEST_CUSTOMER = new Customer();
	TEST_CUSTOMER.setName("Georgi Iliev");
	TEST_CUSTOMER.setDescription("A test customer...");

	DEFAULT_CATEGORY = new Category();
	DEFAULT_CATEGORY.setName("DEFAULT");
	DEFAULT_CATEGORY.setSortOrder(1);
	DEFAULT_CATEGORY.addCategoricalEntity(TEST_CUSTOMER);
    }

    @AfterClass
    public static void deinitialize() {
	TEST_CUSTOMER = null;
	DEFAULT_CATEGORY = null;
	System.gc();
    }

    @Test
    public void testLocalCrud() {
	testCreate();

	final Customer existingCustomer = testRead();

	testUpdate(existingCustomer);

	testDelete(existingCustomer);
    }

    public void testCreate() {
	// final boolean categoryAdded = localCategoryService
	// .addCategory(DbToDomainMapper.dbToDomainCategory(DEFAULT_CATEGORY));
	// Assert.assertTrue(categoryAdded);

	final boolean customerAdded = localCustomerService
		.addCustomer(DbToDomainMapper.dbToDomainCustomer(TEST_CUSTOMER));
	Assert.assertTrue(customerAdded);
    }

    public Customer testRead() {
	final DCustomer existingCustomer = localCustomerService.getCustomer(TEST_CUSTOMER.getName());
	Assert.assertNotNull(existingCustomer);

	return DomainToDbMapper.domainToDbCustomer(existingCustomer);
    }

    public void testUpdate(final Customer existingCustomer) {
	final String oldDescription = existingCustomer.getDescription();
	final String newDescription = oldDescription.substring(0, oldDescription.length() / 2);
	existingCustomer.setDescription(newDescription);

	final boolean customerUpdated = localCustomerService
		.updateCustomer(DbToDomainMapper.dbToDomainCustomer(existingCustomer));
	Assert.assertTrue(customerUpdated);

	final DCustomer updatedCustomer = localCustomerService.getCustomer(existingCustomer.getName());
	Assert.assertEquals(updatedCustomer.getDescription(), newDescription);
    }

    public void testDelete(final Customer existingCustomer) {
	final long id = existingCustomer.getId();

	final boolean removedCustomer = localCustomerService.removeCustomer(id);
	Assert.assertTrue(removedCustomer);
	Assert.assertNull(localCustomerService.getCustomer(id));
    }
}
