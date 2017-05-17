package bg.bc.tools.chronos.dataprovider.db.local.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;

public interface LocalBillingRateModifierRepository extends CrudRepository<BillingRateModifier, Long> {

    // BillingRate findByTaskAndRole(Task task, PerformerRole role);

    // Collection<BillingRate> findByTask(Task task);

    // Collection<BillingRate> findByRole(PerformerRole role);

    // Collection<BillingRate> findByRateLessThan(double rate);
    //
    // Collection<BillingRate> findByRateGreaterThan(double rate);
    //
    // Collection<BillingRate> findByRateBetween(double rateBottom, double
    // rateTop);
}
