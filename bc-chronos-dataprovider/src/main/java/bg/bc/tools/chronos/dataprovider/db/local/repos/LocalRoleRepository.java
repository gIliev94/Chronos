package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;

public interface LocalRoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);

    Collection<Role> findByNameIgnoreCaseContaining(String name);

    Collection<Role> findByBillingRateLessThan(double billingRateLessThan);

    Collection<Role> findByBillingRateGreaterThan(double billingRateGreaterThan);

    Collection<Role> findByBillingRateBetween(double billingRateLower, double billingRateUpper);

    Role findByBooking(Booking booking);

    Role findBySyncKey(String syncKey);
}
