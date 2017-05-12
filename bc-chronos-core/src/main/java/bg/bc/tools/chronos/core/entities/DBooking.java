package bg.bc.tools.chronos.core.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representation of the basic(core) entity - Booking.
 * 
 * @author giliev
 */
public class DBooking {

    private long id;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private DPerformer performer;

    private DTask task;

    private boolean isOvertime;

    private boolean isEffectivelyStopped;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public LocalDateTime getStartTime() {
	return startTime;
    }

    public void setStartTime(LocalDateTime start) {
	this.startTime = start;
    }

    public LocalDateTime getEndTime() {
	return endTime;
    }

    public void setEndTime(LocalDateTime end) {
	this.endTime = end;
    }

    public DPerformer getPerformer() {
	return performer;
    }

    public void setPerformer(DPerformer performer) {
	this.performer = performer;
    }

    public DTask getTask() {
	return task;
    }

    public void setTask(DTask task) {
	this.task = task;
    }

    public boolean isOvertime() {
	return isOvertime;
    }

    public void setOvertime(boolean isOvertime) {
	this.isOvertime = isOvertime;
    }

    public boolean isEffectivelyStopped() {
	return isEffectivelyStopped;
    }

    public void setEffectivelyStopped(boolean isEffectivelyStopped) {
	this.isEffectivelyStopped = isEffectivelyStopped;
    }

    public boolean equals(Object obj) {
	if (obj == this)
	    return true;
	if (!(obj instanceof DBooking)) {
	    return false;
	}

	DBooking booking = (DBooking) obj;
	return id == booking.id // nl
		&& Objects.equals(performer, booking.performer)// nl
		&& Objects.equals(task, booking.task);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, performer.getHandle(), task.getName());
    }
}
