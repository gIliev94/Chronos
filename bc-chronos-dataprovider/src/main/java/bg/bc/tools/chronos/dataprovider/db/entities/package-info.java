// M2A attempt - only relevant in that case, maybe keep to use as javadoc specification to the whole package...
@AnyMetaDef(name = "CategoricalEntityMetaDef", metaType = "string", idType = "long", metaValues = { // nl
	@MetaValue(value = "C", targetEntity = Customer.class), // nl
	@MetaValue(value = "P", targetEntity = Project.class), // nl
	@MetaValue(value = "T", targetEntity = Task.class), // nl
})
/**
 * Contains Hibernate to DB mapping entities
 * 
 * @author giliev
 */
package bg.bc.tools.chronos.dataprovider.db.entities;

import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
