package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Task")
public class Task extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // public enum TaskPhase {
    // PLANNING, // nl
    // DEVELOPMENT, // nl
    // TESTING
    // }
    //
    // @Enumerated(EnumType.STRING)
    // private TaskPhase phase;

    // @Column(unique = false, nullable = false)
    // private LocalTime estimatedTime;

    @Column(unique = false, nullable = false)
    // @Temporal(TemporalType.TIME)
    private int estimatedTimeHours;

    // @Column(unique = false, nullable = false)
    // private double billingRateModifier;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Booking> bookings;

    // public TaskPhase getPhase() {
    // return phase;
    // }
    //
    // public void setPhase(TaskPhase phase) {
    // this.phase = phase;
    // }

    public int getEstimatedTimeHours() {
	return estimatedTimeHours;
    }

    public void setEstimatedTimeHours(int estimatedTimeHours) {
	this.estimatedTimeHours = estimatedTimeHours;
    }

    // public double getBillingRateModifier() {
    // return billingRateModifier;
    // }
    //
    // public void setBillingRateModifier(double billingRateModifier) {
    // this.billingRateModifier = billingRateModifier;
    // }

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public void setBookings(Collection<Booking> bookings) {
	this.bookings = bookings;
    }

    public Collection<Booking> getBookings() {
	return bookings;
    }

    public void addBooking(Booking booking) {
	booking.setTask(this);
	getBookings().add(booking);
    }
}
