package bg.bc.tools.chronos.core.usecases.crud.billingrate;

import bg.bc.tools.chronos.core.entities.DBillingRate;
import bg.bc.tools.chronos.core.entities.DTask;

interface IRemoveBillingScale {

    boolean removeBillingRate(long id);

    boolean removeBillingRate(DBillingRate billingRate);

    boolean removeBillingRate(DTask task);
}
