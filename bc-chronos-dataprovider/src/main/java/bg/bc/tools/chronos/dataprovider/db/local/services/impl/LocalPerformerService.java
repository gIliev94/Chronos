package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DPerformer.DPriviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalPerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalPerformerService;
import bg.bc.tools.chronos.dataprovider.utilities.EntityHelper;

public class LocalPerformerService implements ILocalPerformerService {

    private static final Logger LOGGER = Logger.getLogger(LocalPerformerService.class);

    @Autowired
    private LocalPerformerRepository performerRepo;

    @Autowired
    private LocalChangelogRepository changelogRepo;

    @Override
    public DPerformer addPerformer(DPerformer performer) {
	try {
	    performer.setSyncKey(UUID.randomUUID().toString());
	    final Performer managedNewPerformer = performerRepo.save(DomainToDbMapper.domainToDbPerformer(performer));

	    final Changelog changeLog = new Changelog();
	    changeLog.setChangeTime(Calendar.getInstance().getTime());
	    changeLog.setDeviceName(EntityHelper.getDeviceName());
	    changeLog.setUpdatedEntityKey(managedNewPerformer.getSyncKey());
	    changelogRepo.save(changeLog);
	    
	    return DbToDomainMapper.dbToDomainPerformer(managedNewPerformer);
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new RuntimeException("IMPLEMENT CUSTOM EXCEPTION", e);
	}
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
    public List<DPerformer> getPerformers(DPriviledge priviledge) {
	return performerRepo.findByPriviledgesContaining(Priviledge.valueOf(priviledge.name())).stream()
		.map(DbToDomainMapper::dbToDomainPerformer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DPerformer> getPerformers(List<DPriviledge> priviledges) {
	final List<Priviledge> dbPriviledges = priviledges.stream() // nl
		.map(p -> p.name()) // nl
		.map(Priviledge::valueOf) // nl
		.collect(Collectors.toList());

	return performerRepo.findDistinctByPriviledgesIn(dbPriviledges).stream() // nl
		.map(DbToDomainMapper::dbToDomainPerformer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updatePerformer(DPerformer performer) {
	try {
	    if (performerRepo.exists(performer.getId())) {
		LOGGER.info("Updating entity :: " + Performer.class.getSimpleName() + " :: " + performer.getName());
		performer.setSyncKey(UUID.randomUUID().toString());
		performerRepo.save(DomainToDbMapper.domainToDbPerformer(performer));
	    } else {
		LOGGER.info("No entity found to update :: " + Performer.class.getSimpleName() + " :: "
			+ performer.getName());
	    }
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
}
