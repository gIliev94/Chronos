package bg.bc.tools.chronos.dataprovider.db.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findByName(String name);
}
