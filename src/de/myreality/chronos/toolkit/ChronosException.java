package de.myreality.chronos.toolkit;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Chronos exception with custom output
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ChronosException extends RuntimeException {

	private static final long serialVersionUID = 4209151636030925802L;

	
	public ChronosException(String message) {
		super("\nError while using Chronos: " + message);
	}
}
