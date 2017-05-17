package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity(name = "Role")
public class Role extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = false, nullable = false)
    private double billingRate;

    @OneToOne(mappedBy = "role", optional = false, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Booking booking;

    public double getBillingRate() {
	return billingRate;
    }

    public void setBillingRate(double billingRate) {
	this.billingRate = billingRate;
    }

    public Booking getBooking() {
	return booking;
    }

    public void setBooking(Booking booking) {
	this.booking = booking;
    }
}
