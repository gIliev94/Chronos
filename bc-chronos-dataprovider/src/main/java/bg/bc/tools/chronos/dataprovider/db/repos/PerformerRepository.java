package bg.bc.tools.chronos.dataprovider.db.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer;

public interface PerformerRepository extends CrudRepository<Performer, Long> {

}
