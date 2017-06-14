package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Changelog")
public class Changelog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false)
    private long updateCounter;

    @Column(unique = false, nullable = false)
    private String updatedEntityKey;

    public long getUpdateCounter() {
	return updateCounter;
    }

    public void incrementUpdateCounter() {
	updateCounter++;
    }

    public String getUpdatedEntityKey() {
	return updatedEntityKey;
    }

    public void setUpdatedEntityKey(String updatedEntityKey) {
	this.updatedEntityKey = updatedEntityKey;
    }
}
