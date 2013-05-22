package de.myreality.chronos.examples;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.myreality.chronos.models.Entity;
import de.myreality.chronos.models.EntityManager;
import de.myreality.chronos.models.EntitySystem;
import de.myreality.chronos.resource.Resource;
import de.myreality.chronos.resource.ResourceManager;
import de.myreality.chronos.scripting.Script;
import de.myreality.chronos.slick.ImageLoader;
import de.myreality.chronos.slick.ImageRenderComponent;
import de.myreality.chronos.slick.SlickEntity;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Example for loading a script and move an entity to the right hand side.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ScriptingExample extends BasicGame {
	
	private EntitySystem renderSystem;

	public ScriptingExample() {
		super("Scripting Example with Slick and Chronos");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Entity entity : renderSystem.getAllEntities()) {
			if (entity instanceof SlickEntity) {
				((SlickEntity)entity).render(gc, null, g);
			}
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ResourceManager manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.fromXML("res/images/images.xml");
		manager.fromXML("res/scripts/scripts.xml");
		
		// Add the system
		renderSystem = EntityManager.getInstance().addSystem("RENDER_SYSTEM");
		
		// Add some entities
		SlickEntity entity = new SlickEntity();
		entity.setBounds(0, 0, 100, 100);
		
		ImageRenderComponent imageComponent = new ImageRenderComponent("INTRO_JAVA_LOGO");
		
		entity.addComponent(imageComponent);
		entity.setEntitySystem(renderSystem);
		
		// Append the moving script
		Resource<Script> movingScript = ResourceManager.getInstance().getResource("MOVING", Script.class);
		entity.appendScript(movingScript.get());
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		for (Entity entity : renderSystem.getAllEntities()) {
			if (entity instanceof SlickEntity) {
				SlickEntity slickEntity = (SlickEntity)entity;
				slickEntity.update(gc, null, delta);
			}
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new ScriptingExample());
		game.setDisplayMode(800, 600, false);
		game.start();
	}

}
