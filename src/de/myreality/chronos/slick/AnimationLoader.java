package de.myreality.chronos.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import de.myreality.chronos.resource.Freeable;
import de.myreality.chronos.resource.Resource;
import de.myreality.chronos.resource.ResourceLoader;
import de.myreality.chronos.resource.ResourceManager;

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
public class AnimationLoader extends ResourceLoader<Animation> {
	
	private static AnimationLoader instance;
	
	static {
		instance = new AnimationLoader();
	}
	
	// Singleton is always private
	private AnimationLoader() { }
	
	public static AnimationLoader getInstance() {
		return instance;
	}

	@Override
	protected Animation loadResource(Freeable freeable, ResourceDefinition definition) {
		String spritesheetID = definition.getAttribute("spritesheet");
		int time = Integer.valueOf(definition.getAttribute("time"));
		
		Resource<SpriteSheet> spriteSheetResource = ResourceManager.getInstance().getResource(spritesheetID, SpriteSheet.class);	
		freeable.addFreeable(spriteSheetResource);
		
		return new Animation(spriteSheetResource.get(), time);
	}
}
