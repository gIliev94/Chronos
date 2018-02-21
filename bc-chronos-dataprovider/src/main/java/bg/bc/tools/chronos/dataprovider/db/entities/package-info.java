// M2A attempt - only relevant in that case, maybe keep to use as javadoc specification to the whole package...
@AnyMetaDef(name = "CategoricalEntityMetaDef", metaType = "string", idType = "long", metaValues = { // nl
	@MetaValue(value = "C", targetEntity = Customer.class), // nl
	@MetaValue(value = "P", targetEntity = Project.class), // nl
	@MetaValue(value = "T", targetEntity = Task.class), // nl
})
//TODO: Consider
// https://www.google.bg/search?q=prefer_sequence_per_entity&oq=prefer_sequence_per_entity&aqs=chrome..69i57&sourceid=chrome&ie=UTF-8
//https://stackoverflow.com/a/23007244
/**
 * Contains Hibernate to DB mapping entities
 * 
 * @author giliev
 */
package bg.bc.tools.chronos.dataprovider.db.entities;

import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
