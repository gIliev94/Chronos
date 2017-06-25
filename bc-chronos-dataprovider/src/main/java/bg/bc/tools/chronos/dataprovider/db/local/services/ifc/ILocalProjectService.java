package bg.bc.tools.chronos.dataprovider.db.local.services.ifc;

import bg.bc.tools.chronos.core.entities.DProject;
import bg.bc.tools.chronos.core.usecases.crud.project.IProjectCrud;

public interface ILocalProjectService extends IProjectCrud {

    DProject fetchReferencedEntities(DProject project);

}
