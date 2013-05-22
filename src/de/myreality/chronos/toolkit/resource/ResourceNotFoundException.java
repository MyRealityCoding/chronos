package de.myreality.chronos.toolkit.resource;

import de.myreality.chronos.toolkit.ChronosException;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Exception that gives information about the unknown resource class
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ResourceNotFoundException extends ChronosException {

	private static final long serialVersionUID = 5028668422222864105L;

	public ResourceNotFoundException(Class<?> type, String id) {
		super(type.getSimpleName() + " resource '" + id + "' not found.");
	}
}
