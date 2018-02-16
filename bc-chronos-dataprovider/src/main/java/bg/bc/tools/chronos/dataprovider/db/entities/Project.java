package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "Project")
public class Project extends CategoricalEntity
	// TODO: M2A attempt...
	// extends CategoricalEntityAlt
	implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: Consider carefully fetch / cascade types...
    @ManyToOne(optional = false)
    // , fetch = FetchType.LAZY)
    private Customer customer;

    // TODO: Consider switching to Set collection...
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    // , orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Task> tasks = new ArrayList<>(0);
    //

    public Customer getCustomer() {
	return customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public Collection<Task> getTasks() {
	return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
	this.tasks = tasks;
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
	// if (!(other instanceof Project)) {
	// return false;
	// }

	final Project project = (Project) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(project.getCustomer(), getCustomer()) // nl
		.isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder() // nl
		.appendSuper(super.hashCode()) // nl
		.append(getCustomer()) // nl
		.hashCode();
    }
}
