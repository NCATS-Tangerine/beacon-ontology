package bio.knowledge.ontology.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bio.knowledge.ontology.BiolinkClass;
import bio.knowledge.ontology.BiolinkModel;

public class InheritanceMap {
	
	private Map<BiolinkClass, BiolinkClass> childToParentMap = new HashMap<BiolinkClass, BiolinkClass>();
	private Map<BiolinkClass, Set<BiolinkClass>> parentToChildrenMap = new HashMap<BiolinkClass, Set<BiolinkClass>>();
	
	public InheritanceMap() {
		BiolinkModel model = BiolinkModel.get();
		
		Map<String, BiolinkClass> nameMap = new HashMap<String, BiolinkClass>();
		
		for (BiolinkClass c : model.getClasses()) {
			nameMap.put(c.getName(), c);
		}
		
		for (BiolinkClass child : model.getClasses()) {
			String parentName = child.getIs_a();
			
			if (parentName != null) {
				BiolinkClass parent = nameMap.get(parentName);
				
				childToParentMap.put(child, parent);
				addChild(parent, child);
			}
		}
	}
	
	public BiolinkClass getParent(BiolinkClass child) {
		return childToParentMap.get(child);
	}
	
	public Set<BiolinkClass> getChildren(BiolinkClass parent) {
		return Collections.unmodifiableSet(parentToChildrenMap.get(parent));
	}
	
	/**
	 * Builds up a set of all BiolinkClass's that the given
	 * BiolinkClass inherits from
	 */
	public Set<BiolinkClass> getAncestors(BiolinkClass child) {
		Set<BiolinkClass> set = new HashSet<BiolinkClass>();
		
		BiolinkClass parent = getParent(child);
		
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
	public Set<BiolinkClass> getDescendants(BiolinkClass parent) {
		Set<BiolinkClass> children = getChildren(parent);
		
		if (children == null) {
			return new HashSet<BiolinkClass>();
		} else {
			Set<BiolinkClass> set = new HashSet<BiolinkClass>();
			
			for (BiolinkClass child : children) {
				set.addAll(getDescendants(child));
			}
			
			return set;
		}
	}
	
	public boolean containsParent(BiolinkClass parent) {
		return parentToChildrenMap.containsKey(parent);
	}
	
	public boolean containsChild(BiolinkClass child) {
		return childToParentMap.containsKey(child);
	}
	
	private boolean addChild(BiolinkClass parent, BiolinkClass child) {
		Set<BiolinkClass> children = parentToChildrenMap.get(parent);
		
		if (children == null) {
			children = new HashSet<BiolinkClass>();
		}
		
		boolean isChildAdded = children.add(child);
		parentToChildrenMap.put(parent, children);
		return isChildAdded;
	}
}
