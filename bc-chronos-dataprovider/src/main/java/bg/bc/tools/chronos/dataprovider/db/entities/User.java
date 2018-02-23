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

//User is a keyword so not possible naming...
@Entity(name = "AppUser")
public class User extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = false, nullable = true)
    private String firstName;

    @Column(unique = false, nullable = true)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String abbreviation;

    // TODO: Consider removing password entirely ( domain login / SSO )
    @Column(unique = false, nullable = false)
    private char[] password;

    @Column(unique = true, nullable = false)
    private String email;

    // TODO: Consider a similar attribute in case a mobile app is made later...
    // @Column(unique = false, nullable = false)
    // private boolean isLogged;
    //
    // public boolean isLogged() {
    // return isLogged;
    // }
    //
    // public void setLogged(boolean isLogged) {
    // this.isLogged = isLogged;
    // }

    // TODO: Maybe keep as unidirectional link...
    // @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL)
    // private Performer performer;
    //
    // public Performer getPerformer() {
    // return performer;
    // }
    //
    // public void setPerformer(Performer performer) {
    // this.performer = performer;
    // }

    // TODO: Consider inversing the ownership like category ->
    // categoricalEntity...
    // TODO: Consider specifying JoinTable annotation for better naming of
    // generated FK columns ...
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "AppUser_AppUserGroup", joinColumns = { @JoinColumn(name = "appUser_id") }, inverseJoinColumns = {
	    @JoinColumn(name = "appUserGroup_id") })
    private Set<UserGroup> userGroups = new HashSet<>(0);

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getAbbreviation() {
	return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
	this.abbreviation = abbreviation;
    }

    public char[] getPassword() {
	return password;
    }

    public void setPassword(char[] password) {
	this.password = password;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Set<UserGroup> getUserGroups() {
	return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
	this.userGroups = userGroups;
    }

    // TODO: Consistency methods (keep bidirectional entities up-to-date)
    // (https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/)
    // always use with M2M and possibly M2O / O2M bidirectional...
    public void addUserGroup(UserGroup userGroup) {
	userGroups.add(userGroup);
	userGroup.getUsers().add(this);
    }

    public void removeUserGroup(UserGroup userGroup) {
	userGroups.remove(userGroup);
	userGroup.getUsers().remove(this);
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

	final User user = (User) other;

	return new EqualsBuilder() // nl
		.appendSuper(super.equals(other)) // nl
		.append(user.getFirstName(), getFirstName()) // nl
		.append(user.getLastName(), getLastName()) // nl
		.append(user.getAbbreviation(), getAbbreviation()) // nl
		.append(user.getEmail(), getEmail()) // nl
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
		.append(getFirstName()) // nl
		.append(getLastName()) // nl
		.append(getAbbreviation()) // nl
		.append(getEmail()) // nl
		.hashCode();
    }

    @Override
    public String toString() {
	return abbreviation + " :: [" + email + "]";
    }
}
