package bg.bc.tools.chronos.core.usecases.crud.booking;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DTask;

interface IRemoveBooking {

    boolean removeBooking(long id);

    boolean removeBooking(DBooking booking);

    boolean removeBookings(DTask task);

    boolean removeBookings(DPerformer performer);

    // boolean removeBookingsForHoursSpentBetween(int hoursSpentLower, int
    // hoursSpentUpper);
    //
    // boolean removeBookingsForHoursSpentLessThan(int hoursSpentLessThan);
    //
    // boolean removeBookingsForHoursSpentGreaterThan(int
    // hoursSpentGreaterThan);
    //
    // boolean removeBookingsStartedBetween(LocalDateTime startTimeSpent);
    //
    // boolean removeBookingsStartedBefore(LocalDateTime startTimeSpent);
    //
    // boolean removeBookingsStartedAfter(LocalDateTime startTimeSpent);
    //
    // boolean removeBookingsFinishedBetween(LocalDateTime endTimeSpent);
    //
    // boolean removeBookingsFinishedBefore(LocalDateTime endTimeSpent);
    //
    // boolean removeBookingsFinishedAfter(LocalDateTime endTimeSpent);

    // boolean removeBookingsByOvertime(boolean isOvertime);
}
