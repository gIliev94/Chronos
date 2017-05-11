package bg.bc.tools.chronos.core.usecases.stats.client;

import java.time.LocalTime;

import bg.bc.tools.chronos.core.usecases.stats.IGetStats;

public interface IGetClientStats extends IGetStats {

    int getNumberOfProjects();

    LocalTime getAvgTimeSpentPerProject();

    Double getAvgBillPerProject();
}
