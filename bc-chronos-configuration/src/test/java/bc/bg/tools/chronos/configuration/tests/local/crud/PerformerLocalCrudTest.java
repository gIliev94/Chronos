package bc.bg.tools.chronos.configuration.tests.local.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.support.TransactionTemplate;

import bc.bg.tools.chronos.configuration.CommonDBConfig;
import bc.bg.tools.chronos.configuration.RemoteDBConfig;
import bc.bg.tools.chronos.configuration.RemoteDataProviderConfig;
import bg.bc.tools.chronos.core.entities.DChangelog;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DPerformer.DPriviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Privilege;
import bg.bc.tools.chronos.dataprovider.db.entities.Privilege.UserPrivilege;
import bg.bc.tools.chronos.dataprovider.db.entities.User;
import bg.bc.tools.chronos.dataprovider.db.entities.UserGroup;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalChangelogService;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalPerformerService;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemotePerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteUserGroupRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteUserRepository;

// TODO: Refactor test methods...
@SpringBootApplication
// @ContextConfiguration(classes = { CommonDBConfig.class, LocalDBConfig.class,
// LocalDataProviderConfig.class })
@ContextConfiguration(classes = { CommonDBConfig.class, RemoteDBConfig.class, RemoteDataProviderConfig.class })
public class PerformerLocalCrudTest extends AbstractJUnit4SpringContextTests {

    // @Autowired
    // private ILocalPerformerService localPerformerService;

    @Autowired
    private RemoteUserGroupRepository remoteUserGroupRepository;

    @Autowired
    private RemoteUserRepository remoteUserRepository;

    @Autowired
    private RemotePerformerRepository remotePerformerRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    // @Autowired
    // private ILocalChangelogService localChangelogService;

    private static Performer TEST_PERFORMER;

    private static User TEST_USER;

    private static UserGroup TEST_USERGROUP;

    // https://stackoverflow.com/questions/7883542/getting-the-computer-name-in-java/17956000#17956000
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

	BillingRole br = new BillingRole();
	br.setBillingRate(90.0d);
	br.setDescription("desc");
	br.setName("dev");
	// br.setUpdateCounter(0);
	// br.setSyncCounter(0);

	TEST_PERFORMER.setBillingRole(br);
	// TEST_PERFORMER.setSyncCounter(syncCounter);
	// TEST_PERFORMER.setUpdateCounter(updateCounter);

	TEST_USERGROUP = new UserGroup();
	// TEST_USERGROUP.setSyncCounter(syncCounter);
	// TEST_USERGROUP.setUpdateCounter(updateCounter);
	TEST_USERGROUP.setName("Basic Contributors");
	TEST_USERGROUP.setDescription("A group with minimal rights for contributing...");

	final Privilege readPrivilege = new Privilege();
	readPrivilege.setPrivilege(UserPrivilege.READ);
	TEST_USERGROUP.getPrivileges().add(readPrivilege);

	final Privilege writePrivilege = new Privilege();
	writePrivilege.setPrivilege(UserPrivilege.WRITE);
	TEST_USERGROUP.getPrivileges().add(writePrivilege);

	TEST_USER = new User();
	TEST_USER.setFirstName("Georgi");
	TEST_USER.setLastName("Iliev");
	TEST_USER.setAbbreviation("gil");
	TEST_USER.setPassword("1232".toCharArray());
	TEST_USER.setEmail("gil@systec-services.com");

	TEST_USER.getUserGroups().add(TEST_USERGROUP);
	TEST_USERGROUP.getUsers().add(TEST_USER);

	// TEST_USERGROUP.addPriviledge(UserPrivilege.READ);
	// TEST_USERGROUP.addPriviledge(UserPrivilege.WRITE);

	TEST_PERFORMER.setUser(TEST_USER);
    }

    @AfterClass
    public static void deinitialize() {
	TEST_PERFORMER = null;
	System.gc();
    }

    @Test
    // @Ignore
    public void testLocalCrud() {
	try {
	    final Performer savedPerformer = transactionTemplate.execute(r -> {
		remoteUserGroupRepository.save(TEST_USERGROUP);
		remoteUserRepository.save(TEST_USER);

		return remotePerformerRepository.save(TEST_PERFORMER);
	    });

	    Assert.assertNotNull(savedPerformer);
	} catch (Exception e) {
	    ExceptionUtils.getRootCause(e).printStackTrace(System.err);
	}

	// testCreate();
	//
	// final Performer existingPerformer = testRead();
	// System.err.println(existingPerformer);
	// //
	// // testUpdate(existingCustomer);
	// //
	// // testDelete(existingCustomer);
    }

    // public void testCreate() {
    // final DPerformer performerAdded = localPerformerService
    // .addPerformer(DbToDomainMapper.dbToDomainPerformer(TEST_PERFORMER));
    // Assert.assertTrue(performerAdded != null);
    //
    // final Changelog changeLog = new Changelog();
    // changeLog.setChangeTime(Calendar.getInstance().getTime());
    // Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    // changeLog.setDeviceName(null);
    // changeLog.setUpdatedEntityKey(localPerformerService.getPerformer(TEST_USER.getAbbreviation()).getSyncKey());
    //
    // final DChangelog lastChangelog =
    // localChangelogService.getLastChangelog();
    // if (lastChangelog == null) {
    // changeLog.setUpdateCounter(0);
    // } else {
    // changeLog.setUpdateCounter(lastChangelog.getUpdateCounter() + 1);
    // }
    //
    // final boolean changelogAdded = localChangelogService
    // .addChangelog(DbToDomainMapper.dbToDomainChangelog(changeLog));
    // Assert.assertTrue(changelogAdded);
    // }
    //
    // public Performer testRead() {
    // final List<DPerformer> existingPerformers =
    // localPerformerService.getPerformers(DPriviledge.READ);
    // Assert.assertNotNull(existingPerformers);
    // Assert.assertNotEquals(0, existingPerformers.size());
    //
    // final List<DPerformer> existingPerformersAlt = localPerformerService
    // .getPerformers(Arrays.asList(DPriviledge.READ, DPriviledge.WRITE));
    // Assert.assertNotNull(existingPerformersAlt);
    // Assert.assertNotEquals(0, existingPerformersAlt.size());
    //
    // return DomainToDbMapper.domainToDbPerformer(existingPerformers.get(0));
    // }
    // //
    // // public void testUpdate(final Customer existingCustomer) {
    // // final String oldDescription = existingCustomer.getDescription();
    // // final String newDescription = oldDescription.substring(0,
    // // oldDescription.length() / 2);
    // // existingCustomer.setDescription(newDescription);
    // //
    // // final boolean customerUpdated = localCustomerService
    // //
    // .updateCustomer(DbToDomainMapper.dbToDomainCustomer(existingCustomer));
    // // Assert.assertTrue(customerUpdated);
    // //
    // // final DCustomer updatedCustomer =
    // // localCustomerService.getCustomer(existingCustomer.getName());
    // // Assert.assertEquals(updatedCustomer.getDescription(), newDescription);
    // // }
    // //
    // // public void testDelete(final Customer existingCustomer) {
    // // final long id = existingCustomer.getId();
    // //
    // // final boolean removedCustomer =
    // localCustomerService.removeCustomer(id);
    // // Assert.assertTrue(removedCustomer);
    // // Assert.assertNull(localCustomerService.getCustomer(id));
    // // }
}
