package bg.bc.tools.chronos.core.usecases.crud.performer;

import bg.bc.tools.chronos.core.entities.DPerformer;

interface IRemovePerformer {

    boolean removePerformer(long id);

    boolean removePerformer(String handle);

    boolean removePerformerByEmail(String email);

    boolean removePerformer(DPerformer performer);
}
