package bg.bc.tools.chronos.core.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Representation of the basic(core) entity - Booking.
 * 
 * @author giliev
 */
public class DBooking extends DObject {

    private String syncKey;

    private long id;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private long hoursSpent;

    private DPerformer performer;

    private DRole role;

    private DTask task;

    // private boolean isOvertime;
    //
    // private boolean isEffectivelyStopped;

    private Collection<DBillingRateModifier> billingRateModifiers;

    public String getSyncKey() {
	return syncKey;
    }

    public void setSyncKey(String syncKey) {
	this.syncKey = syncKey;
    }

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

    public long getHoursSpent() {
	return hoursSpent;
    }

    public void setHoursSpent(long hoursSpent) {
	this.hoursSpent = hoursSpent;
    }

    public DPerformer getPerformer() {
	return performer;
    }

    public void setPerformer(DPerformer performer) {
	this.performer = performer;
    }

    public DRole getRole() {
	return role;
    }

    public void setRole(DRole role) {
	this.role = role;
    }

    public DTask getTask() {
	return task;
    }

    public void setTask(DTask task) {
	this.task = task;
    }

    // public boolean isOvertime() {
    // return isOvertime;
    // }
    //
    // public void setOvertime(boolean isOvertime) {
    // this.isOvertime = isOvertime;
    // }
    //
    // public boolean isEffectivelyStopped() {
    // return isEffectivelyStopped;
    // }
    //
    // public void setEffectivelyStopped(boolean isEffectivelyStopped) {
    // this.isEffectivelyStopped = isEffectivelyStopped;
    // }

    public Collection<DBillingRateModifier> getBillingRateModifiers() {
	return billingRateModifiers;
    }

    public void setBillingRateModifiers(Collection<DBillingRateModifier> billingRateModifiers) {
	this.billingRateModifiers = billingRateModifiers;
    }

    public void addBillingRateModifier(DBillingRateModifier billingRateModifier) {
	billingRateModifier.setBooking(this);

	if (getBillingRateModifiers() == null) {
	    setBillingRateModifiers(new ArrayList<DBillingRateModifier>());
	}

	getBillingRateModifiers().add(billingRateModifier);
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
		&& Objects.equals(role, booking.role)// nl
		&& Objects.equals(task, booking.task);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, performer.getHandle(), role.getName(), task.getName());
    }

    @Override
    public String toString() {
	return task + "[" + description + "]";
    }
}
