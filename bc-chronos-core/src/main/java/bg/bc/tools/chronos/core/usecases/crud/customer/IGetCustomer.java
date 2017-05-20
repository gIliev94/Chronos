package bg.bc.tools.chronos.core.usecases.crud.customer;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;

interface IGetCustomer {

    DCustomer getCustomer(long id);

    DCustomer getCustomer(String name);

    List<DCustomer> getCustomers();

    List<DCustomer> getCustomers(DCategory category);

    List<DCustomer> getCustomers(List<DCategory> categories);

    List<DCustomer> getCustomersContaining(String name);
}
