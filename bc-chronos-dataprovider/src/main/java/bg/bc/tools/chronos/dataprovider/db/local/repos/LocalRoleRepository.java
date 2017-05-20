package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;

public interface LocalRoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);

    Collection<Role> findByNameIgnoreCaseContaining(String name);

    Collection<Role> findByDescriptionIgnoreCaseContaining(String description);

    Collection<Role> findByCategoryIsNull();

    Collection<Role> findByCategory(Category category);

    Collection<Role> findByCategoryIn(Collection<Category> categories);

    Collection<Role> findByBillingRateIn(double billingRateLower, double billingRateUpper);

    Collection<Role> findByBillingRateLessThan(double billingRateLessThan);

    Collection<Role> findByBillingRateGreaterThan(double billingRateGreaterThan);

    Role findByBooking(Booking booking);
}
