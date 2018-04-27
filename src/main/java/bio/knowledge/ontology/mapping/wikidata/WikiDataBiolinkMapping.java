/**
 * 
 */
package bio.knowledge.ontology.mapping.wikidata;

import bio.knowledge.ontology.BiolinkTerm;
import bio.knowledge.ontology.mapping.BiolinkModelMapping;

/**
 * @author richard
 *
 */
public class WikiDataBiolinkMapping extends BiolinkModelMapping {

	private static final long serialVersionUID = 5362827597807692913L;
	
	/*
	 *  TODO: Greg Stuppie may need to populate this table 
	 *  with the list of Wikidata "concept type" properties 
	 *  with their mappings to the Biolink Model
	 */
	WikiDataBiolinkMapping() {
		put("wd:Q12140",BiolinkTerm.DRUG);                // "Drug"
		put("wd:Q7187", BiolinkTerm.GENE);                // "Gene"
		put("wd:Q11173",BiolinkTerm.CHEMICAL_SUBSTANCE);  // "Chemical compound"
		put("wd:Q8054", BiolinkTerm.PROTEIN);             // "Protein"
		put("wd:Q12136",BiolinkTerm.DISEASE);             // "Disease"
	}
}
