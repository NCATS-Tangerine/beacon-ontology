package bio.knowledge.ontology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import bio.knowledge.ontology.BiolinkModel.BiolinkClass;

public class ModelLookup {
	public static final String UMLS_PREFIX = "UMLS:";
	
	private Map<String, String> umlsMap = new HashMap<String, String>();
	
	public static void main(String[] args) {
		ModelLookup lookup = new ModelLookup();
		
		BiolinkClass c = lookup.lookupRegex("gene production");
		System.out.println(c.getName());
	}
	
	String lookup(String curie) {
		if (curie.startsWith(UMLS_PREFIX)) {
			return umlsLookup(curie.substring(UMLS_PREFIX.length()));
		} else {
			return null;
		}
	}
	
	public BiolinkClass lookupRegex(String category) {
		Optional<BiolinkModel> opt = BiolinkModel.load();
		BiolinkModel model = opt.get();
		
		List<BiolinkClass> matches = new ArrayList<BiolinkClass>();
		
		for (BiolinkClass biolinkClass : model.getClasses()) {
			String name = biolinkClass.getName().toLowerCase().trim();
			if (name.contains(category.toLowerCase().trim())) {
				matches.add(biolinkClass);
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
}
