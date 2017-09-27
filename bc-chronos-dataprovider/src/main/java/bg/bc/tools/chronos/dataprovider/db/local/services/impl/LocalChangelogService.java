package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DChangelog;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalChangelogService;

public class LocalChangelogService implements ILocalChangelogService {

    private static final Logger LOGGER = Logger.getLogger(LocalChangelogService.class);

    @Autowired
    private LocalChangelogRepository changelogRepo;

    @Override
    public boolean addChangelog(DChangelog changelog) {
	try {
	    changelogRepo.save(DomainToDbMapper.domainToDbChangelog(changelog));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public List<DChangelog> getChangelogsChangedBefore(Date changeTimeBefore) {
	return changelogRepo.findByChangeTimeBefore(changeTimeBefore).stream() // nl
		.map(DbToDomainMapper::dbToDomainChangelog) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DChangelog> getChangelogsChangedAfter(Date changeTimeAfter) {
	return changelogRepo.findByChangeTimeAfter(changeTimeAfter).stream() // nl
		.map(DbToDomainMapper::dbToDomainChangelog) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DChangelog> getChangelogsChangedBetween(Date changeTimeLower, Date changeTimeUpper) {
	return changelogRepo.findByChangeTimeBetween(changeTimeLower, changeTimeUpper).stream() // nl
		.map(DbToDomainMapper::dbToDomainChangelog) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DChangelog> getChangelogsForUpdateCounterLessThan(double lessThanModifierValue) {
	// return
	// changelogRepo.findByUpdateCounterLessThan(lessThanModifierValue).stream()
	// // nl
	// .map(DbToDomainMapper::dbToDomainChangelog) // nl
	// .collect(Collectors.toList());
	return null;
    }

    @Override
    public List<DChangelog> getChangelogsForUpdateCounterGreaterThan(double greaterThanModifierValue) {
	// return
	// changelogRepo.findByUpdateCounterGreaterThan(greaterThanModifierValue).stream()
	// // nl
	// .map(DbToDomainMapper::dbToDomainChangelog) // nl
	// .collect(Collectors.toList());
	return null;
    }

    @Override
    public List<DChangelog> getChangelogsForUpdateCounterBetween(double lessThanUpdateCounter,
	    double greaterThanUpdateCounter) {
	// return
	// changelogRepo.findByUpdateCounterBetween(lessThanUpdateCounter,
	// greaterThanUpdateCounter).stream() // nl
	// .map(DbToDomainMapper::dbToDomainChangelog) // nl
	// .collect(Collectors.toList());
	return null;
    }

    @Override
    public List<DChangelog> getChangelogsForDevice(String deviceName) {
	return changelogRepo.findByDeviceNameOrderByUpdateCounterDesc(deviceName).stream() // nl
		.map(DbToDomainMapper::dbToDomainChangelog) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DChangelog> getChangelogsForUpdatedEntity(String updatedEntityKey) {
	return changelogRepo.findByUpdatedEntityKey(updatedEntityKey).stream() // nl
		.map(DbToDomainMapper::dbToDomainChangelog) // nl
		.collect(Collectors.toList());
    }

    @Override
    public DChangelog getLastChangelog() {
	return DbToDomainMapper.dbToDomainChangelog(changelogRepo.findTopByOrderByUpdateCounterDesc());
    }

}
