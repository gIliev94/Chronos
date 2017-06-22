package bg.bc.tools.chronos.core.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DCategory extends DObject {

    private String syncKey;

    private long id;

    private String name;

    private int sortOrder;

    private Collection<DCategoricalEntity> categoricalEntities;

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

    public Collection<DCategoricalEntity> getCategoricalEntities() {
	return categoricalEntities = categoricalEntities != null ? categoricalEntities
		: new ArrayList<DCategoricalEntity>();
    }

    public void setCategoricalEntities(Collection<DCategoricalEntity> categoricalEntities) {
	this.categoricalEntities = categoricalEntities;
    }

    // public void addCategoricalEntity(DCategoricalEntity categoricalEntity) {
    // categoricalEntity.setCategory(this);
    //
    // if (getCategoricalEntities() == null) {
    // setCategoricalEntities(new ArrayList<DCategoricalEntity>());
    // }
    //
    // getCategoricalEntities().add(categoricalEntity);
    // }

    public void addCategoricalEntity(DCategoricalEntity categoricalEntity) {
	addCategoricalEntity(categoricalEntity, true);
    }

    void addCategoricalEntity(DCategoricalEntity categoricalEntity, boolean set) {
	if (categoricalEntity != null) {
	    if (getCategoricalEntities().contains(categoricalEntity)) {
		((List<DCategoricalEntity>) getCategoricalEntities()).set(
			((List<DCategoricalEntity>) getCategoricalEntities()).indexOf(categoricalEntity),
			categoricalEntity);
	    } else {
		getCategoricalEntities().add(categoricalEntity);
	    }
	    if (set) {
		categoricalEntity.setCategory(this, false);
	    }
	}
    }

    public void removeCategoricalEntity(DCategoricalEntity categoricalEntity) {
	getCategoricalEntities().remove(categoricalEntity);
	categoricalEntity.setCategory(null);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;
	if (!(obj instanceof DCategory)) {
	    return false;
	}

	DCategory category = (DCategory) obj;
	return id == category.id // nl
		&& Objects.equals(name, category.name) // nl
		&& sortOrder == category.sortOrder;
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, name, sortOrder);
    }

    @Override
    public String toString() {
	return name;
    }
}
