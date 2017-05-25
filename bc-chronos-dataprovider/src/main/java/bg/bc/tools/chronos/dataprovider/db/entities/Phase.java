package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.Collection;

//@Entity(name = "Phase")
public class Phase extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long syncKey;

    // @OneToMany(mappedBy = "phase", orphanRemoval = true, cascade =
    // CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Task> tasks;

    public Collection<Task> getTasks() {
	return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
	this.tasks = tasks;
    }

    public void addTask(Task task) {
	// task.setPhase(this);
	getTasks().add(task);
    }
}
