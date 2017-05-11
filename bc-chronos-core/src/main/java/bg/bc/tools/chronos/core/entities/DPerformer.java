package bg.bc.tools.chronos.core.entities;

import java.util.List;

/**
 * Representation of the basic(core) entity - Performer.
 * 
 * @author giliev
 */
public class DPerformer {

    /**
     * рндн: Describe...
     * 
     * @author giliev
     */
    public enum DPerformerRole {
	MANAGER, // nl
	DEVELOPER, // nl
	TESTER
    }

    private long id;

    private String name;

    private String handle;

    private char[] password;

    private String email;

    private DPerformerRole role;

    private boolean isLoggedIn;

    private List<DBooking> bookings;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getHandle() {
	return handle;
    }

    public void setHandle(String handle) {
	this.handle = handle;
    }

    public char[] getPassword() {
	return password;
    }

    public void setPassword(char[] password) {
	this.password = password;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public DPerformerRole getRole() {
	return role;
    }

    public void setRole(DPerformerRole role) {
	this.role = role;
    }

    public boolean isLoggedIn() {
	return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
	this.isLoggedIn = isLoggedIn;
    }

    public List<DBooking> getBookings() {
	return bookings;
    }

    public void setBookings(List<DBooking> bookings) {
	this.bookings = bookings;
    }

    public void addBooking(DBooking booking) {
	booking.setPerformer(this);
	getBookings().add(booking);
    }
}
