package bio.knowledge.ontology;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class UmlsContainer {
	private Set<String> umlsCategories = new HashSet<String>();
	private Set<String> umlsCategoryNames = new HashSet<String>();
	private Set<String> umlsTypes = new HashSet<String>();
	private Set<String> umlsTypeNames = new HashSet<String>();
	
	private void load() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		try (InputStream stream = classloader.getResourceAsStream("SemGroups_2013.txt")) {
			
			Scanner scanner = new Scanner(stream);
			scanner.useDelimiter(Pattern.compile("\\||\\n"));
			
			// Each row has four columns
			while (scanner.hasNext()) {
				umlsCategories.add(scanner.next());
				umlsCategoryNames.add(scanner.next());
				umlsTypes.add(scanner.next());
				umlsTypeNames.add(scanner.next());
			}
			
			scanner.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public UmlsContainer() {
		load();
	}
	
	public Collection<String> getUmlsCategories() {
		return Collections.unmodifiableCollection(umlsCategories);
	}
	
	public Collection<String> getUmlsTypes() {
		return Collections.unmodifiableCollection(umlsTypes);
	}
}
