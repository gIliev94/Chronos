package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Project;

public interface RemoteProjectRepository extends CrudRepository<Project, Long> {

    Project findByName(String name);
}
