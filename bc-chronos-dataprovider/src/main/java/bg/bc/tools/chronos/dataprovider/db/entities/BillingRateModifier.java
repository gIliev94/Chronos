package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "BillingRateModifier")
public class BillingRateModifier extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum ModifierAction {
	ADD, // nl
	SUBTRACT, // nl
	MULTIPLY, // nl
	DIVIDE, // nl
	PERCENT
    }

    @Enumerated(EnumType.STRING)
    private ModifierAction modifierAction;

    @Column(unique = false, nullable = false)
    private double modifierValue;

    @ManyToOne(optional = false)
    // , cascade = CascadeType.ALL)
    private Booking booking;

    public ModifierAction getModifierAction() {
	return modifierAction;
    }

    public void setModifierAction(ModifierAction modifierAction) {
	this.modifierAction = modifierAction;
    }

    public double getModifierValue() {
	return modifierValue;
    }

    public void setModifierValue(double modifierValue) {
	this.modifierValue = modifierValue;
    }

    public Booking getBooking() {
	return booking;
    }

    // TODO:
    // https://github.com/SomMeri/org.meri.jpa.tutorial/blob/master/src/main/java/org/meri/jpa/relationships/entities/bestpractice/SafeTwitterAccount.java
    public void setBooking(Booking booking) {
	// prevent endless loop
	if (this.booking == null ? booking == null : this.booking.equals(booking))
	    return;
	// set new owner
	Booking currentBooking = this.booking;
	this.booking = booking;
	// remove from the old owner
	if (currentBooking != null)
	    currentBooking.removeBillingRateModifier(this);
	// set myself into new owner
	if (booking != null)
	    booking.addBillingRateModifier(this);
    }

    // TODO: Consider adding only unique/immutable fields
    @Override
    public boolean equals(Object other) {
	if (other == null) {
	    return false;
	}
	if (other == this) {
	    return true;
	}
	// TODO: getClass preferred vs instanceof, because this is concrete
	// class
	if (other.getClass() != getClass()) {
	    return false;
	}
	// // vs
	// if (!(other instanceof BillingRateModifier)) {
	// return false;
	// }

	final BillingRateModifier billingRateModifier = (BillingRateModifier) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(billingRateModifier.getBooking(), getBooking()) // nl
		.isEquals();
    }

    @Override
    public int hashCode() {
	// TODO: If ONLY generated PK (id) is used in equals() ensure hashCode()
	// returns consistent value trough all states of an Hibernate entity
	// life cycle (if there is a natural/business key used in conjunction
	// with the PK there is no need to do that)
	// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
	return new HashCodeBuilder() // nl
		.appendSuper(super.hashCode()) // nl
		.append(getBooking()) // nl
		.hashCode();
    }

    @Override
    public String toString() {
	return modifierAction + " :: [" + modifierValue + "]";
    }
}
