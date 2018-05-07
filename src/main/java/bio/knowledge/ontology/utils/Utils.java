package bio.knowledge.ontology.utils;

public class Utils {
	
	public static String toUpperCamelCase(String phrase) {
		if (phrase == null) {
			return null;
		}
		
		String upperCamelCase = "";

		for (String term : phrase.split(" ")) {
			upperCamelCase += substring(term, 0, 1).toUpperCase() + substring(term, 1, term.length()).toLowerCase();
		}
		
		return upperCamelCase;
	}
	
	public static String toLowerCamelCase(String phrase) {
		if (phrase == null) {
			return null;
		}
		
		String s = toUpperCamelCase(phrase);
		return substring(s, 0, 1).toLowerCase() + substring(s, 1);
	}
	
	public static String substring(String s, int beginIndex, int endIndex) {
		try {
			return s.substring(beginIndex, endIndex);
		} catch (IndexOutOfBoundsException e) {
			return "";
		}
	}
	
	public static String substring(String s, int beginIndex) {
		try {
			return s.substring(beginIndex);
		} catch (IndexOutOfBoundsException e) {
			return "";
		}
	}
	
}
