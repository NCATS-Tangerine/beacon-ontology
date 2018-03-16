package bio.knowledge.ontology;

import static org.junit.Assert.fail;

import org.junit.Test;

import bio.knowledge.ontology.mapping.CategoryMap;
import bio.knowledge.ontology.mapping.InheritanceMap;

public class Tests {
	UmlsContainer umls = new UmlsContainer();
	CategoryMap mapper = new CategoryMap();
	InheritanceMap inheritanceMap = new InheritanceMap();
	BiolinkModel model = BiolinkModel.get();
	
	String UMLS_GROUP_PREFIX = "UMLSSG:";
	String UMLS_TYPE_PREFIX = "UMLSST:";

	@Test
	public void testUmlsCategories() {
		for (String category : umls.getUmlsCategories()) {
			BiolinkClass c = mapper.get(UMLS_GROUP_PREFIX + category);
			if (c == null) {
				fail(category + " was not mapped");
			}
		}
	}
	
	@Test
	public void testUmlsTypes() {
		for (String type : umls.getUmlsTypes()) {
			BiolinkClass c = mapper.get(UMLS_TYPE_PREFIX + type);
			if (c == null) {
				fail(type + " was not mapped");
			}
		}
	}

}
