package bg.bc.tools.chronos.dataprovider.db.local.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;

public interface LocalCategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name);
}
