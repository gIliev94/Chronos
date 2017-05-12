package bg.bc.tools.chronos.core.usecases.crud.billingrate;

import java.util.Collection;

import bg.bc.tools.chronos.core.entities.DBillingRate;
import bg.bc.tools.chronos.core.entities.DPerformer.DPerformerRole;
import bg.bc.tools.chronos.core.entities.DTask;

interface IGetBillingRate {

    DBillingRate getBillingRate(long id);

    DBillingRate getBillingRate(DTask task, DPerformerRole role);

    Collection<DBillingRate> getBillingRates();

    Collection<DBillingRate> getBillingRates(DTask task);
}
