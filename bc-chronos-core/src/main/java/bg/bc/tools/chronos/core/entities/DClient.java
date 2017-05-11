package bg.bc.tools.chronos.core.entities;

import java.util.List;

/**
 * Representation of the basic(core) entity - Client.
 * 
 * @author giliev
 */
public class DClient extends DCategoricalEntity {

    // private long id;
    //
    // private String name;
    //
    // private String description;
    //
    // private DCategory category;

    private List<DProject> projects;

    // TODO: Add other relevant field/s
    //
    // public long getId() {
    // return id;
    // }
    //
    // public void setId(long id) {
    // this.id = id;
    // }
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

    public List<DProject> getProjects() {
	return projects;
    }

    public void setProjects(List<DProject> projects) {
	this.projects = projects;
    }

    public void addProject(DProject project) {
	project.setClient(this);
	getProjects().add(project);
    }
}
