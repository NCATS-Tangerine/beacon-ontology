package bio.knowledge.ontology.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bio.knowledge.ontology.BiolinkEntityInterface;

/**
 * 
 * @author lance
 *
 * @param <T>
 * Either a BiolinkClass or a BiolinkSlot
 */
public class ModelLookup<T extends BiolinkEntityInterface> {
	
	private static Logger _logger = LoggerFactory.getLogger(ModelLookup.class);
	
	private Map<String, T> mapping = new HashMap<String, T>();
	private Map<String, Set<String>> reverseMapping = new HashMap<String, Set<String>>();
	private Map<String, T> nameMapping = new HashMap<String, T>();
	
	public ModelLookup(List<T> entities, InheritanceLookup<T> inheritanceMap) {		
		for (T c : entities) {
			List<String> curies = c.getMappings();
			
			if (curies == null) {
				continue;
			}
			
			for (String curie : curies) {
				if (mapping.containsKey(curie)) {
					String msg1 = "%s maps to both %s and %s";
					_logger.error(String.format(msg1, curie, mapping.get(curie).getName(), c.getName()));
					
					T originalClass = mapping.get(curie);
					
					String msg2 = "Mapping %s to %s";
					
					if (inheritanceMap.getAncestors(originalClass).contains(c)) {
						_logger.warn(String.format(msg2, curie, originalClass.getName()));
						mapping.put(curie, originalClass);
					} else if (inheritanceMap.getAncestors(c).contains(originalClass)) {
						_logger.warn(String.format(msg2, curie, c.getName()));
						mapping.put(curie, c);
					} else {
						String msg3 = "%s maps to categories that share no inheritance relation: %s and %s";
						_logger.error(String.format(msg3, curie, c.getName(), originalClass.getName()));
					}
					
				} else {
					mapping.put(curie, c);
				}
			}
		}
		
		for (String k : mapping.keySet()) {
			T c = mapping.get(k);
			nameMapping.put(c.getName(), c);
			
			Set<String> curies = reverseMapping.get(c.getName());
			
			if (curies == null) {
				curies = new HashSet<String>();
			}
			
			curies.add(k);
			
			reverseMapping.put(c.getName(), curies);
		}
	}
	
	/**
	 * 
	 * @param curie
	 * A curie that identifies a category that maps onto the Biolink Model
	 * 
	 * @return
	 * The BiolinkClass that the given category maps onto
	 */
	public T lookup(String curie) {
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
		T c = lookup(curie);
		return c != null ? c.getName() : null;
	}
	
	public String lookupDescription(String curie) {
		T c = lookup(curie);
		return c != null ? c.getDescription() : "";
	}
	
	/**
	 * Gets the set of curies that map onto the given T name
	 */
	public Set<String> reverseLookup(String biolinkClassName) {
		Set<String> set = reverseMapping.get(biolinkClassName);
		return Collections.unmodifiableSet(set != null ? set : new HashSet<String>());
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
	
	public T getClassByName(String biolinkClassName) {
		return nameMapping.get(biolinkClassName);
	}
}
