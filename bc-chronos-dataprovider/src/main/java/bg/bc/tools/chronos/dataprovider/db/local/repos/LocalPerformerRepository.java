package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;

public interface LocalPerformerRepository extends CrudRepository<Performer, Long> {

    Performer findByHandle(String handle);

    Performer findByEmail(String email);

    Performer findByIsLogged(boolean isLogged);

    Collection<Performer> findByName(String name);
}
