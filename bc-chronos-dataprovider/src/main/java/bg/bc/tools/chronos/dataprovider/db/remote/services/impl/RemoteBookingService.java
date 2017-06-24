package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteBookingService;

public class RemoteBookingService implements IRemoteBookingService {

    private static final Logger LOGGER = Logger.getLogger(RemoteBookingService.class);

    @Autowired
    private RemoteBookingRepository bookingRepo;

    @Override
    public DBooking addBooking(DBooking booking) {
	// TODO: Impl at later stage
	// final HolidayManager holidayManager = HolidayManager
	// .getInstance(ManagerParameters.create(HolidayCalendar.BULGARIA));
	// final Set<Holiday> holidays =
	// holidayManager.getHolidays(booking.getStartTime().toLocalDate(),
	// booking.getEndTime().toLocalDate());
	//
	// booking.setOvertime(!holidays.isEmpty());
	// booking.setEffectivelyStopped(false);

	// try {
	// bookingRepo.save(DomainToDbMapper.domainToDbBooking(booking));
	// } catch (Exception e) {
	// LOGGER.error(e);
	// return false;
	// }
	//
	// return true;
	throw new RuntimeException("NOT IMPLEMENTED - DB CHANGE MAYBE...");
    }

    @Override
    public DBooking getBooking(long id) {
	final Booking dbBooking = bookingRepo.findOne(id);
	return DbToDomainMapper.dbToDomainBooking(dbBooking);
    }

    @Override
    public List<DBooking> getBookings() {
	return ((List<Booking>) bookingRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsContaining(String desription) {
	return bookingRepo.findByDescriptionIgnoreCaseContaining(desription).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookings(DPerformer performer) {
	final Performer dbPerformer = DomainToDbMapper.domainToDbPerformer(performer);

	return bookingRepo.findByPerformer(dbPerformer).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookings(DPerformer performer, DRole role) {
	final Performer dbPerformer = DomainToDbMapper.domainToDbPerformer(performer);
	final Role dbRole = DomainToDbMapper.domainToDbRole(role);

	return bookingRepo.findByPerformerAndRole(dbPerformer, dbRole).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookings(DTask task) {
	final Task dbTask = DomainToDbMapper.domainToDbTask(task);

	return bookingRepo.findByTask(dbTask).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsStartedBefore(LocalDateTime startTimeBefore) {
	final Date beforeTime = Date.from(startTimeBefore.atZone(ZoneId.systemDefault()).toInstant());

	return bookingRepo.findByStartTimeBefore(beforeTime).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsStartedAfter(LocalDateTime startTimeAfter) {
	final Date afterTime = Date.from(startTimeAfter.atZone(ZoneId.systemDefault()).toInstant());

	return bookingRepo.findByStartTimeBefore(afterTime).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsStartedBetween(LocalDateTime startTimeLower, LocalDateTime startTimeUpper) {
	final Date fromTime = Date.from(startTimeLower.atZone(ZoneId.systemDefault()).toInstant());
	final Date toTime = Date.from(startTimeUpper.atZone(ZoneId.systemDefault()).toInstant());

	return bookingRepo.findByStartTimeBetween(fromTime, toTime).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsFinishedBefore(LocalDateTime endTimeBefore) {
	final Date beforeTime = Date.from(endTimeBefore.atZone(ZoneId.systemDefault()).toInstant());

	return bookingRepo.findByEndTimeBefore(beforeTime).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsFinishedAfter(LocalDateTime endTimeAfter) {
	final Date afterTime = Date.from(endTimeAfter.atZone(ZoneId.systemDefault()).toInstant());

	return bookingRepo.findByEndTimeAfter(afterTime).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsFinishedBetween(LocalDateTime endTimeLower, LocalDateTime endTimeUpper) {
	final Date fromTime = Date.from(endTimeLower.atZone(ZoneId.systemDefault()).toInstant());
	final Date toTime = Date.from(endTimeUpper.atZone(ZoneId.systemDefault()).toInstant());

	return bookingRepo.findByEndTimeBetween(fromTime, toTime).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsForHoursSpentLessThan(int hoursSpentLessThan) {
	return bookingRepo.findByHoursSpentLessThan(hoursSpentLessThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsForHoursSpentGreaterThan(int hoursSpentGreaterThan) {
	return bookingRepo.findByHoursSpentGreaterThan(hoursSpentGreaterThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsForHoursSpentBetween(int hoursSpentLower, int hoursSpentUpper) {
	return bookingRepo.findByHoursSpentBetween(hoursSpentLower, hoursSpentUpper).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateBooking(DBooking booking) {
	try {
	    if (bookingRepo.exists(booking.getId())) {
		LOGGER.info("Updating entity :: " + Booking.class.getSimpleName() + " :: " + booking.getId());

	    } else {
		LOGGER.error(
			"No entity found to update :: " + Booking.class.getSimpleName() + " :: " + booking.getId());
	    }

	    bookingRepo.save(DomainToDbMapper.domainToDbBooking(booking));

	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBooking(long id) {
	final Booking dbBooking = bookingRepo.findOne(id);
	return removeBooking(DbToDomainMapper.dbToDomainBooking(dbBooking));
    }

    @Override
    public boolean removeBooking(DBooking booking) {
	try {
	    bookingRepo.delete(DomainToDbMapper.domainToDbBooking(booking));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBookings(DTask task) {
	try {
	    task.getBookings() // nl
		    .forEach(b -> bookingRepo.delete(DomainToDbMapper.domainToDbBooking(b)));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBookings(DPerformer performer) {
	try {
	    performer.getBookings() // nl
		    .forEach(b -> bookingRepo.delete(DomainToDbMapper.domainToDbBooking(b)));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public void startBooking(DBooking booking) {
	booking.setStartTime(LocalDateTime.now());

	updateBooking(booking);
    }

    @Override
    public void stopBooking(DBooking booking) {
	booking.setEndTime(LocalDateTime.now());

	final long hoursSpent = Duration.between(booking.getEndTime(), booking.getStartTime()) // nl
		.get(ChronoUnit.HOURS);
	booking.setHoursSpent(hoursSpent);

	updateBooking(booking);
    }

    @Override
    public void resumeBooking(DBooking booking) {
	if (booking.getEndTime() == null || Objects.equals(booking.getEndTime(), LocalDateTime.MIN)) {
	    return;
	}

	booking.setEndTime(LocalDateTime.MIN);
    }

    @Override
    public void pauseBooking(DBooking booking) {
	if (booking.getStartTime() == null || Objects.equals(booking.getStartTime(), LocalDateTime.MIN)) {
	    return;
	}

	stopBooking(booking);
    }

    @Override
    public void scheduleBookingAsDefault(DBooking booking) {
	// TODO: Impl
    }

    @Override
    public void scheduleBookingAfter(DBooking booking, DBooking previousBooking) {
	// TODO: Impl
    }

    // @Override
    // public void scheduleBookingBefore(DBooking booking, DBooking
    // previousBooking) {
    //
    // }

    @Override
    public DBooking mergeBookings(DBooking booking1, DBooking booking2) {
	// TODO: Impl
	return null;
    }
}
