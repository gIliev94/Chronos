package bg.bc.tools.chronos.core.usecases.crud.performer;

import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DPerformer.DPerformerRole;

interface IRemovePerformer {

    boolean removePerformer(DPerformer performer);

    boolean removePerformer(String performerHandle);
    // boolean removePerformer(String performerHandle);

    boolean removePerformersByRole(DPerformerRole role);
}
