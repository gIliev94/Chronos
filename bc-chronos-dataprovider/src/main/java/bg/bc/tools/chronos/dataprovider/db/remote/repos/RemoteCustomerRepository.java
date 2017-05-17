package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Customer;

public interface RemoteCustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByName(String name);
}
