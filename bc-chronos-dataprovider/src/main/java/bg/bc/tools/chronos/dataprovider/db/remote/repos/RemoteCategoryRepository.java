package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;

public interface RemoteCategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name);
}
