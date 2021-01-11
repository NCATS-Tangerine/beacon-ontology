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

import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import bio.knowledge.ontology.utils.Utils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BiolinkClass extends BiolinkEntity {

	public String getObjectId() {
		return Utils.toUpperCamelCase(getName());
	}

	@JsonProperty(value = "slots") private List<String> slots;
	public List<String> getSlots() {
		return slots;
	}
	public void setSlots(List<String> slots) {
		this.slots = slots;
	}

	@JsonProperty(value = "slot_usage") private Object slot_usage;
	public Object getSlot_usage() {
		return slot_usage;
	}
	public void setSlot_usage(Object slot_usage) {
		this.slot_usage = slot_usage;
	}

	@JsonProperty(value = "flags") private List<String> flags;
	public List<String> getFlags() {
		return flags;
	}
	public void setFlags(List<String> flags) {
		this.flags = flags;
	}

	@JsonProperty(value = "values_from") private List<String> values_from;
	public List<String> getValuesFrom() {
		return values_from;
	}
	public void setValuesFrom(List<String> values_from) {
		this.values_from = values_from;
	}

	@JsonProperty(value = "union_of") private List<String> union_of;
	public List<String> getUnionOf() {
		return union_of;
	}
	public void setUnionOf(List<String> union_of) {
		this.union_of = union_of;
	}

	@JsonProperty(value = "local_names") private Map<String,String> local_names;
	public Map<String,String> getLocalNames() {
		return local_names;
	}
	public void setLocalNames(Map<String,String> local_names) {
		this.local_names = local_names;
	}

	@JsonProperty(value = "notes") private List<String> notes;
	public List<String> getNotes() {
		return notes;
	}
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	@JsonProperty(value = "id_prefixes") private List<String> id_prefixes;
	public List<String> getIdPrefixes() {
		return id_prefixes;
	}
	public void setIdPrefixes(List<String> id_prefixes) {
		this.id_prefixes = id_prefixes;
	}

	@JsonProperty(value = "comments") private List<String> comments;
	public List<String> getComments() {
		return comments;
	}
	public void setComment(List<String> comments) {
		this.comments = comments;
	}

	@JsonProperty(value = "defining_slots") private List<String> defining_slots;
	public List<String> getDefiningSlots() {
		return defining_slots;
	}
	public void setDefiningSlots(List<String> defining_slots) {
		this.defining_slots = defining_slots;
	}

	@JsonProperty(value = "symmetric") private Boolean symmetric;
	public Boolean getSymmetric() {
		return symmetric;
	}
	public void setSymmetric(Boolean symmetric) {
		this.symmetric = symmetric;
	}

	@JsonProperty(value = "see_also") private String see_also;
	public String getSeeAlso() {
		return see_also;
	}
	public void setSeeAlso(String see_also) {
		this.see_also = see_also;
	}

	@Override
	public String toString() {
		return super.toString() + "[name=" + getName() + "]";
	}

}