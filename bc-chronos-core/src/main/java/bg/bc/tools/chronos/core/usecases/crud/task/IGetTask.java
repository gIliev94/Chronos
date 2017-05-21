package bg.bc.tools.chronos.core.usecases.crud.task;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;

interface IGetTask {

    DTask getTask(long id);

    DTask getTask(String name);

    List<DTask> getTasks();

    List<DTask> getTasks(DCategory category);

    List<DTask> getTasks(List<DCategory> categories);

    List<DTask> getTasks(DProject project);

    List<DTask> getTasksContaining(String name);

    List<DTask> getTasksEstimatedBetween(int hoursEstimatedLower, int hoursEstimatedUpper);

    List<DTask> getTasksEstimatedLessThan(int hoursEstimatedLessThan);

    List<DTask> getTasksEstimatedGreaterThan(int hoursEstimatedGreaterThan);
}
