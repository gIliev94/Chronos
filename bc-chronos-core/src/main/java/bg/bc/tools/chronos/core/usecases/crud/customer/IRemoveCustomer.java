package bg.bc.tools.chronos.core.usecases.crud.customer;

import bg.bc.tools.chronos.core.entities.DCustomer;

interface IRemoveCustomer {

    boolean removeCustomer(DCustomer customer);

    boolean removeCustomer(String customerName);
}
