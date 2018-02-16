package bc.bg.tools.chronos.configuration.tests.remote.crud;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import bc.bg.tools.chronos.configuration.RemoteDBConfig;
import bc.bg.tools.chronos.configuration.RemoteDataProviderConfig;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteCustomerService;

//TODO: Refactor test methods...
@SpringBootApplication
@ContextConfiguration(classes = { RemoteDBConfig.class, RemoteDataProviderConfig.class })
public class CustomerRemoteCrudTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private IRemoteCustomerService remoteCustomerService;

    private static Customer TEST_CUSTOMER;
    private static Category DEFAULT_CATEGORY;

    @BeforeClass
    public static void initialize() {
	TEST_CUSTOMER = new Customer();
	// TEST_CUSTOMER.setSyncKey(UUID.randomUUID().toString());
	TEST_CUSTOMER.setName("Georgi Iliev");
	TEST_CUSTOMER.setDescription("A test customer...");

	DEFAULT_CATEGORY = new Category();
	// DEFAULT_CATEGORY.setSyncKey(UUID.randomUUID().toString());
	DEFAULT_CATEGORY.setName("DEFAULT");
	DEFAULT_CATEGORY.setSortOrder(1);
	DEFAULT_CATEGORY.getCategoricalEntities().add(TEST_CUSTOMER);
    }

    @AfterClass
    public static void deinitialize() {
	TEST_CUSTOMER = null;
	DEFAULT_CATEGORY = null;
	System.gc();
    }

    @Test
    @Ignore
    public void testRemoteCrud() {
	testCreate();

	final Customer existingCustomer = testRead();

	testUpdate(existingCustomer);

	testDelete(existingCustomer);
    }

    public void testCreate() {
	final DCustomer customerAdded = remoteCustomerService
		.addCustomer(DbToDomainMapper.dbToDomainCustomer(TEST_CUSTOMER));
	Assert.assertNotNull(customerAdded);
    }

    public Customer testRead() {
	final DCustomer existingCustomer = remoteCustomerService.getCustomer(TEST_CUSTOMER.getName());
	Assert.assertNotNull(existingCustomer);

	return DomainToDbMapper.domainToDbCustomer(existingCustomer);
    }

    public void testUpdate(final Customer existingCustomer) {
	final String oldDescription = existingCustomer.getDescription();
	final String newDescription = oldDescription.substring(0, oldDescription.length() / 2);
	existingCustomer.setDescription(newDescription);

	final boolean customerUpdated = remoteCustomerService
		.updateCustomer(DbToDomainMapper.dbToDomainCustomer(existingCustomer));
	Assert.assertTrue(customerUpdated);

	final DCustomer updatedCustomer = remoteCustomerService.getCustomer(existingCustomer.getName());
	Assert.assertEquals(updatedCustomer.getDescription(), newDescription);
    }

    public void testDelete(final Customer existingCustomer) {
	final long id = existingCustomer.getId();

	final boolean removedCustomer = remoteCustomerService.removeCustomer(id);
	Assert.assertTrue(removedCustomer);
	Assert.assertNull(remoteCustomerService.getCustomer(id));
    }
}
