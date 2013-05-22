package de.myreality.chronos.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.chronos.resource.Resource;
import de.myreality.chronos.resource.ResourceManager;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Animation render component in order to draw animations. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class AnimationRenderComponent extends SlickRenderComponent {
	
	private static final long serialVersionUID = 1L;
	
	private Resource<Animation> animation;

	public AnimationRenderComponent(String animationID) {
		animation = ResourceManager.getInstance().getResource(animationID, Animation.class);
		animation.get().setAutoUpdate(false);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if (owner instanceof SlickEntity) {
			SlickEntity sOwner = (SlickEntity)owner;
			animation.get().draw(sOwner.getGlobalX(), sOwner.getGlobalY(), sOwner.getWidth(), sOwner.getHeight());
		} else {
			animation.get().draw(owner.getGlobalX(), owner.getGlobalY());
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		for (int frameIndex = 0; frameIndex < animation.get().getFrameCount(); ++frameIndex) {
			Image frameImage = animation.get().getImage(frameIndex);
			frameImage.rotate(owner.getRotation() - frameImage.getRotation());
		}
		animation.get().update(delta);
	}

}
