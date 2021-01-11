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
import java.util.List;
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
	
	private static final Logger _logger = LoggerFactory.getLogger(Ontology.class);

	private ModelLookup classLookup;
	private ModelLookup slotLookup;

	private final Map<String, String> uriMapping = new HashMap<>();
	
	@PostConstruct
	private void init() {
		uriMapping.put("HTTPS://KBA.NCATS.IO/BEACON/RKB",     NameSpace.BIOLINK.getPrefix());
		uriMapping.put("HTTPS://GARBANZO.SULAB.ORG",          NameSpace.WIKIDATA.getPrefix());
		uriMapping.put("HTTPS://KBA.NCATS.IO/BEACON/BIOLINK", NameSpace.BIOLINK.getPrefix());
		uriMapping.put("HTTPS://KBA.NCATS.IO/BEACON/NDEX",    NameSpace.UMLSSG.getPrefix());

		Optional<BeaconBiolinkModel> optional = BeaconBiolinkModel.load();
		BeaconBiolinkModel biolinkModel;
		if(optional.isPresent()) {
			biolinkModel = optional.get();
		} else
			throw new RuntimeException("Beacon Ontology ERROR: Biolink Model loading unsuccessful?");

		List<? extends BiolinkEntity> classes = biolinkModel.getClasses();
		InheritanceLookup classInheritanceLookup = new InheritanceLookup(classes);
		classLookup = new ModelLookup(classes, classInheritanceLookup);

		List<? extends BiolinkEntity> slots = biolinkModel.getSlots() ;
		InheritanceLookup slotInheritanceLookup = new InheritanceLookup(slots);
		slotLookup  = new ModelLookup(slots,   slotInheritanceLookup);
	}
	
	/**
	 * @return ModelLookup catalog of Biolink classes (categories and  associations)
	 */
	protected ModelLookup getClassLookup() {
		return classLookup;
	}
	
	/**
	 * @return ModelLookup catalog of Biolink slots (e.g. predicates)
	 */
	protected ModelLookup getSlotLookup() {
		return slotLookup;
	}
	
	/**
	 * Find BiolinkClass wrapped class, by Biolink term identifier
	 * @param biolinkTerm whose BiolinkEntity is to be returned
	 * @return Optional of a BiolinkEntity wrapped class
	 */
	public Optional<BiolinkClass> getClassByName(BiolinkTerm biolinkTerm) {
		return getClassByName(biolinkTerm.getLabel());
	}

	/**
	 * Find BiolinkEntity wrapped class, by Biolink class name
	 * @param biolinkClassName String name of Biolink class whose BiolinkEntity is to be returned
	 * @return Optional of a BiolinkEntity wrapped class
	*/
	public Optional<BiolinkClass> getClassByName(String biolinkClassName) {
		BiolinkClass biolinkClass = classLookup.getClassByName(biolinkClassName);
		return Optional.ofNullable(biolinkClass);
	}
	
	/**
	 * @return BiolinkEntity corresponding to the default Biolink category class
	 */
	public BiolinkClass getDefaultCategory() {
		String DEFAULT_CATEGORY = "named thing";
		return classLookup.getClassByName(DEFAULT_CATEGORY);
	}

	/**
	 * @return BiolinkEntity corresponding to the default Biolink predicate
	 */
	public BiolinkSlot getDefaultPredicate() {
		String DEFAULT_PREDICATE = "related to";
		return slotLookup.getSlotByName(DEFAULT_PREDICATE);
	}

	/**
	 * Find BiolinkEntity wrapped class, by Biolink slot name
	 * @param biolinkSlotName String name of Biolink slot whose BiolinkEntity is to be returned
	 * @return Optional of a BiolinkEntity wrapped class
	 */
	public Optional<BiolinkSlot> getSlotByName(String biolinkSlotName) {
		BiolinkSlot slot = slotLookup.getSlotByName(biolinkSlotName);
		return Optional.ofNullable(slot);
	}
	
	/**
	 * Get mapping for given namespace or term identifier in target ModelLookup catalog.
	 * @param namespace string name of a term's namespace.
	 * @param termId string identifier of a term.
	 * @param modelLookup target ModelLookup catalog of Biolink entities
	 * @return Optional of an associated BiolinkEntity matched by term id from the catalog
	 */
	public Optional<BiolinkEntity> getMapping(
			String namespace, 
			String termId,  
			ModelLookup modelLookup
		) {
		
		BiolinkEntity biolinkTerm;
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
			_logger.warn("Ontology.getMapping(): termId '"+termId+"' has no Biolink Mapping in namespace '"+namespace+"'?");
			return Optional.empty();
		}
	}

}
