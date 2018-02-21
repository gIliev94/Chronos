package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class GenericEntity extends SynchronizableEntity implements Serializable {
   
    private static final long serialVersionUID = 1L;

    // TODO: Decide generation method - when using with TABLE-PER-CLASS
    // inheritance DO not use IDENTIY/AUTO:
    // http://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#d0e1168
    @Id
    // @GeneratedValue
    // @GeneratedValue(strategy= GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }
}
