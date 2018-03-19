package bio.knowledge.ontology;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import bio.knowledge.ontology.mapping.InheritanceLookup;
import bio.knowledge.ontology.mapping.ModelLookup;

public class Tests {
	UmlsContainer umls = new UmlsContainer();
	
	ModelLookup modelLookup = ModelLookup.get();
	
	String UMLS_GROUP_PREFIX = "UMLSSG:";
	String UMLS_TYPE_PREFIX = "UMLSST:";

	@Test
	public void testUmlsCategories() {
		for (String category : umls.getUmlsCategories()) {
			BiolinkClass c = modelLookup.lookup(UMLS_GROUP_PREFIX + category);
			if (c == null) {
				fail(category + " was not mapped");
			}
		}
	}
	
	@Test
	public void testUmlsTypes() {
		for (String type : umls.getUmlsTypes()) {
			BiolinkClass c = modelLookup.lookup(UMLS_TYPE_PREFIX + type);
			if (c == null) {
				fail(type + " was not mapped");
			}
		}
	}
	
	@Test
	public void testInheritanceLookup() {
		// Very basic test to show how this class is used
		
		InheritanceLookup inheritanceLookup = InheritanceLookup.get();
		BiolinkClass c = modelLookup.lookup("SIO:010004");
		
		assertEquals(c.getName(), "chemical substance");
		
		BiolinkClass parent = inheritanceLookup.getParent(c);
		
		assertEquals(parent.getName(), "molecular entity");
		
		Set<BiolinkClass> children = inheritanceLookup.getChildren(parent);
		
		assertTrue(children.contains(c));
	}
	
	@Test
	public void reverseMappingLookup() {
		String biolinkClassName = "genome";
		Set<String> curies = modelLookup.reverseLookup(biolinkClassName);
		
		assertTrue(curies.contains("SO:0001026"));
		assertTrue(curies.contains("SIO:000984"));
		assertTrue(curies.contains("WD:Q7020"));
		
		assertTrue(curies.size() == 3);
	}

}
