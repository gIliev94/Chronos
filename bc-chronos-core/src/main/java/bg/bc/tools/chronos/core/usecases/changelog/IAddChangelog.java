package bg.bc.tools.chronos.core.usecases.changelog;

import bg.bc.tools.chronos.core.entities.DChangelog;

interface IAddChangelog {

    boolean addChangelog(DChangelog changelog);
}
