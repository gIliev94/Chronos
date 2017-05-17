package bg.bc.tools.chronos.dataprovider.db.local.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.dataprovider.db.local.repos.LocalRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.local.services.ifc.ILocalRoleService;

public class LocalRoleService implements ILocalRoleService {

    @Autowired
    private LocalRoleRepository roleRepo;
}
