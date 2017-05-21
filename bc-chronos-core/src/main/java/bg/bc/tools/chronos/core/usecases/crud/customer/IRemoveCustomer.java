package bg.bc.tools.chronos.core.usecases.crud.customer;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;

interface IRemoveCustomer {

    boolean removeCustomer(long id);

    boolean removeCustomer(DCustomer customer);

    boolean removeCustomer(String customerName);

    boolean removeCustomers(DCategory category);
}
