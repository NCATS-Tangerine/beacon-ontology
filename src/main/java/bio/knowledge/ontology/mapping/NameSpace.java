package bio.knowledge.ontology.mapping;

public enum NameSpace {
	BIOLINK("BLM","http://bioentity.io/vocab/"),
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
		if(this.equals(BIOLINK))
			objectId = toCamelCaps(objectId);
		return getPrefix()+objectId;
	}

	public String getIri(String objectId) {
		if(this.equals(BIOLINK))
			objectId = toCamelCaps(objectId);
		return getBaseIri()+objectId;
	}
}
