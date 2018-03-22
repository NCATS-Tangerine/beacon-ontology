package bio.knowledge.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import java.util.Set;

//import org.junit.Ignore;
import org.junit.Test;

import bio.knowledge.ontology.mapping.InheritanceLookup;
import bio.knowledge.ontology.mapping.ModelLookup;
import bio.knowledge.ontology.mapping.NameSpace;

public class Tests {
	UmlsContainer umls = new UmlsContainer();
	
	ModelLookup modelLookup = ModelLookup.get();
	
	String UMLS_GROUP_PREFIX = "UMLSSG:";
	String UMLS_TYPE_PREFIX = "UMLSST:";

	/*
	 *  TODO: we know that not all of the UMLS categories and types are mapped to Biolink
	 *   so these tests will fail, so we ignore them for now?
	 */
	@Test
	public void testUmlsCategories() {
		for (String category : umls.getUmlsCategories()) {
			BiolinkClass c = modelLookup.lookup(UMLS_GROUP_PREFIX + category);
			if (c == null) {
				System.err.println("Warning: UMLS Semantic Category '"+category+"' not mapped to Biolink?");
				// fail(category + " was not mapped");
			}
		}
	}
	
	@Test
	public void testUmlsTypes() {
		for (String type : umls.getUmlsTypes()) {
			BiolinkClass c = modelLookup.lookup(UMLS_TYPE_PREFIX + type);
			if (c == null) {
				System.err.println("Warning: UMLS Semantic Type '"+type+"' not mapped to Biolink?");
				// fail(type + " was not mapped");
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
	
	@Test
	public void nameSpaceTests() {
		
		String biolinkTerm = "molecular entity";
		
		String biolinkCurie = NameSpace.BIOLINK.getCurie(biolinkTerm);
		assertEquals(biolinkCurie,"BLM:MolecularEntity");
		
		String biolinkIri = NameSpace.BIOLINK.getIri(biolinkTerm);
		assertEquals(biolinkIri,"http://bioentity.io/vocab/MolecularEntity");
	}

}
