package bg.bc.tools.chronos.dataprovider.db.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
