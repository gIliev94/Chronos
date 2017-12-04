package bg.bc.tools.chronos.dataprovider.db.remote.repos;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier.ModifierAction;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;

@Repository
public interface RemoteBillingRateModifierRepository extends CrudRepository<BillingRateModifier, Long> {

    Collection<BillingRateModifier> findByModifierAction(ModifierAction modifierAction);

    Collection<BillingRateModifier> findByModifierActionIn(Collection<ModifierAction> modifierAction);

    Collection<BillingRateModifier> findByModifierValue(double modifierValue);

    Collection<BillingRateModifier> findByModifierValueLessThan(double lessThanModifierValue);

    Collection<BillingRateModifier> findByModifierValueGreaterThan(double greaterThanModifierValue);

    Collection<BillingRateModifier> findByModifierValueBetween(double lessThanModifierValue,
	    double greaterThanModifierValue);

    Collection<BillingRateModifier> findByBooking(Booking booking);

    BillingRateModifier findBySyncKey(String syncKey);
}
