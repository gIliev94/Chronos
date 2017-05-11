package bg.bc.tools.chronos.dataprovider.db.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByName(String name);
}
