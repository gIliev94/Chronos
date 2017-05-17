package bg.bc.tools.chronos.core.usecases.crud.billingratemodifier;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;

interface IAddBillingRateModifier {

    boolean addBillingRateModifier(DBillingRateModifier billingRateModifier);
}
