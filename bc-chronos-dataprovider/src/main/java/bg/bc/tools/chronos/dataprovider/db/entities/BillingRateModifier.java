package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

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

    public void setBooking(Booking booking) {
	this.booking = booking;
    }

    @Override
    public String toString() {
	return modifierAction + " :: [" + modifierValue + "]";
    }
}
