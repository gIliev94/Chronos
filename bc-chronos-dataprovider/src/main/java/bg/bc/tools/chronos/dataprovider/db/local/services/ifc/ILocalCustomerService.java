package bg.bc.tools.chronos.dataprovider.db.local.services.ifc;

import java.util.Map;
import java.util.function.Function;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.core.entities.DObject;
import bg.bc.tools.chronos.core.usecases.crud.customer.ICustomerCrud;

public interface ILocalCustomerService extends ICustomerCrud {

    // /**
    // * @param category
    // * @return
    // */
    // @Transactional(readOnly = true)
    // boolean setManagedReferences(Customer customer);

    // /**
    // * @return
    // */
    // @Transactional(readOnly = true)
    // Customer fetchManagedEntity(DCustomer customer);
    
    DCustomer addCustomerWithReferences(DCustomer newCustomer, DCategory refCategory);
    
    DCustomer fetchReferencedEntities(DCustomer customer, Function<DCategory, Void> refCategorySetter);
}
