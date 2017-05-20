package bc.bg.tools.chronos.configuration.tests.remote;

import org.springframework.context.annotation.Import;

import bc.bg.tools.chronos.configuration.I18nConfig;
import bc.bg.tools.chronos.configuration.RemoteDBConfig;
import bc.bg.tools.chronos.configuration.RemoteDataProviderConfig;

@Import(value = { RemoteDBConfig.class, RemoteDataProviderConfig.class, I18nConfig.class })
public class RemoteTestConfiguration {
}
