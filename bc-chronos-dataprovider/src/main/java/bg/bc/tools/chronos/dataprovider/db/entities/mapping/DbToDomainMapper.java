package bg.bc.tools.chronos.dataprovider.db.entities.mapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBillingRateModifier.DModifierAction;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DChangelog;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DPerformer.DPriviledge;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

//TODO: Refactor null handling
public final class DbToDomainMapper {

    private static final Logger LOGGER = Logger.getLogger(DbToDomainMapper.class);

    private DbToDomainMapper() {
    }

    public static DCustomer dbToDomainCustomer(Customer dbCustomer) {
	if (dbCustomer == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	DCustomer domainCustomer = new DCustomer();
	domainCustomer.setSyncKey(dbCustomer.getSyncKey());
	domainCustomer.setId(dbCustomer.getId());
	domainCustomer.setName(dbCustomer.getName());
	domainCustomer.setDescription(dbCustomer.getDescription());

	dbToDomainCategory(dbCustomer.getCategory()).addCategoricalEntity(domainCustomer);

	return domainCustomer;
    }

    public static DProject dbToDomainProject(Project dbProject) {
	if (dbProject == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	DProject domainProject = new DProject();
	domainProject.setSyncKey(dbProject.getSyncKey());
	domainProject.setId(dbProject.getId());
	domainProject.setName(dbProject.getName());
	domainProject.setDescription(dbProject.getDescription());

	dbToDomainCustomer(dbProject.getCustomer()).addProject(domainProject);
	dbToDomainCategory(dbProject.getCategory()).addCategoricalEntity(domainProject);

	return domainProject;
    }

    public static DTask dbToDomainTask(Task dbTask) {
	if (dbTask == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	DTask domainTask = new DTask();
	domainTask.setSyncKey(dbTask.getSyncKey());
	domainTask.setId(dbTask.getId());
	domainTask.setName(dbTask.getName());
	domainTask.setDescription(dbTask.getDescription());
	domainTask.setHoursEstimated(dbTask.getHoursEstimated());
	// TODO: CONVERSION
	// final Date estimatedTime = dbTask.getEstimatedTime();
	// final Calendar calendar = Calendar.getInstance();
	// calendar.setTime(estimatedTime);
	// domainTask.setEstimatedTime(LocalTime.of(calendar.get(Calendar.HOUR),
	// calendar.get(Calendar.MINUTE)));

	dbToDomainProject(dbTask.getProject()).addTask(domainTask);
	dbToDomainCategory(dbTask.getCategory()).addCategoricalEntity(domainTask);

	return domainTask;
    }

    public static DPerformer dbToDomainPerformer(Performer dbPerformer) {
	if (dbPerformer == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	DPerformer domainPerformer = new DPerformer();
	domainPerformer.setSyncKey(dbPerformer.getSyncKey());
	domainPerformer.setId(dbPerformer.getId());
	domainPerformer.setHandle(dbPerformer.getHandle());
	domainPerformer.setPassword(dbPerformer.getPassword());
	domainPerformer.setName(dbPerformer.getName());
	domainPerformer.setEmail(dbPerformer.getEmail());
	domainPerformer.setLogged(dbPerformer.isLogged());
	domainPerformer.setPrimaryDeviceName(dbPerformer.getPrimaryDeviceName());

	final List<DPriviledge> domainPriviledges = dbPerformer.getPriviledges().stream() // nl
		.map(p -> p.name()) // nl
		.map(DPriviledge::valueOf) // nl
		.collect(Collectors.toList());
	domainPerformer.setPriviledges(domainPriviledges);

	// final Collection<Priviledge> priviledges =
	// dbPerformer.getPriviledges();
	// for (Priviledge priviledge : priviledges) {
	// domainPerformer.addPriviledge(DPriviledge.valueOf(priviledge.name()));
	// }

	return domainPerformer;
    }

    public static DBooking dbToDomainBooking(Booking dbBooking) {
	if (dbBooking == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	DBooking domainBooking = new DBooking();
	domainBooking.setSyncKey(dbBooking.getSyncKey());
	domainBooking.setId(dbBooking.getId());
	domainBooking.setDescription(dbBooking.getDescription());
	domainBooking
		.setStartTime(LocalDateTime.ofInstant(dbBooking.getStartTime().toInstant(), ZoneId.systemDefault()));
	domainBooking.setEndTime(LocalDateTime.ofInstant(dbBooking.getEndTime().toInstant(), ZoneId.systemDefault()));
	domainBooking.setHoursSpent(dbBooking.getHoursSpent());
	// TODO: CACL way
	// Duration.between(domainBooking.getEndTime(),
	// domainBooking.getStartTime()).get(ChronoUnit.HOURS)

	// domainBooking.setOvertime(dbBooking.isOvertime());
	// domainBooking.setEffectivelyStopped(dbBooking.isEffectivelyStopped());

	dbToDomainPerformer(dbBooking.getPerformer()).addBooking(domainBooking);
	dbToDomainTask(dbBooking.getTask()).addBooking(domainBooking);

	return domainBooking;
    }

    public static DRole dbToDomainRole(Role dbRole) {
	if (dbRole == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	DRole domainRole = new DRole();
	domainRole.setSyncKey(dbRole.getSyncKey());
	domainRole.setId(dbRole.getId());
	domainRole.setName(dbRole.getName());
	domainRole.setBillingRate(dbRole.getBillingRate());

	domainRole.setBooking(dbToDomainBooking(dbRole.getBooking()));
	dbToDomainBooking(dbRole.getBooking()).setRole(domainRole);

	dbToDomainCategory(dbRole.getCategory()).addCategoricalEntity(domainRole);

	return domainRole;
    }

    public static DCategory dbToDomainCategory(Category dbCategory) {
	if (dbCategory == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	DCategory domainCategory = new DCategory();
	domainCategory.setSyncKey(dbCategory.getSyncKey());
	domainCategory.setId(dbCategory.getId());
	domainCategory.setName(dbCategory.getName());
	domainCategory.setSortOrder(dbCategory.getSortOrder());

	return domainCategory;
    }

    public static DBillingRateModifier dbToDomainBillingRateModifier(BillingRateModifier dbBillingRateModifier) {
	if (dbBillingRateModifier == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	DBillingRateModifier domainBillingRateModifier = new DBillingRateModifier();
	domainBillingRateModifier.setSyncKey(dbBillingRateModifier.getSyncKey());
	domainBillingRateModifier.setId(dbBillingRateModifier.getId());
	domainBillingRateModifier
		.setModifierAction(DModifierAction.valueOf(dbBillingRateModifier.getModifierAction().name()));
	domainBillingRateModifier.setModifierValue(dbBillingRateModifier.getModifierValue());

	dbToDomainBooking(dbBillingRateModifier.getBooking()).addBillingRateModifier(domainBillingRateModifier);

	return domainBillingRateModifier;
    }

    public static DChangelog dbToDomainChangelog(Changelog dbChangelog) {
	if (dbChangelog == null) {
	    LOGGER.error("No DB entity(null)!");
	    return null;
	}

	final DChangelog domainChangelog = new DChangelog();
	domainChangelog.setUpdateCounter(dbChangelog.getUpdateCounter());
	domainChangelog.setUpdatedEntityKey(dbChangelog.getUpdatedEntityKey());
	domainChangelog.setDeviceName(dbChangelog.getDeviceName());
	domainChangelog.setChangeTime(
		LocalDateTime.ofInstant(dbChangelog.getChangeTime().toInstant(), ZoneId.systemDefault()));

	return domainChangelog;
    }
}
