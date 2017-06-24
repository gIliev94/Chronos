package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CategoricalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String syncKey;

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String description;

    // TODO: Lazy eval not working properly / cascade also:
    // https://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
    // http://howtodoinjava.com/hibernate/hibernate-jpa-cascade-types/
    // https://dzone.com/articles/beginner%E2%80%99s-guide-jpa-and
    // http://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate
    @ManyToOne(optional = false)
    // , cascade = {CascadeType.REFRESH})
    // , fetch = FetchType.LAZY)
    private Category category;

    public CategoricalEntity() {
	super();
    }

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

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Category getCategory() {
	return category;
    }

    // TODO: Consistency method??
    // public void setCategory(Category category) {
    // this.category = category;
    // }

    public void setCategory(Category category) {
	setCategory(category, true);
    }

    void setCategory(Category category, boolean add) {
	this.category = category;
	if (category != null && add) {
	    category.addCategoricalEntity(this, false);
	}
    }
    //
}
