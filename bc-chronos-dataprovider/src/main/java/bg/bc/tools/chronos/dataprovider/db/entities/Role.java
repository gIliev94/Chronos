package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "Role")
public class Role implements Serializable {
    // extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO:
    @Column(unique = true, nullable = false)
    private String syncKey;

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String description;
    //

    @Column(unique = false, nullable = false)
    private double billingRate;

    @OneToOne(mappedBy = "role", optional = true)
    // , cascade = CascadeType.ALL)
    // , orphanRemoval = true,fetch = FetchType.LAZY)
    private Booking booking;

    // TODO:
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

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }
    //

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

    @Override
    public String toString() {
	return this.name;
    }
}
