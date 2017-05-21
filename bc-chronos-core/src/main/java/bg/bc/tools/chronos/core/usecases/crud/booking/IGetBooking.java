package bg.bc.tools.chronos.core.usecases.crud.booking;

import java.time.LocalDateTime;
import java.util.List;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;

interface IGetBooking {

    DBooking getBooking(long id);

    List<DBooking> getBookings();

    List<DBooking> getBookingsContaining(String desription);

    List<DBooking> getBookings(DPerformer performer);

    List<DBooking> getBookings(DPerformer performer, DRole role);

    List<DBooking> getBookings(DTask task);

    List<DBooking> getBookingsForHoursSpentLessThan(int hoursSpentLessThan);

    List<DBooking> getBookingsForHoursSpentGreaterThan(int hoursSpentGreaterThan);

    List<DBooking> getBookingsForHoursSpentBetween(int hoursSpentLower, int hoursSpentUpper);

    List<DBooking> getBookingsStartedBefore(LocalDateTime startTimeBefore);

    List<DBooking> getBookingsStartedAfter(LocalDateTime startTimeAfter);

    List<DBooking> getBookingsStartedBetween(LocalDateTime startTimeLower, LocalDateTime startTimeUpper);

    List<DBooking> getBookingsFinishedBefore(LocalDateTime endTimeBefore);

    List<DBooking> getBookingsFinishedAfter(LocalDateTime endTimeAfter);

    List<DBooking> getBookingsFinishedBetween(LocalDateTime endTimeLower, LocalDateTime endTimeUpper);

    // DBooking getEffectivelyStoppedBooking(boolean isEffectivelyStopped);
    // List<DBooking> getOvertimeBookings(boolean isOvertime);
}
