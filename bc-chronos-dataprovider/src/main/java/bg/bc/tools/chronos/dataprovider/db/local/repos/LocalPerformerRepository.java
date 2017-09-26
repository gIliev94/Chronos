package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;

public interface LocalPerformerRepository extends CrudRepository<Performer, Long> {

    Performer findByHandle(String handle);

    Performer findByEmail(String email);

    Collection<Performer> findByName(String name);

    Collection<Performer> findByNameIgnoreCaseContaining(String name);

    Collection<Performer> findByIsLoggedTrue();

    Collection<Performer> findByPriviledgesContaining(Priviledge priviledge);

    // https://stackoverflow.com/a/32099527
    Collection<Performer> findDistinctByPriviledgesIn(Collection<Priviledge> priviledges);

    Performer findBySyncKey(String syncKey);
}
