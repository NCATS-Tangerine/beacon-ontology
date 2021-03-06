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

import java.util.Optional;

import bio.knowledge.ontology.mapping.NameSpace;

/**
 * 
 * @author Richard Bruskiewich
 *
 * TODO: The Biolink Model YAML should be directly parsed to 
 * create this class (but can I create an Enum by reflection?)
 */
public enum BiolinkTerm {
	
	// Concept Types
	
	NAMED_THING("named thing"),
	ACTIVITY_AND_BEHAVIOR("activity and behavior"),
	ADMINISTRATIVE_ENTITY("administrative entity"),
	DEVICE("device"),
	//OCCUPATION("occupation"), // not directly tracked in Biolink?
	ANATOMICAL_ENTITY("anatomical entity"),
	GENOMIC_ENTITY("genomic entity"),
	GENE("gene"),
	GENE_OR_GENE_PRODUCT("gene or gene product"),
	PROTEIN("protein"),
	MICRORNA("microRNA"), 
	TRANSCRIPT("transcript"),
	DISEASE("disease"),
	MOLECULAR_ENTITY("molecular entity"),
	CHEMICAL_SUBSTANCE("chemical substance"),
	DRUG("drug"),
	GEOGRAPHIC_LOCATION("geographic location"),
	ORGANISMAL_ENTITY("organismal entity"),
	INDIVIDUAL_ORGANISM("individual organism"),
	BIOLOGICAL_ENTITY("biological entity"),
	PATHWAY("pathway"),
	PHYSIOLOGY("physiology"),
	PHYSIOLOGICAL_PROCESS("physiological process"),
	BIOLOGICAL_PROCESS("biological process"),
	INFORMATION_CONTENT_ENTITY("information content entity"),
	PROCEDURE("procedure"),
	PHENOMENON("phenomenon"),
	SEQUENCE_VARIANT("sequence variant"), 
	PHENOTYPE("phenotype"),
	
	// Association Types
	ASSOCIATION("association")
	;
	
	private final String label;
	private final String objectId;
	
	BiolinkTerm(String label) {
		this.label = label;
		this.objectId = getObjectId(label);
	}
	
	private static String toCamelCaps(String objectId) {
		
		String[] words = objectId.split("\\s+");
		
		objectId = "";
		
		for(String word:words)
			objectId +=
				word.substring(0, 1).toUpperCase()+
				word.substring(1).toLowerCase();
		
		return objectId;
	}
	
	/**
	 * 
	 * @param label
	 * @return
	 */
	public static String getObjectId(String label) {
		return toCamelCaps(label);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * 
	 * @return
	 */
	public String getCurie() {
		return NameSpace.BIOLINK.getPrefix()+objectId;
	}

	/**
	 * @return the iri
	 */
	public String getIri() {
		return NameSpace.BIOLINK.getBaseUri()+objectId;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * 
	 * @param label
	 * @return
	 */
	public static Optional<BiolinkTerm> lookUpName(String label) {
		for(BiolinkTerm term : BiolinkTerm.values()) {
			if(term.getLabel().equals(label)) 
				return Optional.of(term);
		}
		return Optional.empty();
	}
	
	public static BiolinkTerm lookUpName(BiolinkClass biolinkClass) {
		String label = biolinkClass.getName();
		for(BiolinkTerm term : BiolinkTerm.values()) {
			if(term.getLabel().equals(label)) 
				return term;
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String getDefinition() {
		// TODO: The actual definition should be loaded from the Biolink Model!
		return label;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return label;
	}
}
