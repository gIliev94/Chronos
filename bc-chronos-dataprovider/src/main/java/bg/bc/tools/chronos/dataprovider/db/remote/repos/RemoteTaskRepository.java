package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public interface RemoteTaskRepository extends CrudRepository<Task, Long> {

    Task findByName(String name);

    Collection<Task> findByNameIgnoreCaseContaining(String name);

    Collection<Task> findByDescriptionIgnoreCaseContaining(String description);

    Collection<Task> findByCategoryIsNull();

    Collection<Task> findByCategory(Category category);

    Collection<Task> findByCategoryIn(Collection<Category> categories);

    Collection<Task> findByEstimatedTimeHoursIn(int estimatedTimeHoursLower, int estimatedTimeHoursUpper);

    Collection<Task> findByEstimatedTimeHoursLessThan(int estimatedTimeHoursLessThan);

    Collection<Task> findByEstimatedTimeHoursGreaterThan(int estimatedTimeHoursGreaterThan);

    Collection<Task> findByProject(Project project);
}
