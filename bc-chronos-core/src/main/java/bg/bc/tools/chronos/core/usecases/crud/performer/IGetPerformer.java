package bg.bc.tools.chronos.core.usecases.crud.performer;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DPerformer;

interface IGetPerformer {

    DPerformer getPerformer(String handle);

    List<DPerformer> getPerformers();

    List<DPerformer> getPerformers(String name);
}
