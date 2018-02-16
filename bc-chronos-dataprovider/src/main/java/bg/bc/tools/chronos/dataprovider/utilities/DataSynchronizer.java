package bg.bc.tools.chronos.dataprovider.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBillingRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteBillingRateModifierRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemotePerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteBillingRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteTaskRepository;

public class DataSynchronizer {

    public enum SyncDirection {
	LOCAL_TO_REMOTE("view.popup.synchronization.upload"), // nl
	REMOTE_TO_LOCAL("view.popup.synchronization.download"); // nl

	private String directionSuffix;

	private Map<Class<? extends Serializable>, CrudRepository<? extends Serializable, Long>> repoMap;

	private SyncDirection(final String directionSuffix) {
	    repoMap = new HashMap<Class<? extends Serializable>, CrudRepository<? extends Serializable, Long>>();
	    this.directionSuffix = directionSuffix;
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

	public String getDirectionSuffix() {
	    return directionSuffix;
	}

	public Object getEntity(final Changelog change, final CrudRepository localRepo,
		final CrudRepository remoteRepo) {
	    final String key = change.getUpdatedEntityKey();

	    // TODO: invalid due to latest refactor - db schema overhaul...
	    // if (this.equals(SyncDirection.LOCAL_TO_REMOTE)) {
	    // return ((LocalCategoryRepository) localRepo).findBySyncKey(key);
	    // } else {
	    // return ((RemoteCategoryRepository)
	    // remoteRepo).findBySyncKey(key);
	    // }

	    return null;

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
	    private LocalBillingRoleRepository localRoleRepo;

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
	    private RemoteBillingRoleRepository remoteRoleRepo;

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
	final Changelog lastLocalChange = localChangelogRepo
		.findTopByUpdatedEntityTypeOrderByUpdateCounterDesc("Category");
	final Changelog lastRemoteChange = remoteChangelogRepo
		.findTopByUpdatedEntityTypeOrderByUpdateCounterDesc("Category");

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
		// diffFrom = localUpdateCount;
		// diffTo = (remoteUpdateCount - localUpdateCount);
		diffFrom = 4;
		diffTo = 5;

		System.out.println(remoteChangelogRepo.findAll());
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

    public SyncResponse synchronizeDatabases(final SyncDirection direction) throws Exception {
	final Map<String, List<Object>> unsyncedEntities = findUnsyncedObjects(direction);

	final List<Category> unsyncedCategories = unsyncedEntities.get("Category").stream() // nl
		.map(o -> ((Category) o)) // nl
		.collect(Collectors.toList());

	return syncCategories(unsyncedCategories, direction);
    }

    private SyncResponse syncCategories(final List<Category> unsyncedCategories, final SyncDirection initialDirection)
	    throws Exception {
	Stream<Category> categoriesToSyncStream = unsyncedCategories.stream() // nl
		.map(this::cloneCategory);

	Stream<Category> chngToSyncStream = unsyncedCategories.stream() // nl
		.map(this::cloneCategory);

	List<Collection<Changelog>> chagesToSync = new LinkedList<>();

	switch (initialDirection) {
	case LOCAL_TO_REMOTE:
	    // unsyncedClonesStream // nl
	    // .map(remoteCategoryRepo::save) // nl
	    // .forEach(syncedCategories::add);
	    categoriesToSyncStream = categoriesToSyncStream // nl
		    .map(remoteCategoryRepo::save); // nl
	    // categoriesToSyncStream.forEach(cat -> {
	    // final Collection<Changelog> changes =
	    // localChangelogRepo.findByUpdatedEntityKey(cat.getSyncKey());
	    // changes.stream().map(remoteChangelogRepo::save).count();
	    // });

	    // TODO: REFACTORED STRUCTURE
	    // chagesToSync = chngToSyncStream // nl
	    // .map(cat ->
	    // localChangelogRepo.findByUpdatedEntityKey(cat.getSyncKey())) //
	    // nl
	    // .collect(Collectors.toList());
	    // chagesToSync.stream() // nl
	    // .flatMap(o -> o.stream()) // nl
	    // .map(this::cloneChangelog) // nl
	    // .forEach(remoteChangelogRepo::save);

	    break;

	case REMOTE_TO_LOCAL:
	    categoriesToSyncStream = categoriesToSyncStream // nl
		    .map(localCategoryRepo::save); // nl

	    // TODO: REFACTORED STRUCTURE
	    // chagesToSync = chngToSyncStream // nl
	    // .map(cat ->
	    // remoteChangelogRepo.findByUpdatedEntityKey(cat.getSyncKey())) //
	    // nl
	    // .collect(Collectors.toList());
	    chagesToSync.stream() // nl
		    .flatMap(o -> o.stream()) // nl
		    .map(this::cloneChangelog) // nl
		    .forEach(localChangelogRepo::save);
	    break;

	default:
	    return new SyncResponse(initialDirection, unsyncedCategories,
		    new Exception("Invalid/inexistent sync direction!"));
	}

	final List<Category> syncedCategories = new LinkedList<>();
	categoriesToSyncStream.forEach(syncedCategories::add);

	return new SyncResponse(initialDirection, syncedCategories,
		findStillNotSyncedCategories(unsyncedCategories, syncedCategories));
    }

    protected List<Category> findStillNotSyncedCategories(final List<Category> unsyncedCategories,
	    final List<Category> syncedCategories) {
	final List<String> syncedObjectNames = syncedCategories.stream() // nl
		.map(Category::getName) // nl
		.collect(Collectors.toList());
	final List<String> notSyncedObjectNames = unsyncedCategories.stream() // nl
		.map(Category::getName) // nl
		.collect(Collectors.toList());
	notSyncedObjectNames.removeAll(syncedObjectNames);

	final List<Category> notSyncedCategories = unsyncedCategories.stream() // nl
		.filter(c -> (notSyncedObjectNames.contains(c.getName()))) // nl
		.collect(Collectors.toList());
	return notSyncedCategories;
    }

    /**
     * Necessary to make a clone without the ID(primary key) set - Hibernate
     * will manage it for the receiving database.
     * 
     * @param original
     *            - the original object
     * @return A clone of the specified original object with no PK set.
     */
    private Category cloneCategory(final Category original) {
	final Category clone = new Category();
	// clone.setId(id); // NO ID(PK) SET
	clone.setName(original.getName());
	clone.setSortOrder(original.getSortOrder());
	// clone.setSyncKey(original.getSyncKey());

	return clone;
    }

    /**
     * Necessary to make a clone without the ID(primary key) set - Hibernate
     * will manage it for the receiving database.
     * 
     * @param original
     *            - the original object
     * @return A clone of the specified original object with no PK set.
     */
    private Changelog cloneChangelog(final Changelog original) {
	final Changelog clone = new Changelog();
	// clone.setId(id); // NO ID(PK) SET
	clone.setChangeTime(original.getChangeTime());
	clone.setDeviceName(original.getDeviceName());
	clone.setUpdatedEntityKey(original.getUpdatedEntityKey());
	clone.setUpdatedEntityType(original.getUpdatedEntityType());

	return clone;
    }

    public class SyncResponse {

	public static final String MSG_ID_SYNC_SUCCESS = "view.popup.synchronization.success";

	public static final String MSG_ID_SYNC_ERROR = "view.popup.synchronization.error";

	private boolean isSuccessful;

	private String messageId;

	private Throwable error;

	private SyncDirection direction;

	private List<? extends Serializable> syncedEntities;

	private List<? extends Serializable> notSyncedEntities;

	public SyncResponse(final SyncDirection direction, final List<? extends Serializable> syncedEntities,
		final List<? extends Serializable> notSyncedEntities, final Throwable error) {
	    this.notSyncedEntities = notSyncedEntities;
	    this.syncedEntities = syncedEntities;
	    this.error = error;
	    this.direction = direction;

	    this.isSuccessful = ((error == null) && notSyncedEntities.isEmpty());
	    this.messageId = (isSuccessful ? MSG_ID_SYNC_SUCCESS : MSG_ID_SYNC_ERROR);
	}

	public SyncResponse(final SyncDirection direction, final List<? extends Serializable> syncedEntities,
		final List<? extends Serializable> notSyncedEntities) {
	    this(direction, syncedEntities, notSyncedEntities, null);
	}

	public SyncResponse(final SyncDirection direction, final List<? extends Serializable> notSyncedEntities,
		final Throwable error) {
	    this(direction, Collections.emptyList(), notSyncedEntities, error);
	}

	public String getMessageId() {
	    return messageId;
	}

	public Throwable getError() {
	    return error;
	}

	public SyncDirection getDirection() {
	    return direction;
	}

	public List<? extends Serializable> getSyncedEntities() {
	    return syncedEntities;
	}

	public List<? extends Serializable> getNotSyncedEntities() {
	    return notSyncedEntities;
	}

	public boolean isSuccessful() {
	    return isSuccessful;
	}
    }
}
