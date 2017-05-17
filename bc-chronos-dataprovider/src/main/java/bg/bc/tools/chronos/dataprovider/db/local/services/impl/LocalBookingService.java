package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalBookingService;

public class LocalBookingService implements ILocalBookingService {

    @Autowired
    private LocalBookingRepository bookingRepo;

    @Override
    public boolean addBooking(DBooking booking) {
	// TODO: REFACTOR
	// final HolidayManager holidayManager = HolidayManager
	// .getInstance(ManagerParameters.create(HolidayCalendar.BULGARIA));
	// final Set<Holiday> holidays =
	// holidayManager.getHolidays(booking.getStartTime().toLocalDate(),
	// booking.getEndTime().toLocalDate());
	//
	// booking.setOvertime(!holidays.isEmpty());
	// booking.setEffectivelyStopped(false);

	if (bookingRepo.exists(booking.getId())) {
	    // LOGGER.error(...);
	    return false;
	}

	try {
	    bookingRepo.save(DomainToDbMapper.domainToDbBooking(booking));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DBooking getBooking(long id) {
	return DbToDomainMapper.dbToDomainBooking(bookingRepo.findOne(id));
    }

    @Override
    public DBooking getEffectivelyStoppedBooking(boolean isEffectivelyStopped) {
	// return
	// DbToDomainMapper.dbToDomainBooking(bookingRepo.findByIsEffectivelyStopped(isEffectivelyStopped));
	return null;
    }

    @Override
    public List<DBooking> getBookings() {
	return ((List<Booking>) bookingRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookings(DPerformer performer) {
	return ((List<Booking>) bookingRepo.findAll()).stream() // nl
		.filter(b -> Objects.equals(performer, b.getPerformer())) // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookings(DTask task) {
	return ((List<Booking>) bookingRepo.findAll()).stream() // nl
		.filter(b -> Objects.equals(task, b.getTask())) // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookingsIn(LocalDateTime betweenStartTime, LocalDateTime betweenEndTime) {
	return bookingRepo.findByStartTimeBetween(betweenStartTime, betweenEndTime).stream() // nl
		.map(DbToDomainMapper::dbToDomainBooking) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DBooking> getBookinsShorterThan(LocalTime timeSpentUnder) {
	// return ((List<Booking>) bookingRepo.findAll()).stream() // nl
	// .filter(b ->
	// b.getStartTime().toLocalTime().plusHours(b.getEndTime().toInstant(null))
	// < timeSpentUnder.getLong(ChronoUnit.HOURS)) // nl
	// .map(DbToDomainMapper::dbToDomainBooking) // nl
	// .collect(Collectors.toList());

	return null;
    }

    @Override
    public List<DBooking> getBookingsLongerThan(LocalTime timeSpentOver) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DBooking> getOvertimeBookings(boolean isOvertime) {
	// return bookingRepo.findByIsOvertime(isOvertime).stream() // nl
	// .map(DbToDomainMapper::dbToDomainBooking) // nl
	// .collect(Collectors.toList());
	return null;
    }

    @Override
    public boolean updateBooking(DBooking booking) {
	try {
	    if (bookingRepo.exists(booking.getId())) {
		// LOGGER.info("Updating entity :: " +
		// Booking.class.getSimpleName() + " ::" + booking.getName());

	    } else {
		// LOGGER.error("No entity found to update :: " +
		// Booking.class.getSimpleName() + " ::" + booking.getName());
	    }

	    bookingRepo.save(DomainToDbMapper.domainToDbBooking(booking));

	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBooking(DBooking booking) {
	try {
	    bookingRepo.delete(DomainToDbMapper.domainToDbBooking(booking));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeBooking(long id) {
	final Booking booking = bookingRepo.findOne(id);
	return removeBooking(DbToDomainMapper.dbToDomainBooking(booking));
    }

    @Override
    public boolean removeBookingsByTask(DTask task) {
	final Collection<Booking> bookings = bookingRepo.findByTask(DomainToDbMapper.domainToDbTask(task));
	bookings.forEach(b -> removeBooking(DbToDomainMapper.dbToDomainBooking(b)));

	// TODO: REFACTOR
	return true;
    }

    @Override
    public boolean removeBookingsByPerfomer(DPerformer performer) {
	final Collection<Booking> bookings = bookingRepo
		.findByPerformer(DomainToDbMapper.domainToDbPerformer(performer));
	bookings.forEach(b -> removeBooking(DbToDomainMapper.dbToDomainBooking(b)));

	// TODO: REFACTOR
	return true;
    }

    @Override
    public boolean removeBookingsByTimeSpent(LocalTime timeSpent) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeBookingsByOvertime(boolean isOvertime) {
	getOvertimeBookings(isOvertime).stream() // nl
		.map(DomainToDbMapper::domainToDbBooking) // nl
		.forEach(b -> removeBooking(DbToDomainMapper.dbToDomainBooking(b)));

	// TODO: REFACTOR
	return false;
    }

    @Override
    public void stopBooking(DBooking booking) {
	// TODO Auto-generated method stub

    }

    @Override
    public void startBooking(DBooking booking) {
	// TODO Auto-generated method stub

    }

    @Override
    public void scheduleBookingAsDefault(DBooking booking) {
	// TODO Auto-generated method stub

    }

    @Override
    public void scheduleBookingAfter(DBooking booking, DBooking previousBooking) {
	// TODO Auto-generated method stub

    }

    @Override
    public void resumeBooking(DBooking booking) {
	// TODO Auto-generated method stub

    }

    @Override
    public void pauseBooking(DBooking booking) {
	// TODO Auto-generated method stub

    }

    @Override
    public DBooking mergeBookings(DBooking booking1, DBooking booking2) {
	// TODO: Refactor
	return null;
    }

}
