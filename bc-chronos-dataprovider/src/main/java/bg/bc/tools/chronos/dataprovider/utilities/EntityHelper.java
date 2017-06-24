package bg.bc.tools.chronos.dataprovider.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public final class EntityHelper {

    private EntityHelper() {
    }

    // https://stackoverflow.com/questions/7883542/getting-the-computer-name-in-java/17956000#17956000
    // https://developer.atlassian.com/confdev/development-resources/confluence-architecture/hibernate-sessions-and-transaction-management-guidelines
    /**
     * @return
     */
    public static String getComputerName() {
	Map<String, String> env = System.getenv();
	if (env.containsKey("COMPUTERNAME")) {
	    return env.get("COMPUTERNAME");
	} else if (env.containsKey("HOSTNAME")) {
	    return env.get("HOSTNAME");
	} else {
	    try {
		final String hostName = InetAddress.getLocalHost().getHostName();
		return hostName;
	    } catch (UnknownHostException e) {
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
		    p = r.exec("uname -a");
		    BufferedReader rdr = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    // System.out.println(r.readLine());
		    return rdr.readLine();
		} catch (IOException e1) {
		    return "UNKNOWN";
		}
	    }
	}
    }
}
