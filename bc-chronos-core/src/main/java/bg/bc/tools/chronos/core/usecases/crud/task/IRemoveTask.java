package bg.bc.tools.chronos.core.usecases.crud.task;

import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.core.entities.DTask.DTaskPhase;

interface IRemoveTask {

    boolean removeTask(DTask performer);

    boolean removeTask(String taskName);

    boolean removeTasksByProject(DProject project);

    boolean removeTasksByType(DTaskPhase type);
}
