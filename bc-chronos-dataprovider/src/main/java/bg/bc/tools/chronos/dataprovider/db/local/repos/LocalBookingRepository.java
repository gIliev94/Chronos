package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public interface LocalBookingRepository extends CrudRepository<Booking, Long> {

    // Collection<Booking> findByIsOvertimeTrue();
    // Collection<Booking> findByIsOvertimeFalse();

    // Booking findByIsEffectivelyStoppedTrue();
    // Booking findByIsEffectivelyStoppedFalse();

    Collection<Booking> findByDescriptionIgnoreCaseContaining(String description);

    Collection<Booking> findByStartTimeBefore(Date startTimeBefore);

    Collection<Booking> findByStartTimeAfter(Date startTimeAfter);

    Collection<Booking> findByStartTimeBetween(Date startTimeLower, Date startTimeUpper);

    Collection<Booking> findByEndTimeBefore(Date endTimeBefore);

    Collection<Booking> findByEndTimeAfter(Date endTimeAfter);

    Collection<Booking> findByEndTimeBetween(Date endTimeLower, Date endTimeUpper);

    Collection<Booking> findByPerformer(Performer performer);
    
    Collection<Booking> findByRole(Role role);

    Collection<Booking> findByPerformerAndRole(Performer performer, Role role);

    Collection<Booking> findByTask(Task task);
}
