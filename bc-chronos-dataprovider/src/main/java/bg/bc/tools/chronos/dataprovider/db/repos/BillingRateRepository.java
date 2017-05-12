package bg.bc.tools.chronos.dataprovider.db.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRate;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.PerformerRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public interface BillingRateRepository extends CrudRepository<BillingRate, Long> {

    BillingRate findByTaskAndRole(Task task, PerformerRole role);

    Collection<BillingRate> findByTask(Task task);

    Collection<BillingRate> findByRole(PerformerRole role);

    Collection<BillingRate> findByRateLessThan(double rate);

    Collection<BillingRate> findByRateGreaterThan(double rate);

    Collection<BillingRate> findByRateBetween(double rateBottom, double rateTop);
}
