package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "BillingRole")
public class BillingRole extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String description;

    @Column(unique = false, nullable = false)
    private double billingRate;

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

    public double getBillingRate() {
	return billingRate;
    }

    public void setBillingRate(double billingRate) {
	this.billingRate = billingRate;
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
	// if (!(other instanceof BillingRole)) {
	// return false;
	// }

	final BillingRole billingRole = (BillingRole) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(billingRole.getName(), getName()) // nl
		.append(billingRole.getDescription(), getDescription()) // nl
		.append(billingRole.getBillingRate(), getBillingRate()) // nl
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
		.append(getName()) // nl
		.append(getDescription()) // nl
		.append(getBillingRate()) // nl
		.hashCode();
    }

    @Override
    public String toString() {
	return name;
    }
}
