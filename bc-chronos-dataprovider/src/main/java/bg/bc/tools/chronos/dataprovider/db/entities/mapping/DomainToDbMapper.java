package bg.bc.tools.chronos.dataprovider.db.entities.mapping;

import java.time.ZoneId;
import java.util.Date;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier.ModifierAction;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public final class DomainToDbMapper {

    private DomainToDbMapper() {
    }

    public static Customer domainToDbCustomer(DCustomer domainCustomer) {
	Customer dbCustomer = new Customer();
	dbCustomer.setId(domainCustomer.getId());
	dbCustomer.setName(domainCustomer.getName());
	dbCustomer.setDescription(domainCustomer.getDescription());

	domainToDbCategory(domainCustomer.getCategory()).addCategoricalEntity(dbCustomer);

	return dbCustomer;
    }

    public static Project domainToDbProject(DProject domainProject) {
	Project dbProject = new Project();
	dbProject.setId(domainProject.getId());
	dbProject.setName(domainProject.getName());
	dbProject.setDescription(domainProject.getDescription());

	domainToDbCustomer(domainProject.getCustomer()).addProject(dbProject);
	domainToDbCategory(domainProject.getCategory()).addCategoricalEntity(dbProject);

	return dbProject;
    }

    public static Task domainToDbTask(DTask domainTask) {
	Task dbTask = new Task();
	dbTask.setId(domainTask.getId());
	dbTask.setName(domainTask.getName());
	dbTask.setDescription(domainTask.getDescription());
	dbTask.setEstimatedTimeHours(domainTask.getEstimatedTimeHours());

	domainToDbProject(domainTask.getProject()).addTask(dbTask);
	domainToDbCategory(domainTask.getCategory()).addCategoricalEntity(dbTask);

	return dbTask;
    }

    public static Performer domainToDbPerformer(DPerformer domainPerformer) {
	Performer dbPerformer = new Performer();
	dbPerformer.setId(domainPerformer.getId());
	dbPerformer.setHandle(domainPerformer.getHandle());
	dbPerformer.setPassword(domainPerformer.getPassword());
	dbPerformer.setName(domainPerformer.getName());
	dbPerformer.setEmail(domainPerformer.getEmail());
	dbPerformer.setLogged(domainPerformer.isLogged());

	return dbPerformer;
    }

    public static Booking domainToDbBooking(DBooking domainBooking) {
	Booking dbBooking = new Booking();
	dbBooking.setId(domainBooking.getId());
	dbBooking.setDescription(domainBooking.getDescription());
	dbBooking.setStartTime(Date.from(domainBooking.getStartTime().atZone(ZoneId.systemDefault()).toInstant()));
	dbBooking.setEndTime(Date.from(domainBooking.getEndTime().atZone(ZoneId.systemDefault()).toInstant()));
	// dbBooking.setOvertime(domainBooking.isOvertime());
	// dbBooking.setEffectivelyStopped(domainBooking.isEffectivelyStopped());

	domainToDbPerformer(domainBooking.getPerformer()).addBooking(dbBooking);
	domainToDbTask(domainBooking.getTask()).addBooking(dbBooking);

	return dbBooking;
    }

    public static Role domainToDRole(DRole domainRole) {
	Role dbRole = new Role();
	dbRole.setId(domainRole.getId());
	dbRole.setName(domainRole.getName());
	dbRole.setBillingRate(domainRole.getBillingRate());

	dbRole.setBooking(domainToDbBooking(domainRole.getBooking()));
	domainToDbBooking(domainRole.getBooking()).setRole(dbRole);

	domainToDbCategory(domainRole.getCategory()).addCategoricalEntity(dbRole);

	return dbRole;
    }

    public static Category domainToDbCategory(DCategory domainCategory) {
	Category dbCategory = new Category();
	dbCategory.setId(domainCategory.getId());
	dbCategory.setName(domainCategory.getName());
	dbCategory.setSortOrder(domainCategory.getSortOrder());

	return dbCategory;
    }

    public static BillingRateModifier domainToDbBillingRateModifier(DBillingRateModifier domainBillingRateModifier) {
	BillingRateModifier dbBillingRateModifier = new BillingRateModifier();
	dbBillingRateModifier.setId(domainBillingRateModifier.getId());
	dbBillingRateModifier
		.setModifierAction(ModifierAction.valueOf(domainBillingRateModifier.getModifierAction().name()));
	dbBillingRateModifier.setModifierValue(domainBillingRateModifier.getModifierValue());

	domainToDbBooking(domainBillingRateModifier.getBooking()).addBillingRateModifier(dbBillingRateModifier);

	return dbBillingRateModifier;
    }
}
