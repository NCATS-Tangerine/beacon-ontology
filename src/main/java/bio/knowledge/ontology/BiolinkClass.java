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

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import bio.knowledge.ontology.utils.Utils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BiolinkClass implements BiolinkEntityInterface {
	
	private String name;
	private String description;
	@JsonProperty(value = "class_uri")  private String class_uri;
	private String is_a;
	private List<String> slots;
	private Object slot_usage;
	private Boolean mixin;
	private List<String> flags;
	
	@JsonProperty(value = "abstract") private Boolean _abstract;
	
	private List<String> mixins;
	
	@JsonProperty(value = "values_from") private List<String> values_from;
	
	@JsonProperty(value = "subclass_of") private String subclass_of;
	private List<String> aliases = new ArrayList<String>();

	@JsonProperty(value = "local_names") private Map<String,String> local_names;

	@JsonProperty(value = "union_of") private List<String> union_of;

	private List<String> mappings;
	
	private String notes;
	
	@JsonProperty(value = "id_prefixes") private List<String> id_prefixes;
	
	private List<String> comments;
	
	@JsonProperty(value = "defining_slots") private List<String> defining_slots;
	
	private Boolean symmetric;
	
	@JsonProperty(value = "see_also") private String see_also;
	
	private String schema;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getObjectId() {
		return Utils.toUpperCamelCase(name);
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassUri() {
		return class_uri;
	}
	public void setClassUri(String class_uri) {
		this.class_uri = class_uri;
	}


	public String getIs_a() {
		return is_a;
	}
	public void setIs_a(String is_a) {
		this.is_a = is_a;
	}

	public List<String> getSlots() {
		return slots;
	}
	public void setSlots(List<String> slots) {
		this.slots = slots;
	}

	public Object getSlot_usage() {
		return slot_usage;
	}
	public void setSlot_usage(Object slot_usage) {
		this.slot_usage = slot_usage;
	}

	// Don't know if this is a unique tag in the model, but...
    @JsonProperty(value = "subproperty_of") private String subproperty_of;

    public String getSubproperty_of() {
        return subproperty_of;
    }
    public void setSubproperty_of(String subproperty_of) {
        this.subclass_of = subproperty_of;
    }

	public Boolean getMixin() {
		return mixin;
	}
	public void setMixin(Boolean mixin) {
		this.mixin = mixin;
	}

	public List<String> getFlags() {
		return flags;
	}
	public void setFlags(List<String> flags) {
		this.flags = flags;
	}

	public Boolean isAbstract() {
		return _abstract;
	}
	public void setIsAbstract(Boolean _abstract) {
		this._abstract = _abstract;
	}

	public List<String> getMixins() {
		return mixins;
	}
	public void setMixins(List<String> mixins) {
		this.mixins = mixins;
	}

	public List<String> getValuesFrom() {
		return values_from;
	}
	public void setValuesFrom(List<String> values_from) {
		this.values_from = values_from;
	}

	public String getSubclassOf() {
		return subclass_of;
	}
	public void setSubclassOf(String subclass_of) {
		this.subclass_of = subclass_of;
	}

	public List<String> getAliases() {
		return aliases;
	}
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public List<String> getUnionOf() {
		return union_of;
	}
	public void setUnionOf(List<String> union_of) {
		this.union_of = union_of;
	}

	public Map<String,String> getLocalNames() {
		return local_names;
	}
	public void setLocalNames(Map<String,String> local_names) {
		this.local_names = local_names;
	}

	public List<String> getMappings() {
		return mappings;
	}
	public void setMappings(List<String> mappings) {
		this.mappings = mappings;
	}

	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<String> getIdPrefixes() {
		return id_prefixes;
	}
	public void setIdPrefixes(List<String> id_prefixes) {
		this.id_prefixes = id_prefixes;
	}

	public List<String> getComments() {
		return comments;
	}
	public void setComment(List<String> comments) {
		this.comments = comments;
	}

	public List<String> getDefiningSlots() {
		return defining_slots;
	}
	public void setDefiningSlots(List<String> defining_slots) {
		this.defining_slots = defining_slots;
	}

	public Boolean getSymmetric() {
		return symmetric;
	}
	public void setSymmetric(Boolean symmetric) {
		this.symmetric = symmetric;
	}

	public String getSeeAlso() {
		return see_also;
	}
	public void setSeeAlso(String see_also) {
		this.see_also = see_also;
	}

	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[name=" + getName() + "]";
	}

}