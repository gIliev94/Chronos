package bg.bc.tools.chronos.dataprovider.db.remote.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bg.bc.tools.chronos.dataprovider.db.remote.repos.RemoteRoleRepository;
import bg.bc.tools.chronos.dataprovider.db.remote.services.ifc.IRemoteRoleService;

public class RemoteRoleService implements IRemoteRoleService {

    @Autowired
    private RemoteRoleRepository roleRepo;
}
