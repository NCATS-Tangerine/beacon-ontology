package bio.knowledge.ontology;

import java.util.List;

import bio.knowledge.ontology.utils.Utils;

/**
 * See child classes:<br>{@link bio.knowledge.ontology.BiolinkClass}<br>
 * {@link bio.knowledge.ontology.BiolinkSlot}
 *
 */
public interface BiolinkEntityInterface {
	
	public static final String BIOLINK_MODEL_PREFIX = "BLM:";
	
	public List<String> getMappings();

	public void setMappings(List<String> mappings);

	public String getName();

	public void setName(String name);

	public String getIs_a();
	
	public void setIs_a(String is_a);

	public String getDescription();

	public void setDescription(String description);
	
	/**
	 * Gets the CURIE ID of the given entity.
	 */
	public default String getCurie() {
		String name = getName();
		
		if (name == null) {
			return null;
		} else {
			return BIOLINK_MODEL_PREFIX + Utils.toUpperCamelCase(name);
		}
	}
	
}
