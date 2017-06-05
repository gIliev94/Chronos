package bg.bc.tools.chronos.core.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the basic(core) entity - Task.
 * 
 * @author giliev
 */
public class DTask extends DCategoricalEntity {

    private long hoursEstimated;

    private DProject project;

    private List<DBooking> bookings;

    public long getHoursEstimated() {
	return hoursEstimated;
    }

    public void setHoursEstimated(long hoursEstimated) {
	this.hoursEstimated = hoursEstimated;
    }

    public DProject getProject() {
	return project;
    }

    public void setProject(DProject project) {
	this.project = project;
    }

    public List<DBooking> getBookings() {
	return bookings;
    }

    public void setBookings(List<DBooking> bookings) {
	this.bookings = bookings;
    }

    public void addBooking(DBooking booking) {
	booking.setTask(this);

	if (getBookings() == null) {
	    setBookings(new ArrayList<DBooking>());
	}

	getBookings().add(booking);
    }
}
