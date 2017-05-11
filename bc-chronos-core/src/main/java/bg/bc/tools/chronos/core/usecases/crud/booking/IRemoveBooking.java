package bg.bc.tools.chronos.core.usecases.crud.booking;

import java.time.LocalTime;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DTask;

interface IRemoveBooking {

    boolean removeBooking(DBooking booking);

    boolean removeBooking(long id);

    boolean removeBookingsByTask(DTask task);

    boolean removeBookingsByPerfomer(DPerformer performer);

    boolean removeBookingsByTimeSpent(LocalTime timeSpent);

    boolean removeBookingsByOvertime(boolean isOvertime);
}
