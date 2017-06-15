package bg.bc.tools.chronos.core.entities;

import java.io.Serializable;
import java.time.LocalDateTime;


public class DChangelog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long updateCounter;

    private String updatedEntityKey;
    
    private LocalDateTime changeTime;
    
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

    public LocalDateTime getChangeTime() {
	return changeTime;
    }

    public void setChangeTime(LocalDateTime changeTime) {
	this.changeTime = changeTime;
    }
    
    public String getDeviceName() {
	return deviceName;
    }
    
    public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
    }
}
