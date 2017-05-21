package bg.bc.tools.chronos.core.usecases.crud.category;

import java.util.List;

import bg.bc.tools.chronos.core.entities.DCategory;

interface IGetCategory {

    DCategory getCategory(long id);

    DCategory getCategory(String name);

    List<DCategory> getCategories();
}
