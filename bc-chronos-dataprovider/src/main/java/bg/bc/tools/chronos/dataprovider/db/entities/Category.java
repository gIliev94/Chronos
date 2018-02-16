package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "Category")
public class Category extends SynchronizableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private int sortOrder;

    // TODO: M2A attempt..
    // @ManyToAny(metaDef = "CategoricalEntityMetaDef", metaColumn =
    // @Column(name = "categoricalEntity_type"))
    // @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    // @JoinTable(name = "Category_CategoricalEntities", joinColumns =
    // @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name
    // = "categoricalEntity_id"))
    // private Set<ICategoricalEntity> categoricalEntities = new HashSet<>(0);

    // Consider adding CascadeType.REFRESH or maybe @Fetch(value =
    // FetchMode.SUBSELECT)
    // https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<CategoricalEntity> categoricalEntities = new HashSet<>(0);

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getSortOrder() {
	return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
	this.sortOrder = sortOrder;
    }

    // TODO: M2A attempt...
    // public Set<ICategoricalEntity> getCategoricalEntities() {
    // return categoricalEntities;
    // }
    //
    // public void setCategoricalEntities(Set<ICategoricalEntity>
    // categoricalEntities) {
    // this.categoricalEntities = categoricalEntities;
    // }

    public Set<CategoricalEntity> getCategoricalEntities() {
	return categoricalEntities;
    }

    public void setCategoricalEntities(Set<CategoricalEntity> categoricalEntities) {
	this.categoricalEntities = categoricalEntities;
    }

    // TODO: Consistency methods (keep bidirectional entities up-to-date)
    // (https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/)
    // always use with M2M and possibly M2O / O2M bidirectional...
    public void addCategoricalEntity(CategoricalEntity categoricalEntity) {
	categoricalEntities.add(categoricalEntity);
	categoricalEntity.getCategories().add(this);
    }

    public void removeCategoricalEntity(CategoricalEntity categoricalEntity) {
	categoricalEntities.remove(categoricalEntity);
	categoricalEntity.getCategories().remove(this);
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
	if (other.getClass() != getClass()) {
	    return false;
	}
	// // vs
	// if (!(other instanceof Category)) {
	// return false;
	// }

	final Category category = (Category) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(category.getName(), getName()) // nl
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
