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
import java.util.List;

import javax.annotation.PostConstruct;

import bio.knowledge.ontology.*;

/**
 * @author richard
 *
 */
public class BiolinkModelMapping extends HashMap<String, BiolinkTerm> {

	private static final long serialVersionUID=1234687328782964210L;

	protected InheritanceLookup classInheritanceLookup;
	protected ModelLookup classModelLookup;
	
	protected InheritanceLookup slotInheritanceLookup;
	protected ModelLookup slotModelLookup;
	
	@PostConstruct
	void init() {
		BiolinkModel biolinkModel = BiolinkModel.get();

		List<? extends BiolinkEntity> classes = biolinkModel.getClasses();
		classInheritanceLookup = new InheritanceLookup(classes);
		classModelLookup = new ModelLookup(classes, classInheritanceLookup);

		List<? extends BiolinkEntity> slots = biolinkModel.getSlots() ;
		slotInheritanceLookup = new InheritanceLookup(slots);
		slotModelLookup = new ModelLookup(slots, slotInheritanceLookup);
	}

}
