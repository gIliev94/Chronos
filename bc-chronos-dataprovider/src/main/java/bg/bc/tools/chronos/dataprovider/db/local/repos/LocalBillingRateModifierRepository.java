package bg.bc.tools.chronos.dataprovider.db.local.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier.ModifierAction;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;

@Repository
public interface LocalBillingRateModifierRepository extends CrudRepository<BillingRateModifier, Long> {

    Collection<BillingRateModifier> findByModifierAction(ModifierAction modifierAction);

    Collection<BillingRateModifier> findDistinctByModifierAction(ModifierAction modifierAction);

    Collection<BillingRateModifier> findByModifierActionIn(Collection<ModifierAction> modifierAction);

    Collection<BillingRateModifier> findDistinctByModifierActionIn(Collection<ModifierAction> modifierAction);

    Collection<BillingRateModifier> findByModifierValue(double modifierValue);

    Collection<BillingRateModifier> findDistinctByModifierValue(double modifierValue);

    Collection<BillingRateModifier> findByModifierValueLessThan(double lessThanModifierValue);

    Collection<BillingRateModifier> findDistinctByModifierValueLessThan(double lessThanModifierValue);

    Collection<BillingRateModifier> findByModifierValueGreaterThan(double greaterThanModifierValue);

    Collection<BillingRateModifier> findDistinctByModifierValueGreaterThan(double greaterThanModifierValue);

    Collection<BillingRateModifier> findByModifierValueBetween(double lessThanModifierValue,
	    double greaterThanModifierValue);

    Collection<BillingRateModifier> findDistinctByModifierValueBetween(double lessThanModifierValue,
	    double greaterThanModifierValue);

    Collection<BillingRateModifier> findByBooking(Booking booking);

    Collection<BillingRateModifier> findDistinctByBooking(Booking booking);
}
