package bg.bc.tools.chronos.core.usecases.crud.task;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;
import bg.bc.tools.chronos.core.entities.DTask.DTaskPhase;

interface IGetTask {

    DTask getTask(long id);

    List<DTask> getTasks(String name);

    List<DTask> getTasks(DTaskPhase type);

    List<DTask> getTasks(DProject project);

    List<DTask> getTasks(DCategory category);

    List<DTask> getOverdueTasks();
}
