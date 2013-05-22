package de.myreality.chronos.toolkit.objects;

import java.util.ArrayList;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Basic Node Object for parent and child connectivity.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class NodeObject {

	// Parent Node
	private NodeObject parent;
	
	// Child Nodes
	private ArrayList<NodeObject> children;
	
	
	
	/**
	 * Basic constructor to initialize the members
	 */
	public NodeObject() {
		parent = null;
		children = new ArrayList<NodeObject>();
	}
	
	
	/**
	 * Set a new parent object to this instance
	 * 
	 * @param parent Parent object
	 */
	public void attachTo(NodeObject parent) {		
		detach();
		this.parent = parent;
		if (hasParent()) {
			parent.addChild(this);
		}
		
	}
	
	
	
	/**
	 * Detach from a given parent
	 */
	public void detach() {
		if (hasParent()) {
			parent.removeChild(this);
			parent = null;
		}
	}
	
	
	
	/**
	 * Helper function in order to add a child
	 */
	private void addChild(NodeObject object) {
		if (!children.contains(object)) {
			children.add(object);
		}
	}
	
	
	
	/**
	 * Helper function in order to remove a child
	 */
	private void removeChild(NodeObject object) {
		children.remove(object);
	}
	
	
	
	/**
	 * Getter to return the current parent object 
	 *  
	 * @return Current parent object
	 */
	public NodeObject getParent() {
		return parent;
	}
	
	
	
	/**
	 * Determines if the current instance is a child of the given
	 * object.
	 */
	public boolean isChildOf(NodeObject object) {
		return object.children.contains(this);
	}
	
	
	
	/**
	 * Determines if the current instance has a specific child
	 */
	public boolean hasChild(NodeObject object) {
		return object.isChildOf(this);
	}
	
	
	
	/**
	 * Determines if a parent object exists
	 */
	public boolean hasParent() {
		return parent != null;
	}
	
	
	/**
	 * Determines if the current instance has children
	 */
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	
	
	/** 
	 * Returns all children
	 */
	public ArrayList<NodeObject> getChildren() {
		return children;
	}
}
