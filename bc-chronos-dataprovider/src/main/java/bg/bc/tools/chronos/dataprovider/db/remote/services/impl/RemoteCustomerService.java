package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteCustomerService;

public class RemoteCustomerService implements IRemoteCustomerService {

    // private static final Logger LOGGER =
    // Logger.getLogger(CustomerService.class);

    @Autowired
    private RemoteCustomerRepository customerRepo;

    @Override
    public boolean addCustomer(DCustomer customer) {
	try {
	    customerRepo.save(DomainToDbMapper.domainToDbCustomer(customer));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DCustomer getCustomer(String name) {
	return DbToDomainMapper.dbToDomainCustomer(customerRepo.findByName(name));
    }

    @Override
    public List<DCustomer> getCustomers() {
	return ((List<Customer>) customerRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainCustomer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DCustomer> getCustomers(DCategory category) {
	return ((List<Customer>) customerRepo.findAll()).stream() // nl
		.filter(c -> Objects.equals(category, c.getCategory())) // nl
		.map(DbToDomainMapper::dbToDomainCustomer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateCustomer(DCustomer customer) {
	try {
	    if (customerRepo.exists(customer.getId())) {
		// LOGGER.info("Updating entity :: " +
		// Customer.class.getSimpleName() + " ::" + customer.getName());

	    } else {
		// LOGGER.info("No entity found to update :: " +
		// Customer.class.getSimpleName() + " ::" + customer.getName());
	    }

	    customerRepo.save(DomainToDbMapper.domainToDbCustomer(customer));

	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeCustomer(DCustomer customer) {
	try {
	    customerRepo.delete(DomainToDbMapper.domainToDbCustomer(customer));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeCustomer(String customerName) {
	Customer customer = customerRepo.findByName(customerName);
	return removeCustomer(DbToDomainMapper.dbToDomainCustomer(customer));
    }

}
