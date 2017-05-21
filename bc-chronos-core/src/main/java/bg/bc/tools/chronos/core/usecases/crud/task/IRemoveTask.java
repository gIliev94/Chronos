package bg.bc.tools.chronos.core.usecases.crud.task;

import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;

interface IRemoveTask {

    boolean removeTask(long id);

    boolean removeTask(DTask task);

    boolean removeTask(String name);

    boolean removeTasks(DProject project);
}
