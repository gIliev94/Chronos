package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;

@Repository
public interface RemoteCategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name);

    Category findBySyncKey(String syncKey);
}
