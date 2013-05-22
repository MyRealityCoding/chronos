package de.myreality.chronos.resource;

import java.io.Serializable;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * Is used by the resource loader in order to refresh the inner resource
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public interface RenewableResource<Type> extends Serializable, Freeable {

	/**
	 * Reloads the given resource in the resource loader
	 * 
	 * @param resource target resource content
	 */
	void setResourceContent(Type resource);
	
	/**
	 * 
	 * @return Returns the raw resource
	 */
	Type get();
	
	/**
	 * Returns the unique resource ID
	 * 
	 * @return Resource ID
	 */
	String getID();
	
	
	void allocate();
	
	void free();
	
	int getUseCount();
}
