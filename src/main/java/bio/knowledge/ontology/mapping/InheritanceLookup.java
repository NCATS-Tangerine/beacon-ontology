package bio.knowledge.ontology.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bio.knowledge.ontology.BiolinkEntityInterface;

public class InheritanceLookup<T extends BiolinkEntityInterface> {
	
	private Map<T, T> childToParentMap = new HashMap<T, T>();
	private Map<T, Set<T>> parentToChildrenMap = new HashMap<T, Set<T>>();
	
	public InheritanceLookup(List<T> entities) {
		
		Map<String, T> nameMap = new HashMap<String, T>();
		
		for (T c : entities) {
			nameMap.put(c.getName(), c);
		}
		
		for (T child : entities) {
			String parentName = child.getIs_a();
			
			if (parentName != null) {
				T parent = nameMap.get(parentName);
				
				childToParentMap.put(child, parent);
				addChild(parent, child);
			}
		}
	}
	
	/**
	 * Gets the BiolinkClass that the given child class inherits from
	 */
	public T getParent(T child) {
		return childToParentMap.get(child);
	}
	
	/**
	 * Gets the BiolinkClass's that inherit from the given parent
	 */
	public Set<T> getChildren(T parent) {
		Set<T> children = parentToChildrenMap.get(parent);
		return Collections.unmodifiableSet(children != null ? children : new HashSet<T>());
	}
	
	/**
	 * Builds up a set of all BiolinkClass's that the given
	 * BiolinkClass inherits from
	 */
	public Set<T> getAncestors(T child) {
		Set<T> set = new HashSet<T>();
		
		T parent = getParent(child);
		
		while (parent != null) {
			set.add(parent);
			parent = getParent(parent);
		}
		
		return set;
	}
	
	/**
	 * Recursively builds up a set of all BiolinkClass's that inherit from
	 * the given BiolinkClass
	 */
	public Set<T> getDescendants(T parent) {
		Set<T> children = getChildren(parent);
		
		if (children == null || children.isEmpty()) {
			return new HashSet<T>();
			
		} else {
			Set<T> descendants = new HashSet<T>();
			descendants.addAll(children);

			for (T child : children) {
				descendants.addAll(getDescendants(child));
			}
			
			return descendants;
		}
	}
	
	public boolean containsParent(T parent) {
		return parentToChildrenMap.containsKey(parent);
	}
	
	public boolean containsChild(T child) {
		return childToParentMap.containsKey(child);
	}
	
	private boolean addChild(T parent, T child) {
		Set<T> children = parentToChildrenMap.get(parent);
		
		if (children == null) {
			children = new HashSet<T>();
		}
		
		boolean isChildAdded = children.add(child);
		parentToChildrenMap.put(parent, children);
		return isChildAdded;
	}
}
