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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bio.knowledge.ontology.mapping.InheritanceLookup;
import bio.knowledge.ontology.mapping.ModelLookup;
import bio.knowledge.ontology.mapping.NameSpace;
import bio.knowledge.ontology.utils.Utils;

/**
 * Wrapper class for Biolink Model integration
 * 
 * @author richard
 *
 */
public class Ontology {
	
	private static Logger _logger = LoggerFactory.getLogger(Ontology.class);
	
	private BeaconBiolinkModel biolinkModel;
	
	private ModelLookup<BiolinkClass> classLookup;
	private ModelLookup<BiolinkSlot> slotLookup;
	
	private InheritanceLookup<BiolinkClass> classInheritanceLookup;
	private InheritanceLookup<BiolinkSlot> slotInheritanceLookup;
	
	private final String DEFAULT_CATEGORY = "named thing";
	private final String DEFAULT_PREDICATE = "related to";
	
	private final Map<String, String> uriMapping = new HashMap<String, String>();
	
	@PostConstruct
	private void init() {
		uriMapping.put("HTTPS://KBA.NCATS.IO/BEACON/RKB",     NameSpace.BIOLINK.getPrefix());
		uriMapping.put("HTTPS://GARBANZO.SULAB.ORG",          NameSpace.WIKIDATA.getPrefix());
		uriMapping.put("HTTPS://KBA.NCATS.IO/BEACON/BIOLINK", NameSpace.BIOLINK.getPrefix());
		uriMapping.put("HTTPS://KBA.NCATS.IO/BEACON/NDEX",    NameSpace.UMLSSG.getPrefix());

		Optional<BeaconBiolinkModel> optional = BeaconBiolinkModel.load();
		biolinkModel = optional.get();
		
		classInheritanceLookup = new InheritanceLookup<BiolinkClass>(biolinkModel.getClasses());
		slotInheritanceLookup = new InheritanceLookup<BiolinkSlot>(biolinkModel.getSlots());
		
		classLookup = new ModelLookup<BiolinkClass>(biolinkModel.getClasses(), classInheritanceLookup);
		slotLookup = new ModelLookup<BiolinkSlot>(biolinkModel.getSlots(), slotInheritanceLookup);
	}
	
	/**
	 * 
	 * @return
	 */
	protected ModelLookup<BiolinkClass> getClassLookup() {
		return classLookup;
	}
	
	/**
	 * 
	 * @return
	 */
	protected ModelLookup<BiolinkSlot> getSlotLookup() {
		return slotLookup;
	}
	
	/**
	 * 
	 * @param biolinkTerm
	 * @return
	 */
	public Optional<BiolinkClass> getClassByName(BiolinkTerm biolinkTerm) {
		return getClassByName(biolinkTerm.getLabel());
	}
	
	/**
	 * 
	 * @param biolinkClassName
	 * @return
	 */
	public Optional<BiolinkClass> getClassByName(String biolinkClassName) {
		BiolinkClass biolinkClass = classLookup.getClassByName(biolinkClassName);
		return Optional.ofNullable(biolinkClass);
	}
	
	/**
	 * 
	 * @return
	 */
	public BiolinkClass getDefaultCategory() {
		return classLookup.getClassByName(DEFAULT_CATEGORY);
	}

	/**
	 * 
	 * @return
	 */
	public BiolinkSlot getDefaultPredicate() {
		return slotLookup.getClassByName(DEFAULT_PREDICATE);
	}

	/**
	 * 
	 * @param biolinkSlotName
	 * @return
	 */
	public Optional<BiolinkSlot> getSlotByName(String biolinkSlotName) {
		BiolinkSlot slot = slotLookup.getClassByName(biolinkSlotName);
		return Optional.ofNullable(slot);
	}
	
	/**
	 * 
	 * @param namespace
	 * @param termId
	 * @param modelLookup
	 * @return
	 */
	public Optional<BiolinkEntityInterface> getMapping( 
			String namespace, 
			String termId,  
			ModelLookup<? extends BiolinkEntityInterface> modelLookup
		) {
		
		BiolinkEntityInterface biolinkTerm;
		String prefix;
		String curie;
		
		if( Utils.isCurie(termId)) {
			
			// Sanity check: make sure that the Curie is uniformly upper case
			curie = termId.toUpperCase();
			
		} else {
			// doesn't (yet) look like a Curie..but try to synthesize one
			
			if(Utils.isUri(namespace)) {
				
				prefix = uriMapping.get(namespace.toUpperCase());
				curie = prefix + ":" + termId;
				
			} else {
				
				curie = namespace + ":" + termId;
			}
		}
		
		biolinkTerm = modelLookup.lookup(curie);
		
		if (biolinkTerm != null) {
			return Optional.of(biolinkTerm);
		} else {
			_logger.warn("Ontology.getMapping(termId: '"+termId+"') has no Biolink Mapping?");
			return Optional.empty();
		}
	}

}
