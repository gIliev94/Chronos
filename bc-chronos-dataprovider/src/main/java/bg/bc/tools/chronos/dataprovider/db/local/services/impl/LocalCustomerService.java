package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;

public class LocalCustomerService implements ILocalCustomerService {

    private static final Logger LOGGER = Logger.getLogger(LocalCustomerService.class);

    @Autowired
    private LocalCustomerRepository customerRepo;

    @Override
    public boolean addCustomer(DCustomer customer) {
	try {
	    customerRepo.save(DomainToDbMapper.domainToDbCustomer(customer));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DCustomer getCustomer(long id) {
	return DbToDomainMapper.dbToDomainCustomer(customerRepo.findOne(id));
    }

    @Override
    public DCustomer getCustomer(String name) {
	return DbToDomainMapper.dbToDomainCustomer(customerRepo.findByName(name));
    }

    @Override
    public List<DCustomer> getCustomersContaining(String name) {
	return customerRepo.findByNameIgnoreCaseContaining(name).stream() // nl
		.map(DbToDomainMapper::dbToDomainCustomer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DCustomer> getCustomers() {
	return ((List<Customer>) customerRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainCustomer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DCustomer> getCustomers(DCategory category) {
	final Category dbCategory = DomainToDbMapper.domainToDbCategory(category);

	return customerRepo.findByCategory(dbCategory).stream() // nl
		.map(DbToDomainMapper::dbToDomainCustomer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DCustomer> getCustomers(List<DCategory> categories) {
	final List<Category> dbCategories = categories.stream() // nl
		.map(DomainToDbMapper::domainToDbCategory) // nl
		.collect(Collectors.toList());

	return customerRepo.findByCategoryIn(dbCategories).stream() // nl
		.map(DbToDomainMapper::dbToDomainCustomer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateCustomer(DCustomer customer) {
	try {
	    if (customerRepo.exists(customer.getId())) {
		LOGGER.info("Updating entity :: " + Customer.class.getSimpleName() + " :: " + customer.getName());
	    } else {
		LOGGER.info(
			"No entity found to update :: " + Customer.class.getSimpleName() + " :: " + customer.getName());
	    }

	    customerRepo.save(DomainToDbMapper.domainToDbCustomer(customer));

	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeCustomer(long id) {
	final Customer dbCustomer = customerRepo.findOne(id);
	return removeCustomer(DbToDomainMapper.dbToDomainCustomer(dbCustomer));
    }

    @Override
    public boolean removeCustomer(DCustomer customer) {
	try {
	    customerRepo.delete(DomainToDbMapper.domainToDbCustomer(customer));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeCustomer(String name) {
	final Customer dbCustomer = customerRepo.findByName(name);
	return removeCustomer(DbToDomainMapper.dbToDomainCustomer(dbCustomer));
    }

    @Override
    public boolean removeCustomers(DCategory category) {
	try {
	    final Category dbCategory = DomainToDbMapper.domainToDbCategory(category);

	    customerRepo.findByCategory(dbCategory) // nl
		    .forEach(c -> removeCustomer(c.getId()));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }
}
