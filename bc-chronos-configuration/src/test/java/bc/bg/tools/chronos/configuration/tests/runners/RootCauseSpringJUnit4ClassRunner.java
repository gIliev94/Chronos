package bc.bg.tools.chronos.configuration.tests.runners;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test runner that attempts to extract and display root cause exception
 * message when a test fails. Targeted at Spring-related tests (see super class
 * {@link SpringJUnit4ClassRunner})
 * 
 * @author giliev
 */
public class RootCauseSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {

    public RootCauseSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
	super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
	// TODO: https://stackoverflow.com/a/32081772
	notifier.addListener(RootCauseTestRunListener.getInstance());
	super.run(notifier);
    }
}
