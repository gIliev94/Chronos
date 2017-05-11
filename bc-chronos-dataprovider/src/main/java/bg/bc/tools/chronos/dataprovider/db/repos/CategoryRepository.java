package bg.bc.tools.chronos.dataprovider.db.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
