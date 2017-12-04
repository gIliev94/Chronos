package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

@Repository
public interface RemoteBookingRepository extends CrudRepository<Booking, Long> {

    // Collection<Booking> findByIsOvertimeTrue();
    // Collection<Booking> findByIsOvertimeFalse();

    // Booking findByIsEffectivelyStoppedTrue();
    // Booking findByIsEffectivelyStoppedFalse();

    Collection<Booking> findByDescriptionIgnoreCaseContaining(String description);

    Collection<Booking> findByHoursSpentLessThan(long hoursSpentLessThan);

    Collection<Booking> findByHoursSpentGreaterThan(long hoursSpentGreaterThan);

    Collection<Booking> findByHoursSpentBetween(long hoursSpentLower, long hoursSpentUpper);

    Collection<Booking> findByStartTimeBefore(Date startTimeBefore);

    Collection<Booking> findByStartTimeAfter(Date startTimeAfter);

    Collection<Booking> findByStartTimeBetween(Date startTimeLower, Date startTimeUpper);

    Collection<Booking> findByEndTimeBefore(Date endTimeBefore);

    Collection<Booking> findByEndTimeAfter(Date endTimeAfter);

    Collection<Booking> findByEndTimeBetween(Date endTimeLower, Date endTimeUpper);

    Collection<Booking> findByPerformer(Performer performer);

    Collection<Booking> findByPerformerAndRole(Performer performer, Role role);

    Collection<Booking> findByTask(Task task);

    Booking findBySyncKey(String syncKey);
}
