package bg.bc.tools.chronos.dataprovider.db.local.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Client;

public interface LocalClientRepository extends CrudRepository<Client, Long> {

    Client findByName(String name);
}
