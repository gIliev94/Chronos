package bg.bc.tools.chronos.core.usecases.crud.client;

import bg.bc.tools.chronos.core.entities.DClient;

interface IRemoveClient {

    boolean removeClient(DClient client);

    boolean removeClient(String clientName);
}
