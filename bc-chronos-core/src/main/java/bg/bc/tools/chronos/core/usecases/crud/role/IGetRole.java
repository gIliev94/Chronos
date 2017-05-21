package bg.bc.tools.chronos.core.usecases.crud.role;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DRole;

interface IGetRole {

    DRole getRole(long id);

    DRole getRole(String name);

    DRole getRole(DBooking booking);

    List<DRole> getRoles();

    List<DRole> getRolesContaining(String name);

    List<DRole> getRoles(DCategory category);

    List<DRole> getRoles(List<DCategory> categories);

    List<DRole> getRolesLessThan(double billingRateLessThan);

    List<DRole> getRolesGreaterThan(double billingRateGreaterThan);

    List<DRole> getRolesBetween(double billingRateLower, double billingRateUpper);
}
