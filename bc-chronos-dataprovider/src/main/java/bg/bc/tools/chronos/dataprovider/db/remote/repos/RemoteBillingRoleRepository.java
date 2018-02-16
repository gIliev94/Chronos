package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRole;

@Repository
public interface RemoteBillingRoleRepository extends CrudRepository<BillingRole, Long> {

    BillingRole findByName(String name);

    Collection<BillingRole> findByNameIgnoreCaseContaining(String name);

    Collection<BillingRole> findDistinctByNameIgnoreCaseContaining(String name);

    Collection<BillingRole> findByBillingRate(double billingRate);

    Collection<BillingRole> findDistinctByBillingRate(double billingRate);

    Collection<BillingRole> findByBillingRateLessThan(double billingRateLessThan);

    Collection<BillingRole> findDistinctByBillingRateLessThan(double billingRateLessThan);

    Collection<BillingRole> findByBillingRateGreaterThan(double billingRateGreaterThan);

    Collection<BillingRole> findDistinctByBillingRateGreaterThan(double billingRateGreaterThan);

    Collection<BillingRole> findByBillingRateBetween(double billingRateLower, double billingRateUpper);

    Collection<BillingRole> findDistinctByBillingRateBetween(double billingRateLower, double billingRateUpper);
}
