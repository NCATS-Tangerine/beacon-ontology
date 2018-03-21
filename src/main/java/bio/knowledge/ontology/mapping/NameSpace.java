package bio.knowledge.ontology.mapping;

public enum NameSpace {
	BIOLINK("BIOLINK","bioentity.io/vocab/"),
	WIKIDATA("WD","https://www.wikidata.org/wiki/"),
	UMLSSG("UMLSSG","https://metamap.nlm.nih.gov/Docs/SemGroups_2013#")
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
}
