package de.myreality.chronos.slick;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import de.myreality.chronos.resource.Freeable;
import de.myreality.chronos.resource.ResourceLoader;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Resource loader for slick sounds. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class SoundLoader extends ResourceLoader<Sound> {
	
	private static SoundLoader instance;
	
	static {
		instance = new SoundLoader();
	}
	
	// Singleton is always private
	private SoundLoader() { }

	@Override
	protected Sound loadResource(Freeable freeable, ResourceDefinition definition) {
		try {
			return new Sound(definition.getContent());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static SoundLoader getInstance() {
		return instance;
	}

}
