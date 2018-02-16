package bc.bg.tools.chronos.configuration.tests.local.crud;

import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.configuration.CommonDBConfig;
import bc.bg.tools.chronos.configuration.RemoteDBConfig;
import bc.bg.tools.chronos.configuration.RemoteDataProviderConfig;
import bg.bc.tools.chronos.dataprovider.db.entities.CategoricalEntity;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCustomerRepository;

// TODO: Refactor test methods...
@SpringBootApplication
// @ContextConfiguration(classes = { CommonDBConfig.class, LocalDBConfig.class,
// LocalDataProviderConfig.class })
@ContextConfiguration(classes = { CommonDBConfig.class, RemoteDBConfig.class, RemoteDataProviderConfig.class })
// @Ignore
public class CategoryCategoricalEntityCrudTest extends AbstractJUnit4SpringContextTests {

    // @Autowired
    // private ILocalPerformerService localPerformerService;

    @Autowired
    private RemoteCategoryRepository remoteCategoryRepository;

    @Autowired
    private RemoteCustomerRepository remoteCustomerRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    // @Autowired
    // private ILocalChangelogService localChangelogService;

    private static Customer TEST_CUSTOMER;

    private static Category TEST_CATEGORY;

    @BeforeClass
    public static void initialize() {
	TEST_CUSTOMER = new Customer();
	TEST_CUSTOMER.setName("RAID");
	TEST_CUSTOMER.setDescription("Bug-expellent solutions");

	TEST_CATEGORY = new Category();
	TEST_CATEGORY.setName("NON-PHARMA");
	TEST_CATEGORY.setSortOrder(1);

	TEST_CATEGORY.getCategoricalEntities().add(TEST_CUSTOMER);
	// TODO:
	// TEST_CUSTOMER.getCategories().add(TEST_CATEGORY);
    }

    @AfterClass
    public static void deinitialize() {
	TEST_CUSTOMER = null;
	TEST_CATEGORY = null;
	System.gc();
    }

    @Test
    public void testLocalCrud() {
	try {
	    final Category savedCategory = transactionTemplate.execute(r -> {
		return remoteCategoryRepository.save(TEST_CATEGORY);
	    });
	    Assert.assertNotNull(savedCategory);

	    final Customer savedCustomer = transactionTemplate.execute(r -> {
		return remoteCustomerRepository.save(TEST_CUSTOMER);
	    });
	    Assert.assertNotNull(savedCustomer);

	    final Category fetchedCategory = transactionTemplate.execute(r -> {
		return remoteCategoryRepository.findOne(savedCategory.getId());
	    });

	    final Set<CategoricalEntity> categoricalEntities = fetchedCategory.getCategoricalEntities();
	    System.out.println(categoricalEntities);
	    Assert.assertNotNull(categoricalEntities);

	    final Customer fetchedCustomer = transactionTemplate.execute(r -> {
		return remoteCustomerRepository.findOne(savedCustomer.getId());
	    });

	    final Set<Category> categories = fetchedCustomer.getCategories();
	    System.out.println(categories);
	    Assert.assertNotNull(categories);

	} catch (Exception e) {
	    ExceptionUtils.getRootCause(e).printStackTrace(System.err);
	}
    }
}
