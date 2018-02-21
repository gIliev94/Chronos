package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

//// ALT 1:
@Entity
//// OPT 1:
//// @Inheritance(strategy = InheritanceType.JOINED)
//// OPT 2:
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//// OPT 3:
//// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)

// ALT 2:
// @MappedSuperclass
public abstract class CategoricalEntity extends SynchronizableEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // TODO: Decide generation method - when using with TABLE-PER-CLASS
    // inheritance DO not use IDENTIY/AUTO:
    // http://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#d0e1168
    @Id
    // @GeneratedValue
    // @GeneratedValue(strategy= GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String description;

    // TODO: Lazy eval not working properly / cascade also:
    // //
    // https://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
    // // http://howtodoinjava.com/hibernate/hibernate-jpa-cascade-types/
    // // https://dzone.com/articles/beginner%E2%80%99s-guide-jpa-and
    // //
    // http://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate
    // @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
    // CascadeType.MERGE })
    // // @JoinTable(name = "CatergoricalEntity_Category", joinColumns = {
    // // @JoinColumn(name = "catergoricalEntity_id") }, inverseJoinColumns = {
    // // @JoinColumn(name = "category_id") })

    @ManyToMany(mappedBy = "categoricalEntities", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
	    CascadeType.MERGE })
    private Set<Category> categories = new HashSet<>(0);

    // @ManyToMany(mappedBy = "categoricalEntities", fetch = FetchType.EAGER,
    // cascade = { CascadeType.PERSIST,
    // CascadeType.MERGE })
    public Set<Category> getCategories() {
	return categories;
    }

    public void setCategories(Set<Category> categories) {
	this.categories = categories;
    }

    public CategoricalEntity() {
	super();
    }

    // private long id;
    // @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // public long getId() {
    // return id;
    // }
    //
    // public void setId(long id) {
    // this.id = id;
    // }

    // @Column(unique = true, nullable = false)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    // @Column(unique = false, nullable = true)
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    // TODO: Consistency methods (keep bidirectional entities up-to-date)
    // (https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/)
    // always use with M2M and possibly M2O / O2M bidirectional...
    public void addCategory(Category category) {
	categories.add(category);
	category.getCategoricalEntities().add(this);
    }

    public void removeCategory(Category category) {
	categories.remove(category);
	category.getCategoricalEntities().remove(this);
    }

    // TODO: TEST...
    @Override
    public boolean equals(Object other) {
	if (other == null) {
	    return false;
	}
	if (other == this) {
	    return true;
	}
	// if (other.getClass() != getClass()) {
	// return false;
	// }
	// vs
	if (!(other instanceof CategoricalEntity)) {
	    return false;
	}

	final CategoricalEntity categoricalEntity = (CategoricalEntity) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(categoricalEntity.getName(), getName()) // nl
		.isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder() // nl
		.appendSuper(super.hashCode()) // nl
		.append(getName()) // nl
		.hashCode();
    }

    @Override
    public String toString() {
	return name;
    }
}
