package bg.bc.tools.chronos.core.usecases.crud.task;

import bg.bc.tools.chronos.core.entities.DTask;

interface IAddTask {

    boolean addTask(DTask task);
}
