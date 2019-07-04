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
	private Set<String> umlsCodes = new HashSet<String>();
	private Set<String> umlsTypeNames = new HashSet<String>();
	private Set<String> umlsTypes = new HashSet<String>();

	private void load() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		// Load UMLS Semantic Groups
		try (InputStream stream = classloader.getResourceAsStream("SemGroups_2018.txt")) {
			
			Scanner scanner = new Scanner(stream);
			scanner.useDelimiter(Pattern.compile("\\||\\n"));
			
			// Each row has four columns
			while (scanner.hasNext()) {
				umlsCategories.add(scanner.next());
				umlsCategoryNames.add(scanner.next());
				umlsCodes.add(scanner.next());
				umlsTypeNames.add(scanner.next());
			}
			
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Load UMLS Semantic Types
		try (InputStream stream = classloader.getResourceAsStream("SemanticTypes_2018AB.txt")) {

			Scanner scanner = new Scanner(stream);
			scanner.useDelimiter(Pattern.compile("\\||\\n"));

			// Each row has four columns
			while (scanner.hasNext()) {

				umlsTypes.add(scanner.next());
				/*
				 skip the UMLS codes and type names..
				 already have them from above?
				 */
				scanner.next(); scanner.next();
			}

			scanner.close();
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

	public Collection<String> getUmlsCodes() {
		return Collections.unmodifiableCollection(umlsCodes);
	}

	public Collection<String> getUmlsTypes() {
		return Collections.unmodifiableCollection(umlsTypes);
	}
}
