package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBillingRateModifier.DModifierAction;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier.ModifierAction;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBillingRateModifierRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalBillingRateModifierService;
import bg.bc.tools.chronos.dataprovider.utilities.EntityHelper;

public class LocalBillingRateModifierService implements ILocalBillingRateModifierService {

    private static final Logger LOGGER = Logger.getLogger(LocalBillingRateModifierService.class);

    @Autowired
    private LocalBillingRateModifierRepository billingRateModifierRepo;

    @Autowired
    private LocalBookingRepository bookingRepo;

    @Autowired
    private LocalChangelogRepository changelogRepo;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public boolean addBillingRateModifier(DBillingRateModifier billingRateModifier) {
	billingRateModifier.setSyncKey(UUID.randomUUID().toString());

	try {
	    // TODO:??
	    // final Performer dbBooking = transactionTemplate
	    // .execute(txDef ->
	    // bookingRepo.findBysHandle(billingRateModifier.getBooking().getSyncKey()));
	    //
	    // final Booking dbBooking =
	    // DomainToDbMapper.domainToDbBooking(booking);
	    // dbBooking.setTask(dbTask);
	    // dbBooking.setRole(dbRole);
	    // dbBooking.setPerformer(dbPeformer);
	    //
	    // final Booking managedNewBooking = transactionTemplate.execute(t
	    // -> bookingRepo.save(dbBooking));
	    //
	    // final Changelog changeLog = new Changelog();
	    // changeLog.setChangeTime(Calendar.getInstance().getTime());
	    // changeLog.setDeviceName(EntityHelper.getComputerName());
	    // changeLog.setUpdatedEntityKey(managedNewBooking.getSyncKey());
	    // changelogRepo.save(changeLog);
	    //
	    // return DbToDomainMapper.dbToDomainBooking(managedNewBooking);
	    throw new RuntimeException("NOT IMPLEMENTED - DB CHANGE MAYBE...");
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new RuntimeException("IMPLEMENT CUSTOM EXCEPTION", e);
	}
    }

    @Override
    public DBillingRateModifier getBillingRateModifier(long id) {
	final BillingRateModifier dbBillingRateModifier = billingRateModifierRepo.findOne(id);
	return DbToDomainMapper.dbToDomainBillingRateModifier(dbBillingRateModifier);
    }

    @Override
    public List<DBillingRateModifier> getBillingRateModifiers() {
	return ((List<BillingRateModifier>) billingRateModifierRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBillingRateModifier> getBillingRateModifiers(DModifierAction modifierAction) {
	return billingRateModifierRepo.findByModifierAction(ModifierAction.valueOf(modifierAction.name())).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBillingRateModifier> getBillingRateModifiers(List<DModifierAction> modifierActions) {
	final List<ModifierAction> dbModifierActions = modifierActions.stream() // nl
		.map(a -> ModifierAction.valueOf(a.name())) // nl
		.collect(Collectors.toList());

	return billingRateModifierRepo.findByModifierActionIn(dbModifierActions).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBillingRateModifier> getBillingRateModifiers(DBooking booking) {
	final Booking dbBooking = DomainToDbMapper.domainToDbBooking(booking);

	return billingRateModifierRepo.findByBooking(dbBooking).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBillingRateModifier> getBillingRateModifiers(double modifierValue) {
	return billingRateModifierRepo.findByModifierValue(modifierValue).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBillingRateModifier> getBillingRateModifiersLessThan(double modifierValueLessThan) {
	return billingRateModifierRepo.findByModifierValueLessThan(modifierValueLessThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBillingRateModifier> getBillingRateModifiersGreaterThan(double modifierValueGreaterThan) {
	return billingRateModifierRepo.findByModifierValueGreaterThan(modifierValueGreaterThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBillingRateModifier> getBillingRateModifiersBetween(double modifierValueLower,
	    double modifierValueUpper) {
	return billingRateModifierRepo.findByModifierValueBetween(modifierValueLower, modifierValueUpper).stream() // nl
		.map(DbToDomainMapper::dbToDomainBillingRateModifier) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateBillingRateModifier(DBillingRateModifier billingRateModifier) {
	try {
	    if (billingRateModifierRepo.exists(billingRateModifier.getId())) {
		LOGGER.info("Updating entity :: " + BillingRateModifier.class.getSimpleName() + " :: "
			+ billingRateModifier.getId());
		billingRateModifier.setSyncKey(UUID.randomUUID().toString());
		billingRateModifierRepo.save(DomainToDbMapper.domainToDbBillingRateModifier(billingRateModifier));
	    } else {
		LOGGER.info("No entity found to update :: " + BillingRateModifier.class.getSimpleName() + " :: "
			+ billingRateModifier.getId());
	    }
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBillingRateModifier(long id) {
	final BillingRateModifier dbBillingRateModifier = billingRateModifierRepo.findOne(id);
	return removeBillingRateModifier(DbToDomainMapper.dbToDomainBillingRateModifier(dbBillingRateModifier));
    }

    @Override
    public boolean removeBillingRateModifier(DBillingRateModifier billingRateModifier) {
	try {
	    billingRateModifierRepo.delete(DomainToDbMapper.domainToDbBillingRateModifier(billingRateModifier));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBillingRateModifiers(DBooking booking) {
	try {
	    booking.getBillingRateModifiers() // nl
		    .forEach(m -> billingRateModifierRepo.delete(DomainToDbMapper.domainToDbBillingRateModifier(m)));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }
}
