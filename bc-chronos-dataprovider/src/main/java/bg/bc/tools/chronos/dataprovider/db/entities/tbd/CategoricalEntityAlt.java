package bg.bc.tools.chronos.dataprovider.db.entities.tbd;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import bg.bc.tools.chronos.dataprovider.db.entities.SynchronizableEntity;

//// ALT 1:
//@Entity
//// OPT 1:
//// @Inheritance(strategy = InheritanceType.JOINED)
//// OPT 2:
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//// OPT 3:
//// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)

//// ALT 2:
@MappedSuperclass
public abstract class CategoricalEntityAlt extends SynchronizableEntity implements Serializable, ICategoricalEntity {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String description;
    
    public CategoricalEntityAlt() {
	super();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public String toString() {
	return name;
    }
}
