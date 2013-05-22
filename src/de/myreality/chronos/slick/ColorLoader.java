package de.myreality.chronos.slick;

import org.newdawn.slick.Color;

import de.myreality.chronos.resource.Freeable;
import de.myreality.chronos.resource.ResourceLoader;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Resource loader for slick colors. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ColorLoader extends ResourceLoader<Color> {
	
	private static ColorLoader instance;
	
	static {		
		instance = new ColorLoader();
	}
	
	// Singleton is always private
	private ColorLoader() { }

	@Override
	protected Color loadResource(Freeable freeable, ResourceDefinition definition) {
		return Color.decode(definition.getContent());
	}
	
	public static ColorLoader getInstance() {
		return instance;
	}

}
