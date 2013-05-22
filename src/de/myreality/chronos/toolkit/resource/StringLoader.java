package de.myreality.chronos.toolkit.resource;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Default resource loader for string types
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class StringLoader extends ResourceLoader<String> {

	@Override
	protected String loadResource(String value) {
		return value;
	}

}
