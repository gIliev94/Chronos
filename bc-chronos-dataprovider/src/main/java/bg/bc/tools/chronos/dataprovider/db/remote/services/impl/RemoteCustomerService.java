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
    // Logger.getLogger(ClientService.class);

    @Autowired
    private RemoteCustomerRepository clientRepo;

    @Override
    public boolean addClient(DCustomer client) {
	try {
	    clientRepo.save(DomainToDbMapper.domainToDbCustomer(client));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DCustomer getClient(String name) {
	return DbToDomainMapper.dbToDomainCustomer(clientRepo.findByName(name));
    }

    @Override
    public List<DCustomer> getClients() {
	return ((List<Customer>) clientRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainCustomer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DCustomer> getClients(DCategory category) {
	return ((List<Customer>) clientRepo.findAll()).stream() // nl
		.filter(c -> Objects.equals(category, c.getCategory())) // nl
		.map(DbToDomainMapper::dbToDomainCustomer) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateClient(DCustomer client) {
	try {
	    if (clientRepo.exists(client.getId())) {
		// LOGGER.info("Updating entity :: " +
		// Client.class.getSimpleName() + " ::" + client.getName());

	    } else {
		// LOGGER.info("No entity found to update :: " +
		// Client.class.getSimpleName() + " ::" + client.getName());
	    }

	    clientRepo.save(DomainToDbMapper.domainToDbCustomer(client));

	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeClient(DCustomer client) {
	try {
	    clientRepo.delete(DomainToDbMapper.domainToDbCustomer(client));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeClient(String clientName) {
	Customer client = clientRepo.findByName(clientName);
	return removeClient(DbToDomainMapper.dbToDomainCustomer(client));
    }

}
