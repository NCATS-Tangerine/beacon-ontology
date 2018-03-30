/**
 * 
 */
package bio.knowledge.ontology.mapping;

import bio.knowledge.ontology.BiolinkTerm;

/**
 * @author richard
 *
 */
public class UMLSBiolinkMapping extends BiolinkModelMapping {

	private static final long serialVersionUID = 373558122178877621L;
	
	UMLSBiolinkMapping() {
		
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
}
