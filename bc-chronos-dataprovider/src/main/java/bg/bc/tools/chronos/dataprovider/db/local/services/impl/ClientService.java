package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.core.entities.DCategory;
import bg.bc.tools.chronos.core.entities.DClient;
import bg.bc.tools.chronos.dataprovider.db.entities.Client;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DbToDomainMapper;
import bg.bc.tools.chronos.dataprovider.db.entities.mapping.DomainToDbMapper;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.IClientService;
import bg.bc.tools.chronos.dataprovider.db.repos.ClientRepository;

public class ClientService implements IClientService {

    // private static final Logger LOGGER =
    // Logger.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepo;

    @Override
    public boolean addClient(DClient client) {
	try {
	    clientRepo.save(DomainToDbMapper.domainToDbClient(client));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public DClient getClient(String name) {
	return DbToDomainMapper.dbToDomainClient(clientRepo.findByName(name));
    }

    @Override
    public List<DClient> getClients() {
	return ((List<Client>) clientRepo.findAll()).stream() // nl
		.map(DbToDomainMapper::dbToDomainClient) // nl
		.collect(Collectors.toList());
    }

    @Override
    public List<DClient> getClients(DCategory category) {
	return ((List<Client>) clientRepo.findAll()).stream() // nl
		.filter(c -> Objects.equals(category, c.getCategory())) // nl
		.map(DbToDomainMapper::dbToDomainClient) // nl
		.collect(Collectors.toList());
    }

    @Override
    public boolean updateClient(DClient client) {
	try {
	    if (clientRepo.exists(client.getId())) {
		// LOGGER.info("Updating entity :: " +
		// Client.class.getSimpleName() + " ::" + client.getName());

	    } else {
		// LOGGER.info("No entity found to update :: " +
		// Client.class.getSimpleName() + " ::" + client.getName());
	    }

	    clientRepo.save(DomainToDbMapper.domainToDbClient(client));

	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeClient(DClient client) {
	try {
	    clientRepo.delete(DomainToDbMapper.domainToDbClient(client));
	} catch (Exception e) {
	    // LOGGER.error(e);
	    return false;
	}

	return true;
    }

    @Override
    public boolean removeClient(String clientName) {
	Client client = clientRepo.findByName(clientName);
	return removeClient(DbToDomainMapper.dbToDomainClient(client));
    }

}
