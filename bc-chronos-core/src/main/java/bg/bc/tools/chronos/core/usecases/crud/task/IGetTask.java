package bg.bc.tools.chronos.core.usecases.crud.task;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;

interface IGetTask {

    DTask getTask(long id);

    List<DTask> getTasks(String name);

    List<DTask> getTasks(DProject project);

    List<DTask> getTasks(DCategory category);

    List<DTask> getOverdueTasks();
}
