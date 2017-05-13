package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Client;

public interface RemoteClientRepository extends CrudRepository<Client, Long> {

    Client findByName(String name);
}
