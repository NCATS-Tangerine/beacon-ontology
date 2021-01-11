/*-------------------------------------------------------------------------------
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-18 STAR Informatics / Delphinai Corporation (Canada) - Dr. Richard Bruskiewich
 * Copyright (c) 2017    NIH National Center for Advancing Translational Sciences (NCATS)
 * Copyright (c) 2015-16 Scripps Institute (USA) - Dr. Benjamin Good
 *                       
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *-------------------------------------------------------------------------------
 */
package bio.knowledge.ontology;

import java.util.List;

import bio.knowledge.ontology.mapping.NameSpace;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * See child classes:<br>{@link bio.knowledge.ontology.BiolinkClass}<br>
 * {@link bio.knowledge.ontology.BiolinkSlot}
 *
 */
abstract public class BiolinkEntity {

	public BiolinkEntity() {}

	@JsonProperty(value = "is_a") private String is_a;
	public String getIs_a() {
		return is_a;
	}
	public void setIs_a(String is_a) {
		this.is_a = is_a;
	}

	@JsonProperty(value = "abstract") private Boolean _abstract;
	public Boolean isAbstract() {
		return _abstract;
	}
	public void setIsAbstract(Boolean _abstract) {
		this._abstract = _abstract;
	}

	@JsonProperty(value = "mixin") private Boolean mixin;
	public Boolean getMixin() {
		return mixin;
	}
	public void setMixin(Boolean mixin) {
		this.mixin = mixin;
	}

	@JsonProperty(value = "mixins") private List<String> mixins;
	public List<String> getMixins() {
		return mixins;
	}
	public void setMixins(List<String> mixins) {
		this.mixins = mixins;
	}

	@JsonProperty(value = "name") private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) { this.name = name; }

	@JsonProperty(value = "aliases") private List<String> aliases;
	public List<String> getAliases() {
		return aliases;
	}
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	@JsonProperty(value = "description") private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	abstract public String getObjectId();

	/**
	 * Gets a "default" CURIE ID of a given Biolink Model term
	 * in case an explicit curie is not available for a given ter
	 */
	public String getDefaultCurie() {
		
		String id = getObjectId();
		
		if (id == null) {
			return null;
		} else {
			return NameSpace.BIOLINK.getPrefix() + getObjectId();
		}
	}

	/**
	 * @return URI of the given Biolink Model term.
	 */
	public String getUri() {
		return NameSpace.BIOLINK.getBaseUri() + getObjectId();
	}

	/**
	 * @return the CURIE of the given Biolink Model term.
	 */
	public String getCurie() {
		return NameSpace.BIOLINK.getPrefix() + getObjectId();
	}

	@JsonProperty(value = "in_subset") private List<String> in_subset;
	public List<String> getInSubset() {
		return in_subset;
	}
	public void setInSubset(List<String> in_subset) {
		this.in_subset = in_subset;
	}

	// Original aggregate undifferentiated 'mappings' - deprecated?
	@JsonProperty(value = "mappings") private List<String> mappings;
	public List<String> getMappings() {
		return mappings;
	}
	public void setMappings(List<String> mappings) {
		this.mappings = mappings;
	}

	@JsonProperty(value = "exact_mappings") private List<String> exact_mappings;
	public List<String> getExactMappings() {
		return exact_mappings;
	}
	public void setExactMappings(List<String> exact_mappings) {
		this.exact_mappings = exact_mappings;
	}

	@JsonProperty(value = "close_mappings") private List<String> close_mappings;
	public List<String> getCloseMappings() {
		return close_mappings;
	}
	public void setCloseMappings(List<String> close_mappings) {
		this.close_mappings = close_mappings;
	}

	@JsonProperty(value = "related_mappings") private List<String> related_mappings;
	public List<String> getRelatedMappings() {
		return related_mappings;
	}
	public void setRelatedMappings(List<String> related_mappings) {
		this.related_mappings = related_mappings;
	}

	@JsonProperty(value = "broad_mappings") private List<String> broad_mappings;
	public List<String> getBroadMappings() {
		return broad_mappings;
	}
	public void setBroadMappings(List<String> broad_mappings) {
		this.broad_mappings = broad_mappings;
	}

	@JsonProperty(value = "narrow_mappings") private List<String> narrow_mappings;
	public List<String> getNarrowMappings() { return narrow_mappings; }
	public void setNarrowMappings(List<String> narrow_mappings) {
		this.narrow_mappings = narrow_mappings;
	}
}
