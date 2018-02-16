package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;

@Repository
public interface RemoteProjectRepository extends CrudRepository<Project, Long> {

    Project findByName(String name);

    Collection<Project> findByNameIgnoreCaseContaining(String name);

    Collection<Project> findDistinctByNameIgnoreCaseContaining(String name);

    // Collection<Project> findByCategoryIsNull();

    // Collection<Project> findByCategory(Category category);
    //
    // Collection<Project> findDistinctByCategory(Category category);
    //
    // Collection<Project> findByCategoryIn(Collection<Category> categories);
    //
    // Collection<Project> findDistinctByCategoryIn(Collection<Category>
    // categories);

    Collection<Project> findByCustomer(Customer customer);

    Collection<Project> findDistinctByCustomer(Customer customer);
}
