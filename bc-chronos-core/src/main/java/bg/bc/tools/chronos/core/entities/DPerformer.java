package bg.bc.tools.chronos.core.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representation of the basic(core) entity - Performer.
 * 
 * @author giliev
 */
public class DPerformer extends DObject {

    public enum DPriviledge {
	READ, // nl
	WRITE, // nl
	DELETE, // nl
	FORCESYNC, // nl
	ALL
    }

    private String syncKey;

    private long id;

    private String name;

    private String handle;

    private char[] password;

    private String email;

    private String primaryDeviceName;

    private boolean isLogged;

    private List<DPriviledge> priviledges;

    private List<DBooking> bookings;

    public String getSyncKey() {
	return syncKey;
    }

    public void setSyncKey(String syncKey) {
	this.syncKey = syncKey;
    }

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

    public String getPrimaryDeviceName() {
	return primaryDeviceName;
    }

    public void setPrimaryDeviceName(String primaryDeviceName) {
	this.primaryDeviceName = primaryDeviceName;
    }

    public boolean isLogged() {
	return isLogged;
    }

    public void setLogged(boolean isLogged) {
	this.isLogged = isLogged;
    }

    public List<DPriviledge> getPriviledges() {
	return priviledges;
    }

    public void setPriviledges(List<DPriviledge> priviledges) {
	this.priviledges = priviledges;
    }

    public void addPriviledge(DPriviledge priviledge) {
	if (getPriviledges() == null) {
	    setPriviledges(new ArrayList<DPriviledge>());
	}

	getPriviledges().add(priviledge);
    }

    public List<DBooking> getBookings() {
	return bookings;
    }

    public void setBookings(List<DBooking> bookings) {
	this.bookings = bookings;
    }

    public void addBooking(DBooking booking) {
	booking.setPerformer(this);

	if (getBookings() == null) {
	    setBookings(new ArrayList<DBooking>());
	}

	getBookings().add(booking);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;
	if (!(obj instanceof DPerformer)) {
	    return false;
	}

	DPerformer performer = (DPerformer) obj;
	return id == performer.id // nl
		&& Objects.equals(handle, performer.handle) // nl
		&& Objects.equals(email, performer.email);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, handle, email);
    }

    @Override
    public String toString() {
	return handle + "[" + name + "]";
    }
}
