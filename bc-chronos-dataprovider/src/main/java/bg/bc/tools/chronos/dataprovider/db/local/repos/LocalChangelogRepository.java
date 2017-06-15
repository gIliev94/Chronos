package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;

public interface LocalChangelogRepository extends CrudRepository<Changelog, Long> {
    
    Collection<Changelog> findByChangeTimeBefore(Date changeTimeBefore);

    Collection<Changelog> findByChangeTimeAfter(Date changeTimeAfter);

    Collection<Changelog> findByChangeTimeBetween(Date changeTimeLower, Date changeTimeUpper);

    Collection<Changelog> findByUpdateCounterLessThan(double lessThanModifierValue);

    Collection<Changelog> findByUpdateCounterGreaterThan(double greaterThanModifierValue);

    Collection<Changelog> findByUpdateCounterBetween(double lessThanUpdateCounter, double greaterThanUpdateCounter);

    Collection<Changelog> findByDeviceNameOrderByUpdateCounterDesc(String deviceName);
    
    Collection<Changelog> findByUpdatedEntityKey(String updatedEntityKey);
    
    //TODO: test http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.limit-query-result
    Changelog findTopByOrderByUpdateCounterDesc();
}
