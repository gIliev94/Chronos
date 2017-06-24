package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DCustomer;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Changelog;
import bg.bc.tools.chronos.dataprovider.db.entities.Customer;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalChangelogRepository;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCustomerRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCustomerService;

@Service
public class LocalCustomerService implements ILocalCustomerService {

    private static final Logger LOGGER = Logger.getLogger(LocalCustomerService.class);

    @Autowired
    private LocalCustomerRepository customerRepo;

    @Autowired
    private LocalCategoryRepository categoryRepo;

    @Autowired
    private LocalChangelogRepository changelogRepo;

    @Override
    // @Transactional
    public DCustomer addCustomer(DCustomer customer) {
	try {
	    customer.setSyncKey(UUID.randomUUID().toString());
	    if (customer.getCategory().getSyncKey() == null) {
		customer.getCategory().setSyncKey(UUID.randomUUID().toString());
	    }

	    final Customer persistedCustomer = customerRepo.save(DomainToDbMapper.domainToDbCustomer(customer));
	    return DbToDomainMapper.dbToDomainCustomer(persistedCustomer);

	    // customer.setSyncKey(UUID.randomUUID().toString());
	    //
	    // final Category dbCategory =
	    // categoryRepo.findByName(customer.getCategory().getName());
	    // // final Category dbCategory = getReferencedCustomer(customer);
	    // final Customer dbCustomer =
	    // DomainToDbMapper.domainToDbCustomer(customer);
	    // dbCustomer.setCategory(dbCategory);
	    //
	    // customerRepo.save(dbCustomer);
	    // return null;
	} catch (Exception e) {
	    // TODO: Debug only - remove later...
	    LOGGER.error(e);
	    throw new RuntimeException(e);
	}
    }

    public DCustomer addCustomerWithReferences(DCustomer newCustomer, DCategory refCategory) {
	final Category managedRefCategory = DomainToDbMapper.domainToDbCategory(newCustomer.getCategory());
	// save already does save OR merge
	// https://stackoverflow.com/questions/24420572/update-or-saveorupdate-in-crudrespository-is-there-any-options-available
	categoryRepo.save(managedRefCategory);
	System.err.println(managedRefCategory.getSyncKey());

	final Customer managedNewCustomer = DomainToDbMapper.domainToDbCustomer(newCustomer);
	managedNewCustomer.setCategory(managedRefCategory);
	customerRepo.save(managedNewCustomer);

	final Changelog changeLog = new Changelog();
	changeLog.setChangeTime(Calendar.getInstance().getTime());
	changeLog.setDeviceName("THIS_DEV_NAME");
	changeLog.setUpdatedEntityKey(managedNewCustomer.getSyncKey());
	changelogRepo.save(changeLog);

	System.err
		.println("KEYS MATCH :: " + (managedNewCustomer.getSyncKey().equals(changeLog.getUpdatedEntityKey())));

	return DbToDomainMapper.dbToDomainCustomer(managedNewCustomer);
    }

    @Transactional(readOnly = true, propagation = Propagation.NESTED)
    private Category getReferencedCustomer(DCustomer customer) {
	final Category dbCategory = categoryRepo.findByName(customer.getCategory().getName());
	return dbCategory;
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
		customer.setSyncKey(UUID.randomUUID().toString());
		customerRepo.save(DomainToDbMapper.domainToDbCustomer(customer));
	    } else {
		LOGGER.info(
			"No entity found to update :: " + Customer.class.getSimpleName() + " :: " + customer.getName());
	    }
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
