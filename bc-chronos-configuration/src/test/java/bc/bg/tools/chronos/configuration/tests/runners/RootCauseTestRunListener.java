package bc.bg.tools.chronos.configuration.tests.runners;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Enhanced Unit test runner that attempts to extract and display root cause
 * exception message. Falls back to original exception message in case root
 * cause cannot be determined.
 * 
 * @author giliev
 */
public final class RootCauseTestRunListener extends RunListener {

    private static final Logger LOGGER = Logger.getLogger(RootCauseTestRunListener.class);

    @Override
    public void testFailure(Failure failure) throws Exception {
	final Throwable originalException = failure.getException();
	final Throwable rootCauseException = ExceptionUtils.getRootCause(originalException);

	final Throwable displayedException = (rootCauseException != null) ? rootCauseException : originalException;
	displayedException.printStackTrace(System.err);
	LOGGER.error(displayedException);
    }

    public static final RootCauseTestRunListener getInstance() {
	return new RootCauseTestRunListener();
    }
}
