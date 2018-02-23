package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

//ALT 1:
//@Entity
// OPT 1:
// @Inheritance(strategy = InheritanceType.JOINED)
// OPT 2:
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// OPT 3:
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)

//// ALT 2:
@MappedSuperclass
public abstract class SynchronizableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final long NEW_ENTITY_UPDATE_COUNT = 1;

    public static final long NEW_ENTITY_SYNC_COUNT = 0;

    // // TODO: Decide generation method - when using with TABLE-PER-CLASS
    // // inheritance DO not use IDENTIY/AUTO:
    // //
    // http://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#d0e1168
    // @Id
    // // @GeneratedValue
    // // @GeneratedValue(strategy= GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // private long id;

    @Column(unique = false, nullable = false)
    // TODO: Generators work only in conjunction with @Id - find a workaround
    // for non-primary columns.
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // @Generated(GenerationTime.ALWAYS)
    private long updateCounter = NEW_ENTITY_UPDATE_COUNT;

    @Column(unique = false, nullable = false)
    private long syncCounter = NEW_ENTITY_SYNC_COUNT;

    // // @Id
    // // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // public long getId() {
    // return id;
    // }
    //
    // public void setId(long id) {
    // this.id = id;
    // }

    // @Column(unique = false, nullable = false)
    public long getUpdateCounter() {
	return updateCounter;
    }

    public void setUpdateCounter(long updateCounter) {
	this.updateCounter = updateCounter;
    }

    // @Column(unique = false, nullable = false)
    public long getSyncCounter() {
	return syncCounter;
    }

    public void setSyncCounter(long syncCounter) {
	this.syncCounter = syncCounter;
    }

    // TODO: Consider adding methods that operate on counters (is... style
    // checks) here...
    // @Transient
    public final void markUpdated() {
	updateCounter++;
    }

    // @Transient
    public final void markSynchronized() {
	syncCounter = updateCounter;
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
	// class
	// if (other.getClass() != getClass()) {
	// return false;
	// }
	// vs
	if (!(other instanceof SynchronizableEntity)) {
	    return false;
	}

	final SynchronizableEntity synchronizableEntity = (SynchronizableEntity) other;

	return new EqualsBuilder() // nl
		// TODO: Append super skipped due to being the one from
		// Object...
		// .appendSuper(super.equals(other)) // nl
		.append(synchronizableEntity.getUpdateCounter(), getUpdateCounter()) // nl
		.append(synchronizableEntity.getSyncCounter(), getSyncCounter()) // nl
		.isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder() // nl
		// TODO: Append super skipped due to being the one from
		// Object...
		// .appendSuper(super.hashCode()) // nl
		.append(getUpdateCounter()) // nl
		.append(getSyncCounter()) // nl
		.hashCode();
    }
}
