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
 * Uses the BiolinkModel to get the Biolink categories that are mapped to
 * by the given category curie
 * 
 * @author Lance Hannestad
 *
 */
public class CategoryMap {
	
	private static Logger _logger = LoggerFactory.getLogger(CategoryMap.class);
	
	private Map<String, BiolinkClass> mapping = new HashMap<String, BiolinkClass>();
	
	public CategoryMap() {
		InheritanceMap inheritanceMap = new InheritanceMap();
		
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
	
	public BiolinkClass get(String curie) {
		return mapping.get(curie);
	}
	
	public boolean containsCurie(String curie) {
		return mapping.containsKey(curie);
	}
}
