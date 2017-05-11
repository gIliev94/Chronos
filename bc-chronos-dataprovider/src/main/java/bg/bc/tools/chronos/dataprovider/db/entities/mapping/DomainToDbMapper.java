package bg.bc.tools.chronos.dataprovider.db.entities.mapping;

import bg.bc.tools.chronos.core.entities.DBillingRate;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DClient;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRate;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Client;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.PerformerRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.Task.TaskPhase;

public final class DomainToDbMapper {

    private DomainToDbMapper() {
    }

    public static Client domainToDbClient(DClient domainClient) {
	Client dbClient = new Client();
	dbClient.setId(domainClient.getId());
	dbClient.setName(domainClient.getName());
	dbClient.setDescription(domainClient.getDescription());

	domainToDbCategory(domainClient.getCategory()).addCategoricalEntity(dbClient);

	return dbClient;
    }

    public static Project domainToDbProject(DProject domainProject) {
	Project dbProject = new Project();
	dbProject.setId(domainProject.getId());
	dbProject.setName(domainProject.getName());
	dbProject.setDescription(domainProject.getDescription());

	domainToDbClient(domainProject.getClient()).addProject(dbProject);
	domainToDbCategory(domainProject.getCategory()).addCategoricalEntity(dbProject);

	return dbProject;
    }

    public static Task domainToDbTask(DTask domainTask) {
	Task dbTask = new Task();
	dbTask.setId(domainTask.getId());
	dbTask.setName(domainTask.getName());
	dbTask.setDescription(domainTask.getDescription());
	dbTask.setPhase(TaskPhase.valueOf(domainTask.getPhase().name()));
	dbTask.setEstimatedTime(domainTask.getEstimatedTime());

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
	dbPerformer.setLoggedIn(domainPerformer.isLoggedIn());
	dbPerformer.setRole(PerformerRole.valueOf(domainPerformer.getRole().name()));

	return dbPerformer;
    }

    public static Booking domainToDbBooking(DBooking domainBooking) {
	Booking dbBooking = new Booking();
	dbBooking.setId(domainBooking.getId());
	dbBooking.setDescription(domainBooking.getDescription());
	dbBooking.setStartTime(domainBooking.getStartTime());
	dbBooking.setEndTime(domainBooking.getEndTime());
	dbBooking.setOvertime(domainBooking.isOvertime());
	dbBooking.setEffectivelyStopped(domainBooking.isEffectivelyStopped());

	domainToDbPerformer(domainBooking.getPerformer()).addBooking(dbBooking);
	domainToDbTask(domainBooking.getTask()).addBooking(dbBooking);

	return dbBooking;
    }

    public static Category domainToDbCategory(DCategory domainCategory) {
	Category dbCategory = new Category();
	dbCategory.setId(domainCategory.getId());
	dbCategory.setName(domainCategory.getName());
	dbCategory.setSortOrder(domainCategory.getSortOrder());

	return dbCategory;
    }

    public static BillingRate domainToDbBillingRate(DBillingRate domainBillingRate) {
	BillingRate dbBillingRate = new BillingRate();
	dbBillingRate.setId(domainBillingRate.getId());
	dbBillingRate.setRate(domainBillingRate.getRate());
	dbBillingRate.setRole(PerformerRole.valueOf(domainBillingRate.getRole().name()));

	domainToDbTask(domainBillingRate.getTask()).addBillingRate(dbBillingRate);

	return dbBillingRate;
    }
}
