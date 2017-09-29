package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;

public interface RemoteChangelogRepository extends CrudRepository<Changelog, Long> {

    Collection<Changelog> findByChangeTimeBefore(Date changeTimeBefore);

    Collection<Changelog> findByChangeTimeAfter(Date changeTimeAfter);

    Collection<Changelog> findByChangeTimeBetween(Date changeTimeLower, Date changeTimeUpper);

    Collection<Changelog> findByUpdateCounterLessThan(long lessThanUpdateCounter);

    Collection<Changelog> findByUpdateCounterGreaterThan(long greaterThanUpdateCounter);

    Collection<Changelog> findByUpdateCounterBetween(long lessThanUpdateCounter, long greaterThanUpdateCounter);

    Collection<Changelog> findByDeviceNameOrderByUpdateCounterDesc(String deviceName);

    Collection<Changelog> findByUpdatedEntityKey(String updatedEntityKey);

    // TODO: test
    // http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.limit-query-result
    Changelog findTopByOrderByUpdateCounterDesc();

    Changelog findTopByUpdatedEntityTypeOrderByUpdateCounterDesc(String updatedEntityType);
}
