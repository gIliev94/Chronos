package bg.bc.tools.chronos.core.usecases.crud.billingrate;

import bg.bc.tools.chronos.core.entities.DBillingRate;

interface IUpdateBillingRate {

    boolean updateBillingRate(DBillingRate billingRate);
}
