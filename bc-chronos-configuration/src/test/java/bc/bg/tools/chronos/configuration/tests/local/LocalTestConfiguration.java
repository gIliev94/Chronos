package bc.bg.tools.chronos.configuration.tests.local;

import org.springframework.context.annotation.Import;

import bc.bg.tools.chronos.configuration.I18nConfig;
import bc.bg.tools.chronos.configuration.LocalDBConfig;
import bc.bg.tools.chronos.configuration.LocalDataProviderConfig;

@Import(value = { LocalDBConfig.class, LocalDataProviderConfig.class, I18nConfig.class })
public class LocalTestConfiguration {
}
