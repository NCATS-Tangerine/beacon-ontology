package bio.knowledge.ontology;

import java.util.List;

/**
 * See child classes:<br>{@link bio.knowledge.ontology.BiolinkClass}<br>
 * {@link bio.knowledge.ontology.BiolinkSlot}
 *
 */
public interface BiolinkEntityInterface {
	public List<String> getMappings();

	public void setMappings(List<String> mappings);

	public String getName();

	public void setName(String name);

	public String getIs_a();
	
	public void setIs_a(String is_a);

	public String getDescription();

	public void setDescription(String description);
	
}
