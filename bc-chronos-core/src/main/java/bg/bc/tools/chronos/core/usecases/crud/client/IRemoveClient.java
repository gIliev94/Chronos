package bg.bc.tools.chronos.core.usecases.crud.client;

import bg.bc.tools.chronos.core.entities.DCustomer;

interface IRemoveClient {

    boolean removeClient(DCustomer client);

    boolean removeClient(String clientName);
}
