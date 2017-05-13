package bg.bc.tools.chronos.dataprovider.db.entities.mapping;

import bg.bc.tools.chronos.core.entities.DBillingRate;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DClient;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.core.entities.DPerformer.DPerformerRole;
import bg.bc.tools.chronos.core.entities.DTask.DTaskPhase;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRate;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Client;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public final class DbToDomainMapper {

    private DbToDomainMapper() {
    }

    public static DClient dbToDomainClient(Client dbClient) {
	DClient domainClient = new DClient();
	domainClient.setId(dbClient.getId());
	domainClient.setName(dbClient.getName());
	domainClient.setDescription(dbClient.getDescription());

	dbToDomainCategory(dbClient.getCategory()).addCategoricalEntity(domainClient);

	return domainClient;
    }

    public static DProject dbToDomainProject(Project dbProject) {
	DProject domainProject = new DProject();
	domainProject.setId(dbProject.getId());
	domainProject.setName(dbProject.getName());
	domainProject.setDescription(dbProject.getDescription());

	dbToDomainClient(dbProject.getClient()).addProject(domainProject);
	dbToDomainCategory(dbProject.getCategory()).addCategoricalEntity(domainProject);

	return domainProject;
    }

    public static DTask dbToDomainTask(Task dbTask) {
	DTask domainTask = new DTask();
	domainTask.setId(dbTask.getId());
	domainTask.setName(dbTask.getName());
	domainTask.setDescription(dbTask.getDescription());
	domainTask.setPhase(DTaskPhase.valueOf(dbTask.getPhase().name()));
	domainTask.setEstimatedTime(dbTask.getEstimatedTime());

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
	domainPerformer.setRole(DPerformerRole.valueOf(dbPerformer.getRole().name()));

	return domainPerformer;
    }

    public static DBooking dbToDomainBooking(Booking dbBooking) {
	DBooking domainBooking = new DBooking();
	domainBooking.setId(dbBooking.getId());
	domainBooking.setDescription(dbBooking.getDescription());
	domainBooking.setStartTime(dbBooking.getStartTime());
	domainBooking.setEndTime(dbBooking.getEndTime());
	domainBooking.setOvertime(dbBooking.isOvertime());
	domainBooking.setEffectivelyStopped(dbBooking.isEffectivelyStopped());

	dbToDomainPerformer(dbBooking.getPerformer()).addBooking(domainBooking);
	dbToDomainTask(dbBooking.getTask()).addBooking(domainBooking);

	return domainBooking;
    }

    public static DCategory dbToDomainCategory(Category dbCategory) {
	DCategory domainCategory = new DCategory();
	domainCategory.setId(dbCategory.getId());
	domainCategory.setName(dbCategory.getName());
	domainCategory.setSortOrder(dbCategory.getSortOrder());

	return domainCategory;
    }

    public static DBillingRate dbToDomainBillingRate(BillingRate dbBillingRate) {
	DBillingRate domainBillingRate = new DBillingRate();
	domainBillingRate.setId(dbBillingRate.getId());
	domainBillingRate.setRate(dbBillingRate.getRate());
	domainBillingRate.setRole(DPerformerRole.valueOf(dbBillingRate.getRole().name()));

	dbToDomainTask(dbBillingRate.getTask()).addBillingRate(domainBillingRate);

	return domainBillingRate;
    }
}
