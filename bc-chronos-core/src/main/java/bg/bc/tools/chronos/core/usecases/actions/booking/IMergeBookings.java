package bg.bc.tools.chronos.core.usecases.actions.booking;

import bg.bc.tools.chronos.core.entities.DBooking;

public interface IMergeBookings {

    DBooking mergeBookings(DBooking booking1, DBooking booking2);
}
