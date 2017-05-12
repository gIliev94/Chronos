package bg.bc.tools.chronos.core.entities;

import java.util.Collection;
import java.util.Objects;

public class DCategory {

    private long id;

    private String name;

    private int sortOrder;

    private Collection<DCategoricalEntity> categoricalEntities;

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
	return categoricalEntities;
    }

    public void setCategoricalEntities(Collection<DCategoricalEntity> categoricalEntities) {
	this.categoricalEntities = categoricalEntities;
    }

    public void addCategoricalEntity(DCategoricalEntity categoricalEntity) {
	categoricalEntity.setCategory(this);
	getCategoricalEntities().add(categoricalEntity);
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
}
