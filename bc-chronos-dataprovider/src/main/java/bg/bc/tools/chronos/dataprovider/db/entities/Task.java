package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "Task")
public class Task extends CategoricalEntity
	// TODO: M2A attempt...
	// extends CategoricalEntityAlt
	implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = false, nullable = false)
    private long hoursEstimated;

    // TODO: Consider carefully fetch / cascade types...
    @ManyToOne(optional = false)
    // , cascade = CascadeType.ALL)
    // , fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    // ,orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Booking> bookings = new ArrayList<>(0);
    //

    public long getHoursEstimated() {
	return hoursEstimated;
    }

    public void setHoursEstimated(long hoursEstimated) {
	this.hoursEstimated = hoursEstimated;
    }

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public void setBookings(Collection<Booking> bookings) {
	this.bookings = bookings;
    }

    public Collection<Booking> getBookings() {
	return bookings;
    }

    // TODO: TEST...
    @Override
    public boolean equals(Object other) {
	if (other == null) {
	    return false;
	}
	if (other == this) {
	    return true;
	}
	if (other.getClass() != getClass()) {
	    return false;
	}
	// // vs
	// if (!(other instanceof Task)) {
	// return false;
	// }

	final Task task = (Task) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(task.getProject(), getProject()) // nl
		.isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder() // nl
		.appendSuper(super.hashCode()) // nl
		.append(getProject()) // nl
		.hashCode();
    }
}
