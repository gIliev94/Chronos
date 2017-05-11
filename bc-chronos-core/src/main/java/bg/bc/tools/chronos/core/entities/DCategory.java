package bg.bc.tools.chronos.core.entities;

import java.util.Collection;

public class DCategory {

    // /**
    // * рндн: Describe...
    // *
    // * @author giliev
    // */
    // public enum CategoryContext {
    // CLIENT, // nl
    // PROJECT, // nl
    // TASK, // nl
    // PERFOMER, // nl
    // BOOKING
    // }

    private long id;

    private String name;

    private int sortOrder;

    private Collection<DCategoricalEntity> categoricalEntities;

    // private CategoryContext context;

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

    // public CategoryContext getContext() {
    // return context;
    // }
    //
    // public void setContext(CategoryContext context) {
    // this.context = context;
    // }

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
}
