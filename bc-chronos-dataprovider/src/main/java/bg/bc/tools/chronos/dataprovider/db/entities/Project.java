package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Project")
public class Project extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    // , fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    // , orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Task> tasks;

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

    public void addTask(Task task) {
	task.setProject(this);

	if (getTasks() == null) {
	    setTasks(new ArrayList<Task>());
	}

	getTasks().add(task);
    }
}
