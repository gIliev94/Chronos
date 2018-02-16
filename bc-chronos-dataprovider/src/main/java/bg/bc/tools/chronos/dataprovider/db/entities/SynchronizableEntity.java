package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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

    // TODO: Decide generation method - when using with TABLE-PER-CLASS
    // inheritance DO not use IDENTIY/AUTO:
    // http://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#d0e1168
    @Id
    // @GeneratedValue
    // @GeneratedValue(strategy= GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = false, nullable = false)
    // TODO: Generators work only in conjunction with @Id - find a workaround
    // for non-primary columns.
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // @Generated(GenerationTime.ALWAYS)
    private long updateCounter;

    @Column(unique = false, nullable = false)
    private long syncCounter;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getUpdateCounter() {
	return updateCounter;
    }

    public void setUpdateCounter(long updateCounter) {
	this.updateCounter = updateCounter;
    }

    public long getSyncCounter() {
	return syncCounter;
    }

    public void setSyncCounter(long syncCounter) {
	this.syncCounter = syncCounter;
    }
}
