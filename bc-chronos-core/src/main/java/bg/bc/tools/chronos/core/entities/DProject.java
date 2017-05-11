package bg.bc.tools.chronos.core.entities;

import java.util.List;

/**
 * Representation of the basic(core) entity - Project.
 * 
 * @author giliev
 */
public class DProject extends DCategoricalEntity {

    // private String name;
    //
    // private String description;
    //
    // private DCategory category;

    private DClient client;

    private List<DTask> tasks;

    // TODO: Add other relevant field/s
    //
    // public String getName() {
    // return name;
    // }
    //
    // public void setName(String name) {
    // this.name = name;
    // }
    //
    // public String getDescription() {
    // return description;
    // }
    //
    // public void setDescription(String description) {
    // this.description = description;
    // }
    //
    // public DCategory getCategory() {
    // return category;
    // }
    //
    // public void setCategory(DCategory category) {
    // this.category = category;
    // }

    public DClient getClient() {
	return client;
    }

    public void setClient(DClient client) {
	this.client = client;
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
