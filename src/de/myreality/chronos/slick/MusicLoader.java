package de.myreality.chronos.slick;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import de.myreality.chronos.resource.Freeable;
import de.myreality.chronos.resource.ResourceLoader;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Resource loader for slick music. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class MusicLoader extends ResourceLoader<Music> {
	
	private static MusicLoader instance;
	
	static {
		instance = new MusicLoader();
	}
	
	// Singleton is always private
	private MusicLoader() { }

	@Override
	protected Music loadResource(Freeable freeable, ResourceDefinition definition) {
		try {
			return new Music(definition.getContent());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static MusicLoader getInstance() {
		return instance;
	}

}
