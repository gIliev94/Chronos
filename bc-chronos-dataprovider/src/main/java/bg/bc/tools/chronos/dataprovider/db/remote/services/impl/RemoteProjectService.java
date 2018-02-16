package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteProjectService;

public class RemoteProjectService implements IRemoteProjectService {

    private static final Logger LOGGER = Logger.getLogger(RemoteProjectService.class);

    @Autowired
    private RemoteProjectRepository projectRepo;

    @Override
    public DProject addProject(DProject project) {
	// try {
	// projectRepo.save(DomainToDbMapper.domainToDbProject(project));
	// } catch (Exception e) {
	// LOGGER.error(e);
	// return null;
	// }
	//
	// return null;
	throw new RuntimeException("NOT IMPLEMENTED - DB CHANGE MAYBE...");
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
	    } else {
		LOGGER.info(
			"No entity found to update :: " + Project.class.getSimpleName() + " :: " + project.getName());
	    }

	    projectRepo.save(DomainToDbMapper.domainToDbProject(project));

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
