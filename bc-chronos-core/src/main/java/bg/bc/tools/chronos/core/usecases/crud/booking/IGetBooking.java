package bg.bc.tools.chronos.core.usecases.crud.booking;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DTask;

interface IGetBooking {

    DBooking getBooking(long id);

    DBooking getEffectivelyStoppedBooking(boolean isEffectivelyStopped);

    List<DBooking> getBookings();

    List<DBooking> getBookings(DPerformer performer);

    List<DBooking> getBookings(DTask task);

    List<DBooking> getBookingsIn(LocalDateTime betweenStartTime, LocalDateTime betweenEndTime);

    List<DBooking> getBookinsShorterThan(LocalTime timeSpentUnder);

    List<DBooking> getBookingsLongerThan(LocalTime timeSpentOver);

    List<DBooking> getOvertimeBookings(boolean isOvertime);
}
