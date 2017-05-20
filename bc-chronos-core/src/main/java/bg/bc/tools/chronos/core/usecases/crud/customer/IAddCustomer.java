package bg.bc.tools.chronos.core.usecases.crud.customer;

import bg.bc.tools.chronos.core.entities.DCustomer;

interface IAddCustomer {

    boolean addCustomer(DCustomer customer);
}
