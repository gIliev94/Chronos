package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "Booking")
public class Booking extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = false, nullable = true)
    private String description;

    @Column(unique = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(unique = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(unique = false, nullable = false)
    private double hoursSpent;

    @Column(unique = true, nullable = false)
    private String deviceName;

    @ManyToOne(optional = false)
    // , cascade = CascadeType.ALL)
    // , fetch = FetchType.LAZY)
    // @JoinColumn(name = "performer_id")
    private Performer performer;

    @ManyToOne(optional = false)
    // , cascade = CascadeType.ALL)
    // , fetch = FetchType.LAZY)
    private BillingRole billingRole;

    @ManyToOne(optional = false)
    // , cascade = CascadeType.ALL)
    // , fetch = FetchType.LAZY)
    // @JoinColumn(name = "task_id")
    private Task task;

    // @LazyCollection(LazyCollectionOption.FALSE)
    // @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch =
    // FetchType.EAGER)
    // @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    // , orphanRemoval = true,fetch = FetchType.LAZY)
    private Collection<BillingRateModifier> billingRateModifiers = new ArrayList<>(0);

    // TODO: Need???
    // @Column(unique = false, nullable = false)
    // private boolean isOvertime;
    //
    // @Column(unique = false, nullable = false)
    // private boolean isEffectivelyStopped;

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Date getStartTime() {
	return startTime;
    }

    public void setStartTime(Date startTime) {
	this.startTime = startTime;
    }

    public Date getEndTime() {
	return endTime;
    }

    public void setEndTime(Date endTime) {
	this.endTime = endTime;
    }

    public double getHoursSpent() {
	return hoursSpent;
    }

    public void setHoursSpent(double hoursSpent) {
	this.hoursSpent = hoursSpent;
    }

    public String getDeviceName() {
	return deviceName;
    }

    public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
    }

    public Performer getPerformer() {
	return performer;
    }

    public void setPerformer(Performer performer) {
	this.performer = performer;
    }

    public BillingRole getBillingRole() {
	return billingRole;
    }

    public void setBillingRole(BillingRole billingRole) {
	this.billingRole = billingRole;
    }

    public Task getTask() {
	return task;
    }

    public void setTask(Task task) {
	this.task = task;
    }

    // TODO: Need???
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

    public Collection<BillingRateModifier> getBillingRateModifiers() {
	return billingRateModifiers;
    }

    public void setBillingRateModifiers(Collection<BillingRateModifier> billingRateModifiers) {
	this.billingRateModifiers = billingRateModifiers;
    }

    // TODO: TEST...
    @Override
    public boolean equals(Object other) {
	if (other == null) {
	    return false;
	}
	if (other == this) {
	    return true;
	}
	if (other.getClass() != getClass()) {
	    return false;
	}
	// // vs
	// if (!(other instanceof Booking)) {
	// return false;
	// }

	final Booking booking = (Booking) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(booking.getDeviceName(), getDeviceName()) // nl
		.append(booking.getPerformer(), getPerformer()) // nl
		.append(booking.getBillingRole(), getBillingRole()) // nl
		.append(booking.getTask(), getTask()) // nl
		.isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder() // nl
		.appendSuper(super.hashCode()) // nl
		.append(getDeviceName()) // nl
		.append(getPerformer()) // nl
		.append(getBillingRole()) // nl
		.append(getTask()) // nl
		.hashCode();
    }
}
