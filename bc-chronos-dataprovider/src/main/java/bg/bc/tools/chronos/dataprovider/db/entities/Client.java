package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity(name = "Client")
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Client extends CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Project> projects;

    public Collection<Project> getProjects() {
	return projects;
    }

    public void setProjects(Collection<Project> projects) {
	this.projects = projects;
    }

    public void addProject(Project project) {
	project.setClient(this);
	getProjects().add(project);
    }
}
