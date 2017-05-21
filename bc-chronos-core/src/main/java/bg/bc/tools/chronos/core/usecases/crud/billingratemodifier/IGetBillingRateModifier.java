package bg.bc.tools.chronos.core.usecases.crud.billingratemodifier;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DBillingRateModifier;
import bg.bc.tools.chronos.core.entities.DBillingRateModifier.DModifierAction;
import bg.bc.tools.chronos.core.entities.DBooking;

interface IGetBillingRateModifier {

    DBillingRateModifier getBillingRateModifier(long id);

    List<DBillingRateModifier> getBillingRateModifiers();

    List<DBillingRateModifier> getBillingRateModifiers(DModifierAction modifierAction);

    List<DBillingRateModifier> getBillingRateModifiers(List<DModifierAction> modifierActions);

    List<DBillingRateModifier> getBillingRateModifiers(DBooking booking);

    List<DBillingRateModifier> getBillingRateModifiers(double modifierValue);

    List<DBillingRateModifier> getBillingRateModifiersLessThan(double modifierValueLessThan);

    List<DBillingRateModifier> getBillingRateModifiersGreaterThan(double modifierValueGreaterThan);

    List<DBillingRateModifier> getBillingRateModifiersBetween(double modifierValueLower, double modifierValueUpper);

    // List<DBillingRateModifier> getBillingRateModifiers(DRole role);
    //
    // List<DBillingRateModifier> getBillingRateModifiers(DPerformer performer);
    //
    // List<DBillingRateModifier> getBillingRateModifiers(DTask task);
}
