package bg.bc.tools.chronos.core.usecases.actions;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DClient;
import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.entities.DTask;

public interface IAssignCategory {

    DCategory assignCategory(DCategory category, DClient client);

    DCategory assignCategory(DCategory category, DProject project);

    DCategory assignCategory(DCategory category, DTask task);
}
