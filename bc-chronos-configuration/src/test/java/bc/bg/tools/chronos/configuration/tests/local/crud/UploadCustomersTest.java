package bc.bg.tools.chronos.configuration.tests.local.crud;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bg.bc.tools.chronos.dataprovider.db.entities.Customer;

public class UploadCustomersTest {

    private static Customer[] customersLocal = null;
    private static Customer[] customersRemote = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	customersLocal = new Customer[] {};
	customersRemote = new Customer[] {};
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	customersLocal = null;
	customersRemote = null;
    }

    private static final int COUNT_MISSING_ENTITY = 0;

    private static final int COUNT_NEW_ENTITY = 1;

    private static final String FIELD_DIFF_FORMAT = "\n\t{0}. Field << {1} >> :: [{2}] => [{3}]";

    private enum DiffOptions {
	Overwrite("Overwrite REMOTE"), // nl
	Skip("Skip update");

	private String displayName;

	private DiffOptions(String displayName) {
	    this.displayName = displayName;
	}

	@Override
	public String toString() {
	    return displayName;
	}

	public static DiffOptions valueOfOrdinal(int ordinal) {
	    DiffOptions[] values = values();
	    for (DiffOptions option : values) {
		if (option.ordinal() == ordinal) {
		    return option;
		}
	    }

	    return DiffOptions.Skip;
	}
    }

    @Before
    public void setUp() throws Exception {
	// EXISTING entity
	Customer lc1 = new Customer();
	lc1.setId(1);
	lc1.setName("C1");
	lc1.setUpdateCounter(COUNT_NEW_ENTITY);
	lc1.setSyncCounter(COUNT_NEW_ENTITY);

	Customer lc2 = new Customer();
	lc2.setId(2);
	lc2.setName("C2");
	lc2.setUpdateCounter(COUNT_NEW_ENTITY);
	lc2.setSyncCounter(COUNT_NEW_ENTITY);

	// UPDATED entity (no conflicts)
	Customer lc3 = new Customer();
	lc3.setId(3);
	lc3.setName("C3");
	lc3.setDescription("Set description with the update...");
	lc3.setUpdateCounter(2);
	lc3.setSyncCounter(COUNT_NEW_ENTITY);

	// UPDATED entity (with conflict for field => description)
	Customer lc4 = new Customer();
	lc4.setId(4);
	lc4.setName("C4");
	lc4.setDescription("LOCAL description (set by me)");
	lc4.setUpdateCounter(2);
	lc4.setSyncCounter(COUNT_NEW_ENTITY);

	// NEW entity
	Customer lc5 = new Customer();
	lc5.setId(5);
	lc5.setName("C5");
	lc5.setUpdateCounter(COUNT_NEW_ENTITY);
	lc5.setSyncCounter(COUNT_MISSING_ENTITY);

	customersLocal = new Customer[] { lc1, lc2, lc3, lc4, lc5 };

	Customer rc1 = new Customer();
	rc1.setId(1);
	rc1.setName("C1");
	rc1.setUpdateCounter(COUNT_NEW_ENTITY);
	rc1.setSyncCounter(COUNT_NEW_ENTITY);

	Customer rc2 = new Customer();
	rc2.setId(2);
	rc2.setName("C2");
	rc2.setUpdateCounter(COUNT_NEW_ENTITY);
	rc2.setSyncCounter(COUNT_NEW_ENTITY);

	Customer rc3 = new Customer();
	rc3.setId(3);
	rc3.setName("C3");
	rc3.setUpdateCounter(COUNT_NEW_ENTITY);
	rc3.setSyncCounter(COUNT_NEW_ENTITY);

	Customer rc4 = new Customer();
	rc4.setId(4);
	rc4.setName("C4");
	rc4.setDescription("REMOTE description (set by someone else)");
	rc4.setUpdateCounter(2);
	rc4.setSyncCounter(2);

	customersRemote = new Customer[] { rc1, rc2, rc3, rc4 };
    }

    @After
    public void tearDown() throws Exception {
	customersLocal = new Customer[] {};
	customersRemote = new Customer[] {};
    }

    @Test
    public void test() {
	System.out.println("DATA:");
	System.out.println("===============================================================");

	List<Customer> localCustList = Arrays.asList(customersLocal);
	System.out.println("LOCAL customers :: " + localCustList);

	List<Customer> remoteCustList = Arrays.asList(customersRemote);
	System.out.println("REMOTE customers :: " + remoteCustList);
	System.out.println("---------------------------------------------------------------");

	System.out.println("\n\nUPLOAD STARTED:");
	System.out.println("===============================================================");

	// {1} - Find LOCAL entities to upload
	List<Customer> toBeSyncedLcList = localCustList.stream() // nl
		.filter(c -> c.getUpdateCounter() > c.getSyncCounter()) // nl
		.collect(Collectors.toList());
	System.out.println("LOCAL customers to upload/update in REMOTE :: " + toBeSyncedLcList);

	// {2} - Iterate them and upload to REMOTE / update existing REMOTE
	// entities
	List<Customer> refreshedRcList = new ArrayList<>(remoteCustList);
	List<Customer> skippedRcList = new ArrayList<>();

	boolean uploadDone = false;
	boolean updateDone = false;
	boolean updateSkipped = false;

	for (Customer lc : toBeSyncedLcList) {
	    System.out.println("\nAttempting UPLOAD/UPDATE operation...");

	    // {3} - Try find existing REMOTE entity matching the LOCAL one
	    Optional<Customer> foundRc = remoteCustList.stream() // nl
		    .filter(rc -> rc.getId() == lc.getId()) // nl
		    .findFirst();

	    // {4.1} - Was not present in REMOTE - upload directly
	    if (!(foundRc.isPresent())) {
		uploadDone = refreshedRcList.add(lc);
		System.out.println("Uploading new customer to REMOTE :: [" + lc + "]");
	    }
	    // {4.2} - Was present before in REMOTE - replace with LOCAL
	    // entity
	    else if (lc.getSyncCounter() == foundRc.get().getSyncCounter()) {
		updateRemoteCustomer(remoteCustList, refreshedRcList, lc, foundRc);
		updateDone = true;
	    }
	    // {4.3} - LOCAL sync count is behind relative to REMOTE - conflicts
	    // found, act accordingly
	    else if (lc.getSyncCounter() < foundRc.get().getSyncCounter()) {
		String diff = getDiffs(lc, foundRc.get());
		String dialogMsg = "LOCAL sync count is behind REMOTE sync count - conflict/s found: \n" + diff
			+ "\n\nOverwrite REMOTE entity with LOCAL changes?";

		DiffOptions[] options = DiffOptions.values();
		DiffOptions defaultOption = DiffOptions.Skip;

		int userDecision = JOptionPane.showOptionDialog(null, dialogMsg, "SYNC CONFLICT",
			JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, defaultOption);

		switch (DiffOptions.valueOfOrdinal(userDecision)) {
		case Overwrite:
		    updateRemoteCustomer(remoteCustList, refreshedRcList, lc, foundRc);
		    updateDone = true;
		    break;
		case Skip:
		default:
		    updateSkipped = skippedRcList.add(foundRc.get());
		    System.err.println("Skipping update on existing REMOTE customer :: [" + foundRc.get() + "]"
			    + "\nDiff: (Field << fieldName >> :: [localValue] => [remoteValue])" + diff);
		    break;
		}
	    }
	    // {4.4} - LOCAL sync count is ahead relative to REMOTE - should NOT
	    // be possible, display error
	    else {
		System.err.println("LOCAL sync count is ahead of REMOTE sync count (ERROR)!");
		Assert.fail("LOCAL sync count is ahead of REMOTE sync count (ERROR)!");
	    }
	}
	System.out.println("---------------------------------------------------------------");

	// {5} - Remove skipped entities from to-be-synced list...
	if (updateSkipped) {
	    toBeSyncedLcList.removeIf(lc -> skippedRcList.stream().anyMatch(rc -> rc.getId() == lc.getId()));
	}

	// {6.1} - Mark all successfully uploaded/updated REMOTE entities as
	// synced (update count = sync count)
	// refreshedRcList.forEach(refreshedRc -> {
	// refreshedRc.setSyncCounter(refreshedRc.getUpdateCounter());
	// });

	// {6.2} - Mark all successfully pushed LOCAL entities as synced (update
	// count = sync count)
	// final LongStream skippedRcIds =
	// skippedRcList.stream().mapToLong(Customer::getId);
	// skippedRcIds.forEach(id -> {
	// localCustList.stream().filter(lc -> lc.getId() !=
	// id).forEach(updatedLc -> {
	// // LOCAL :: Update count is ahead - use to equalize sync count
	// // (mark as synced)
	// updatedLc.setSyncCounter(updatedLc.getUpdateCounter());
	// });
	// });
	// vs
	toBeSyncedLcList.forEach(updatedLc -> {
	    // LOCAL :: Update count is ahead - use to equalize sync count
	    // (mark as synced)
	    localCustList.get(localCustList.indexOf(updatedLc)).setSyncCounter(updatedLc.getUpdateCounter());
	});

	// {7} - Report what entities were uploaded/updated
	System.out.println("\n\nUPLOAD FINISHED:");
	System.out.println("===============================================================");
	System.out.println("Uploaded/updated LOCAL customers to REMOTE :: "
		+ ((uploadDone || updateDone) ? toBeSyncedLcList : "N/A"));
	System.out.println("Skipped update on REMOTE customers :: " + skippedRcList);

	// TODO: Section below ONLY relevant for unit test - format entity
	// lists/arrays to perform assertions

	// Sort by name to enforce same order in both collections
	localCustList.sort(Comparator.comparing(Customer::getName));
	refreshedRcList.sort(Comparator.comparing(Customer::getName));

	System.out.println("\nSUMMARY:");
	System.out.println("===============================================================");
	System.out.println("LOCAL customers final :: " + localCustList);
	System.out.println("REMOTE customers final :: " + refreshedRcList + (updateSkipped ? "*" : ""));
	System.out.println(updateSkipped ? "---------------------------------------------------------------" : "");
	System.out.println(updateSkipped ? "* Skipped update/s in REMOTE - not fully synced." : "");
	Assert.assertTrue("LOCAL customers MISSING (were not synced / skipped) in REMOTE customers!",
		refreshedRcList.containsAll(toBeSyncedLcList));

	customersLocal = localCustList.toArray(new Customer[0]);
	customersRemote = refreshedRcList.toArray(new Customer[0]);
	Assert.assertTrue(customersLocal.length == customersRemote.length);

	// Exclude skipped customers
	Assert.assertTrue(
		"Uploaded/updated LOCAL customers not marked as such (update count != sync count) still exist!",
		toBeSyncedLcList.stream().allMatch(finLc -> finLc.getUpdateCounter() == finLc.getSyncCounter()));
	Assert.assertTrue(
		"Uploaded/updated REMOTE customers not marked as such (update count != sync count) still exist!",
		refreshedRcList.stream().allMatch(finRc -> finRc.getUpdateCounter() == finRc.getSyncCounter()));
    }

    private String getDiffs(Customer localCust, Customer remoteCust) {
	StringBuilder diff = new StringBuilder();

	int diffCount = 0;
	try {
	    Field[] allFields = Customer.class.getSuperclass().getDeclaredFields();
	    for (Field f : allFields) {

		Class<?> fieldDataType = f.getType();

		// Skip collection fields - they represent database
		// relationships AND skip constants...
		if (Collection.class.isAssignableFrom(fieldDataType) || Modifier.isFinal(f.getModifiers())) {
		    continue;
		}

		// Enable access and obtain values...
		f.setAccessible(true);
		Object localValue = f.get(localCust);
		Object remoteValue = f.get(remoteCust);

		if (!(Objects.equals(localValue, remoteValue))) {
		    String fieldName = f.getName();

		    diffCount++;
		    diff.append(MessageFormat.format(FIELD_DIFF_FORMAT, diffCount, fieldName, localValue, remoteValue));
		}
	    }

	    return diff.toString();
	} catch (IllegalArgumentException | IllegalAccessException e) {
	    e.printStackTrace();
	    return "";
	}
    }

    protected void updateRemoteCustomer(List<Customer> remoteCustList, List<Customer> refreshedRcList, Customer lc,
	    Optional<Customer> foundRc) {
	final int rcIdx = remoteCustList.indexOf(foundRc.get());

	refreshedRcList.set(rcIdx, lc);
	System.out.println("Updating existing REMOTE customer :: [" + foundRc.get() + "]");
    }
}
