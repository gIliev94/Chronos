package bg.bc.tools.chronos.dataprovider.db.entities.mapping;

import java.time.LocalDateTime;
import java.time.ZoneId;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBillingRateModifier.DModifierAction;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public final class DbToDomainMapper {

    private DbToDomainMapper() {
    }

    public static DCustomer dbToDomainCustomer(Customer dbCustomer) {
	DCustomer domainCustomer = new DCustomer();
	domainCustomer.setId(dbCustomer.getId());
	domainCustomer.setName(dbCustomer.getName());
	domainCustomer.setDescription(dbCustomer.getDescription());

	dbToDomainCategory(dbCustomer.getCategory()).addCategoricalEntity(domainCustomer);

	return domainCustomer;
    }

    public static DProject dbToDomainProject(Project dbProject) {
	DProject domainProject = new DProject();
	domainProject.setId(dbProject.getId());
	domainProject.setName(dbProject.getName());
	domainProject.setDescription(dbProject.getDescription());

	dbToDomainCustomer(dbProject.getCustomer()).addProject(domainProject);
	dbToDomainCategory(dbProject.getCategory()).addCategoricalEntity(domainProject);

	return domainProject;
    }

    public static DTask dbToDomainTask(Task dbTask) {
	DTask domainTask = new DTask();
	domainTask.setId(dbTask.getId());
	domainTask.setName(dbTask.getName());
	domainTask.setDescription(dbTask.getDescription());
	domainTask.setEstimatedTimeHours(dbTask.getEstimatedTimeHours());
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
	DPerformer domainPerformer = new DPerformer();
	domainPerformer.setId(dbPerformer.getId());
	domainPerformer.setHandle(dbPerformer.getHandle());
	domainPerformer.setPassword(dbPerformer.getPassword());
	domainPerformer.setName(dbPerformer.getName());
	domainPerformer.setEmail(dbPerformer.getEmail());
	domainPerformer.setLogged(dbPerformer.isLogged());

	return domainPerformer;
    }

    public static DBooking dbToDomainBooking(Booking dbBooking) {
	DBooking domainBooking = new DBooking();
	domainBooking.setId(dbBooking.getId());
	domainBooking.setDescription(dbBooking.getDescription());
	domainBooking
		.setStartTime(LocalDateTime.ofInstant(dbBooking.getStartTime().toInstant(), ZoneId.systemDefault()));
	domainBooking.setEndTime(LocalDateTime.ofInstant(dbBooking.getEndTime().toInstant(), ZoneId.systemDefault()));
	// domainBooking.setOvertime(dbBooking.isOvertime());
	// domainBooking.setEffectivelyStopped(dbBooking.isEffectivelyStopped());

	dbToDomainPerformer(dbBooking.getPerformer()).addBooking(domainBooking);
	dbToDomainTask(dbBooking.getTask()).addBooking(domainBooking);

	return domainBooking;
    }

    public static DRole dbToDomainRole(Role dbRole) {
	DRole domainRole = new DRole();
	domainRole.setId(dbRole.getId());
	domainRole.setName(dbRole.getName());
	domainRole.setBillingRate(dbRole.getBillingRate());

	domainRole.setBooking(dbToDomainBooking(dbRole.getBooking()));
	dbToDomainBooking(dbRole.getBooking()).setRole(domainRole);
	
	dbToDomainCategory(dbRole.getCategory()).addCategoricalEntity(domainRole);

	return domainRole;
    }

    public static DCategory dbToDomainCategory(Category dbCategory) {
	DCategory domainCategory = new DCategory();
	domainCategory.setId(dbCategory.getId());
	domainCategory.setName(dbCategory.getName());
	domainCategory.setSortOrder(dbCategory.getSortOrder());

	return domainCategory;
    }

    public static DBillingRateModifier dbToDomainBillingRateModifier(BillingRateModifier dbBillingRateModifier) {
	DBillingRateModifier domainBillingRateModifier = new DBillingRateModifier();
	domainBillingRateModifier.setId(dbBillingRateModifier.getId());
	domainBillingRateModifier
		.setModifierAction(DModifierAction.valueOf(dbBillingRateModifier.getModifierAction().name()));
	domainBillingRateModifier.setModifierValue(dbBillingRateModifier.getModifierValue());

	dbToDomainBooking(dbBillingRateModifier.getBooking()).addBillingRateModifier(domainBillingRateModifier);

	return domainBillingRateModifier;
    }
}
