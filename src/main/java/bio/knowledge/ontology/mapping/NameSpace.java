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

import bio.knowledge.ontology.BiolinkTerm;

public enum NameSpace {
	
	// PubMed concepts should always be tagged as scientific articles?
	PMID("PMID", "", BiolinkTerm.INFORMATION_CONTENT_ENTITY),
	PUBMED("PMID", "", BiolinkTerm.INFORMATION_CONTENT_ENTITY),
	
	MONDO("MONDO", "http://purl.obolibrary.org/obo/MONDO_", BiolinkTerm.DISEASE),  // Monarch Disease Ontology
	
	DOID("DOID", "", BiolinkTerm.DISEASE),  // Disease Ontology
	
	NCBIGENE("NCBIGENE", "", BiolinkTerm.GENE),
	HGNC_SYMBOL("HGNC.SYMBOL", "", BiolinkTerm.GENE),
	
	ORPHANET("ORPHANET", "", BiolinkTerm.DISEASE), //	ORPHANET: http://www.orpha.net/
	
	GENECARDS("GENECARDS", "", BiolinkTerm.GENE),
	
	UNIPROT("UNIPROT", "", BiolinkTerm.PROTEIN),  // Uniprot protein database - actually also "CHEM"...
	
	CHEBI("CHEBI", "", BiolinkTerm.CHEMICAL_SUBSTANCE),
	DRUGBANK("DRUGBANK", "", BiolinkTerm.DRUG),
	
	BIOPAX("BP","http://www.biopax.org/release/biopax-level3.owl#", BiolinkTerm.PHYSIOLOGY),

	// Kyoto Encyclopedia of Genes and Genomes
	KEGG("KEGG", "", BiolinkTerm.PHYSIOLOGY), 
	KEGG_PATHWAY("KEGG_PATHWAY", "", BiolinkTerm.PHYSIOLOGY),
	
	REACT("REACT", "", BiolinkTerm.PHYSIOLOGY),    // REACTome == pathways?
	REACTOME("REACTOME", "", BiolinkTerm.PHYSIOLOGY), // REACTOME == pathways?

	PATHWAYCOMMONS("PATHWAYCOMMONS", "", BiolinkTerm.PHYSIOLOGY),  // Pathway Commons
	MIR("MIRTARBASE", "", BiolinkTerm.MICRORNA), // mirtarbase - micro RNA targets
	SMPDB("SMPDB", "", BiolinkTerm.PHYSIOLOGY),   // Small Molecular Pathway Database
	
	UMLSSG("UMLSSG","https://metamap.nlm.nih.gov/Docs/SemGroups_2013#", BiolinkTerm.NAMED_THING),
	
	NDEXBIO("NDEX","http://http://www.ndexbio.org/", BiolinkTerm.NAMED_THING),
	
	WIKIDATA("WD", "https://www.wikidata.org/wiki/", BiolinkTerm.NAMED_THING),
	
	BIOLINK("BLM","http://bioentity.io/vocab/", BiolinkTerm.NAMED_THING)
	;
	
	private final String prefix;
	private final String baseIri;
	private final BiolinkTerm defaultConceptType;
	
	private NameSpace(String prefix, String baseIri, BiolinkTerm conceptType) {
		this.prefix  = prefix;
		this.baseIri = baseIri;
		this.defaultConceptType = conceptType;
	}
	
	/**
	 * @return XMLNS namespace prefix (generally all capitalized). 
	 * Note: only the raw namespace "prefix" is given, *without* the 'colon'
	 */
	public String getPrefix() {
		return prefix+":";
	}
	
	/**
	 * 
	 * @return
	 */
	public String getBaseIri() {
		return baseIri;
	}
	
	/**
	 * 
	 * @return
	 */
	public BiolinkTerm defaultConceptType() {
		return defaultConceptType;
	}
	
	/**
	 * 
	 * @param objectId
	 * @return
	 */
	public String getCurie(String objectId) {
		if(objectId==null) return null;
		if(this.equals(BIOLINK))
			objectId = BiolinkTerm.getObjectId(objectId);
		return getPrefix()+objectId;
	}
	
	/**
	 * 
	 * @param objectId
	 * @return
	 */
	public String getIri(String objectId) {
		if(objectId==null) return null;
		if(this.equals(BIOLINK))
			objectId = BiolinkTerm.getObjectId(objectId);
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
		String prefix = idpart[0].toUpperCase();
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
			 * to generate proper URIs for beacon specific CURIEs
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
