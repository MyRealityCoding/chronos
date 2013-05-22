package de.myreality.chronos.slick;

import org.newdawn.slick.Image;
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
public class SpriteSheetLoader extends ResourceLoader<SpriteSheet> {
	
	private static SpriteSheetLoader instance;
	
	static {
		instance = new SpriteSheetLoader();
	}
	
	// Singleton is always private
	private SpriteSheetLoader() { }
	
	public static SpriteSheetLoader getInstance() {
		return instance;
	}

	@Override
	protected SpriteSheet loadResource(Freeable freeable, ResourceDefinition definition) {
		// Fetch the sprite sheet data from XML
		String imageID = definition.getAttribute("image");
		int xPos = Integer.valueOf(definition.getAttribute("xPos"));
		int yPos = Integer.valueOf(definition.getAttribute("yPos"));
		int tileWidth = Integer.valueOf(definition.getAttribute("tileWidth"));
		int tileHeight = Integer.valueOf(definition.getAttribute("tileHeight"));
		int sheetWidth = Integer.valueOf(definition.getAttribute("sheetWidth"));
		int sheetHeight = Integer.valueOf(definition.getAttribute("sheetHeight"));
		
		// Load the image and prepare it for the sprite sheet
		Resource<Image> imgResource = ResourceManager.getInstance().getResource(imageID, Image.class);		
		Image spriteImage = imgResource.get().getSubImage(xPos, yPos, sheetWidth, sheetHeight);
		freeable.addFreeable(imgResource);
		
		return new SpriteSheet(spriteImage, tileWidth, tileHeight);
	}

}
