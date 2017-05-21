package bg.bc.tools.chronos.core.usecases.crud.project;

import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DProject;

interface IRemoveProject {
    
    boolean removeProject(long id);
    
    boolean removeProject(String name);

    boolean removeProject(DProject project);

    boolean removeProjects(DCustomer customer);
}
