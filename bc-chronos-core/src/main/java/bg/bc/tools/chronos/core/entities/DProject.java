package bg.bc.tools.chronos.core.entities;

import java.util.List;

/**
 * Representation of the basic(core) entity - Project.
 * 
 * @author giliev
 */
public class DProject extends DCategoricalEntity {

    private DCustomer client;

    private List<DTask> tasks;

    public DCustomer getCustomer() {
	return client;
    }

    public void setCustomer(DCustomer customer) {
	this.client = customer;
    }

    public List<DTask> getTasks() {
	return tasks;
    }

    public void setTasks(List<DTask> tasks) {
	this.tasks = tasks;
    }

    public void addTask(DTask task) {
	task.setProject(this);
	getTasks().add(task);
    }
}
