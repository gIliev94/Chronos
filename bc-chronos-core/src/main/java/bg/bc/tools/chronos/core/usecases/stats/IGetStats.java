package bg.bc.tools.chronos.core.usecases.stats;

import java.time.LocalTime;
import java.util.List;

import bg.bc.tools.chronos.core.entities.DPerformer;

public interface IGetStats {

    List<DPerformer> getPerfomers();

    LocalTime getTotalTimeSpent();
}
