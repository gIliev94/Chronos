package bg.bc.tools.chronos.core.entities;

import java.util.Objects;

public abstract class DCategoricalEntity {

    private long id;

    private String name;

    private String description;

    private DCategory category;

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

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public DCategory getCategory() {
	return category;
    }

    public void setCategory(DCategory category) {
	this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;
	if (!(obj instanceof DCategoricalEntity)) {
	    return false;
	}

	DCategoricalEntity categoricalEntity = (DCategoricalEntity) obj;
	return id == categoricalEntity.id // nl
		&& Objects.equals(name, categoricalEntity.name) // nl
		&& Objects.equals(category.getName(), categoricalEntity.category.getName());
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, name, category.getName());
    }
}
