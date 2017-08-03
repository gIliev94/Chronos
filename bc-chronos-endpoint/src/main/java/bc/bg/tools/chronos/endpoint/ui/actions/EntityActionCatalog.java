package bc.bg.tools.chronos.endpoint.ui.actions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import bg.bc.tools.chronos.dataprovider.db.entities.BillingRateModifier;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer;
import bg.bc.tools.chronos.dataprovider.db.entities.Project;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.Task;
import bg.bc.tools.chronos.dataprovider.db.entities.Performer.Priviledge;

// ADD AS BEAN PROTOTYPE
public class EntityActionCatalog {

    public static final Set<EntityAction<Category>> CATEGORY_ACTIONS = Collections
	    .unmodifiableSet(new LinkedHashSet<>());

    public static final Set<EntityAction<Customer>> CUSTOMER_ACTIONS = Collections
	    .unmodifiableSet(new LinkedHashSet<>());

    public static final Set<EntityAction<Project>> PROJECT_ACTIONS = Collections.unmodifiableSet(new LinkedHashSet<>());

    public static final Set<EntityAction<Task>> TASK_ACTIONS = Collections.unmodifiableSet(new LinkedHashSet<>());

    public static final Set<EntityAction<Booking>> BOOKING_ACTIONS = Collections.unmodifiableSet(new LinkedHashSet<>());

    public static final Set<EntityAction<Performer>> PERFORMER_ACTIONS = Collections
	    .unmodifiableSet(new LinkedHashSet<>());

    public static final Set<EntityAction<Role>> ROLE_ACTIONS = Collections.unmodifiableSet(new LinkedHashSet<>());

    public static final Set<EntityAction<BillingRateModifier>> BILLING_RATE_MODIFIER_ACTIONS = Collections
	    .unmodifiableSet(new LinkedHashSet<>());

    public EntityActionCatalog() {
	addStandardActions();
	addCustomActions();
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> Set<EntityAction<T>> getActionsFor(Class<T> entityClass) {
	Stream<?> actionStream = null;

	if (Category.class.isAssignableFrom(entityClass)) {
	    actionStream = CATEGORY_ACTIONS.stream();
	} else if (Customer.class.isAssignableFrom(entityClass)) {
	    actionStream = CUSTOMER_ACTIONS.stream();
	} else if (Project.class.isAssignableFrom(entityClass)) {
	    actionStream = PROJECT_ACTIONS.stream();
	} else if (Task.class.isAssignableFrom(entityClass)) {
	    actionStream = TASK_ACTIONS.stream();
	} else if (Booking.class.isAssignableFrom(entityClass)) {
	    actionStream = BOOKING_ACTIONS.stream();
	} else if (Performer.class.isAssignableFrom(entityClass)) {
	    actionStream = PERFORMER_ACTIONS.stream();
	} else if (Role.class.isAssignableFrom(entityClass)) {
	    actionStream = ROLE_ACTIONS.stream();
	} else if (BillingRateModifier.class.isAssignableFrom(entityClass)) {
	    actionStream = BILLING_RATE_MODIFIER_ACTIONS.stream();
	} else {
	    throw new RuntimeException("NO ACTIONS FOR THIS ENTITY!");
	}

	return actionStream.map(actionDef -> (EntityAction<T>) actionDef) // nl
		.collect(Collectors.toSet());
    }

    private static final String ENTITY_CATEGORY = "Category";

    protected static <T> Set<T> initialize(Class<? extends Serializable> entityClass) {
	final Set<T> theSet = new LinkedHashSet<>();

	final String entityName = entityClass.getSimpleName();

	switch (entityName) {
	case ENTITY_CATEGORY:
	    final EntityAction<Category> actionRefreshCategory = new EntityAction<Category>(Category.class)
		    .requiredPriviledges(Arrays.asList(Priviledge.READ));
	    CATEGORY_ACTIONS.add(actionRefreshCategory);

	    final EntityAction<Category> actionHideCategory = new EntityAction<Category>(Category.class)
		    .requiredPriviledges(Arrays.asList(Priviledge.READ));
	    CATEGORY_ACTIONS.add(actionHideCategory);
	    break;

	default:
	    break;
	}

	return Collections.unmodifiableSet(theSet);
    }

    // TODO: Wtf???
    private void addStandardActions() {
	// CUSTOMER_ACTIONS = new EntityAction().

    }

    protected void addCustomActions() {
	// OVERRIDE THIS AND ADD NEW ACTIONS IF YOU WANT...
	// CUSTOMER_ACTIONS.add(...);
    }
}
