package bg.bc.tools.chronos.core.usecases.stats.task;

import java.time.LocalTime;

import bg.bc.tools.chronos.core.usecases.stats.IGetStats;

public interface IGetTaskStats extends IGetStats {

    int getNumberOfBookings();

    LocalTime getAvgTimeSpentPerBooking();

    Double getAvgBillPerBooking();
}
