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
    // @ManyToOne(optional = false, cascade = { CascadeType.PERSIST,
    // CascadeType.MERGE })
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

    // TODO:
    // https://github.com/SomMeri/org.meri.jpa.tutorial/blob/master/src/main/java/org/meri/jpa/relationships/entities/bestpractice/SafeTwitterAccount.java
    public void setCustomer(Customer customer) {
	this.customer = customer;

	// prevent endless loop
	if (this.customer == null ? customer == null : this.customer.equals(customer))
	    return;
	// set new owner
	Customer currentCustomer = this.customer;
	this.customer = customer;
	// remove from the old owner
	if (currentCustomer != null)
	    currentCustomer.removeProject(this);
	// set myself into new owner
	if (customer != null)
	    customer.addProject(this);
    }

    public Collection<Task> getTasks() {
	return tasks;
    }
    
    public void setTasks(Collection<Task> tasks) {
	this.tasks = tasks;
    }

    // TODO:
    // https://github.com/SomMeri/org.meri.jpa.tutorial/blob/master/src/main/java/org/meri/jpa/relationships/entities/bestpractice/SafePerson.java
    public void addTask(Task task) {
	// prevent endless loop
	if (getTasks().contains(task))
	    return;
	// add new task
	getTasks().add(task);
	// set myself
	task.setProject(this);
    }

    public void removeTask(Task task) {
	// prevent endless loop
	if (!getTasks().contains(task))
	    return;
	// remove the task
	getTasks().remove(task);
	// remove myself from the project
	task.setProject(null);
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
