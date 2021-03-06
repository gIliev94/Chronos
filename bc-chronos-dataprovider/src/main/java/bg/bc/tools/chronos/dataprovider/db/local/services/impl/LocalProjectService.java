package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalProjectService;
import bg.bc.tools.chronos.dataprovider.utilities.EntityHelper;

public class LocalProjectService implements ILocalProjectService {

    private static final Logger LOGGER = Logger.getLogger(LocalProjectService.class);

    @Autowired
    private LocalProjectRepository projectRepo;

    @Autowired
    private LocalCustomerRepository customerRepo;

    @Autowired
    private LocalCategoryRepository categoryRepo;

    @Autowired
    private LocalChangelogRepository changelogRepo;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    // @Transactional
    public DProject addProject(DProject project) {
	project.setSyncKey(UUID.randomUUID().toString());

	try {
	    // final Category dbCategory = transactionTemplate
	    // .execute(txDef ->
	    // categoryRepo.findByName(project.getCategory().getName()));
	    //
	    // final Customer dbCustomer = transactionTemplate
	    // .execute(txDef ->
	    // customerRepo.findByName(project.getCustomer().getName()));
	    //
	    // final Project dbProject =
	    // DomainToDbMapper.domainToDbProject(project);
	    // dbProject.setCustomer(dbCustomer);
	    // dbProject.setCategory(dbCategory);
	    //
	    // final Project managedNewProject = transactionTemplate.execute(t
	    // -> projectRepo.save(dbProject));

	    final Project managedNewProject = projectRepo.save(DomainToDbMapper.domainToDbProject(project));

	    final Changelog changeLog = new Changelog();
	    changeLog.setChangeTime(Calendar.getInstance().getTime());
	    changeLog.setDeviceName(EntityHelper.getDeviceName());
	    // changeLog.setUpdatedEntityKey(managedNewProject.getSyncKey());
	    changelogRepo.save(changeLog);

	    return DbToDomainMapper.dbToDomainProject(managedNewProject);
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new RuntimeException("IMPLEMENT CUSTOM EXCEPTION", e);
	}
    }

    @Override
    public DProject fetchReferencedEntities(DProject project) {
	final Category refCategory = categoryRepo.findByName(project.getCategory().getName());
	final Customer refCustomer = customerRepo.findByName(project.getCustomer().getName());

	final Project dbProject = DomainToDbMapper.domainToDbProject(project);
	// dbProject.setCategory(refCategory);
	dbProject.setCustomer(refCustomer);

	return DbToDomainMapper.dbToDomainProject(dbProject);
    }

    @Override
    public DProject getProject(long id) {
	return DbToDomainMapper.dbToDomainProject(projectRepo.findOne(id));
    }

    @Override
    public DProject getProject(String name) {
	return DbToDomainMapper.dbToDomainProject(projectRepo.findByName(name));
    }

    @Override
    public List<DProject> getProjectsContaining(String name) {
	return projectRepo.findByNameIgnoreCaseContaining(name).stream() // nl
		.map(DbToDomainMapper::dbToDomainProject) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DProject> getProjects() {
	return ((List<Project>) projectRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainProject) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DProject> getProjects(DCustomer customer) {
	final Customer dbCustomer = DomainToDbMapper.domainToDbCustomer(customer);

	return projectRepo.findByCustomer(dbCustomer).stream() // nl
		.map(DbToDomainMapper::dbToDomainProject) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DProject> getProjects(DCategory category) {
	final Category dbCategory = DomainToDbMapper.domainToDbCategory(category);

	// return projectRepo.findByCategory(dbCategory).stream() // nl
	// .map(DbToDomainMapper::dbToDomainProject) // nl
	// .collect(Collectors.toList());

	return null;
    }

    @Override
    public List<DProject> getProjects(List<DCategory> categories) {
	final List<Category> dbCategories = categories.stream() // nl
		.map(DomainToDbMapper::domainToDbCategory) // nl
		.collect(Collectors.toList());

	// return projectRepo.findByCategoryIn(dbCategories).stream() // nl
	// .map(DbToDomainMapper::dbToDomainProject) // nl
	// .collect(Collectors.toList());

	return null;
    }

    @Override
    public boolean updateProject(DProject project) {
	try {
	    if (projectRepo.exists(project.getId())) {
		LOGGER.info("Updating entity :: " + Project.class.getSimpleName() + " :: " + project.getName());
		project.setSyncKey(UUID.randomUUID().toString());
		projectRepo.save(DomainToDbMapper.domainToDbProject(project));
	    } else {
		LOGGER.info(
			"No entity found to update :: " + Project.class.getSimpleName() + " :: " + project.getName());
	    }
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeProject(long id) {
	final Project project = projectRepo.findOne(id);
	return removeProject(DbToDomainMapper.dbToDomainProject(project));
    }

    @Override
    public boolean removeProject(DProject project) {
	try {
	    projectRepo.delete(DomainToDbMapper.domainToDbProject(project));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeProject(String name) {
	final Project dbProject = projectRepo.findByName(name);
	return removeProject(DbToDomainMapper.dbToDomainProject(dbProject));
    }

    @Override
    public boolean removeProjects(DCustomer customer) {
	try {
	    customer.getProjects() // nl
		    .forEach(p -> projectRepo.delete(DomainToDbMapper.domainToDbProject(p)));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }
}
