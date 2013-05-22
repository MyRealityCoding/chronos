package de.myreality.chronos.resource;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * Is used in order to free a resource
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.5
 */
public interface Freeable {

	void free();
	
	void addFreeable(Freeable freeable);
	
	void freeChildren();
}
