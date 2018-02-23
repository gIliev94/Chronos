package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "Performer")
public class Performer extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: Consider cascade / fetch types...
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appUser_id")
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private BillingRole billingRole;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    // ,orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Booking> bookings = new HashSet<>(0);
    //

    public BillingRole getBillingRole() {
	return billingRole;
    }

    public void setBillingRole(BillingRole billingRole) {
	this.billingRole = billingRole;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Set<Booking> getBookings() {
	return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
	this.bookings = bookings;
    }

    // TODO:
    // https://github.com/SomMeri/org.meri.jpa.tutorial/blob/master/src/main/java/org/meri/jpa/relationships/entities/bestpractice/SafePerson.java
    public void addBooking(Booking booking) {
	// prevent endless loop
	if (getBookings().contains(booking))
	    return;
	// add new booking
	getBookings().add(booking);
	// set myself
	booking.setPerformer(this);
    }

    public void removeBooking(Booking booking) {
	// prevent endless loop
	if (!getBookings().contains(booking))
	    return;
	// remove the booking
	getBookings().remove(booking);
	// remove myself from the project
	booking.setPerformer(null);
    }

    // TODO: TEST...
    @Override
    public boolean equals(Object other) {
	if (other == null) {
	    return false;
	}
	if (other == this) {
	    return true;
	}
	if (other.getClass() != getClass()) {
	    return false;
	}
	// // vs
	// if (!(other instanceof Performer)) {
	// return false;
	// }

	final Performer performer = (Performer) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(performer.getBillingRole(), getBillingRole()) // nl
		.append(performer.getUser(), getUser()) // nlnl
		.isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder() // nl
		.appendSuper(super.hashCode()) // nl
		.append(getBillingRole()) // nl
		.append(getUser()) // nl
		.hashCode();
    }

    @Override
    public String toString() {
	return "[" + billingRole + "] :: " + user;
    }
}
