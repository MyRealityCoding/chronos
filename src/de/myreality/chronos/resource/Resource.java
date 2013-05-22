package de.myreality.chronos.resource;

import java.util.HashSet;

import de.myreality.chronos.util.ReflectionTemplate;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * Basic resource that provides dynamic resource loading on runtime.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class Resource<Type> extends ReflectionTemplate<Type> implements RenewableResource<Type> {
	
	private static final long serialVersionUID = 1L;
	
	private HashSet<Freeable> freeableResources;

	// The current raw resource
	private Type resource;
	
	// ID of the resource
	private String id;
	
	// Represents the count of use
	private int useCount;
	
	private ResourceLoader<Type> parentLoader;
	
	
	/**
	 * Initialize the resource
	 */
	public Resource(String id, ResourceLoader<Type> loader) {
		this.id = id;
		parentLoader = loader;
		freeableResources = new HashSet<Freeable>();
		allocate();
	}
	
	public void addFreeable(Freeable freeable) {
		freeableResources.add(freeable);
	}
	
	public int getUseCount() {
		return useCount;
	}
	
	public void allocate() {
		++useCount;
	}
	
	public void free() {
		--useCount;
		if (useCount == 1) {
			resource = null;
			parentLoader.freeResource(getID());
		}
		
		freeChildren();
	}
	
	@Override
	public void freeChildren() {
		for (Freeable freeable : freeableResources) {
			freeable.free();
		}
	}
	
	
	/**
	 * @return Returns the current resource
	 */
	public Type get() {
		return resource;
	}
	
	
	/**
	 * @return The current resource ID
	 */
	public String getID() {
		return id;
	}


	@Override
	public void setResourceContent(Type resource) {
		this.resource = resource;
		
	}
}
