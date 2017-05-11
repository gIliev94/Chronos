package bg.bc.tools.chronos.core.usecases.crud.client;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DClient;

interface IGetClient {

    DClient getClient(String name);

    List<DClient> getClients();

    List<DClient> getClients(DCategory category);
}
