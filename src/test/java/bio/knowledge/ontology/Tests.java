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

	BeaconBiolinkModel biolinkModel = BeaconBiolinkModel.get();

	InheritanceLookup<BiolinkClass> classInheritanceLookup = new InheritanceLookup<BiolinkClass>(biolinkModel.getClasses());
	InheritanceLookup<BiolinkSlot> slotInheritanceLookup = new InheritanceLookup<BiolinkSlot>(biolinkModel.getSlots());

	ModelLookup<BiolinkClass> biolinkClassLookup = new ModelLookup<BiolinkClass>(biolinkModel.getClasses(), classInheritanceLookup);
	ModelLookup<BiolinkSlot> biolinkSlotLookup = new ModelLookup<BiolinkSlot>(biolinkModel.getSlots(), slotInheritanceLookup);

	String UMLS_GROUP_PREFIX = "UMLSSG:";
	String UMLS_CODE_PREFIX  = "UMLSSC:";
	String UMLS_TYPE_PREFIX  = "UMLSST:";

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
	public void testUmlsCodes() {
		for (String code : umls.getUmlsCodes()) {
			BiolinkClass c = biolinkClassLookup.lookup(UMLS_CODE_PREFIX + code);
			if (c == null) {
				System.err.println("Warning: UMLS Semantic Code '"+code+"' not mapped to Biolink?");
				// fail(code + " was not mapped");
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

		/*
		 SO:0001026 is a "class_uri" so needs to be
		 uncovered by the reverseLookup in this manner now
		 */
		assertTrue(curies.contains("SO:0001026"));
		assertTrue(curies.contains("SIO:000984"));
		assertTrue(curies.contains("WIKIDATA:Q7020"));

		assertTrue(curies.size() == 3);
	}

	@Test
	public void slotReverseMappingLookup() {
		String biolinkSlotName = "causes";
		Set<String> curies = biolinkSlotLookup.reverseLookup(biolinkSlotName);

		assertTrue(curies.contains("RO:0002410"));
		assertTrue(curies.contains("SEMMEDDB:CAUSES"));
		assertTrue(curies.contains("WIKIDATA:P1542"));
		assertTrue(curies.contains("MONDO:disease_triggers"));

		assertTrue(curies.size() == 3);
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
