package bc.bg.tools.chronos.configuration.tests.remote.crud;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import bc.bg.tools.chronos.configuration.tests.remote.RemoteTestConfiguration;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCustomerRepository;

@SpringBootApplication
@ContextConfiguration(classes = { RemoteTestConfiguration.class })
public class CustomerRemoteCrudTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RemoteCustomerRepository customerRepo;

    // @Autowired
    // private RemoteCustomerService customerService;

    private static Customer TEST_CUSTOMER;

    @BeforeClass
    public static void initialize() {
	TEST_CUSTOMER = new Customer();
	TEST_CUSTOMER.setName("Georgi Iliev");
	TEST_CUSTOMER.setDescription("A test customer...");
    }

    @AfterClass
    public static void deinitialize() {
	TEST_CUSTOMER = null;
	System.gc();
    }

    @Test
    public void testLocalCrud() {
	testCreate();

	final Customer existingCustomer = testRead();

	testUpdate(existingCustomer);

	testDelete();
    }

    public void testCreate() {
	// final Customer customer = new Customer();
	// customer.setName("Georgi Iliev test");
	// customer.setDescription("A test customer...");

	final Customer persistedCustomer = customerRepo.save(TEST_CUSTOMER);
	// final boolean ffs =
	// customerService.addClient(DbToDomainMapper.dbToDomainCustomer(customer));
	Assert.assertNotNull(persistedCustomer);
    }

    public Customer testRead() {
	final Customer existingCustomer = customerRepo.findByName(TEST_CUSTOMER.getName());
	Assert.assertNotNull(existingCustomer);

	return existingCustomer;
    }

    public void testUpdate(final Customer existingCustomer) {
	final String oldDescription = existingCustomer.getDescription();
	final String newDescription = oldDescription.substring(0, oldDescription.length() / 2);
	existingCustomer.setDescription(newDescription);

	final Customer updatedCustomer = customerRepo.save(existingCustomer);
	Assert.assertEquals(updatedCustomer.getDescription(), newDescription);
    }

    public void testDelete() {
	final long id = TEST_CUSTOMER.getId();
	customerRepo.delete(id);
	Assert.assertNull(customerRepo.findOne(id));
    }
}
