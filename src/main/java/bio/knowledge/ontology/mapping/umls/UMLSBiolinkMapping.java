/**
 * 
 */
package bio.knowledge.ontology.mapping.umls;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bio.knowledge.ontology.BiolinkEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bio.knowledge.ontology.BiolinkTerm;
import bio.knowledge.ontology.mapping.BiolinkModelMapping;
import bio.knowledge.ontology.mapping.NameSpace;

/**
 * @author richard
 *
 */
public class UMLSBiolinkMapping extends BiolinkModelMapping {

	private static final long serialVersionUID = 373558122178877621L;
	private static Logger _logger = LoggerFactory.getLogger(UMLSBiolinkMapping.class);
	
	public UMLSBiolinkMapping() {
		
		 put("OBJC",BiolinkTerm.NAMED_THING);           // "Objects"
		 put("ACTI",BiolinkTerm.ACTIVITY_AND_BEHAVIOR); // "Activities & Behaviors"
		 put("ANAT",BiolinkTerm.ANATOMICAL_ENTITY);     // "Anatomy"
		 put("CHEM",BiolinkTerm.CHEMICAL_SUBSTANCE);    // "Chemicals & Drugs"
		 put("CONC",BiolinkTerm.INFORMATION_CONTENT_ENTITY); // "Concepts & Ideas"
		 put("DEVI",BiolinkTerm.DEVICE);                // "Devices"
		 put("DISO",BiolinkTerm.DISEASE);               // "Disorders"
		 put("GENE",BiolinkTerm.GENOMIC_ENTITY);        // "Genes & Molecular Sequences"
		 put("GEOG",BiolinkTerm.GEOGRAPHIC_LOCATION);   // "Geographic Areas"
		 put("LIVB",BiolinkTerm.ORGANISMAL_ENTITY);     // "Living Beings"
		 put("OCCU",BiolinkTerm.NAMED_THING);            // "Occupations" not directly tracked in Biolink
		 put("ORGA",BiolinkTerm.ADMINISTRATIVE_ENTITY); // "Organizations"
		 put("PHEN",BiolinkTerm.PHENOMENON);            // "Phenomena"
		 put("PHYS",BiolinkTerm.PHYSIOLOGY);            // "Physiology"
		 put("PROC",BiolinkTerm.PROCEDURE);             // "Procedures"
		
	}
	
	/*********************** New Direct Biolink Model Configuration **********************/
	
	/**
	 * 
	 * @param semGroup
	 * @return
	 */
	public String umlsToBiolinkLabel(String semGroup) {
		return classModelLookup.lookupName(NameSpace.UMLSSG.getPrefix() + semGroup);
	}
	
	/**
	 * Returns a list of UMLS categories that map onto the given
	 * biolink classes. If no mapping exists for some biolink class
	 * then a NO_MATCH string is added to the list to ensure that
	 * filter is active (resulting in no matches for that filter).
	 * 
	 * @param biolinkClasses
	 * @return
	 */
	public List<String> biolinkToUmls(List<String> biolinkClasses) {
		if (biolinkClasses == null) {
			return null;
		}
		
		Set<BiolinkEntity> classes = new HashSet<>();
		
		for (String name : biolinkClasses) {
			BiolinkEntity c = classModelLookup.getClassByName(name);
			if (c != null) {
				classes.add(c);
				classes.addAll(classInheritanceLookup.getDescendants(c));
			} else {
				_logger.info(name + " does not appear to be a BiolinkClass");
			}
		}
		
		biolinkClasses = classes.stream().map(c -> c.getName()).collect(Collectors.toList());
		
		Set<String> curies = new HashSet<>();
		
		for (String biolinkClass : biolinkClasses) {
			String curie = biolinkToUmls(biolinkClass);
			if (curie != null) {
				curie = curie.replace(NameSpace.UMLSSG.getPrefix(), "");
				curies.add(curie);
			} else {
				curies.add("NO_MATCH[" + biolinkClass + "]");
			}
		}
		
		return new ArrayList<String>(curies);
	}
	
	/*
	 * 
	 */
	private String biolinkToUmls(String biolinkClassName) {
		BiolinkEntity biolinkClass = classModelLookup.getClassByName(biolinkClassName);
		
		if (biolinkClass != null) {
			for (String curie : biolinkClass.getMappings()) {
				if (curie.startsWith(NameSpace.UMLSSG.getPrefix())) {
					return curie;
				}
			}
		}
		
		return null;
	}
}
