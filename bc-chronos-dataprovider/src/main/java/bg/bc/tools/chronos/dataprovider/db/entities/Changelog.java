package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//TODO: Remove entirely - old design...
@Entity(name = "Changelog")
public class Changelog implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: How to increment - use generated value for now???
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(unique = true, nullable = false)
    private long updateCounter;

    @Column(unique = false, nullable = false)
    private String updatedEntityKey;

    @Column(unique = false, nullable = false)
    private String updatedEntityType;

    @Column(unique = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeTime;

    @Column(unique = false, nullable = false)
    private String deviceName;

    public long getUpdateCounter() {
	return updateCounter;
    }

    public void setUpdateCounter(long updateCounter) {
	this.updateCounter = updateCounter;
    }

    public String getUpdatedEntityKey() {
	return updatedEntityKey;
    }

    public void setUpdatedEntityKey(String updatedEntityKey) {
	this.updatedEntityKey = updatedEntityKey;
    }

    public String getUpdatedEntityType() {
	return updatedEntityType;
    }

    public void setUpdatedEntityType(String updatedEntityType) {
	this.updatedEntityType = updatedEntityType;
    }

    public Date getChangeTime() {
	return changeTime;
    }

    public void setChangeTime(Date changeTime) {
	this.changeTime = changeTime;
    }

    public String getDeviceName() {
	return deviceName;
    }

    public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
    }
}
