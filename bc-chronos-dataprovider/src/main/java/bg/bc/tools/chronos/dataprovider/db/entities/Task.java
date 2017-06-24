package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Task")
public class Task extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = false, nullable = false)
    private long hoursEstimated;

    @ManyToOne(optional = false)
    // , cascade = CascadeType.ALL)
    // , fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    // ,orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Booking> bookings;

    public long getHoursEstimated() {
	return hoursEstimated;
    }

    public void setHoursEstimated(long hoursEstimated) {
	this.hoursEstimated = hoursEstimated;
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

    public Collection<Booking> getBookings() {
	return bookings;
    }

    public void addBooking(Booking booking) {
	booking.setTask(this);

	if (getBookings() == null) {
	    setBookings(new ArrayList<Booking>());
	}

	getBookings().add(booking);
    }
}
