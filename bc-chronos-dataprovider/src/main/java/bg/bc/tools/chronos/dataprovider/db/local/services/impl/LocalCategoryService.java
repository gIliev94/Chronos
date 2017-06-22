package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.dataprovider.db.entities.Category;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalCategoryRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalCategoryService;

public class LocalCategoryService implements ILocalCategoryService {

    private static final Logger LOGGER = Logger.getLogger(LocalCategoryService.class);

    @Autowired
    private LocalCategoryRepository categoryRepo;

    @Override
    @Transactional
    public boolean addCategory(DCategory category) {
	try {
	    category.setSyncKey(UUID.randomUUID().toString());
	    categoryRepo.save(DomainToDbMapper.domainToDbCategory(category));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DCategory getCategory(long id) {
	return DbToDomainMapper.dbToDomainCategory(categoryRepo.findOne(id));
    }

    @Override
    public DCategory getCategory(String name) {
	return DbToDomainMapper.dbToDomainCategory(categoryRepo.findByName(name));
    }

    @Override
    public List<DCategory> getCategories() {
	return ((List<Category>) categoryRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainCategory) // nl
		.collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean updateCategory(DCategory category) {
	try {
	    if (categoryRepo.exists(category.getId())) {
		LOGGER.info("Updating entity :: " + Category.class.getSimpleName() + " :: " + category.getName());
		// category.setSyncKey(UUID.randomUUID().toString());
		categoryRepo.save(DomainToDbMapper.domainToDbCategory(category));
	    } else {
		LOGGER.info(
			"No entity found to update :: " + Category.class.getSimpleName() + " :: " + category.getName());
	    }
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeCategory(long id) {
	final Category dbCategory = categoryRepo.findOne(id);
	return removeCategory(DbToDomainMapper.dbToDomainCategory(dbCategory));
    }

    @Override
    public boolean removeCategory(String name) {
	final Category dbCategory = categoryRepo.findByName(name);
	return removeCategory(DbToDomainMapper.dbToDomainCategory(dbCategory));
    }

    @Override
    public boolean removeCategory(DCategory category) {
	try {
	    categoryRepo.delete(DomainToDbMapper.domainToDbCategory(category));
	} catch (Exception e) {
	    LOGGER.error(e);
	    return false;
	}

	return true;
    }
}
