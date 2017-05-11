package bg.bc.tools.chronos.core.entities;

import bg.bc.tools.chronos.core.entities.DPerformer.DPerformerRole;

public class DBillingRate {

    private long id;

    private DPerformerRole role;

    private double rate;

    private DTask task;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public DPerformerRole getRole() {
	return role;
    }

    public void setRole(DPerformerRole role) {
	this.role = role;
    }

    public double getRate() {
	return rate;
    }

    public void setRate(double rate) {
	this.rate = rate;
    }

    public DTask getTask() {
	return task;
    }

    public void setTask(DTask task) {
	this.task = task;
    }
}
