package bio.knowledge.ontology.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import bio.knowledge.ontology.BiolinkClass;
import bio.knowledge.ontology.BiolinkModel;

public class ModelLookup {
	public static final String UMLS_PREFIX = "UMLS:";
	
	private Map<String, String> umlsMap = new HashMap<String, String>();
	
	public static void main(String[] args) {
		ModelLookup lookup = new ModelLookup();
		
		BiolinkClass c = lookup.lookupBestFit("DISO");
		System.out.println(c.getName());
	}
	
	String lookup(String curie) {
		if (curie.startsWith(UMLS_PREFIX)) {
			return umlsLookup(curie.substring(UMLS_PREFIX.length()));
		} else {
			return null;
		}
	}
	
	WikiDataBiolinkMapping wikidataMapping = new WikiDataBiolinkMapping();
	UMLSBiolinkMapping umlsMapping = new UMLSBiolinkMapping();
	
	public String lookup(NameSpace namespace, String category) {
		
		return null;
	}
	
	
	/**
	 * Finds the Biolink category with the shortest name that matches
	 * the given category. Considers both the Biolink category's name
	 * as well as aliases.
	 * 
	 * This method may return null if nothing fits.
	 */
	public BiolinkClass lookupBestFit(String category) {
		Optional<BiolinkModel> opt = BiolinkModel.load();
		BiolinkModel model = opt.get();
		
		List<BiolinkClass> matches = new ArrayList<BiolinkClass>();
		
		for (BiolinkClass biolinkClass : model.getClasses()) {
			String name = biolinkClass.getName().toLowerCase().trim();
			if (contains(name, category)) {
				matches.add(biolinkClass);
			} else {
				for (String alias : biolinkClass.getAliases()) {
					if (contains(alias, category)) {
						matches.add(biolinkClass);
					}
				}
			}
		}
		
		BiolinkClass biolinkClass = null;
		int shortest = Integer.MAX_VALUE;
		
		for (BiolinkClass match : matches) {
			if (match.getName().length() < shortest) {
				biolinkClass = match;
				shortest = match.getName().length();
			}
		}
		
		return biolinkClass;
	}
	
	String umlsLookup(String umls) {
		return null;
	}
	
	/**
	 * Performs a case insensitive check whether a is
	 * contained in b or b is contained in a.
	 */
	private boolean contains(String a, String b) {
		String lowerA = a.toLowerCase();
		String lowerB = b.toLowerCase();
		return lowerB.contains(lowerA) || lowerA.contains(lowerB);
	}
}
