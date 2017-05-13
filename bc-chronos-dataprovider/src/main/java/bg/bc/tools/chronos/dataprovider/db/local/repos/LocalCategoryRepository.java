package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;

public interface LocalCategoryRepository extends CrudRepository<Category, Long> {

    Collection<Category> findByName(String name);

    Collection<Category> findByNameAndSortOrder(String name, int sortOrder);
}
