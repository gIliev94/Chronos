package bg.bc.tools.chronos.dataprovider.db.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.PerformerRole;

public interface PerformerRepository extends CrudRepository<Performer, Long> {

    Performer findByHandle(String handle);

    Performer findByEmail(String email);

    Performer findByIsLoggedIn(boolean isLoggedIn);

    Collection<Performer> findByName(String name);

    Collection<Performer> findByRole(PerformerRole role);
}
