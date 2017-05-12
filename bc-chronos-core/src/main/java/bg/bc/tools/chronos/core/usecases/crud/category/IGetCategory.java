package bg.bc.tools.chronos.core.usecases.crud.category;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;

interface IGetCategory {

    DCategory getCategory(long id);

    List<DCategory> getCategories();

    List<DCategory> getCategories(String name);

    List<DCategory> getCategories(String name, int sortOrder);
}
