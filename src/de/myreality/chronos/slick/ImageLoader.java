package de.myreality.chronos.slick;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.myreality.chronos.resource.Freeable;
import de.myreality.chronos.resource.ResourceLoader;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Resource loader for slick images. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ImageLoader extends ResourceLoader<Image> {
	
	private static ImageLoader instance;
	
	static {
		instance = new ImageLoader();
	}
	
	// Singleton is always private
	private ImageLoader() { }

	@Override
	protected Image loadResource(Freeable freeable, ResourceDefinition definition) {
		try {
			return new Image(definition.getContent());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ImageLoader getInstance() {
		return instance;
	}

}
