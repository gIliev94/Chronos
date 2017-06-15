package bg.bc.tools.chronos.core.usecases.crud.performer;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DPerformer.DPriviledge;

interface IGetPerformer {

    DPerformer getPerformer(long id);

    DPerformer getPerformer(String handle);

    DPerformer getPerformerByEmail(String email);

    List<DPerformer> getPerformers();

    List<DPerformer> getPerformers(String name);

    List<DPerformer> getLoggedPerformers();
    
    List<DPerformer> getPerformers(DPriviledge priviledge);
    
    List<DPerformer> getPerformers(List<DPriviledge> priviledges);
}
