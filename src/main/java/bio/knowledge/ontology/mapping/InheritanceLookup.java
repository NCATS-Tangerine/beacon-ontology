package bio.knowledge.ontology.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bio.knowledge.ontology.BiolinkEntity;

public class InheritanceLookup {
	
	private Map<
			BiolinkEntity,
			BiolinkEntity
			> childToParentMap = new HashMap<>();
	private Map<
			BiolinkEntity,
				Set<BiolinkEntity>
			> parentToChildrenMap = new HashMap<>();
	
	public InheritanceLookup(List<? extends BiolinkEntity> entities) {
		
		Map<String, BiolinkEntity> nameMap = new HashMap<String, BiolinkEntity>();
		
		for (BiolinkEntity c : entities) {
			nameMap.put(c.getName(), c);
		}
		
		for (BiolinkEntity child : entities) {
			String parentName = child.getIs_a();
			
			if (parentName != null) {
				BiolinkEntity parent = nameMap.get(parentName);
				
				childToParentMap.put(child, parent);
				addChild(parent, child);
			}
		}
	}
	
	/**
	 * Gets the BiolinkClass that the given child class inherits from
	 */
	public BiolinkEntity getParent(BiolinkEntity child) {
		return childToParentMap.get(child);
	}
	
	/**
	 * Gets the BiolinkClass's that inherit from the given parent
	 */
	public Set<BiolinkEntity> getChildren(BiolinkEntity parent) {
		Set<BiolinkEntity> children = parentToChildrenMap.get(parent);
		return Collections.unmodifiableSet(children != null ? children : new HashSet<BiolinkEntity>());
	}
	
	/**
	 * Builds up a set of all BiolinkClass's that the given
	 * BiolinkClass inherits from
	 */
	public Set<BiolinkEntity> getAncestors(BiolinkEntity child) {
		Set<BiolinkEntity> set = new HashSet<BiolinkEntity>();
		
		BiolinkEntity parent = getParent(child);
		
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
	public Set<BiolinkEntity> getDescendants(BiolinkEntity parent) {
		Set<BiolinkEntity> children = getChildren(parent);
		
		if (children == null || children.isEmpty()) {
			return new HashSet<BiolinkEntity>();
			
		} else {
			Set<BiolinkEntity> descendants = new HashSet<BiolinkEntity>();
			descendants.addAll(children);

			for (BiolinkEntity child : children) {
				descendants.addAll(getDescendants(child));
			}
			
			return descendants;
		}
	}
	
	public boolean containsParent(BiolinkEntity parent) {
		return parentToChildrenMap.containsKey(parent);
	}
	
	public boolean containsChild(BiolinkEntity child) {
		return childToParentMap.containsKey(child);
	}
	
	private boolean addChild(BiolinkEntity parent, BiolinkEntity child) {
		Set<BiolinkEntity> children = parentToChildrenMap.get(parent);
		
		if (children == null) {
			children = new HashSet<BiolinkEntity>();
		}
		
		boolean isChildAdded = children.add(child);
		parentToChildrenMap.put(parent, children);
		return isChildAdded;
	}
}
