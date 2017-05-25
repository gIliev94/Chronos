package bg.bc.tools.chronos.core.entities;

import java.util.List;

/**
 * Representation of the basic(core) entity - Client.
 * 
 * @author giliev
 */
public class DCustomer extends DCategoricalEntity {
    
    private List<DProject> projects;

    public List<DProject> getProjects() {
	return projects;
    }

    public void setProjects(List<DProject> projects) {
	this.projects = projects;
    }

    public void addProject(DProject project) {
	project.setCustomer(this);
	getProjects().add(project);
    }
}
