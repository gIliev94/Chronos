package bg.bc.tools.chronos.core.usecases.crud.task;

import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;

interface IRemoveTask {

    boolean removeTask(DTask task);

    boolean removeTask(String taskName);

    boolean removeTasksByProject(DProject project);
}
