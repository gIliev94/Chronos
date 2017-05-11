package bg.bc.tools.chronos.core.usecases.crud.project;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DClient;
import bg.bc.tools.chronos.core.entities.DProject;

interface IGetProject {

    DProject getProject(String name);

    List<DProject> getProjects();

    List<DProject> getProjects(DClient client);

    List<DProject> getProjects(DCategory category);
}
