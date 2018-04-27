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
package bio.knowledge.ontology.mapping;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import bio.knowledge.ontology.BiolinkClass;
import bio.knowledge.ontology.BiolinkModel;
import bio.knowledge.ontology.BiolinkSlot;
import bio.knowledge.ontology.BiolinkTerm;

/**
 * @author richard
 *
 */
public class BiolinkModelMapping extends HashMap<String, BiolinkTerm> {

	private static final long serialVersionUID=1234687328782964210L;
	
	private BiolinkModel biolinkModel;
	
	protected InheritanceLookup<BiolinkClass> classInheritanceLookup;
	protected ModelLookup<BiolinkClass> classModelLookup;
	
	protected InheritanceLookup<BiolinkSlot> slotInheritanceLookup;
	protected ModelLookup<BiolinkSlot> slotModelLookup;
	
	@PostConstruct
	void init() {
		biolinkModel = BiolinkModel.get();
		
		classInheritanceLookup = new InheritanceLookup<BiolinkClass>(biolinkModel.getClasses());
		classModelLookup = new ModelLookup<BiolinkClass>(biolinkModel.getClasses(), classInheritanceLookup);
		
		slotInheritanceLookup = new InheritanceLookup<BiolinkSlot>(biolinkModel.getSlots());
		slotModelLookup = new ModelLookup<BiolinkSlot>(biolinkModel.getSlots(), slotInheritanceLookup);
	}
	
	/**
	 * 
	 * @param curie
	 * @return
	 */
	public String termCurieToBiolinkDescription(String curie) {
		return classModelLookup.lookupDescription(curie);
	}

}
