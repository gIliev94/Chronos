package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

    public enum PerformerRole {
	MANAGER, // nl
	DEVELOPER, // nl
	TESTER
    }

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = false, nullable = true)
    private String name;

    @Column(unique = true, nullable = false)
    private String handle;

    @Column(unique = false, nullable = false)
    private char[] password;

    @Column(unique = true, nullable = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private PerformerRole role;

    @Column(unique = false, nullable = false)
    private boolean isLogged;

    @OneToMany(mappedBy = "performer", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

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

    public PerformerRole getRole() {
	return role;
    }

    public void setRole(PerformerRole role) {
	this.role = role;
    }

    public boolean isLogged() {
	return isLogged;
    }

    public void setLogged(boolean isLogged) {
	this.isLogged = isLogged;
    }

    public List<Booking> getBookings() {
	return bookings;
    }

    public void setBookings(List<Booking> bookings) {
	this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
	booking.setPerformer(this);
	getBookings().add(booking);
    }
}
