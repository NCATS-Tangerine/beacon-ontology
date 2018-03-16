package bio.knowledge.ontology.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bio.knowledge.ontology.BiolinkClass;
import bio.knowledge.ontology.BiolinkModel;

/**
 * Singleton class that uses the BiolinkModel to get the Biolink categories
 * that a given category curie map onto
 * 
 * @author Lance Hannestad
 *
 */
public class ModelLookup {
	
	private static Logger _logger = LoggerFactory.getLogger(ModelLookup.class);
	
	private static ModelLookup singleton;
	
	private Map<String, BiolinkClass> mapping = new HashMap<String, BiolinkClass>();
	
	/**
	 * 
	 * @return
	 * An instance of the ModelLookup singleton
	 */
	public static ModelLookup get() {
		if (singleton != null) {
			return singleton;
		} else {
			singleton = new ModelLookup();
			return singleton;
		}
	}
	
	private ModelLookup() {
		InheritanceLookup inheritanceMap = InheritanceLookup.get();
		
		BiolinkModel model = BiolinkModel.get();
		
		for (BiolinkClass c : model.getClasses()) {
			List<String> curies = c.getMappings();
			
			if (curies == null) {
				continue;
			}
			
			for (String curie : curies) {
				if (mapping.containsKey(curie)) {
					String msg1 = "%s maps to both %s and %s";
					_logger.error(String.format(msg1, curie, mapping.get(curie).getName(), c.getName()));
					
					BiolinkClass originalClass = mapping.get(curie);
					
					String msg2 = "Mapping %s to %s";
					
					if (inheritanceMap.getAncestors(originalClass).contains(c)) {
						_logger.warn(String.format(msg2, curie, originalClass.getName()));
						mapping.put(curie, originalClass);
					} else if (inheritanceMap.getAncestors(c).contains(originalClass)) {
						_logger.warn(String.format(msg2, curie, c.getName()));
						mapping.put(curie, c);
					} else {
						String msg3 = "%s maps to categories that share no inheritance relation: %s and %s";
						_logger.error(String.format(msg2, curie, c.getName(), originalClass.getName()));
					}
					
				} else {
					mapping.put(curie, c);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param curie
	 * A curie that identifies a category that maps onto the Biolink Model
	 * @return
	 * The BiolinkClass that the given category maps onto
	 */
	public BiolinkClass lookup(String curie) {
		return mapping.get(curie);
	}
	
	/**
	 * 
	 * @param curie
	 * A curie that identifies a category that maps onto the Biolink Model
	 * @return
	 * The name of the BiolinkClass that the given category maps onto
	 */
	public String lookupName(String curie) {
		BiolinkClass c = lookup(curie);
		return c != null ? c.getName() : null;
	}
	
	/**
	 * 
	 * @param curie
	 * A curie that identifies a category that maps onto the Biolink Model
	 * @return
	 * True if curie is recognized, false otherwise
	 */
	public boolean containsCurie(String curie) {
		return mapping.containsKey(curie);
	}
}
