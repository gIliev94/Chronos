package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "Customer")
public class Customer extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    // , orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Project> projects;

    public Collection<Project> getProjects() {
	return projects;
    }

    public void addProject(Project project) {
	project.setCustomer(this);
	getProjects().add(project);
    }
}
