package bg.bc.tools.chronos.dataprovider.db.local.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Project;

public interface LocalProjectRepository extends CrudRepository<Project, Long> {

    Project findByName(String name);
}
