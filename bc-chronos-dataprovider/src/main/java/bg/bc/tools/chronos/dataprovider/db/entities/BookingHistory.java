package bg.bc.tools.chronos.dataprovider.db.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "BookingHistory")
public class BookingHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO: Create repository for table...

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = false, nullable = true)
    private String description;

    @Column(unique = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(unique = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(unique = false, nullable = false)
    private double hoursSpent;

    @Column(unique = false, nullable = false)
    private String taskName;

    @Column(unique = false, nullable = false)
    private String performerName;

    @Column(unique = false, nullable = false)
    private String billingRoleName;

    @Column(unique = false, nullable = false)
    private double billingRoleRate;

    @Column(unique = false, nullable = true)
    private String billingRateModifiers;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Date getStartTime() {
	return startTime;
    }

    public void setStartTime(Date startTime) {
	this.startTime = startTime;
    }

    public Date getEndTime() {
	return endTime;
    }

    public void setEndTime(Date endTime) {
	this.endTime = endTime;
    }

    public double getHoursSpent() {
	return hoursSpent;
    }

    public void setHoursSpent(double hoursSpent) {
	this.hoursSpent = hoursSpent;
    }

    public String getTaskName() {
	return taskName;
    }

    public void setTaskName(String taskName) {
	this.taskName = taskName;
    }

    public String getPerformerName() {
	return performerName;
    }

    public void setPerformerName(String performerName) {
	this.performerName = performerName;
    }

    public String getBillingRoleName() {
	return billingRoleName;
    }

    public void setBillingRoleName(String billingRoleName) {
	this.billingRoleName = billingRoleName;
    }

    public double getBillingRoleRate() {
	return billingRoleRate;
    }

    public void setBillingRoleRate(double billingRoleRate) {
	this.billingRoleRate = billingRoleRate;
    }

    public String getBillingRateModifiers() {
	return billingRateModifiers;
    }

    public void setBillingRateModifiers(String billingRateModifiers) {
	this.billingRateModifiers = billingRateModifiers;
    }

}
