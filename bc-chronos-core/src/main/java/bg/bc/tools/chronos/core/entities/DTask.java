package bg.bc.tools.chronos.core.entities;

import java.util.List;

/**
 * Representation of the basic(core) entity - Task.
 * 
 * @author giliev
 */
public class DTask extends DCategoricalEntity {

    // public enum DTaskPhase {
    // PLANNING, // nl
    // DEVELOPMENT, // nl
    // TESTING
    // }
    //
    // private DTaskPhase phase;

    private long hoursEstimated;

    private DProject project;

    private List<DBooking> bookings;

    // public DTaskPhase getPhase() {
    // return phase;
    // }
    //
    // public void setPhase(DTaskPhase phase) {
    // this.phase = phase;
    // }

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
	getBookings().add(booking);
    }
}
