package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "Customer")
public class Customer extends CategoricalEntity
	// TODO: M2A attempt...
	// extends CategoricalEntityAlt
	implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: Consider carefully fetch / cascade types...
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    // , orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Project> projects = new ArrayList<>(0);
    //

    public Collection<Project> getProjects() {
	return projects;
    }

    public void setProjects(Collection<Project> projects) {
	this.projects = projects;
    }

    // TODO:
    // https://github.com/SomMeri/org.meri.jpa.tutorial/blob/master/src/main/java/org/meri/jpa/relationships/entities/bestpractice/SafePerson.java
    public void addProject(Project project) {
	// prevent endless loop
	if (getProjects().contains(project))
	    return;
	// add new project
	getProjects().add(project);
	// set myself
	project.setCustomer(this);
    }

    public void removeProject(Project project) {
	// prevent endless loop
	if (!getProjects().contains(project))
	    return;
	// remove the project
	getProjects().remove(project);
	// remove myself from the project
	project.setCustomer(null);
    }
}
