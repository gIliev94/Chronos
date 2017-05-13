package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DPerformer.DPerformerRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.PerformerRole;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemotePerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemotePerformerService;

public class RemotePerformerService implements IRemotePerformerService {

    @Autowired
    private RemotePerformerRepository performerRepo;

    @Override
    public boolean addPerformer(DPerformer performer) {
	try {
	    performerRepo.save(DomainToDbMapper.domainToDbPerformer(performer));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DPerformer getPerformer(String handle) {
	return DbToDomainMapper.dbToDomainPerformer(performerRepo.findByHandle(handle));
    }

    @Override
    public List<DPerformer> getPerformers() {
	return ((List<Performer>) performerRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainPerformer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DPerformer> getPerformers(String name) {
	return performerRepo.findByName(name).stream() // nl
		.map(DbToDomainMapper::dbToDomainPerformer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DPerformer> getPerformers(DPerformerRole role) {
	return performerRepo.findByRole(PerformerRole.valueOf(role.name())).stream() // nl
		.map(DbToDomainMapper::dbToDomainPerformer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updatePerformer(DPerformer performer) {
	try {
	    if (performerRepo.exists(performer.getId())) {
		// LOGGER.info("Updating entity :: " +
		// Performer.class.getSimpleName() + " ::" +
		// performer.getName());

	    } else {
		// LOGGER.info("No entity found to update :: " +
		// Performer.class.getSimpleName() + " ::" +
		// performer.getName());
	    }

	    performerRepo.save(DomainToDbMapper.domainToDbPerformer(performer));

	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removePerformer(DPerformer performer) {
	try {
	    performerRepo.delete(DomainToDbMapper.domainToDbPerformer(performer));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removePerformer(String performerHandle) {
	Performer performer = performerRepo.findByHandle(performerHandle);
	return removePerformer(DbToDomainMapper.dbToDomainPerformer(performer));
    }

    @Override
    public boolean removePerformersByRole(DPerformerRole role) {
	try {
	    performerRepo.findByRole(PerformerRole.valueOf(role.name())).forEach(p -> {
		removePerformer(DbToDomainMapper.dbToDomainPerformer(p));
	    });
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

}
