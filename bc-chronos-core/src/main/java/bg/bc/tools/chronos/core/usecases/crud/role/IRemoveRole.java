package bg.bc.tools.chronos.core.usecases.crud.role;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DRole;

interface IRemoveRole {

    boolean removeRole(long id);

    boolean removeRole(DRole role);

    boolean removeRole(DBooking booking);
}
