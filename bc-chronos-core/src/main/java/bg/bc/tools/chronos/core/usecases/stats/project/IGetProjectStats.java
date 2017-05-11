package bg.bc.tools.chronos.core.usecases.stats.project;

import java.time.LocalTime;

import bg.bc.tools.chronos.core.usecases.stats.IGetStats;

public interface IGetProjectStats extends IGetStats {

    int getNumberOfTasks();

    int getNumberOfOverdueTasks();

    int getNumberOfOvertimeTasks();

    LocalTime getAvgTimeSpentPerTask();

    LocalTime getAvgOverdueTimeSpentPerTask();

    Double getAvgBillPerTask();
}
