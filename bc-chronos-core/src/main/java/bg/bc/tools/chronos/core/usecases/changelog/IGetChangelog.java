package bg.bc.tools.chronos.core.usecases.changelog;

import java.util.Date;
import java.util.List;

import bg.bc.tools.chronos.core.entities.DChangelog;

interface IGetChangelog {
    
    List<DChangelog> getChangelogsChangedBefore(Date changeTimeBefore);

    List<DChangelog> getChangelogsChangedAfter(Date changeTimeAfter);

    List<DChangelog> getChangelogsChangedBetween(Date changeTimeLower, Date changeTimeUpper);

    List<DChangelog> getChangelogsForUpdateCounterLessThan(double lessThanModifierValue);

    List<DChangelog> getChangelogsForUpdateCounterGreaterThan(double greaterThanModifierValue);

    List<DChangelog> getChangelogsForUpdateCounterBetween(double lessThanUpdateCounter, double greaterThanUpdateCounter);

    List<DChangelog> getChangelogsForDevice(String deviceName);
    
    List<DChangelog> getChangelogsForUpdatedEntity(String updatedEntityKey);
    
    DChangelog getLastChangelog();
}
