package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "AppUserGroup")
public class UserGroup extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String description;

    // TODO: Consider fetch / cascade...
    @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable()
    @Enumerated(EnumType.STRING)
    private Collection<Privilege> privileges = new ArrayList<>(0);

    @ManyToMany(mappedBy = "userGroups", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<User> users = new HashSet<>(0);
    //

    public Collection<Privilege> getPrivileges() {
	return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
	this.privileges = privileges;
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

    public Set<User> getUsers() {
	return users;
    }

    public void setUsers(Set<User> users) {
	this.users = users;
    }

    // TODO: Consistency methods (keep bidirectional entities up-to-date)
    // (https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/)
    // always use with M2M and possibly M2O / O2M bidirectional...
    public void addUser(User user) {
	users.add(user);
	user.getUserGroups().add(this);
    }

    public void removeUser(User user) {
	users.remove(user);
	user.getUserGroups().remove(this);
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
	// if (!(other instanceof User)) {
	// return false;
	// }

	final UserGroup userGroup = (UserGroup) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(userGroup.getName(), getName()) // nl
		.append(userGroup.getDescription(), getDescription()) // nl
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
		.append(getDescription()) // nl
		.hashCode();
    }

    @Override
    public String toString() {
	return name;
    }
}
