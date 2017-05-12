package bg.bc.tools.chronos.core.entities;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;
	if (!(obj instanceof DBillingRate)) {
	    return false;
	}

	DBillingRate billingRate = (DBillingRate) obj;
	return id == billingRate.id // nl
		&& Objects.equals(role, billingRate.role) // nl
		&& Objects.equals(task, billingRate.task);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, role, task.getName());
    }
}
