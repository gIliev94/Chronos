package bg.bc.tools.chronos.core.usecases.crud.performer;

import bg.bc.tools.chronos.core.entities.DPerformer;

interface IRemovePerformer {

    boolean removePerformer(DPerformer performer);

    boolean removePerformer(String performerHandle);
}
