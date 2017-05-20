package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier.ModifierAction;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;

public interface LocalBillingRateModifierRepository extends CrudRepository<BillingRateModifier, Long> {
    
    Collection<BillingRateModifier> findByModifierAction(ModifierAction modifierAction);

    Collection<BillingRateModifier> findByModifierActionIn(Collection<ModifierAction> modifierAction);

    Collection<BillingRateModifier> findByModifierValue(double modifierValue);

    Collection<BillingRateModifier> findByModifierValueLessThan(double lessThanModifierValue);

    Collection<BillingRateModifier> findByModifierValueGreaterThan(double greaterThanModifierValue);

    Collection<BillingRateModifier> findByModifierValueBetween(double lessThanModifierValue,
	    double greaterThanModifierValue);

    Collection<BillingRateModifier> findByBooking(Booking booking);
}
