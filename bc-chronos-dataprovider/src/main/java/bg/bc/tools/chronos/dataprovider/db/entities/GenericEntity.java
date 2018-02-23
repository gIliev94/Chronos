package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;

@MappedSuperclass
public class GenericEntity extends SynchronizableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: Decide generation method - when using with TABLE-PER-CLASS
    // inheritance DO not use IDENTIY/AUTO:
    // http://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#d0e1168
    @Id
    // @GeneratedValue
    // @GeneratedValue(strategy= GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
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
	// TODO: instanceof preferred vs getClass, because this is abstract
	// if (other.getClass() != getClass()) {
	// return false;
	// }
	// vs
	if (!(other instanceof GenericEntity)) {
	    return false;
	}

	final GenericEntity genericEntity = (GenericEntity) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(genericEntity.getId(), getId()) // nl
		.isEquals();
    }

    @Override
    public int hashCode() {
	// TODO: If ONLY generated PK (id) is used in equals() ensure hashCode()
	// returns consistent value trough all states of an Hibernate entity
	// life cycle
	// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
	return 31;
	// return new HashCodeBuilder() // nl
	// // .appendSuper(super.hashCode()) // nl
	// .append(getId()) // nl
	// .hashCode();
    }
}
