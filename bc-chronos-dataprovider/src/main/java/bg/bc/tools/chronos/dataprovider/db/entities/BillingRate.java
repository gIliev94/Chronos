package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import bg.bc.tools.chronos.dataprovider.db.entities.Performer.PerformerRole;

@Entity(name = "BillingRate")
public class BillingRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private PerformerRole role;

    @Column(unique = false, nullable = false)
    private double rate;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Task task;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public PerformerRole getRole() {
	return role;
    }

    public void setRole(PerformerRole role) {
	this.role = role;
    }

    public double getRate() {
	return rate;
    }

    public void setRate(double rate) {
	this.rate = rate;
    }

    public Task getTask() {
	return task;
    }

    public void setTask(Task task) {
	this.task = task;
    }
}
