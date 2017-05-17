package bg.bc.tools.chronos.core.usecases.crud.client;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;

interface IGetClient {

    DCustomer getClient(String name);

    List<DCustomer> getClients();

    List<DCustomer> getClients(DCategory category);
}
