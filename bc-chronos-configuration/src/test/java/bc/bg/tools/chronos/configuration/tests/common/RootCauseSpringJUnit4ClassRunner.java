package bc.bg.tools.chronos.configuration.tests.common;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Custom test runner for Spring-related tests that outputs only the root cause exception stack.
 * 
 * @author giliev
 */
public class RootCauseSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {

    public RootCauseSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
	super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
	//TODO: https://stackoverflow.com/a/32081772
	notifier.addListener(new RunListener() {
	    @Override
	    public void testFailure(Failure failure) throws Exception {
		ExceptionUtils.getRootCause(failure.getException()).printStackTrace(System.err);
	    }
	});
	super.run(notifier);
    }
}
