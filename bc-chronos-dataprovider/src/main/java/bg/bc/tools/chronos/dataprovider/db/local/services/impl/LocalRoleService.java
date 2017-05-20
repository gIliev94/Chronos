package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalRoleService;

public class LocalRoleService implements ILocalRoleService {

    private static final Logger LOGGER = Logger.getLogger(LocalRoleService.class);

    @Autowired
    private LocalRoleRepository roleRepo;
}
