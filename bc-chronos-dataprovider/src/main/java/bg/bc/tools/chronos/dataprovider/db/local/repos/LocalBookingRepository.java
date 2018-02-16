package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

@Repository
public interface LocalBookingRepository extends CrudRepository<Booking, Long> {

    // Collection<Booking> findByIsOvertimeTrue();
    // Collection<Booking> findByIsOvertimeFalse();

    // Booking findByIsEffectivelyStoppedTrue();
    // Booking findByIsEffectivelyStoppedFalse();

    Collection<Booking> findByDescriptionIgnoreCaseContaining(String description);

    Collection<Booking> findDistinctByDescriptionIgnoreCaseContaining(String description);

    Collection<Booking> findByDeviceName(String deviceName);

    Collection<Booking> findDistinctByDeviceName(String deviceName);

    Collection<Booking> findByHoursSpentLessThan(long hoursSpentLessThan);

    Collection<Booking> findDistinctByHoursSpentLessThan(long hoursSpentLessThan);

    Collection<Booking> findByHoursSpentGreaterThan(long hoursSpentGreaterThan);

    Collection<Booking> findDistinctByHoursSpentGreaterThan(long hoursSpentGreaterThan);

    Collection<Booking> findByHoursSpentBetween(long hoursSpentLower, long hoursSpentUpper);

    Collection<Booking> findDistinctByHoursSpentBetween(long hoursSpentLower, long hoursSpentUpper);

    Collection<Booking> findByStartTimeBefore(Date startTimeBefore);

    Collection<Booking> findDistinctByStartTimeBefore(Date startTimeBefore);

    Collection<Booking> findByStartTimeAfter(Date startTimeAfter);

    Collection<Booking> findDistinctByStartTimeAfter(Date startTimeAfter);

    Collection<Booking> findByStartTimeBetween(Date startTimeLower, Date startTimeUpper);

    Collection<Booking> findDistinctByStartTimeBetween(Date startTimeLower, Date startTimeUpper);

    Collection<Booking> findByEndTimeBefore(Date endTimeBefore);

    Collection<Booking> findDistinctByEndTimeBefore(Date endTimeBefore);

    Collection<Booking> findByEndTimeAfter(Date endTimeAfter);

    Collection<Booking> findDistinctByEndTimeAfter(Date endTimeAfter);

    Collection<Booking> findByEndTimeBetween(Date endTimeLower, Date endTimeUpper);

    Collection<Booking> findDistinctByEndTimeBetween(Date endTimeLower, Date endTimeUpper);

    Collection<Booking> findByPerformer(Performer performer);

    Collection<Booking> findDistinctByPerformer(Performer performer);

    Collection<Booking> findByPerformerAndBillingRole(Performer performer, BillingRole billingRole);

    Collection<Booking> findDistinctByPerformerAndBillingRole(Performer performer, BillingRole billingRole);

    Collection<Booking> findByTask(Task task);

    Collection<Booking> findDistinctByTask(Task task);
}
