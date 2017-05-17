package bg.bc.tools.chronos.core.entities;

public class DRole extends DCategoricalEntity {

    private double billingRate;

    private DBooking booking;

    public double getBillingRate() {
	return billingRate;
    }

    public void setBillingRate(double billingRate) {
	this.billingRate = billingRate;
    }

    public DBooking getBooking() {
	return booking;
    }

    public void setBooking(DBooking booking) {
	this.booking = booking;
    }
}
