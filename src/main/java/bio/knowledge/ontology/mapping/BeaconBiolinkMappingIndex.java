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
import java.util.Optional;

import bio.knowledge.ontology.BiolinkTerm;
import bio.knowledge.ontology.mapping.umls.UMLSBiolinkMapping;
import bio.knowledge.ontology.mapping.wikidata.WikiDataBiolinkMapping;

/**
 * @author richard
 *
 */
public class BeaconBiolinkMappingIndex extends HashMap<String, BiolinkModelMapping> {

	private static final long serialVersionUID = -8922021938416438973L;
	
	private final DirectBiolinkMapping directBiolinkMapping     = new DirectBiolinkMapping();
	private final UMLSBiolinkMapping     umlsBiolinkMapping     = new UMLSBiolinkMapping();
	private final WikiDataBiolinkMapping wikidataBiolinkMapping = new WikiDataBiolinkMapping();
	
	public BeaconBiolinkMappingIndex() {
		
		// By Beacon Id (used as a String key)
		put("1",  directBiolinkMapping);
		put("2",  wikidataBiolinkMapping);
		put("5",  umlsBiolinkMapping);
		put("11", umlsBiolinkMapping);

	
		put(NameSpace.BIOLINK.getPrefix(),  directBiolinkMapping);
		put(NameSpace.WIKIDATA.getPrefix(), wikidataBiolinkMapping);
		put(NameSpace.UMLSSG.getPrefix(),   umlsBiolinkMapping);
		put(NameSpace.BIOPAX.getPrefix(),   umlsBiolinkMapping);
		put(NameSpace.NDEXBIO.getPrefix(),  umlsBiolinkMapping);
	}

	private static BeaconBiolinkMappingIndex beaconMappingIndex = new BeaconBiolinkMappingIndex();
	
	/**
	 * 
	 * @param namespace
	 * @param termId
	 * @return
	 */
	public static Optional<BiolinkTerm> getMapping (String namespace, String termId ) {
		if(beaconMappingIndex.containsKey(namespace)) {
			BiolinkModelMapping bmm = beaconMappingIndex.get(namespace);
			if(bmm.containsKey(termId)) {
				return Optional.of(bmm.get(termId));
			}
		}
		return Optional.empty();
	}

}
