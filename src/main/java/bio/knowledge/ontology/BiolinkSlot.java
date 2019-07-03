package bio.knowledge.ontology;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import bio.knowledge.ontology.utils.Utils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BiolinkSlot implements BiolinkEntityInterface {
	
	private String name;
	private String description;
	private List<String> mappings;
	@JsonProperty(value = "slot_uri") private String slot_uri;
	private String is_a;
	@JsonProperty(value = "in_subset") private List<String> in_subset;
	private String domain;
	private String range;
	private String inverse;
	private Boolean mixin;
	private Boolean required;
	private List<String> aliases;
	
	@JsonProperty(value = "abstract")
	private Boolean isAbstract;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getObjectId() {
		return Utils.toSnakeCase(name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getMappings() {
		return mappings;
	}

	public void setMappings(List<String> mappings) {
		this.mappings = mappings;
	}

	public String getSlotUri() {
		return slot_uri;
	}

	public void setSlotUri(String slot_uri) {
		this.slot_uri = slot_uri;
	}

	public String getIs_a() {
		return is_a;
	}

	public void setIs_a(String is_a) {
		this.is_a = is_a;
	}

	public List<String> getInSubset() {
		return in_subset;
	}

	public void setInSubset(List<String> in_subset) {
		this.in_subset = in_subset;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getInverse() {
		return inverse;
	}

	public void setInverse(String inverse) {
		this.inverse = inverse;
	}

	public Boolean getMixin() {
		return mixin;
	}

	public void setMixin(Boolean mixin) {
		this.mixin = mixin;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[name=" + getName() + "]";
	}

}
