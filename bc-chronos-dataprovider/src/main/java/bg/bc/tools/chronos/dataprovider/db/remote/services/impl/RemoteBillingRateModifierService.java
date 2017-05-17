package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteBillingRateModifierRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteBillingRateModifierService;

public class RemoteBillingRateModifierService implements IRemoteBillingRateModifierService {

    @Autowired
    private RemoteBillingRateModifierRepository billingRateModifierRepo;

    // @Override
    // public boolean addBillingRate(DBillingRateModifier billingRateModifier) {
    // try {
    // billingRateModifierRepo.save(DomainToDbMapper.domainToDbBillingRateModifier(billingRateModifier));
    // } catch (Exception e) {
    // // LOGGER.error(e);
    // return false;
    // }
    //
    // return true;
    // }
    //
    // @Override
    // public DBillingRate getBillingRate(long id) {
    // return
    // DbToDomainMapper.dbToDomainBillingRate(billingRateModifierRepo.findOne(id));
    // }
    //
    // @Override
    // public DBillingRate getBillingRate(DTask task, DPerformerRole role) {
    // return DbToDomainMapper.dbToDomainBillingRate(billingRateModifierRepo
    // .findByTaskAndRole(DomainToDbMapper.domainToDbTask(task),
    // PerformerRole.valueOf(role.name())));
    // }
    //
    // @Override
    // public Collection<DBillingRate> getBillingRates() {
    // return ((List<BillingRate>) billingRateModifierRepo.findAll()).stream()
    // // nl
    // .map(DbToDomainMapper::dbToDomainBillingRate) // nl
    // .collect(Collectors.toList());
    // }
    //
    // @Override
    // public Collection<DBillingRate> getBillingRates(DTask task) {
    // return
    // billingRateModifierRepo.findByTask(DomainToDbMapper.domainToDbTask(task)).stream()
    // // nl
    // .map(DbToDomainMapper::dbToDomainBillingRate) // nl
    // .collect(Collectors.toList());
    // }
    //
    // @Override
    // public boolean updateBillingRate(DBillingRate billingRate) {
    // try {
    // if (billingRateModifierRepo.exists(billingRate.getId())) {
    // // LOGGER.info("Updating entity :: " +
    // // BillingRate.class.getSimpleName() + " ::" +
    // // billingRate.getName());
    //
    // } else {
    // // LOGGER.info("No entity found to update :: " +
    // // BillingRate.class.getSimpleName() + " ::" +
    // // billingRate.getName());
    // }
    //
    // billingRateModifierRepo.save(DomainToDbMapper.domainToDbBillingRate(billingRate));
    //
    // } catch (Exception e) {
    // // LOGGER.error(e);
    // return false;
    // }
    //
    // return true;
    // }
    //
    // @Override
    // public boolean removeBillingRate(long id) {
    // final BillingRate billingRate = billingRateModifierRepo.findOne(id);
    // return
    // removeBillingRate(DbToDomainMapper.dbToDomainBillingRate(billingRate));
    // }
    //
    // @Override
    // public boolean removeBillingRate(DBillingRate billingRate) {
    // try {
    // billingRateModifierRepo.delete(DomainToDbMapper.domainToDbBillingRate(billingRate));
    // } catch (Exception e) {
    // // LOGGER.error(e);
    // return false;
    // }
    //
    // return true;
    // }
    //
    // @Override
    // public boolean removeBillingRate(DTask task) {
    // try {
    // billingRateModifierRepo.findByTask(DomainToDbMapper.domainToDbTask(task)).forEach(b
    // -> {
    // removeBillingRate(DbToDomainMapper.dbToDomainBillingRate(b));
    // });
    // } catch (Exception e) {
    // // LOGGER.error(e);
    // return false;
    // }
    //
    // return true;
    // }

    @Override
    public boolean addBillingRateModifier(DBillingRateModifier billingRateModifier) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public DBillingRateModifier getBillingRateModifier(long id) {
	return DbToDomainMapper.dbToDomainBillingRateModifier(billingRateModifierRepo.findOne(id));
    }

    @Override
    public Collection<DBillingRateModifier> getBillingRateModifiers() {
	return ((List<BillingRateModifier>) billingRateModifierRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public Collection<DBillingRateModifier> getBillingRateModifiers(DBooking booking) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<DBillingRateModifier> getBillingRateModifiers(DRole role) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<DBillingRateModifier> getBillingRateModifiers(DPerformer performer) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<DBillingRateModifier> getBillingRateModifiers(DTask task) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean updateBillingRateModifier(DBillingRateModifier billingRateModifier) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeBillingRateModifier(long id) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeBillingRateModifier(DBillingRateModifier billingRateModifier) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeBillingRateModifiers(DBooking booking) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeBillingRateModifiers(DRole role) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeBillingRateModifiers(DPerformer performer) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeBillingRateModifiers(DTask task) {
	// TODO Auto-generated method stub
	return false;
    }

}
