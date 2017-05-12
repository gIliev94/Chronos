package bg.bc.tools.chronos.dataprovider.db.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRate;

public interface BillingRateRepository extends CrudRepository<BillingRate, Long> {

}
