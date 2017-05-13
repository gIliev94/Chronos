package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public interface RemoteTaskRepository extends CrudRepository<Task, Long> {

}
