package bc.bg.tools.chronos.configuration.tests.remote;

import org.springframework.context.annotation.Import;

import bc.bg.tools.chronos.configuration.RemoteDBConfig;
import bc.bg.tools.chronos.configuration.RemoteDataProviderConfig;

@Import(value = { RemoteDBConfig.class, RemoteDataProviderConfig.class })
public class RemoteTestConfiguration {
}
