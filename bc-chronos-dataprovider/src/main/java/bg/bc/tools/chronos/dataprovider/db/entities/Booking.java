package bg.bc.tools.chronos.dataprovider.db.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = false, nullable = true)
    private String description;

    @Column(unique = false, nullable = true)
    private LocalDateTime startTime;

    @Column(unique = false, nullable = true)
    private LocalDateTime endTime;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Performer performer;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Task task;

    @Column(unique = false, nullable = false)
    private boolean isOvertime;

    @Column(unique = false, nullable = false)
    private boolean isEffectivelyStopped;

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

    public LocalDateTime getStartTime() {
	return startTime;
    }

    public void setStartTime(LocalDateTime start) {
	this.startTime = start;
    }

    public LocalDateTime getEndTime() {
	return endTime;
    }

    public void setEndTime(LocalDateTime end) {
	this.endTime = end;
    }

    public Performer getPerformer() {
	return performer;
    }

    public void setPerformer(Performer performer) {
	this.performer = performer;
    }

    public Task getTask() {
	return task;
    }

    public void setTask(Task task) {
	this.task = task;
    }

    public boolean isOvertime() {
	return isOvertime;
    }

    public void setOvertime(boolean isOvertime) {
	this.isOvertime = isOvertime;
    }

    public boolean isEffectivelyStopped() {
	return isEffectivelyStopped;
    }

    public void setEffectivelyStopped(boolean isEffectivelyStopped) {
	this.isEffectivelyStopped = isEffectivelyStopped;
    }
}
