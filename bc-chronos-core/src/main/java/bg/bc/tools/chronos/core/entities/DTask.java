package bg.bc.tools.chronos.core.entities;

import java.time.LocalTime;
import java.util.List;

/**
 * Representation of the basic(core) entity - Task.
 * 
 * @author giliev
 */
public class DTask extends DCategoricalEntity {

    /**
     * рндн: Describe...
     * 
     * @author giliev
     */
    public enum DTaskPhase {
	PLANNING, // nl
	DEVELOPMENT, // nl
	TESTING
    }
    //
    // private long id;
    //
    // private String name;
    //
    // private String description;

    private DTaskPhase phase;

    private LocalTime estimatedTime;

    // private DCategory category;

    private DProject project;

    private List<DBooking> bookings;

    private List<DBillingRate> billingRates;

    // TODO: Add other relevant field/s

    // public long getId() {
    // return id;
    // }
    //
    // public void setId(long id) {
    // this.id = id;
    // }
    //
    // public String getName() {
    // return name;
    // }
    //
    // public void setName(String name) {
    // this.name = name;
    // }
    //
    // public String getDescription() {
    // return description;
    // }
    //
    // public void setDescription(String description) {
    // this.description = description;
    // }

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

    // public DCategory getCategory() {
    // return category;
    // }
    //
    // public void setCategory(DCategory category) {
    // this.category = category;
    // }

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
