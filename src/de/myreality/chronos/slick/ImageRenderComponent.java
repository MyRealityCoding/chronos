package de.myreality.chronos.slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.chronos.resource.ResourceManager;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Image render component in order to draw images. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ImageRenderComponent extends SlickRenderComponent {
	
	private static final long serialVersionUID = 1L;
	
	// Image to render later
	private String imageID;
	

	public ImageRenderComponent(String imageID) {
		this.imageID = imageID;
	}
	
	public Image getImage() {
		ResourceManager manager = ResourceManager.getInstance();
		return manager.getResource(imageID, Image.class).get();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		Image image = getImage();
		if (owner instanceof SlickEntity) {
			SlickEntity slickOwner = (SlickEntity)owner;			
			image.draw(slickOwner.getGlobalX(), slickOwner.getGlobalY(), slickOwner.getWidth(), slickOwner.getHeight(), slickOwner.getColor());
		} else {
			image.draw(owner.getGlobalX(), owner.getGlobalY(), owner.getScale());
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		getImage().rotate(owner.getRotation() - getImage().getRotation());		
	}
}
