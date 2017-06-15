package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;

@Embeddable
public class PerformerPriviledge implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Enumerated(EnumType.STRING)
    private Priviledge priviledge;
    
    public Priviledge getPriviledge() {
	return priviledge;
    }
    
    public void setPriviledge(Priviledge priviledge) {
	this.priviledge = priviledge;
    }
}
