package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DPerformer.DPriviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemotePerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemotePerformerService;

public class RemotePerformerService implements IRemotePerformerService {

    private static final Logger LOGGER = Logger.getLogger(RemotePerformerService.class);

    @Autowired
    private RemotePerformerRepository performerRepo;

    @Override
    public DPerformer addPerformer(DPerformer performer) {
	// try {
	// performerRepo.save(DomainToDbMapper.domainToDbPerformer(performer));
	// } catch (Exception e) {
	// LOGGER.error(e);
	// return false;
	// }
	//
	// return true;
	throw new RuntimeException("NOT IMPLEMENTED - DB CHANGE MAYBE...");
    }

    @Override
    public DPerformer getPerformer(long id) {
	final Performer dbPerformer = performerRepo.findOne(id);
	return DbToDomainMapper.dbToDomainPerformer(dbPerformer);
    }

    @Override
    public DPerformer getPerformer(String handle) {
	return DbToDomainMapper.dbToDomainPerformer(performerRepo.findByHandle(handle));
    }

    @Override
    public DPerformer getPerformerByEmail(String email) {
	return DbToDomainMapper.dbToDomainPerformer(performerRepo.findByEmail(email));
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
    public List<DPerformer> getLoggedPerformers() {
	return performerRepo.findByIsLoggedTrue().stream() // nlS
		.map(DbToDomainMapper::dbToDomainPerformer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updatePerformer(DPerformer performer) {
	try {
	    if (performerRepo.exists(performer.getId())) {
		LOGGER.info("Updating entity :: " + Performer.class.getSimpleName() + " :: " + performer.getName());
	    } else {
		LOGGER.info("No entity found to update :: " + Performer.class.getSimpleName() + " :: "
			+ performer.getName());
	    }

	    performerRepo.save(DomainToDbMapper.domainToDbPerformer(performer));

	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removePerformer(long id) {
	final Performer dbPerformer = performerRepo.findOne(id);
	return removePerformer(DbToDomainMapper.dbToDomainPerformer(dbPerformer));
    }

    @Override
    public boolean removePerformer(DPerformer performer) {
	try {
	    performerRepo.delete(DomainToDbMapper.domainToDbPerformer(performer));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removePerformer(String handle) {
	final Performer dbPerformer = performerRepo.findByHandle(handle);
	return removePerformer(DbToDomainMapper.dbToDomainPerformer(dbPerformer));
    }

    @Override
    public boolean removePerformerByEmail(String email) {
	final Performer dbPerformer = performerRepo.findByEmail(email);
	return removePerformer(DbToDomainMapper.dbToDomainPerformer(dbPerformer));
    }

    @Override
    public List<DPerformer> getPerformers(DPriviledge priviledge) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DPerformer> getPerformers(List<DPriviledge> priviledges) {
	// TODO Auto-generated method stub
	return null;
    }
}
