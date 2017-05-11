package bg.bc.tools.chronos.dataprovider.db.local.services.ifc;

import bg.bc.tools.chronos.core.usecases.actions.booking.IMergeBookings;
import bg.bc.tools.chronos.core.usecases.actions.booking.IPauseBooking;
import bg.bc.tools.chronos.core.usecases.actions.booking.IResumeBooking;
import bg.bc.tools.chronos.core.usecases.actions.booking.IScheduleBooking;
import bg.bc.tools.chronos.core.usecases.actions.booking.IStartBooking;
import bg.bc.tools.chronos.core.usecases.actions.booking.IStopBooking;
import bg.bc.tools.chronos.core.usecases.crud.booking.IBookingCrud;

public interface IBookingService extends IBookingCrud, IMergeBookings, IPauseBooking, IResumeBooking, IScheduleBooking,
	IStartBooking, IStopBooking {

}
