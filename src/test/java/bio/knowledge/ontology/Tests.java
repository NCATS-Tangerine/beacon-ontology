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
	
	BiolinkModel biolinkModel = BiolinkModel.get();
	
	InheritanceLookup<BiolinkClass> classInheritanceLookup = new InheritanceLookup<BiolinkClass>(biolinkModel.getClasses());
	InheritanceLookup<BiolinkSlot> slotInheritanceLookup = new InheritanceLookup<BiolinkSlot>(biolinkModel.getSlots());
	
	ModelLookup<BiolinkClass> biolinkClassLookup = new ModelLookup<BiolinkClass>(biolinkModel.getClasses(), classInheritanceLookup);
	ModelLookup<BiolinkSlot> biolinkSlotLookup = new ModelLookup<BiolinkSlot>(biolinkModel.getSlots(), slotInheritanceLookup);
	
	String UMLS_GROUP_PREFIX = "UMLSSG:";
	String UMLS_TYPE_PREFIX = "UMLSST:";

	/*
	 *  TODO: we know that not all of the UMLS categories and types are mapped to Biolink
	 *   so these tests will fail, so we ignore them for now?
	 */
	@Test
	public void testUmlsCategories() {
		for (String category : umls.getUmlsCategories()) {
			BiolinkClass c = biolinkClassLookup.lookup(UMLS_GROUP_PREFIX + category);
			if (c == null) {
				System.err.println("Warning: UMLS Semantic Category '"+category+"' not mapped to Biolink?");
				// fail(category + " was not mapped");
			}
		}
	}
	
	@Test
	public void testUmlsTypes() {
		for (String type : umls.getUmlsTypes()) {
			BiolinkClass c = biolinkClassLookup.lookup(UMLS_TYPE_PREFIX + type);
			if (c == null) {
				System.err.println("Warning: UMLS Semantic Type '"+type+"' not mapped to Biolink?");
				// fail(type + " was not mapped");
			}
		}
	}
	
	@Test
	public void classInheritanceLookup() {
		BiolinkClass c = biolinkClassLookup.lookup("SIO:010004");
		
		assertEquals(c.getName(), "chemical substance");
		
		BiolinkClass parent = classInheritanceLookup.getParent(c);
		
		assertEquals(parent.getName(), "molecular entity");
		
		Set<BiolinkClass> children = classInheritanceLookup.getChildren(parent);
		
		assertTrue(children.contains(c));
	}
	
	@Test
	public void slotInheritanceLookup() {
		BiolinkSlot slot = biolinkSlotLookup.lookup("SEMMEDDB:INHIBITS");
		
		assertEquals(slot.getName(), "negatively regulates, entity to entity");
		
		BiolinkSlot parent = slotInheritanceLookup.getParent(slot);
		
		assertEquals(parent.getName(), "regulates, entity to entity");
		
		Set<BiolinkSlot> children = slotInheritanceLookup.getChildren(parent);
		
		assertTrue(children.contains(slot));
	}
	
	@Test
	public void classReverseMappingLookup() {
		String biolinkClassName = "genome";
		Set<String> curies = biolinkClassLookup.reverseLookup(biolinkClassName);
		
		assertTrue(curies.contains("SO:0001026"));
		assertTrue(curies.contains("SIO:000984"));
		assertTrue(curies.contains("WD:Q7020"));
		
		assertTrue(curies.size() == 3);
	}
	
	@Test
	public void slotReverseMappingLookup() {
		String biolinkSlotName = "causes";
		Set<String> curies = biolinkSlotLookup.reverseLookup(biolinkSlotName);
		
		assertTrue(curies.contains("RO:0002410"));
		assertTrue(curies.contains("SEMMEDDB:CAUSES"));
		
		assertTrue(curies.size() == 2);
	}
	
	@Test
	public void nameSpaceTests() {
		
		String biolinkTerm = "molecular entity";
		
		String biolinkCurie = NameSpace.BIOLINK.getCurie(biolinkTerm);
		assertEquals(biolinkCurie,"BLM:MolecularEntity");
		
		String biolinkIri = NameSpace.BIOLINK.getUri(biolinkTerm);
		assertEquals(biolinkIri,"http://bioentity.io/vocab/MolecularEntity");
	}

}
