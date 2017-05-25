package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DBooking;
import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DRole;
import bg.bc.tools.chronos.dataprovider.db.entities.Booking;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.Role;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalRoleService;

public class LocalRoleService implements ILocalRoleService {

    private static final Logger LOGGER = Logger.getLogger(LocalRoleService.class);

    @Autowired
    private LocalRoleRepository roleRepo;

    @Override
    public boolean addRole(DRole role) {
	try {
	    role.setSyncKey(UUID.randomUUID().toString());
	    roleRepo.save(DomainToDbMapper.domainToDbRole(role));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DRole getRole(long id) {
	return DbToDomainMapper.dbToDomainRole(roleRepo.findOne(id));
    }

    @Override
    public DRole getRole(String name) {
	return DbToDomainMapper.dbToDomainRole(roleRepo.findByName(name));
    }

    @Override
    public DRole getRole(DBooking booking) {
	final Booking dbBooking = DomainToDbMapper.domainToDbBooking(booking);
	return DbToDomainMapper.dbToDomainRole(roleRepo.findByBooking(dbBooking));
    }

    @Override
    public List<DRole> getRoles() {
	return ((List<Role>) roleRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainRole) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DRole> getRolesContaining(String name) {
	return roleRepo.findByNameIgnoreCaseContaining(name).stream() // nl
		.map(DbToDomainMapper::dbToDomainRole) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DRole> getRoles(DCategory category) {
	final Category dbCategory = DomainToDbMapper.domainToDbCategory(category);

	return roleRepo.findByCategory(dbCategory).stream() // nl
		.map(DbToDomainMapper::dbToDomainRole) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DRole> getRoles(List<DCategory> categories) {
	final List<Category> dbCategories = categories.stream() // nl
		.map(DomainToDbMapper::domainToDbCategory) // nl
		.collect(Collectors.toList());

	return roleRepo.findByCategoryIn(dbCategories).stream() // nl
		.map(DbToDomainMapper::dbToDomainRole) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DRole> getRolesLessThan(double billingRateLessThan) {
	return roleRepo.findByBillingRateLessThan(billingRateLessThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainRole) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DRole> getRolesGreaterThan(double billingRateGreaterThan) {
	return roleRepo.findByBillingRateGreaterThan(billingRateGreaterThan).stream() // nl
		.map(DbToDomainMapper::dbToDomainRole) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DRole> getRolesBetween(double billingRateLower, double billingRateUpper) {
	return roleRepo.findByBillingRateBetween(billingRateLower, billingRateUpper).stream() // nl
		.map(DbToDomainMapper::dbToDomainRole) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateRole(DRole role) {
	try {
	    if (roleRepo.exists(role.getId())) {
		LOGGER.info("Updating entity :: " + Role.class.getSimpleName() + " :: " + role.getName());
		role.setSyncKey(UUID.randomUUID().toString());
		roleRepo.save(DomainToDbMapper.domainToDbRole(role));
	    } else {
		LOGGER.info("No entity found to update :: " + Role.class.getSimpleName() + " :: " + role.getName());
	    }
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeRole(long id) {
	final Role dbRole = roleRepo.findOne(id);
	return removeRole(DbToDomainMapper.dbToDomainRole(dbRole));
    }

    @Override
    public boolean removeRole(DRole role) {
	try {
	    roleRepo.delete(DomainToDbMapper.domainToDbRole(role));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeRole(DBooking booking) {
	return removeRole(booking.getRole());
    }
}
