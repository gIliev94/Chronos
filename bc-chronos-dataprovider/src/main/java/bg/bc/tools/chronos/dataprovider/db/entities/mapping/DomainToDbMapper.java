package bg.bc.tools.chronos.dataprovider.db.entities.mapping;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DChangelog;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier.ModifierAction;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

//TODO: Refactor null handling
public final class DomainToDbMapper {

    private static final Logger LOGGER = Logger.getLogger(DomainToDbMapper.class);

    private DomainToDbMapper() {
    }

    public static Customer domainToDbCustomer(DCustomer domainCustomer) {
	if (domainCustomer == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final Customer dbCustomer = new Customer();
	dbCustomer.setSyncKey(domainCustomer.getSyncKey());
	dbCustomer.setId(domainCustomer.getId());
	dbCustomer.setName(domainCustomer.getName());
	dbCustomer.setDescription(domainCustomer.getDescription());

	domainToDbCategory(domainCustomer.getCategory()).addCategoricalEntity(dbCustomer);

	return dbCustomer;
    }

    public static Project domainToDbProject(DProject domainProject) {
	if (domainProject == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final Project dbProject = new Project();
	dbProject.setSyncKey(domainProject.getSyncKey());
	dbProject.setId(domainProject.getId());
	dbProject.setName(domainProject.getName());
	dbProject.setDescription(domainProject.getDescription());

	domainToDbCustomer(domainProject.getCustomer()).addProject(dbProject);
	domainToDbCategory(domainProject.getCategory()).addCategoricalEntity(dbProject);

	return dbProject;
    }

    public static Task domainToDbTask(DTask domainTask) {
	if (domainTask == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final Task dbTask = new Task();
	dbTask.setSyncKey(domainTask.getSyncKey());
	dbTask.setId(domainTask.getId());
	dbTask.setName(domainTask.getName());
	dbTask.setDescription(domainTask.getDescription());
	dbTask.setHoursEstimated(domainTask.getHoursEstimated());

	domainToDbProject(domainTask.getProject()).addTask(dbTask);
	domainToDbCategory(domainTask.getCategory()).addCategoricalEntity(dbTask);

	return dbTask;
    }

    public static Performer domainToDbPerformer(DPerformer domainPerformer) {
	if (domainPerformer == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final Performer dbPerformer = new Performer();
	dbPerformer.setSyncKey(domainPerformer.getSyncKey());
	dbPerformer.setId(domainPerformer.getId());
	dbPerformer.setHandle(domainPerformer.getHandle());
	dbPerformer.setPassword(domainPerformer.getPassword());
	dbPerformer.setName(domainPerformer.getName());
	dbPerformer.setEmail(domainPerformer.getEmail());
	dbPerformer.setLogged(domainPerformer.isLogged());
	dbPerformer.setPrimaryDeviceName(domainPerformer.getPrimaryDeviceName());

	final List<Priviledge> dbPriviledges = domainPerformer.getPriviledges().stream() // nl
		.map(p -> p.name()) // nl
		.map(Priviledge::valueOf) // nl
		.collect(Collectors.toList());
	dbPerformer.setPriviledges(dbPriviledges);

	// final Collection<DPriviledge> priviledges =
	// domainPerformer.getPriviledges();
	// for (DPriviledge priviledge : priviledges) {
	// dbPerformer.addPriviledge(Priviledge.valueOf(priviledge.name()));
	// }

	return dbPerformer;
    }

    public static Booking domainToDbBooking(DBooking domainBooking) {
	if (domainBooking == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final Booking dbBooking = new Booking();
	dbBooking.setSyncKey(domainBooking.getSyncKey());
	dbBooking.setId(domainBooking.getId());
	dbBooking.setDescription(domainBooking.getDescription());
	dbBooking.setStartTime(Date.from(domainBooking.getStartTime().atZone(ZoneId.systemDefault()).toInstant()));
	dbBooking.setEndTime(Date.from(domainBooking.getEndTime().atZone(ZoneId.systemDefault()).toInstant()));
	dbBooking.setHoursSpent(domainBooking.getHoursSpent());
	// dbBooking.setOvertime(domainBooking.isOvertime());
	// dbBooking.setEffectivelyStopped(domainBooking.isEffectivelyStopped());

	domainToDbPerformer(domainBooking.getPerformer()).addBooking(dbBooking);
	domainToDbTask(domainBooking.getTask()).addBooking(dbBooking);

	return dbBooking;
    }

    public static Role domainToDbRole(DRole domainRole) {
	if (domainRole == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final Role dbRole = new Role();
	dbRole.setSyncKey(domainRole.getSyncKey());
	dbRole.setId(domainRole.getId());
	dbRole.setName(domainRole.getName());
	dbRole.setBillingRate(domainRole.getBillingRate());

	dbRole.setBooking(domainToDbBooking(domainRole.getBooking()));
	domainToDbBooking(domainRole.getBooking()).setRole(dbRole);

	domainToDbCategory(domainRole.getCategory()).addCategoricalEntity(dbRole);

	return dbRole;
    }

    public static Category domainToDbCategory(DCategory domainCategory) {
	if (domainCategory == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final Category dbCategory = new Category();
	dbCategory.setSyncKey(domainCategory.getSyncKey());
	dbCategory.setId(domainCategory.getId());
	dbCategory.setName(domainCategory.getName());
	dbCategory.setSortOrder(domainCategory.getSortOrder());

	return dbCategory;
    }

    public static BillingRateModifier domainToDbBillingRateModifier(DBillingRateModifier domainBillingRateModifier) {
	if (domainBillingRateModifier == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final BillingRateModifier dbBillingRateModifier = new BillingRateModifier();
	dbBillingRateModifier.setSyncKey(domainBillingRateModifier.getSyncKey());
	dbBillingRateModifier.setId(domainBillingRateModifier.getId());
	dbBillingRateModifier
		.setModifierAction(ModifierAction.valueOf(domainBillingRateModifier.getModifierAction().name()));
	dbBillingRateModifier.setModifierValue(domainBillingRateModifier.getModifierValue());

	domainToDbBooking(domainBillingRateModifier.getBooking()).addBillingRateModifier(dbBillingRateModifier);

	return dbBillingRateModifier;
    }

    public static Changelog domainToDbChangelog(DChangelog domainChangelog) {	
	if (domainChangelog == null) {
	    LOGGER.error("No domain entity(null)!");
	    return null;
	}

	final Changelog dbChangelog = new Changelog();
	dbChangelog.setUpdateCounter(domainChangelog.getUpdateCounter());
	dbChangelog.setUpdatedEntityKey(domainChangelog.getUpdatedEntityKey());
	dbChangelog.setDeviceName(domainChangelog.getDeviceName());
	dbChangelog.setChangeTime(Date.from(domainChangelog.getChangeTime().atZone(ZoneId.systemDefault()).toInstant()));

	return dbChangelog;
    }
}
