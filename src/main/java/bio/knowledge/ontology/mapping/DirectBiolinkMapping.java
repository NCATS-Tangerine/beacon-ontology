/**
 * 
 */
package bio.knowledge.ontology.mapping;

import bio.knowledge.ontology.BiolinkTerm;
import bio.knowledge.ontology.BeaconBiolinkModel;
/**
 * @author richard
 *
 */
public class DirectBiolinkMapping extends BiolinkModelMapping {

	private static final long serialVersionUID = -4624889836940523935L;
	private static final String BLM = BeaconBiolinkModel.BIOLINK_MODEL_NAMESPACE;
			
	DirectBiolinkMapping() {
		put(BLM+":NamedThing",BiolinkTerm.NAMED_THING);
		put(BLM+":ActivityAndBehavior",BiolinkTerm.ACTIVITY_AND_BEHAVIOR);
		put(BLM+":AdministrativeEntity",BiolinkTerm.ADMINISTRATIVE_ENTITY);
		put(BLM+":Device",BiolinkTerm.DEVICE);
		put(BLM+":AnatomicalEntity",BiolinkTerm.ANATOMICAL_ENTITY);
		put(BLM+":GenomicEntity",BiolinkTerm.GENOMIC_ENTITY);
		put(BLM+":Gene",BiolinkTerm.GENE);
		put(BLM+":Protein",BiolinkTerm.PROTEIN);
		put(BLM+":Disease",BiolinkTerm.DISEASE);
		put(BLM+":SequenceVariant",BiolinkTerm.SEQUENCE_VARIANT);
		put(BLM+":Phenotype",BiolinkTerm.PHENOTYPE);
		put(BLM+":ChemicalSubstance",BiolinkTerm.CHEMICAL_SUBSTANCE);
		put(BLM+":Drug",BiolinkTerm.DRUG);
		put(BLM+":GeographicLocation",BiolinkTerm.GEOGRAPHIC_LOCATION);
		put(BLM+":OrganismalEntity",BiolinkTerm.ORGANISMAL_ENTITY);
		put(BLM+":IndividualOrganism",BiolinkTerm.INDIVIDUAL_ORGANISM);
		put(BLM+":BiologicalProcess",BiolinkTerm.BIOLOGICAL_PROCESS);
		put(BLM+":Physiology",BiolinkTerm.PHYSIOLOGY);
		put(BLM+":InformationContentEntity",BiolinkTerm.INFORMATION_CONTENT_ENTITY);
		put(BLM+":Procedure",BiolinkTerm.PROCEDURE);
		put(BLM+":Phenomenon",BiolinkTerm.PHENOMENON);
	}
}
