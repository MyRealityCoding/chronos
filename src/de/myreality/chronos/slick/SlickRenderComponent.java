package de.myreality.chronos.slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.chronos.models.RenderComponent;
import de.myreality.chronos.scripting.ScriptBuilder;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Render component for slick games. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class SlickRenderComponent extends SlickComponent implements RenderComponent {

	private static final long serialVersionUID = 8391790904562117664L;

	/**
	 * Draws the component on the graphics
	 * 
	 * @param gc Game Container of slick
	 * @param sbg State based game of slick
	 * @param g Graphics in order to draw something
	 */
	public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics g);

	@Override
	public void generateScript(ScriptBuilder builder) {
		super.generateScript(builder);
		final String renderFunction = "render";
		final String ownerName = getOwner().getClass().getSimpleName().toLowerCase();
		final String[] params = {ownerName, "container", "stategame", "graphics"};
		builder.addFunction(renderFunction, params);
	}

	
}
