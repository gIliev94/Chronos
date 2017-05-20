package bg.bc.tools.chronos.core.usecases.stats.customer;

import java.time.LocalTime;

import bg.bc.tools.chronos.core.usecases.stats.IGetStats;

public interface IGetCustomerStats extends IGetStats {

    int getNumberOfProjects();

    LocalTime getAvgTimeSpentPerProject();

    Double getAvgBillPerProject();
}
