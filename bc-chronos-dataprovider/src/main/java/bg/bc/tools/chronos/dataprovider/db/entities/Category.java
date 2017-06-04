package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
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

    public void addCategoricalEntity(CategoricalEntity categoricalEntity) {
	categoricalEntity.setCategory(this);
	getCategoricalEntities().add(categoricalEntity);
    }
}
