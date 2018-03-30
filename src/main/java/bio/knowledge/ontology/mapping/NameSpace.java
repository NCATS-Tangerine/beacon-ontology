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

import java.util.Optional;

import org.apache.commons.text.WordUtils;

public enum NameSpace {
	
	BIOLINK("blm","http://bioentity.io/vocab/"),
	WIKIDATA("wd","https://www.wikidata.org/wiki/"),
	UMLSSG("umlssg","https://metamap.nlm.nih.gov/Docs/SemGroups_2013#"),
	BIOPAX("bp","http://www.biopax.org/release/biopax-level3.owl#"),
	NDEXBIO("ndex","http://http://www.ndexbio.org/")
	;
	
	private final String prefix;
	private final String baseIri;
	
	private NameSpace(String prefix, String baseIri) {
		this.prefix  = prefix;
		this.baseIri = baseIri;
	}
	
	public String getPrefix() {
		return prefix+":";
	}
	
	public String getBaseIri() {
		return baseIri;
	}

	private String toCamelCaps(String objectId) {
		
		String[] words = objectId.split("\\s+");
		
		objectId = "";
		
		for(String word:words)
			objectId +=
				word.substring(0, 1).toUpperCase()+
				word.substring(1).toLowerCase();
		
		return objectId;
	}
	
	public String getCurie(String objectId) {
		if(objectId==null) return null;
		if(this.equals(BIOLINK))
			objectId = toCamelCaps(objectId);
		return getPrefix()+objectId;
	}

	public String getIri(String objectId) {
		if(objectId==null) return null;
		if(this.equals(BIOLINK))
			objectId = toCamelCaps(objectId);
		return getBaseIri()+objectId;
	}
	
	/**
	 * 
	 * @param curie
	 * @return
	 */
    public static Optional<NameSpace> lookUpByPrefix(
    		String curie // curie may also be a raw prefix... should still work?
    ) { 
    	if(curie==null || curie.isEmpty()) return Optional.empty();
    	String[] idpart = curie.split(":");	
		String prefix = idpart[0].toLowerCase();
    	for(NameSpace type: NameSpace.values()) {
    		if(type.prefix.equals(prefix))
    			return Optional.of(type) ;
    	}
    	return Optional.empty();
    }
	
    /**
     * 
     * @param id
     * @return
     */
	static public String makeIri(String id) {
		
		// Sanity check
		if(id == null || id.isEmpty()) return "";
		
		String iri = "" ;
		
		// Check if this is a CURIE, then look it up
		String[]curieParts = id.split("\\:");
		if(curieParts.length>1) {
			
			/*
			 * TODO: add a NameSpace lookup mechanism here
			 * to generate proper IRIs for beacon specific CURIEs
			 */
			Optional<NameSpace> nsOpt = lookUpByPrefix(curieParts[0]);
			
			if(nsOpt.isPresent()) {
				
				NameSpace ns = nsOpt.get();
				
				iri = ns.getBaseIri();
				
				// Local Hack to tweak Wikidata properties URI
				if(ns.equals(WIKIDATA)) {
					if(curieParts[1].startsWith("P")) {
						iri += "Property:";
					}
				}
				
				iri += curieParts[1];

			} else {
				// Don't know how to transform?
				iri = id;
			}
			
		} else {
			/*
			 * Assume that this is a simple Biolink type and default 
			 * to a regular Biolink IRI, with camel case objectId
			 */
			iri = BIOLINK.getBaseIri() + WordUtils.capitalizeFully(id,null).replaceAll(" ", "");
		}
		
		return iri;
	}
}
