package bg.bc.tools.chronos.core.entities;

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

}
