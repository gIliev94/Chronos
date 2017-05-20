package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;

public interface LocalProjectRepository extends CrudRepository<Project, Long> {

    Project findByName(String name);

    Collection<Project> findByNameIgnoreCaseContaining(String name);
    
    //TODO: Useless?
    Collection<Project> findByCategoryIsNull();

    Collection<Project> findByCategory(Category category);

    Collection<Project> findByCategoryIn(Collection<Category> categories);

    Collection<Project> findByCustomer(Customer customer);
}
