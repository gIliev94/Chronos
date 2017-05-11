package bg.bc.tools.chronos.core.usecases.crud.project;

import bg.bc.tools.chronos.core.entities.DClient;
import bg.bc.tools.chronos.core.entities.DProject;

interface IRemoveProject {

    boolean removeProject(DProject performer);

    boolean removeProject(String projectName);

    boolean removeProjectsByClient(DClient client);
}
