package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Privilege implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum UserPrivilege {
	READ, // nl
	WRITE, // nl
	MERGE, // nl
	DELETE, // nl
	FORCESYNC, // nl
	ALL
    }

    @Enumerated(EnumType.STRING)
    private UserPrivilege privilege;

    public UserPrivilege getPrivilege() {
	return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
	this.privilege = privilege;
    }

    @Override
    public String toString() {
	return privilege.toString();
    }
}
