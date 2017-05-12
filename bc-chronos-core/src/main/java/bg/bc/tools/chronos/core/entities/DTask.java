package bg.bc.tools.chronos.core.entities;

import java.time.LocalTime;
import java.util.List;

/**
 * Representation of the basic(core) entity - Task.
 * 
 * @author giliev
 */
public class DTask extends DCategoricalEntity {

    public enum DTaskPhase {
	PLANNING, // nl
	DEVELOPMENT, // nl
	TESTING
    }

    private DTaskPhase phase;

    private LocalTime estimatedTime;

    private DProject project;

    private List<DBooking> bookings;

    private List<DBillingRate> billingRates;

    public DTaskPhase getPhase() {
	return phase;
    }

    public void setPhase(DTaskPhase phase) {
	this.phase = phase;
    }

    public LocalTime getEstimatedTime() {
	return estimatedTime;
    }

    public void setEstimatedTime(LocalTime timeEstimated) {
	this.estimatedTime = timeEstimated;
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

    public List<DBillingRate> getBillingRates() {
	return billingRates;
    }

    public void setBillingRates(List<DBillingRate> billingRates) {
	this.billingRates = billingRates;
    }

    public void addBillingRate(DBillingRate billingRate) {
	billingRate.setTask(this);
	getBillingRates().add(billingRate);
    }
}
