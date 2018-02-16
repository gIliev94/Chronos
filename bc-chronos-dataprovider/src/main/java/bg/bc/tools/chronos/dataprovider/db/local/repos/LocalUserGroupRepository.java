package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.Privilege;
import bg.bc.tools.chronos.dataprovider.db.entities.User;
import bg.bc.tools.chronos.dataprovider.db.entities.UserGroup;

@Repository
public interface LocalUserGroupRepository extends CrudRepository<UserGroup, Long> {

    // https://stackoverflow.com/a/32099527
    Collection<UserGroup> findByPrivilegesContaining(Privilege privilege);

    Collection<UserGroup> findDistinctByPrivilegesContaining(Privilege privilege);

    Collection<UserGroup> findByPrivilegesIn(Collection<Privilege> privileges);

    Collection<UserGroup> findDistinctByPrivilegesIn(Collection<Privilege> privileges);

    Collection<UserGroup> findByUsersContaining(User user);

    Collection<UserGroup> findDistinctByUsersContaining(User user);

    Collection<UserGroup> findByUsersIn(Collection<User> users);

    Collection<UserGroup> findDistinctByUsersIn(Collection<User> users);
}
