package bg.bc.tools.chronos.core.usecases.crud.booking;

import bg.bc.tools.chronos.core.entities.DBooking;

interface IAddBooking {

    DBooking addBooking(DBooking booking);
}
