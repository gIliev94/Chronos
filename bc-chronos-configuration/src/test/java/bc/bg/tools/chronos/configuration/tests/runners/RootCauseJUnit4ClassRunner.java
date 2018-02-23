package bc.bg.tools.chronos.configuration.tests.runners;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Unit test runner that attempts to extract and display root cause exception
 * message when a test fails. Generic test runner (see super class
 * {@link BlockJUnit4ClassRunner})
 * 
 * @author giliev
 */
public class RootCauseJUnit4ClassRunner extends BlockJUnit4ClassRunner {

    public RootCauseJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
	super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
	// TODO: https://stackoverflow.com/a/32081772
	notifier.addListener(RootCauseTestRunListener.getInstance());
	super.run(notifier);
    }

}
