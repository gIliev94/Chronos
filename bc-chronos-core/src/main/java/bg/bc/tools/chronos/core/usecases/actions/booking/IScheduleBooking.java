package bg.bc.tools.chronos.core.usecases.actions.booking;

import bg.bc.tools.chronos.core.entities.DBooking;

public interface IScheduleBooking {

    void scheduleBookingAsDefault(DBooking booking);

    void scheduleBookingAfter(DBooking booking, DBooking previousBooking);
}
