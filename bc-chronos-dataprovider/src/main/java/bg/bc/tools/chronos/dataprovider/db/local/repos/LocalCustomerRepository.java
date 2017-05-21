package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;

public interface LocalCustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByName(String name);

    // Customer findByNameIgnoreCase(String name);

    Collection<Customer> findByNameIgnoreCaseContaining(String name);

    // Collection<Customer> findByCategoryIsNull();

    Collection<Customer> findByCategory(Category category);

    Collection<Customer> findByCategoryIn(Collection<Category> categories);
}
