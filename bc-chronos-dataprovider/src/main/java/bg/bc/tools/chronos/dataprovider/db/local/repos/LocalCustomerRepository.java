package bg.bc.tools.chronos.dataprovider.db.local.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Customer;

public interface LocalCustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByName(String name);
}
