package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.User;

@Repository
public interface LocalPerformerRepository extends CrudRepository<Performer, Long> {

    Performer findByUser(User user);

    Collection<Performer> findByBillingRole(BillingRole billingRole);

    Collection<Performer> findDistinctByBillingRole(BillingRole billingRole);
}
