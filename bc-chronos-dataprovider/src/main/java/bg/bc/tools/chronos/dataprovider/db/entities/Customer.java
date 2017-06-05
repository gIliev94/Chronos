package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "Customer")
public class Customer extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: Lazy eval not working properly...
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    // , orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Project> projects;

    public Collection<Project> getProjects() {
	return projects;
    }

    public void setProjects(Collection<Project> projects) {
	this.projects = projects;
    }

    public void addProject(Project project) {
	project.setCustomer(this);

	if (getProjects() == null) {
	    setProjects(new ArrayList<Project>());
	}

	getProjects().add(project);
    }
}
