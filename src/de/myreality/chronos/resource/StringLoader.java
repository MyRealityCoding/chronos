package de.myreality.chronos.resource;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Default resource loader for string types
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class StringLoader extends ResourceLoader<String> {
	
	private static StringLoader instance;
	
	static {		
		instance = new StringLoader();
	}
	
	// Singleton is always private
	private StringLoader() { }
	
	public static StringLoader getInstance() {
		return instance;
	}

	@Override
	protected String loadResource(Freeable freeable, ResourceDefinition definition) {
		return definition.getContent();
	}
}
