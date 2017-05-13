package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DBillingRate;
import bg.bc.tools.chronos.core.entities.DPerformer.DPerformerRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRate;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.PerformerRole;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteBillingRateRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteBillingRateService;

public class RemoteBillingRateService implements IRemoteBillingRateService {

    @Autowired
    private RemoteBillingRateRepository billingRateRepo;

    @Override
    public boolean addBillingRate(DBillingRate billingRate) {
	try {
	    billingRateRepo.save(DomainToDbMapper.domainToDbBillingRate(billingRate));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DBillingRate getBillingRate(long id) {
	return DbToDomainMapper.dbToDomainBillingRate(billingRateRepo.findOne(id));
    }

    @Override
    public DBillingRate getBillingRate(DTask task, DPerformerRole role) {
	return DbToDomainMapper.dbToDomainBillingRate(billingRateRepo
		.findByTaskAndRole(DomainToDbMapper.domainToDbTask(task), PerformerRole.valueOf(role.name())));
    }

    @Override
    public Collection<DBillingRate> getBillingRates() {
	return ((List<BillingRate>) billingRateRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRate) // nl
		.collect(Collectors.toList());
    }

    @Override
    public Collection<DBillingRate> getBillingRates(DTask task) {
	return billingRateRepo.findByTask(DomainToDbMapper.domainToDbTask(task)).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRate) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateBillingRate(DBillingRate billingRate) {
	try {
	    if (billingRateRepo.exists(billingRate.getId())) {
		// LOGGER.info("Updating entity :: " +
		// BillingRate.class.getSimpleName() + " ::" +
		// billingRate.getName());

	    } else {
		// LOGGER.info("No entity found to update :: " +
		// BillingRate.class.getSimpleName() + " ::" +
		// billingRate.getName());
	    }

	    billingRateRepo.save(DomainToDbMapper.domainToDbBillingRate(billingRate));

	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBillingRate(long id) {
	final BillingRate billingRate = billingRateRepo.findOne(id);
	return removeBillingRate(DbToDomainMapper.dbToDomainBillingRate(billingRate));
    }

    @Override
    public boolean removeBillingRate(DBillingRate billingRate) {
	try {
	    billingRateRepo.delete(DomainToDbMapper.domainToDbBillingRate(billingRate));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBillingRate(DTask task) {
	try {
	    billingRateRepo.findByTask(DomainToDbMapper.domainToDbTask(task)).forEach(b -> {
		removeBillingRate(DbToDomainMapper.dbToDomainBillingRate(b));
	    });
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

}
