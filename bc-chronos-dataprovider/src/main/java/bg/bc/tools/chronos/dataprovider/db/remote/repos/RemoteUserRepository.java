package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.User;

@Repository
public interface RemoteUserRepository extends CrudRepository<User, Long> {

    User findByAbbreviation(String abbreviation);

    User findByEmail(String email);

    // User findByIsLoggedTrue();

    Collection<User> findByFirstName(String firstName);

    Collection<User> findByFirstNameIgnoreCaseContaining(String firstName);

    Collection<User> findByLastName(String lastName);

    Collection<User> findByLastNameIgnoreCaseContaining(String lastName);

    Collection<User> findByAbbreviationIgnoreCaseContaining(String nickname);

    // Collection<User> findByPrimaryDeviceNameIgnoreCaseContaining(String
    // primaryDeviceName);

    // Collection<User> findByPriviledgesContaining(Privilege priviledge);

    // https://stackoverflow.com/a/32099527
    // Collection<User> findDistinctByPriviledgesIn(Collection<User>
    // priviledges);
}
