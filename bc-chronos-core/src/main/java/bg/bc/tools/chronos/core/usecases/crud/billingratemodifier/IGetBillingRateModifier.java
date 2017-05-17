package bg.bc.tools.chronos.core.usecases.crud.billingratemodifier;

import java.util.Collection;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DPerformer;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.core.entities.DTask;

interface IGetBillingRateModifier {

    DBillingRateModifier getBillingRateModifier(long id);

    Collection<DBillingRateModifier> getBillingRateModifiers();

    Collection<DBillingRateModifier> getBillingRateModifiers(DBooking booking);

    Collection<DBillingRateModifier> getBillingRateModifiers(DRole role);

    Collection<DBillingRateModifier> getBillingRateModifiers(DPerformer performer);

    Collection<DBillingRateModifier> getBillingRateModifiers(DTask task);
}
