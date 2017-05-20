package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteTaskRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteTaskService;

public class RemoteTaskService implements IRemoteTaskService {

    private static final Logger LOGGER = Logger.getLogger(RemoteTaskService.class);

    @Autowired
    private RemoteTaskRepository taskRepo;

    @Override
    public boolean addTask(DTask task) {
	try {
	    taskRepo.save(DomainToDbMapper.domainToDbTask(task));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DTask getTask(long id) {
	return DbToDomainMapper.dbToDomainTask(taskRepo.findOne(id));
    }

    @Override
    public DTask getTask(String name) {
	return DbToDomainMapper.dbToDomainTask(taskRepo.findByName(name));
    }

    @Override
    public List<DTask> getTasksContaining(String name) {
	return taskRepo.findByNameIgnoreCaseContaining(name).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasks() {
	return ((List<Task>) taskRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasks(DCategory category) {
	final Category dbCategory = DomainToDbMapper.domainToDbCategory(category);

	return taskRepo.findByCategory(dbCategory).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasks(List<DCategory> categories) {
	final List<Category> dbCategories = categories.stream() // nl
		.map(DomainToDbMapper::domainToDbCategory) // nl
		.collect(Collectors.toList());

	return taskRepo.findByCategoryIn(dbCategories).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasks(DProject project) {
	final Project dbProject = DomainToDbMapper.domainToDbProject(project);

	return taskRepo.findByProject(dbProject).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasksEstimatedBetween(int estimatedTimeHoursLower, int estimatedTimeHoursUpper) {
	return taskRepo.findByEstimatedTimeHoursIn(estimatedTimeHoursLower, estimatedTimeHoursUpper).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasksEstimatedLessThan(int estimatedTimeHoursLessThan) {
	return taskRepo.findByEstimatedTimeHoursLessThan(estimatedTimeHoursLessThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasksEstimatedGreaterThan(int estimatedTimeHoursGreaterThan) {
	return taskRepo.findByEstimatedTimeHoursGreaterThan(estimatedTimeHoursGreaterThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateTask(DTask task) {
	try {
	    if (taskRepo.exists(task.getId())) {
		LOGGER.info("Updating entity :: " + Task.class.getSimpleName() + " ::" + task.getName());
	    } else {
		LOGGER.info("No entity found to update :: " + Task.class.getSimpleName() + " ::" + task.getName());
	    }

	    taskRepo.save(DomainToDbMapper.domainToDbTask(task));

	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeTask(DTask task) {
	try {
	    taskRepo.delete(DomainToDbMapper.domainToDbTask(task));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeTask(String taskName) {
	final Task task = taskRepo.findByName(taskName);
	return removeTask(DbToDomainMapper.dbToDomainTask(task));
    }

    @Override
    public boolean removeTasksByProject(DProject project) {
	final List<Task> dbTasks = project.getTasks().stream().map(DomainToDbMapper::domainToDbTask)
		.collect(Collectors.toList());
	dbTasks.forEach(t -> removeTask(DbToDomainMapper.dbToDomainTask(t)));

	return true;
    }
}
