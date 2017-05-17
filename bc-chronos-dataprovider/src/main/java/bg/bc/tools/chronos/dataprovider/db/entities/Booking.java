package bg.bc.tools.chronos.dataprovider.db.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = false, nullable = true)
    private String description;

    @Column(unique = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(unique = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Performer performer;

    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Role role;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Task task;

    @OneToMany(mappedBy = "booking", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<BillingRateModifier> billingRateModifiers;

    // @Column(unique = false, nullable = false)
    // private boolean isOvertime;
    //
    // @Column(unique = false, nullable = false)
    // private boolean isEffectivelyStopped;

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

    public Performer getPerformer() {
	return performer;
    }

    public void setPerformer(Performer performer) {
	this.performer = performer;
    }

    public Role getRole() {
	return role;
    }

    public void setRole(Role role) {
	this.role = role;
    }

    public Task getTask() {
	return task;
    }

    public void setTask(Task task) {
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

    public Collection<BillingRateModifier> getBillingRateModifiers() {
	return billingRateModifiers;
    }

    public void setBillingRateModifiers(Collection<BillingRateModifier> billingRateModifiers) {
	this.billingRateModifiers = billingRateModifiers;
    }

    public void addBillingRateModifier(BillingRateModifier billingRateModifier) {
	billingRateModifier.setBooking(this);
	getBillingRateModifiers().add(billingRateModifier);
    }
}
