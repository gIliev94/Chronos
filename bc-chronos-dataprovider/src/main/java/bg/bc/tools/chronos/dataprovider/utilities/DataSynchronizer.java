package bg.bc.tools.chronos.dataprovider.utilities;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBillingRateModifierRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalPerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteBillingRateModifierRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemotePerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteTaskRepository;

public class DataSynchronizer {

    public enum SyncDirection {
	LOCAL_TO_REMOTE, // nl
	REMOTE_TO_LOCAL; // nl

	private Map<Class<? extends Serializable>, CrudRepository<? extends Serializable, Long>> repoMap;

	private SyncDirection() {
	    repoMap = new HashMap<Class<? extends Serializable>, CrudRepository<? extends Serializable, Long>>();
	}

	public void addRepo(final CrudRepository<? extends Serializable, Long> repo) {
	    @SuppressWarnings("unchecked")
	    final Class<? extends Serializable> entityClass = (Class<? extends Serializable>) repo.getClass()
		    .getGenericInterfaces()[0].getClass();
	    repoMap.put(entityClass, repo);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> CrudRepository<T, Long> getRepoForEntity(Class<T> entityClass) {
	    return (CrudRepository<T, Long>) repoMap.get(entityClass);
	}

	public Object getEntity(final Changelog change, final CrudRepository localRepo,
		final CrudRepository remoteRepo) {
	    final String key = change.getUpdatedEntityKey();

	    if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
		return ((LocalCategoryRepository) localRepo).findBySyncKey(key);
	    } else {
		return ((RemoteCategoryRepository) remoteRepo).findBySyncKey(key);
	    }

	    // final String entityType = change.getUpdatedEntityType();
	    // switch (entityType) {
	    // case "Category":
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalCategoryRepository)
	    // getRepoForEntity(Category.class)).findBySyncKey(key);
	    // } else {
	    // return ((RemoteCategoryRepository)
	    // getRepoForEntity(Category.class)).findBySyncKey(key);
	    // }
	    // case "Customer":
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalCustomerRepository)
	    // getRepoForEntity(Customer.class)).findBySyncKey(key);
	    // } else {
	    // return ((RemoteCustomerRepository)
	    // getRepoForEntity(Customer.class)).findBySyncKey(key);
	    // }
	    // case "Project":
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalProjectRepository)
	    // getRepoForEntity(Project.class)).findBySyncKey(key);
	    // } else {
	    // return ((RemoteProjectRepository)
	    // getRepoForEntity(Project.class)).findBySyncKey(key);
	    // }
	    // case "Task":
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalTaskRepository)
	    // getRepoForEntity(Task.class)).findBySyncKey(key);
	    // } else {
	    // return ((RemoteTaskRepository)
	    // getRepoForEntity(Task.class)).findBySyncKey(key);
	    // }
	    // case "Booking":
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalBookingRepository)
	    // getRepoForEntity(Booking.class)).findBySyncKey(key);
	    // } else {
	    // return ((RemoteBookingRepository)
	    // getRepoForEntity(Booking.class)).findBySyncKey(key);
	    // }
	    // case "BillingRateModifier":
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalBillingRateModifierRepository)
	    // getRepoForEntity(BillingRateModifier.class))
	    // .findBySyncKey(key);
	    // } else {
	    // return ((RemoteBillingRateModifierRepository)
	    // getRepoForEntity(BillingRateModifier.class))
	    // .findBySyncKey(key);
	    // }
	    // case "Performer":
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalPerformerRepository)
	    // getRepoForEntity(Performer.class)).findBySyncKey(key);
	    // } else {
	    // return ((RemotePerformerRepository)
	    // getRepoForEntity(Performer.class)).findBySyncKey(key);
	    // }
	    // case "Role":
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalRoleRepository)
	    // getRepoForEntity(Role.class)).findBySyncKey(key);
	    // } else {
	    // return ((RemoteRoleRepository)
	    // getRepoForEntity(Role.class)).findBySyncKey(key);
	    // }
	    // default:
	    // return null;
	    // }
	}

	@Component
	public static class SyncDirectionRepositoryInjector {

	    @Autowired
	    private LocalCategoryRepository localCategoryRepo;

	    @Autowired
	    private LocalCustomerRepository localCustomerRepo;

	    @Autowired
	    private LocalProjectRepository localProjectRepo;

	    @Autowired
	    private LocalTaskRepository localTaskRepo;

	    @Autowired
	    private LocalBookingRepository localBookingRepo;

	    @Autowired
	    private LocalBillingRateModifierRepository localBillingRateModifierRepo;

	    @Autowired
	    private LocalPerformerRepository localPerformerRepo;

	    @Autowired
	    private LocalRoleRepository localRoleRepo;

	    @Autowired
	    private RemoteCategoryRepository remoteCategoryRepo;

	    @Autowired
	    private RemoteCustomerRepository remoteCustomerRepo;

	    @Autowired
	    private RemoteProjectRepository remoteProjectRepo;

	    @Autowired
	    private RemoteTaskRepository remoteTaskRepo;

	    @Autowired
	    private RemoteBookingRepository remoteBookingRepo;

	    @Autowired
	    private RemoteBillingRateModifierRepository remoteBillingRateModifierRepo;

	    @Autowired
	    private RemotePerformerRepository remotePerformerRepo;

	    @Autowired
	    private RemoteRoleRepository remoteRoleRepo;

	    @PostConstruct
	    public void postConstruct() {
		SyncDirection.LOCAL_TO_REMOTE.addRepo(localCategoryRepo);
		SyncDirection.LOCAL_TO_REMOTE.addRepo(localCustomerRepo);
		SyncDirection.LOCAL_TO_REMOTE.addRepo(localProjectRepo);
		SyncDirection.LOCAL_TO_REMOTE.addRepo(localTaskRepo);
		SyncDirection.LOCAL_TO_REMOTE.addRepo(localBookingRepo);
		SyncDirection.LOCAL_TO_REMOTE.addRepo(localBillingRateModifierRepo);
		SyncDirection.LOCAL_TO_REMOTE.addRepo(localPerformerRepo);
		SyncDirection.LOCAL_TO_REMOTE.addRepo(localRoleRepo);

		SyncDirection.REMOTE_TO_LOCAL.addRepo(remoteCategoryRepo);
		SyncDirection.REMOTE_TO_LOCAL.addRepo(remoteCustomerRepo);
		SyncDirection.REMOTE_TO_LOCAL.addRepo(remoteProjectRepo);
		SyncDirection.REMOTE_TO_LOCAL.addRepo(remoteTaskRepo);
		SyncDirection.REMOTE_TO_LOCAL.addRepo(remoteBookingRepo);
		SyncDirection.REMOTE_TO_LOCAL.addRepo(remoteBillingRateModifierRepo);
		SyncDirection.REMOTE_TO_LOCAL.addRepo(remotePerformerRepo);
		SyncDirection.REMOTE_TO_LOCAL.addRepo(remoteRoleRepo);
	    }
	}
    }

    @Autowired
    private LocalChangelogRepository localChangelogRepo;

    @Autowired
    private LocalCategoryRepository localCategoryRepo;
    //
    // @Autowired
    // private LocalCustomerRepository localCustomerRepo;
    //
    // @Autowired
    // private LocalProjectRepository localProjectRepo;
    //
    // @Autowired
    // private LocalTaskRepository localTaskRepo;
    //
    // @Autowired
    // private LocalBookingRepository localBookingRepo;
    //
    // @Autowired
    // private LocalBillingRateModifierRepository localBillingRateModifierRepo;
    //
    // @Autowired
    // private LocalPerformerRepository localPerformerRepo;
    //
    // @Autowired
    // private LocalRoleRepository localRoleRepo;

    @Autowired
    private RemoteChangelogRepository remoteChangelogRepo;

    @Autowired
    private RemoteCategoryRepository remoteCategoryRepo;
    //
    // @Autowired
    // private RemoteCustomerRepository remoteCustomerRepo;
    //
    // @Autowired
    // private RemoteProjectRepository remoteProjectRepo;
    //
    // @Autowired
    // private RemoteTaskRepository remoteTaskRepo;
    //
    // @Autowired
    // private RemoteBookingRepository remoteBookingRepo;
    //
    // @Autowired
    // private RemoteBillingRateModifierRepository
    // remoteBillingRateModifierRepo;
    //
    // @Autowired
    // private RemotePerformerRepository remotePerformerRepo;
    //
    // @Autowired
    // private RemoteRoleRepository remoteRoleRepo;

    public Map<String, List<Object>> findUnsyncedObjects(final SyncDirection syncDirection) throws Exception {
	// Find last change for both DBs
	final Changelog lastLocalChange = localChangelogRepo.findTopByOrderByUpdateCounterDesc();
	final Changelog lastRemoteChange = remoteChangelogRepo.findTopByOrderByUpdateCounterDesc();

	final long localUpdateCount = (lastLocalChange == null) ? 0 : lastLocalChange.getUpdateCounter();
	final long remoteUpdateCount = (lastRemoteChange == null) ? 0 : lastRemoteChange.getUpdateCounter();

	// Determine if there is difference between DBs
	boolean differenceFound = (localUpdateCount != remoteUpdateCount);

	// Find difference
	final List<Changelog> differentEntities = new LinkedList<>();

	if (differenceFound) {
	    long diffFrom = 0L;
	    long diffTo = 0L;

	    switch (syncDirection) {
	    case LOCAL_TO_REMOTE:
		diffFrom = remoteUpdateCount;
		diffTo = (localUpdateCount - remoteUpdateCount);

		differentEntities.addAll(localChangelogRepo.findByUpdateCounterBetween(diffFrom, diffTo));
		break;

	    case REMOTE_TO_LOCAL:
		diffFrom = localUpdateCount;
		diffTo = (remoteUpdateCount - localUpdateCount);

		differentEntities.addAll(remoteChangelogRepo.findByUpdateCounterBetween(diffFrom, diffTo));
		break;

	    default:
		throw new Exception("Invalid synchronization type! Please contact administrator!");
	    }

	    // Collect different entities
	    final Map<String, List<Object>> differenciesMap = new LinkedHashMap<>();
	    differenciesMap.put("Category", new LinkedList<Object>());
	    differenciesMap.put("Customer", new LinkedList<Object>());
	    differenciesMap.put("Project", new LinkedList<Object>());
	    differenciesMap.put("Task", new LinkedList<Object>());
	    differenciesMap.put("Booking", new LinkedList<Object>());
	    differenciesMap.put("BillingRateModifier", new LinkedList<Object>());
	    differenciesMap.put("Performer", new LinkedList<Object>());
	    differenciesMap.put("Role", new LinkedList<Object>());

	    differentEntities.stream() // nl
		    .filter(change -> "Category".equals(change.getUpdatedEntityType())) // nl
		    .map(change -> syncDirection.getEntity(change, localCategoryRepo, remoteCategoryRepo)) // nl
		    .forEach(differenciesMap.get("Category")::add);
	    System.out.println(differenciesMap);
	    return differenciesMap;

	    // TODO: Finish for the rest of the entities...
	}
	return Collections.emptyMap();
    }

    public void synchronizeDatabases(final SyncDirection direction) throws Exception {
	final Map<String, List<Object>> unsyncedEntities = findUnsyncedObjects(direction);

	final List<Category> unsyncedCategories = unsyncedEntities.get("Category").stream().map(o -> ((Category) o))
		.collect(Collectors.toList());
	syncCategories(unsyncedCategories, direction);
    }

    private void syncCategories(final List<Category> unsyncedCategories, final SyncDirection initialDirection)
	    throws Exception {
	switch (initialDirection) {
	case LOCAL_TO_REMOTE:
	    unsyncedCategories.stream().map(this::cloneCategory).forEach(remoteCategoryRepo::save);
	    break;
	case REMOTE_TO_LOCAL:
	    unsyncedCategories.stream().map(this::cloneCategory).forEach(localCategoryRepo::save);
	    break;
	default:
	    throw new Exception("Inexistent channel!");
	}
    }

    private Category cloneCategory(final Category original) {
	final Category clone = new Category();
	clone.setName(original.getName());
	clone.setSortOrder(original.getSortOrder());
	clone.setSyncKey(original.getSyncKey());

	return clone;
    }

    // @SuppressWarnings({ "rawtypes", "unchecked" })
    public void synchronizeObjects() throws Exception {
	final SyncDirection[] syncDirections = SyncDirection.values();

	Stream.of(syncDirections).forEach(syncDirection -> {
	    try {
		final Map<String, List<Object>> unsyncedObjects = findUnsyncedObjects(syncDirection);

		// unsyncedObjects.stream().forEach(entity -> {
		// final Class<? extends Serializable> entityClass =
		// entity.getClass();
		// final CrudRepository entityRepo =
		// syncDirection.getRepoForEntity(entityClass);
		// entityRepo.save(entityClass.cast(entity));
		// });

	    } catch (Exception e) {
		showErrorDialog("Unsuccessful sync attempt! Please contact administrator!", e);
	    }

	});
    }

    private void resolveConflicts() {

    }

    private void showErrorDialog(String string, Exception e) {
	// TODO Auto-generated method stub

    }
}
