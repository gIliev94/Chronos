package bg.bc.tools.chronos.dataprovider.utilities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

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

    private enum SyncDirection {
	LOCAL_TO_REMOTE, REMOTE_TO_LOCAL;

	private CrudRepository getRepoForEntity(Class entityClass) {
	    return null;
	}
    }

    @Autowired
    private LocalChangelogRepository localChangelogRepo;

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
    private RemoteChangelogRepository remoteChangelogRepo;

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

    public List<Serializable> findUnsyncedObjects(final SyncDirection syncDirection) throws Exception {
	final Changelog lastLocalChange = localChangelogRepo.findTopByOrderByUpdateCounterDesc();
	final Changelog lastRemoteChange = remoteChangelogRepo.findTopByOrderByUpdateCounterDesc();

	final long localUpdateCount = lastLocalChange.getUpdateCounter();
	final long remoteUpdateCount = lastRemoteChange.getUpdateCounter();
	if (localUpdateCount != remoteUpdateCount) {
	    long countFrom = 0L;
	    long countTo = 0L;

	    switch (syncDirection) {
	    case LOCAL_TO_REMOTE:
		countFrom = remoteUpdateCount;
		countTo = (localUpdateCount - remoteUpdateCount);

		final Collection<Changelog> unsyncedChanges = localChangelogRepo.findByUpdateCounterBetween(countFrom,
			countTo);
		final List<Serializable> unsyncedEntities = new LinkedList<Serializable>();
		unsyncedChanges.stream().forEach(change -> {
		    final String key = change.getUpdatedEntityKey();

		    unsyncedEntities.add(localCategoryRepo.findBySyncKey(key));
		    unsyncedEntities.add(localCustomerRepo.findBySyncKey(key));
		    unsyncedEntities.add(localProjectRepo.findBySyncKey(key));
		    unsyncedEntities.add(localTaskRepo.findBySyncKey(key));
		    unsyncedEntities.add(localBookingRepo.findBySyncKey(key));
		    unsyncedEntities.add(localPerformerRepo.findBySyncKey(key));
		    unsyncedEntities.add(localRoleRepo.findBySyncKey(key));
		    unsyncedEntities.add(localBillingRateModifierRepo.findBySyncKey(key));
		});
		return unsyncedEntities;
	    case REMOTE_TO_LOCAL:
		countFrom = localUpdateCount;
		countTo = (remoteUpdateCount - localUpdateCount);
		// Do the same as in above case only for Remote repositories
		return null;
	    default:
		throw new Exception("Invalid synchronization type! Please contact administrator!");
	    }
	}
	return Collections.emptyList();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void synchronizeObjects(final List<Serializable> unsyncedEntities) throws Exception {
	final SyncDirection[] syncDirections = SyncDirection.values();

	Stream.of(syncDirections).forEach(syncDirection -> {
	    try {
		final List<Serializable> unsyncedObjects = findUnsyncedObjects(syncDirection);

		unsyncedObjects.stream().forEach(entity -> {
		    final Class<? extends Serializable> entityClass = entity.getClass();
		    final CrudRepository entityRepo = syncDirection.getRepoForEntity(entityClass);
		    entityRepo.save(entityClass.cast(entity));
		});

	    } catch (Exception e) {
		showErrorDialog("Unsuccessful sync attempt! Please contact administrator!", e);
	    }

	});
    }

    private void showErrorDialog(String string, Exception e) {
	// TODO Auto-generated method stub

    }
}
