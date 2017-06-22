package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "Category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String syncKey;

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private int sortOrder;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    // ,orphanRemoval = true)
    private Collection<CategoricalEntity> categoricalEntities;

    public String getSyncKey() {
	return syncKey;
    }

    public void setSyncKey(String syncKey) {
	this.syncKey = syncKey;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

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

    public Collection<CategoricalEntity> getCategoricalEntities() {
	return categoricalEntities = categoricalEntities != null ? categoricalEntities
		: new ArrayList<CategoricalEntity>();
    }

    public void setCategoricalEntities(Collection<CategoricalEntity> categoricalEntities) {
	this.categoricalEntities = categoricalEntities;
    }

    // TODO: Consistency method??
    // public void addCategoricalEntity(CategoricalEntity categoricalEntity) {
    // categoricalEntity.setCategory(this);
    //
    // if (getCategoricalEntities() == null) {
    // setCategoricalEntities(new ArrayList<CategoricalEntity>());
    // }
    //
    // getCategoricalEntities().add(categoricalEntity);
    // }

    public void addCategoricalEntity(CategoricalEntity categoricalEntity) {
	addCategoricalEntity(categoricalEntity, true);
    }

    void addCategoricalEntity(CategoricalEntity categoricalEntity, boolean set) {
	if (categoricalEntity != null) {
	    if (getCategoricalEntities().contains(categoricalEntity)) {
		((List<CategoricalEntity>) getCategoricalEntities()).set(
			((List<CategoricalEntity>) getCategoricalEntities()).indexOf(categoricalEntity),
			categoricalEntity);
	    } else {
		getCategoricalEntities().add(categoricalEntity);
	    }
	    if (set) {
		categoricalEntity.setCategory(this, false);
	    }
	}
    }

    public void removeCategoricalEntity(CategoricalEntity categoricalEntity) {
	getCategoricalEntities().remove(categoricalEntity);
	categoricalEntity.setCategory(null);
    }
    //
}
