package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "Category")
public class Category extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: Alphabetized or add time stamp column to enforce sorting by order
    // of creation...
    /**
     * Constant for special sort order => PREPEND such Categories to the START
     * of the displayed list.
     */
    public static final int SORT_ORDER_UNSORTED_PREPENDED = -1;

    /**
     * Constant for special sort order => APPEND such Categories to the END of
     * the displayed list.
     */
    public static final int SORT_ORDER_UNSORTED_APPENDED = 0;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private int sortOrder = SORT_ORDER_UNSORTED_APPENDED;

    // TODO: Consider adding description field...

    // TODO: M2A attempt..
    // @ManyToAny(metaDef = "CategoricalEntityMetaDef", metaColumn =
    // @Column(name = "categoricalEntity_type"))
    // @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    // @JoinTable(name = "Category_CategoricalEntities", joinColumns =
    // @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name
    // = "categoricalEntity_id"))
    // private Set<ICategoricalEntity> categoricalEntities = new HashSet<>(0);

    // // Consider adding CascadeType.REFRESH or maybe @Fetch(value =
    // // FetchMode.SUBSELECT)
    // //
    // https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
    // @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER, cascade = {
    // CascadeType.PERSIST, CascadeType.MERGE })

    // TODO: Reverese the ownership of the M2M relationship for AppUser /
    // AppUserGroup
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "Category_CategoricalEntity", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "categoricalEntity_id"))
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
	final boolean add = categoricalEntities.add(categoricalEntity);
	final boolean add2 = categoricalEntity.getCategories().add(this);
	System.out.println(add + " " + add2);
    }

    public void removeCategoricalEntity(CategoricalEntity categoricalEntity) {
	final boolean remove = categoricalEntities.remove(categoricalEntity);
	final boolean remove2 = categoricalEntity.getCategories().remove(this);
	System.out.println(remove + " " + remove2);
    }

    // TODO: Consider adding only unique/immutable fields
    @Override
    public boolean equals(Object other) {
	if (other == null) {
	    return false;
	}
	if (other == this) {
	    return true;
	}
	// TODO: getClass preferred vs instanceof, because this is concrete
	// class
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
	// TODO: If ONLY generated PK (id) is used in equals() ensure hashCode()
	// returns consistent value trough all states of an Hibernate entity
	// life cycle (if there is a natural/business key used in conjunction
	// with the PK there is no need to do that)
	// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
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
