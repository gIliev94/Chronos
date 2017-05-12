package bg.bc.tools.chronos.core.usecases.crud.category;

import bg.bc.tools.chronos.core.entities.DCategory;

interface IRemoveCategory {

    boolean removeCategory(DCategory category);

    boolean removeCategory(long id);

    boolean removeCategory(String name);

    boolean removeCategory(String name, int sortOrder);
}
