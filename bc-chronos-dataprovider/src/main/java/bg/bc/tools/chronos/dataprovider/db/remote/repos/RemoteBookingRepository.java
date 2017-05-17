package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;

public interface RemoteBookingRepository extends CrudRepository<Booking, Long> {

    // Collection<Booking> findByIsOvertime(boolean isOvertime);

    // Booking findByIsEffectivelyStopped(boolean isEffectivelyStopped);

    Collection<Booking> findByStartTimeBetween(LocalDateTime lowerBound, LocalDateTime upperBound);

    Collection<Booking> findByTask(Task task);

    Collection<Booking> findByPerformer(Performer performer);
}
