package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;

public interface RemoteCategoryRepository extends CrudRepository<Category, Long> {

    Collection<Category> findByName(String name);

    Collection<Category> findByNameAndSortOrder(String name, int sortOrder);
}
