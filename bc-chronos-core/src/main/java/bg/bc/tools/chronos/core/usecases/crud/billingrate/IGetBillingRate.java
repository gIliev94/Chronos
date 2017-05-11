package bg.bc.tools.chronos.core.usecases.crud.billingrate;

import bg.bc.tools.chronos.core.entities.DBillingRate;
import bg.bc.tools.chronos.core.entities.DTask;

interface IGetBillingRate {

    DBillingRate getBillingRate(long id);

    DBillingRate getBillingRate(DTask task);
}
