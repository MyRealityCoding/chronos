package de.myreality.chronos.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Basic Node Object for parent and child connectivity.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class NodeObject<Type> implements Serializable {

	private static final long serialVersionUID = 1L;

	// Parent Node
	private Type parent;
	
	// Child Nodes
	private ArrayList<Type> children;
	
	
	
	/**
	 * Basic constructor to initialize the members
	 */
	public NodeObject() {
		parent = null;
		children = new ArrayList<Type>();
	}
	
	
	/**
	 * Set a new parent object to this instance
	 * 
	 * @param parent Parent object
	 */
	public void attachTo(Type parent) {		
		detach();
		this.parent = parent;
		if (hasParent()) {
			if (parent instanceof NodeObject<?>) {
				NodeObject<?> parentNode = (NodeObject<?>)parent;
				parentNode.addChild(this);
			}
		}
		
	}
	
	
	
	/**
	 * Detach from a given parent
	 */
	public void detach() {
		if (hasParent()) {
			if (parent instanceof NodeObject<?>) {
				NodeObject<?> parentNode = (NodeObject<?>)parent;
				parentNode.removeChild(this);
			}
			parent = null;
		}
	}
	
	
	
	/**
	 * Helper function in order to add a child
	 */
	private void addChild(NodeObject<?> nodeObject) {
		if (!children.contains(nodeObject)) {
			@SuppressWarnings("unchecked")
			Type nodeTypeObject = (Type) nodeObject;
			children.add(nodeTypeObject);
		}
	}
	
	
	
	/**
	 * Helper function in order to remove a child
	 */
	private void removeChild(NodeObject<?> nodeObject) {
		@SuppressWarnings("unchecked")
		Type nodeTypeObject = (Type) nodeObject;
		children.remove(nodeTypeObject);
	}
	
	
	
	/**
	 * Getter to return the current parent object 
	 *  
	 * @return Current parent object
	 */
	public Type getParent() {
		return parent;
	}
	
	
	
	/**
	 * Determines if the current instance is a child of the given
	 * object.
	 */
	public boolean isChildOf(NodeObject<?> object) {
		return object.children.contains(this);
	}
	
	
	
	/**
	 * Determines if the current instance has a specific child
	 */
	public boolean hasChild(NodeObject<?> object) {
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
	public ArrayList<Type> getChildren() {
		return children;
	}
	
	
}
