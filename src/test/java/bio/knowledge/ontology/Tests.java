package bio.knowledge.ontology;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;

//import org.junit.Ignore;
import org.junit.Assert;
import org.junit.Test;

import bio.knowledge.ontology.mapping.InheritanceLookup;
import bio.knowledge.ontology.mapping.ModelLookup;
import bio.knowledge.ontology.mapping.NameSpace;

public class Tests {
	UmlsContainer umls = new UmlsContainer();

	BeaconBiolinkModel biolinkModel = BeaconBiolinkModel.get();

	List<? extends BiolinkEntity> classes = biolinkModel.getClasses();
	InheritanceLookup classInheritanceLookup = new InheritanceLookup(classes);
	ModelLookup biolinkClassLookup = new ModelLookup(classes, classInheritanceLookup);

	List<? extends BiolinkEntity> slots = biolinkModel.getSlots() ;
	InheritanceLookup slotInheritanceLookup  = new InheritanceLookup(slots);
	ModelLookup biolinkSlotLookup  = new ModelLookup(slots,   slotInheritanceLookup);

	String UMLS_GROUP_PREFIX = "UMLSSG:";
	String UMLS_CODE_PREFIX  = "UMLSSC:";
	String UMLS_TYPE_PREFIX  = "UMLSST:";

	@Test
	public void testUmlsCategories() {
		for (String category : umls.getUmlsCategories()) {
			BiolinkEntity c = biolinkClassLookup.lookup(UMLS_GROUP_PREFIX + category);
			if (c == null) {
				System.err.println("Warning: UMLS Semantic Category '"+category+"' not mapped to Biolink?");
				// fail(category + " was not mapped");
			}
		}
	}

	@Test
	public void testUmlsCodes() {
		for (String code : umls.getUmlsCodes()) {
			BiolinkEntity c = biolinkClassLookup.lookup(UMLS_CODE_PREFIX + code);
			if (c == null) {
				System.err.println("Warning: UMLS Semantic Code '"+code+"' not mapped to Biolink?");
				// fail(code + " was not mapped");
			}
		}
	}

	@Test
	public void testUmlsTypes() {
		for (String type : umls.getUmlsTypes()) {
			BiolinkEntity c = biolinkClassLookup.lookup(UMLS_TYPE_PREFIX + type);
			if (c == null) {
				System.err.println("Warning: UMLS Semantic Type '"+type+"' not mapped to Biolink?");
				// fail(type + " was not mapped");
			}
		}
	}

	@Test
	public void classInheritanceLookup() {
		BiolinkEntity c = biolinkClassLookup.lookup("SIO:010004");

		assertEquals(c.getName(), "chemical substance");

		BiolinkEntity parent = classInheritanceLookup.getParent(c);

		assertEquals(parent.getName(), "molecular entity");

		Set<BiolinkEntity> children = classInheritanceLookup.getChildren(parent);

		Assert.assertTrue(children.contains(c));
	}

	@Test
	public void slotInheritanceLookup() {
		BiolinkEntity slot = biolinkSlotLookup.lookup("SEMMEDDB:IS_A");

		assertEquals(slot.getName(), "subclass of");

		BiolinkEntity parent = slotInheritanceLookup.getParent(slot);

		assertEquals(parent.getName(), "related to");

		Set<BiolinkEntity> children = slotInheritanceLookup.getChildren(parent);

		Assert.assertTrue(children.contains(slot));
	}

	@Test
	public void classReverseMappingLookup() {
		String biolinkClassName = "genome";
		Set<String> curies = biolinkClassLookup.reverseLookup(biolinkClassName);

		/*
		 SO:0001026 is a "class_uri" so needs to be
		 uncovered by the reverseLookup in this manner now
		 */
		Assert.assertTrue(curies.contains("biolink:Genome"));
		Assert.assertTrue(curies.contains("SO:0001026"));
		Assert.assertTrue(curies.contains("SIO:000984"));
		Assert.assertTrue(curies.contains("WIKIDATA:Q7020"));

		assertEquals(4, curies.size());
	}

	@Test
	public void slotReverseMappingLookup() {
		String biolinkSlotName = "causes";
		Set<String> curies = biolinkSlotLookup.reverseLookup(biolinkSlotName);

		Assert.assertTrue(curies.contains("biolink:causes"));
		Assert.assertTrue(curies.contains("RO:0002410"));
		Assert.assertTrue(curies.contains("SEMMEDDB:CAUSES"));
		Assert.assertTrue(curies.contains("WIKIDATA_PROPERTY:P1542"));
		Assert.assertTrue(curies.contains("MONDO:disease_triggers"));
	}

	@Test
	public void nameSpaceTests() {

		String biolinkTerm = "molecular entity";

		String biolinkCurie = NameSpace.BIOLINK.getCurie(biolinkTerm);
		assertEquals(biolinkCurie,"biolink:MolecularEntity");

		String biolinkIri = NameSpace.BIOLINK.getUri(biolinkTerm);
		assertEquals(biolinkIri,NameSpace.BIOLINK.getBaseUri()+"MolecularEntity");
	}

}
