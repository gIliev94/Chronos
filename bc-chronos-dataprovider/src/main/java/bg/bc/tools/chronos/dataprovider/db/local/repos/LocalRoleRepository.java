package bg.bc.tools.chronos.dataprovider.db.local.repos;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Role;

public interface LocalRoleRepository extends CrudRepository<Role, Long> {
}
