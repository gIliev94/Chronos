package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalProjectService;

public class LocalProjectService implements ILocalProjectService {

    private static final Logger LOGGER = Logger.getLogger(LocalProjectService.class);

    @Autowired
    private LocalProjectRepository projectRepo;

    @Override
    public boolean addProject(DProject project) {
	try {
	    projectRepo.save(DomainToDbMapper.domainToDbProject(project));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DProject getProject(String name) {
	return DbToDomainMapper.dbToDomainProject(projectRepo.findByName(name));
    }

    @Override
    public List<DProject> getProjects() {
	return ((List<Project>) projectRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainProject) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DProject> getProjects(DCustomer client) {
	return ((List<Project>) projectRepo.findAll()).stream() // nl
		.filter(c -> Objects.equals(client, c.getCustomer())) // nl
		.map(DbToDomainMapper::dbToDomainProject) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DProject> getProjects(DCategory category) {
	return ((List<Project>) projectRepo.findAll()).stream() // nl
		.filter(c -> Objects.equals(category, c.getCategory())) // nl
		.map(DbToDomainMapper::dbToDomainProject) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateProject(DProject project) {
	try {
	    if (projectRepo.exists(project.getId())) {
		LOGGER.info("Updating entity :: " + Project.class.getSimpleName() + " ::" + project.getName());
	    } else {
		LOGGER.info(
			"No entity found to update :: " + Project.class.getSimpleName() + " ::" + project.getName());
	    }

	    projectRepo.save(DomainToDbMapper.domainToDbProject(project));

	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
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
    public boolean removeProject(String projectName) {
	Project project = projectRepo.findByName(projectName);
	return removeProject(DbToDomainMapper.dbToDomainProject(project));
    }

    @Override
    public boolean removeProjectsByClient(DCustomer client) {
	try {
	    client.getProjects().forEach(p -> projectRepo.delete(DomainToDbMapper.domainToDbProject(p)));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }
}
