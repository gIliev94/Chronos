package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

@Repository
public interface LocalTaskRepository extends CrudRepository<Task, Long> {

    Task findByName(String name);

    Collection<Task> findByNameIgnoreCaseContaining(String name);

    Collection<Task> findByCategory(Category category);

    Collection<Task> findByCategoryIn(Collection<Category> categories);

    Collection<Task> findByHoursEstimatedBetween(long hoursEstimatedLower, long hoursEstimatedUpper);

    Collection<Task> findByHoursEstimatedLessThan(long hoursEstimatedLessThan);

    Collection<Task> findByHoursEstimatedGreaterThan(long hoursEstimatedGreaterThan);

    Collection<Task> findByProject(Project project);

    Task findBySyncKey(String syncKey);
}
