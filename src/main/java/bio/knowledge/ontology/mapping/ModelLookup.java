package bio.knowledge.ontology.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import bio.knowledge.ontology.BiolinkEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author Lance
 *
 * Update, January 2021
 * @author Richard
 *
 * Either a BiolinkClass or a BiolinkSlot
 */
public class ModelLookup {
	
	private static final Logger _logger = LoggerFactory.getLogger(ModelLookup.class);
	
	private final Map<String, BiolinkEntity> mapping = new HashMap<>();

	private final Map<String, Set<String>> reverseMapping = new HashMap<>();
	private final Map<String, BiolinkEntity> nameMapping = new HashMap<>();

	private void loadMappings(
			BiolinkEntity c,
			Function<BiolinkEntity, List<String>> getTheMappings,
			InheritanceLookup inheritanceMap,
			Set<String> curies
	) {

		List<String> mappings = getTheMappings.apply(c);
		if (mappings != null) {
			curies.addAll(mappings);
		}

		for (String curie : curies) {

			if (mapping.containsKey(curie)) {

				String msg1 = "%s maps to both %s and %s";

				_logger.error(String.format(msg1, curie, mapping.get(curie).getName(), c.getName()));

				BiolinkEntity originalClass = mapping.get(curie);

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

	public ModelLookup(
			List<? extends BiolinkEntity> entities,
			InheritanceLookup inheritanceMap
	) {
		for (BiolinkEntity c : entities) {

			Set<String> curies = new HashSet<>();

			// Capture the canonical identifier of the entity
			curies.add(c.getCurie());

			// Brute force loading of all the mappings, irrespective of their SKOS match nature
			loadMappings(c, BiolinkEntity::getMappings,       inheritanceMap, curies) ;
			loadMappings(c, BiolinkEntity::getExactMappings,  inheritanceMap, curies) ;
			loadMappings(c, BiolinkEntity::getCloseMappings,  inheritanceMap, curies) ;
			loadMappings(c, BiolinkEntity::getRelatedMappings,inheritanceMap, curies) ;
			loadMappings(c, BiolinkEntity::getBroadMappings,  inheritanceMap, curies) ;
			loadMappings(c, BiolinkEntity::getNarrowMappings, inheritanceMap, curies) ;
		}

		for (String k : mapping.keySet()) {

			BiolinkEntity c = mapping.get(k);

			nameMapping.put(c.getName(), c);

			Set<String> curies = reverseMapping.get(c.getName());

			if (curies == null) {
				curies = new HashSet<>();
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
	public BiolinkEntity lookup(String curie) {
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
		BiolinkEntity c = lookup(curie);
		return c != null ? c.getName() : null;
	}
	
	public String lookupDescription(String curie) {
		BiolinkEntity c = lookup(curie);
		return c != null ? c.getDescription() : "";
	}
	
	/**
	 * Gets the set of curies that map onto the given T name
	 */
	public Set<String> reverseLookup(String biolinkClassName) {
		Set<String> set = reverseMapping.get(biolinkClassName);
		return Collections.unmodifiableSet(set != null ? set : new HashSet<>());
	}

	public BiolinkEntity getClassByName(String biolinkClassName) {
		return nameMapping.get(biolinkClassName);
	}
}
