package de.myreality.chronos;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Chronos exception with custom output
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ChronosException extends RuntimeException {

	private static final long serialVersionUID = 4209151636030925802L;

	
	/**
	 * Basic constructor for a chronos exception
	 * 
	 * @param message
	 */
	public ChronosException(String message) {
		super("Error while using Chronos: " + message);
	}
}
