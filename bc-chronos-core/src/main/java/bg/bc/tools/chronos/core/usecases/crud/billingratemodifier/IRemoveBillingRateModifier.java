package bg.bc.tools.chronos.core.usecases.crud.billingratemodifier;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBooking;

interface IRemoveBillingRateModifier {

    boolean removeBillingRateModifier(long id);

    boolean removeBillingRateModifier(DBillingRateModifier billingRateModifier);

    boolean removeBillingRateModifiers(DBooking booking);

    // boolean removeBillingRateModifiers(DRole role);
    //
    // boolean removeBillingRateModifiers(DPerformer performer);
    //
    // boolean removeBillingRateModifiers(DTask task);

}
