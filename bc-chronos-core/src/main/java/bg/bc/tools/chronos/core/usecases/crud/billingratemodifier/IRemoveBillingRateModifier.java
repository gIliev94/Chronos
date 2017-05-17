package bg.bc.tools.chronos.core.usecases.crud.billingratemodifier;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;

interface IRemoveBillingRateModifier {

    boolean removeBillingRateModifier(long id);

    boolean removeBillingRateModifier(DBillingRateModifier billingRateModifier);

    boolean removeBillingRateModifiers(DBooking booking);

    boolean removeBillingRateModifiers(DRole role);

    boolean removeBillingRateModifiers(DPerformer performer);

    boolean removeBillingRateModifiers(DTask task);

}
