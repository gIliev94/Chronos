package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Task")
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum TaskPhase {
	PLANNING, // nl
	DEVELOPMENT, // nl
	TESTING
    }

    @Enumerated(EnumType.STRING)
    private TaskPhase phase;

    @Column(unique = false, nullable = false)
    private LocalTime estimatedTime;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Booking> bookings;

    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<BillingRate> billingRates;

    public TaskPhase getPhase() {
	return phase;
    }

    public void setPhase(TaskPhase phase) {
	this.phase = phase;
    }

    public LocalTime getEstimatedTime() {
	return estimatedTime;
    }

    public void setEstimatedTime(LocalTime timeEstimated) {
	this.estimatedTime = timeEstimated;
    }

    public Collection<Booking> getBookings() {
	return bookings;
    }

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public void setBookings(Collection<Booking> bookings) {
	this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
	booking.setTask(this);
	getBookings().add(booking);
    }

    public Collection<BillingRate> getBillingRates() {
	return billingRates;
    }

    public void setBillingRates(Collection<BillingRate> billingRates) {
	this.billingRates = billingRates;
    }

    public void addBillingRate(BillingRate billingRate) {
	billingRate.setTask(this);
	getBillingRates().add(billingRate);
    }
}
