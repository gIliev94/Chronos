package bg.bc.tools.chronos.core.usecases.crud.project;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DProject;

interface IGetProject {

    DProject getProject(String name);

    List<DProject> getProjects();

    List<DProject> getProjects(DCustomer client);

    List<DProject> getProjects(DCategory category);
}
