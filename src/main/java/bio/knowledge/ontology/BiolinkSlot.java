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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import bio.knowledge.ontology.utils.Utils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BiolinkSlot extends BiolinkEntity {

	public String getObjectId() {
		return Utils.toSnakeCase(getName());
	}

	@JsonProperty(value = "slot_uri") private String slot_uri;
	public String getSlotUri() { return slot_uri; }
	public void setSlotUri(String slot_uri) {
		this.slot_uri = slot_uri;
	}

	@JsonProperty(value = "domain") private String domain;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@JsonProperty(value = "range") private String range;
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}

	@JsonProperty(value = "inverse") private String inverse;
	public String getInverse() {
		return inverse;
	}
	public void setInverse(String inverse) {
		this.inverse = inverse;
	}

	@JsonProperty(value = "required") private Boolean required;
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}

	@JsonProperty(value = "subproperty_of") private String subproperty_of;
	public String getSubproperty_of() { return subproperty_of; }
	public void setSubproperty_of(String subproperty_of) { this.subproperty_of = subproperty_of; }

	@Override
	public String toString() {
		return super.toString() + "[name=" + getName() + "]";
	}

}
