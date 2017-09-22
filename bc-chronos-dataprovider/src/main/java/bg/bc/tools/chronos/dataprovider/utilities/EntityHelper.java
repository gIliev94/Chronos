package bg.bc.tools.chronos.dataprovider.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBillingRateModifierRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalBookingRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalPerformerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalProjectRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalTaskRepository;

/**
 * Provides utility functions related to attributes of entities.
 * 
 * @author giliev
 */
public final class EntityHelper {

    private static final String DEFAULT_COMPUTER_NAME = "UNKNOWN";

    private static final String VAR_COMPUTER_NAME = "COMPUTERNAME";
    private static final String VAR_HOST_NAME = "HOSTNAME";

    /**
     * The command used to determine device hardware name on UNIX-based systems.
     *
     * @see <a href="https://www.computerhope.com/unix/uuname.htm">UNAME
     *      function documentation</a>
     */
    private static final String COMMAND_UNIX_COMPUTER_NAME = "uname -m";

    private EntityHelper() {
    }

    // TODO: See
    // https://stackoverflow.com/questions/7883542/getting-the-computer-name-in-java/17956000#17956000

    /**
     *
     * 
     * @return The device hardware OR network name, depending on the OS
     *         implementation.
     */
    public static String getDeviceName() {
	final Map<String, String> systemEnvironment = System.getenv();

	// In case of Windows OS
	if (systemEnvironment.containsKey(VAR_COMPUTER_NAME)) {
	    return systemEnvironment.get(VAR_COMPUTER_NAME);
	} else if (systemEnvironment.containsKey(VAR_HOST_NAME)) {
	    return systemEnvironment.get(VAR_HOST_NAME);
	}
	// In case of other OS (i.e. UNIX-based)
	else {
	    try {
		final String hostName = InetAddress.getLocalHost().getHostName();
		return hostName;
	    } catch (UnknownHostException uhExc) {
		// Final resort - try to extract machine name using UNAME
		// function
		final Runtime processRuntime = Runtime.getRuntime();
		final Process process;
		try {

		    process = processRuntime.exec(COMMAND_UNIX_COMPUTER_NAME);
		    final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    return reader.readLine();
		} catch (IOException ioExc) {
		    return DEFAULT_COMPUTER_NAME;
		}
	    }
	}
    }
}
