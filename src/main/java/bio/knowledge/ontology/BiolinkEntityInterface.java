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

/**
 * See child classes:<br>{@link bio.knowledge.ontology.BiolinkClass}<br>
 * {@link bio.knowledge.ontology.BiolinkSlot}
 *
 */
public interface BiolinkEntityInterface {
	
	List<String> getMappings();

	void setMappings(List<String> mappings);

	String getName();

	void setName(String name);
	
	String getObjectId();

	String getIs_a();
	
	void setIs_a(String is_a);

	String getDescription();

	void setDescription(String description);

	String getCurie();
	
	/**
	 * Gets a "default" CURIE ID of a given Biolink Model term
	 * in case an explicit curie is not available for a given ter
	 */
	default String getDefaultCurie() {
		
		String id = getObjectId();
		
		if (id == null) {
			return null;
		} else {
			return NameSpace.BIOLINK.getPrefix() + getObjectId();
		}
	}
	
	/**
	 * Gets the URI ID of the given Biolink Model term.
	 */
	default String getUri() {
		
		String name = getName();
		
		if (name == null) {
			return null;
		} else {
			return NameSpace.BIOLINK.getBaseUri() + getObjectId();
		}
	}
	
}
