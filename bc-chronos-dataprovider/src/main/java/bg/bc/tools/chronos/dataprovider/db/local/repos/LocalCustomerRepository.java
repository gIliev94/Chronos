package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.Customer;

@Repository
public interface LocalCustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByName(String name);

    Collection<Customer> findByNameIgnoreCaseContaining(String name);

    Collection<Customer> findDistinctByNameIgnoreCaseContaining(String name);

    // Collection<Customer> findByCategory(Category category);
    //
    // Collection<Customer> findDistinctByCategory(Category category);
    //
    // Collection<Customer> findByCategoryIn(Collection<Category> categories);
    //
    // Collection<Customer> findDistinctByCategoryIn(Collection<Category>
    // categories);
}
