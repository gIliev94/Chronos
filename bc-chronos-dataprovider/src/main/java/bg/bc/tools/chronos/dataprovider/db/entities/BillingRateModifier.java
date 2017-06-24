package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "BillingRateModifier")
public class BillingRateModifier implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum ModifierAction {
	ADD, // nl
	SUBTRACT, // nl
	MULTIPLY, // nl
	DIVIDE, // nl
	PERCENT
    }

    @Column(unique = true, nullable = false)
    private String syncKey;

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private ModifierAction modifierAction;

    @Column(unique = false, nullable = false)
    private double modifierValue;

    @ManyToOne(optional = false)
    // , cascade = CascadeType.ALL)
    private Booking booking;

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
}
