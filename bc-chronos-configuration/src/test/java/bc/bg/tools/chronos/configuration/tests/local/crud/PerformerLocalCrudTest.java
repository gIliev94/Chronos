package bc.bg.tools.chronos.configuration.tests.local.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import bc.bg.tools.chronos.configuration.LocalDBConfig;
import bc.bg.tools.chronos.configuration.LocalDataProviderConfig;
import bg.bc.tools.chronos.core.entities.DChangelog;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DPerformer.DPriviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalChangelogService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalPerformerService;

// TODO: Refactor test methods...
@SpringBootApplication
@ContextConfiguration(classes = { LocalDBConfig.class, LocalDataProviderConfig.class })
public class PerformerLocalCrudTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private ILocalPerformerService localPerformerService;

    @Autowired
    private ILocalChangelogService localChangelogService;

    private static Performer TEST_PERFORMER;

    //https://stackoverflow.com/questions/7883542/getting-the-computer-name-in-java/17956000#17956000
    private static String getComputerName() {
	Map<String, String> env = System.getenv();
	if (env.containsKey("COMPUTERNAME")) {
	    return env.get("COMPUTERNAME");
	} else if (env.containsKey("HOSTNAME")) {
	    return env.get("HOSTNAME");
	} else {
	    try {
		final String hostName = InetAddress.getLocalHost().getHostName();
		return hostName;
	    } catch (UnknownHostException e) {
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
		    p = r.exec("uname -a");
		    BufferedReader rdr = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    // System.out.println(r.readLine());
		    return rdr.readLine();
		} catch (IOException e1) {
		    return "UNKNOWN";
		}
	    }
	}
    }

    @BeforeClass
    public static void initialize() {
	TEST_PERFORMER = new Performer();
	TEST_PERFORMER.setName("Georgi Iliev");
	TEST_PERFORMER.setHandle("gil");
	TEST_PERFORMER.setPassword("1232".toCharArray());
	TEST_PERFORMER.setPrimaryDeviceName(getComputerName());
	TEST_PERFORMER.setEmail("gil@systec-services.com");
	TEST_PERFORMER.setLogged(false);

	TEST_PERFORMER.addPriviledge(Priviledge.READ);
	TEST_PERFORMER.addPriviledge(Priviledge.WRITE);
    }

    @AfterClass
    public static void deinitialize() {
	TEST_PERFORMER = null;
	System.gc();
    }

    @Test
    public void testLocalCrud() {
	testCreate();

	final Performer existingPerformer = testRead();
	System.err.println(existingPerformer);
	//
	// testUpdate(existingCustomer);
	//
	// testDelete(existingCustomer);
    }

    public void testCreate() {
	final boolean performerAdded = localPerformerService
		.addPerformer(DbToDomainMapper.dbToDomainPerformer(TEST_PERFORMER));
	Assert.assertTrue(performerAdded);

	final Changelog changeLog = new Changelog();
	changeLog.setChangeTime(Calendar.getInstance().getTime());
	changeLog.setDeviceName(TEST_PERFORMER.getPrimaryDeviceName());
	changeLog.setUpdatedEntityKey(localPerformerService.getPerformer(TEST_PERFORMER.getHandle()).getSyncKey());

	final DChangelog lastChangelog = localChangelogService.getLastChangelog();
	if (lastChangelog == null) {
	    changeLog.setUpdateCounter(0);
	} else {
	    changeLog.setUpdateCounter(lastChangelog.getUpdateCounter() + 1);
	}
	
	final boolean changelogAdded = localChangelogService.addChangelog(DbToDomainMapper.dbToDomainChangelog(changeLog));
	Assert.assertTrue(changelogAdded);
    }

    public Performer testRead() {
	final List<DPerformer> existingPerformers = localPerformerService.getPerformers(DPriviledge.READ);
	Assert.assertNotNull(existingPerformers);
	Assert.assertNotEquals(0, existingPerformers.size());

	final List<DPerformer> existingPerformersAlt = localPerformerService
		.getPerformers(Arrays.asList(DPriviledge.READ, DPriviledge.WRITE));
	Assert.assertNotNull(existingPerformersAlt);
	Assert.assertNotEquals(0, existingPerformersAlt.size());

	return DomainToDbMapper.domainToDbPerformer(existingPerformers.get(0));
    }
    //
    // public void testUpdate(final Customer existingCustomer) {
    // final String oldDescription = existingCustomer.getDescription();
    // final String newDescription = oldDescription.substring(0,
    // oldDescription.length() / 2);
    // existingCustomer.setDescription(newDescription);
    //
    // final boolean customerUpdated = localCustomerService
    // .updateCustomer(DbToDomainMapper.dbToDomainCustomer(existingCustomer));
    // Assert.assertTrue(customerUpdated);
    //
    // final DCustomer updatedCustomer =
    // localCustomerService.getCustomer(existingCustomer.getName());
    // Assert.assertEquals(updatedCustomer.getDescription(), newDescription);
    // }
    //
    // public void testDelete(final Customer existingCustomer) {
    // final long id = existingCustomer.getId();
    //
    // final boolean removedCustomer = localCustomerService.removeCustomer(id);
    // Assert.assertTrue(removedCustomer);
    // Assert.assertNull(localCustomerService.getCustomer(id));
    // }
}
