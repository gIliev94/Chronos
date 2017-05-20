package bg.bc.tools.chronos.core.usecases.crud.project;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DProject;

interface IGetProject {

    DProject getProject(long id);

    DProject getProject(String name);

    List<DProject> getProjects();

    List<DProject> getProjects(DCustomer customer);

    List<DProject> getProjects(DCategory category);

    List<DProject> getProjects(List<DCategory> categories);

    List<DProject> getProjectsContaining(String name);
}
