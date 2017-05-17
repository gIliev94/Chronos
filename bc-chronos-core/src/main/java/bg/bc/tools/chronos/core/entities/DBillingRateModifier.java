package bg.bc.tools.chronos.core.entities;

import java.util.Objects;

public class DBillingRateModifier {

    public enum DModifierAction {
	ADD, // nl
	SUBTRACT, // nl
	MULTIPLY, // nl
	DIVIDE
    }

    private long id;

    private DModifierAction modifierAction;

    private double modifierValue;

    private DBooking booking;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public DModifierAction getModifierAction() {
	return modifierAction;
    }

    public void setModifierAction(DModifierAction modifierAction) {
	this.modifierAction = modifierAction;
    }

    public double getModifierValue() {
	return modifierValue;
    }

    public void setModifierValue(double modifierValue) {
	this.modifierValue = modifierValue;
    }

    public DBooking getBooking() {
	return booking;
    }

    public void setBooking(DBooking booking) {
	this.booking = booking;
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;
	if (!(obj instanceof DBillingRateModifier)) {
	    return false;
	}

	DBillingRateModifier billingRateModifier = (DBillingRateModifier) obj;
	return id == billingRateModifier.id // nl
		&& Objects.equals(modifierAction, billingRateModifier.modifierAction) // nl
		&& Objects.equals(booking, billingRateModifier.booking);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, modifierAction.name(), booking.getId());
    }
}
