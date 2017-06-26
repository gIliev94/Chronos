package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Performer")
public class Performer implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Priviledge {
	READ, // nl
	WRITE, // nl
	DELETE, // nl
	FORCESYNC, // nl
	ALL
    }

    @Column(unique = true, nullable = false)
    private String syncKey;

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = false, nullable = true)
    private String name;

    @Column(unique = true, nullable = false)
    private String handle;

    // TODO: Remove password entirely - Windows SSO + check username only...
    @Column(unique = false, nullable = false)
    private char[] password;

    @Column(unique = true, nullable = true)
    private String email;

    @Column(unique = false, nullable = false)
    private String primaryDeviceName;

    @Column(unique = false, nullable = false)
    private boolean isLogged;

    @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable()
    @Enumerated(EnumType.STRING)
    private Collection<Priviledge> priviledges;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    // ,orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Booking> bookings;

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

    public Collection<Priviledge> getPriviledges() {
	return priviledges;
    }

    public void addPriviledge(Priviledge priviledge) {
	if (getPriviledges() == null) {
	    setPriviledges(new ArrayList<Priviledge>());
	}

	getPriviledges().add(priviledge);
    }

    public void setPriviledges(Collection<Priviledge> priviledges) {
	this.priviledges = priviledges;
    }

    public Collection<Booking> getBookings() {
	return bookings;
    }

    public void setBookings(Collection<Booking> bookings) {
	this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
	booking.setPerformer(this);

	if (getBookings() == null) {
	    setBookings(new ArrayList<Booking>());
	}

	getBookings().add(booking);
    }

    @Override
    public String toString() {
	return handle + "[" + name + "]";
    }
}
