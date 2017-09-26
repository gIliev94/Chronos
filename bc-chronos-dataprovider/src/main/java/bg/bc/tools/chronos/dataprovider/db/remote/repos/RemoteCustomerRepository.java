package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;

public interface RemoteCustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByName(String name);

    Collection<Customer> findByNameIgnoreCaseContaining(String name);

    Collection<Customer> findByCategory(Category category);

    Collection<Customer> findByCategoryIn(Collection<Category> categories);

    Customer findBySyncKey(String syncKey);
}
