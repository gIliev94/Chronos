package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.core.entities.DTask.DTaskPhase;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ITaskService;

public class TaskService implements ITaskService {

    @Override
    public boolean addTask(DTask task) {
	return false;
    }

    @Override
    public DTask getTask(long id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DTask> getTasks(String name) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DTask> getTasks(DTaskPhase type) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DTask> getTasks(DProject project) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DTask> getTasks(DCategory category) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DTask> getOverdueTasks() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean updateTask(DTask task) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeTask(DTask performer) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeTask(String taskName) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeTasksByProject(DProject project) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeTasksByType(DTaskPhase type) {
	// TODO Auto-generated method stub
	return false;
    }

}
