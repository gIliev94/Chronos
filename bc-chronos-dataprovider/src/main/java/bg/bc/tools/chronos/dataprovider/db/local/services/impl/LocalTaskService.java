package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalTaskService;
import bg.bc.tools.chronos.dataprovider.utilities.EntityHelper;

public class LocalTaskService implements ILocalTaskService {

    private static final Logger LOGGER = Logger.getLogger(LocalTaskService.class);

    @Autowired
    private LocalTaskRepository taskRepo;

    @Autowired
    private LocalProjectRepository projectRepo;

    @Autowired
    private LocalCategoryRepository categoryRepo;

    @Autowired
    private LocalChangelogRepository changelogRepo;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public DTask addTask(DTask task) {
	task.setSyncKey(UUID.randomUUID().toString());

	try {
	    final Category dbCategory = transactionTemplate
		    .execute(txDef -> categoryRepo.findByName(task.getCategory().getName()));

	    final Project dbProject = transactionTemplate
		    .execute(txDef -> projectRepo.findByName(task.getProject().getName()));

	    final Task dbTask = DomainToDbMapper.domainToDbTask(task);
	    dbTask.setProject(dbProject);
	    dbTask.setCategory(dbCategory);

	    final Task managedNewTask = transactionTemplate.execute(t -> taskRepo.save(dbTask));

	    final Changelog changeLog = new Changelog();
	    changeLog.setChangeTime(Calendar.getInstance().getTime());
	    changeLog.setDeviceName(EntityHelper.getDeviceName());
	    changeLog.setUpdatedEntityKey(managedNewTask.getSyncKey());
	    changelogRepo.save(changeLog);

	    return DbToDomainMapper.dbToDomainTask(managedNewTask);
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new RuntimeException("IMPLEMENT CUSTOM EXCEPTION", e);
	}
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
    public List<DTask> getTasksEstimatedBetween(int hoursEstimatedLower, int hoursEstimatedUpper) {
	return taskRepo.findByHoursEstimatedBetween(hoursEstimatedLower, hoursEstimatedUpper).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasksEstimatedLessThan(int hoursEstimatedLessThan) {
	return taskRepo.findByHoursEstimatedLessThan(hoursEstimatedLessThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DTask> getTasksEstimatedGreaterThan(int hoursEstimatedGreaterThan) {
	return taskRepo.findByHoursEstimatedGreaterThan(hoursEstimatedGreaterThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainTask) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateTask(DTask task) {
	try {
	    if (taskRepo.exists(task.getId())) {
		LOGGER.info("Updating entity :: " + Task.class.getSimpleName() + " :: " + task.getName());
		task.setSyncKey(UUID.randomUUID().toString());
		taskRepo.save(DomainToDbMapper.domainToDbTask(task));
	    } else {
		LOGGER.info("No entity found to update :: " + Task.class.getSimpleName() + " :: " + task.getName());
	    }
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeTask(long id) {
	final Task dbTask = taskRepo.findOne(id);
	return removeTask(DbToDomainMapper.dbToDomainTask(dbTask));
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
    public boolean removeTask(String name) {
	final Task task = taskRepo.findByName(name);
	return removeTask(DbToDomainMapper.dbToDomainTask(task));
    }

    @Override
    public boolean removeTasks(DProject project) {
	try {
	    project.getTasks() // nl
		    .forEach(t -> taskRepo.delete(DomainToDbMapper.domainToDbTask(t)));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }
}
