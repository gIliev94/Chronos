package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Project")
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Project extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Client client;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Task> tasks;

    public Client getClient() {
	return client;
    }

    public void setClient(Client client) {
	this.client = client;
    }

    public Collection<Task> getTasks() {
	return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
	this.tasks = tasks;
    }

    public void addTask(Task task) {
	task.setProject(this);
	getTasks().add(task);
    }
}
