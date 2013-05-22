package de.myreality.chronos.examples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.myreality.chronos.models.Entity;
import de.myreality.chronos.models.EntityManager;
import de.myreality.chronos.models.EntitySystem;
import de.myreality.chronos.resource.ResourceManager;
import de.myreality.chronos.slick.ImageLoader;
import de.myreality.chronos.slick.ImageRenderComponent;
import de.myreality.chronos.slick.SlickEntity;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Example for saving and loading a current game state.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class SerialisationExample extends BasicGame {
	
	private EntitySystem renderSystem;
	
	private static final String OBJECT_FILE = "game.dat";

	public SerialisationExample() {
		super("Serialisation Example with Slick and Chronos");
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
		
		// Add the system
		renderSystem = EntityManager.getInstance().addSystem("RENDER_SYSTEM");
		
		// Add some entities
		SlickEntity entity1 = new SlickEntity();
		SlickEntity entity2 = new SlickEntity();
		entity1.setBounds(100, 100, 100, 100);
		entity2.setBounds(0, 0, 60, 60);
		entity2.attachTo(entity1);
		
		
		ImageRenderComponent imageComponent1 = new ImageRenderComponent("INTRO_JAVA_LOGO");
		ImageRenderComponent imageComponent2 = new ImageRenderComponent("ARTWORK_01");
		entity1.addComponent(imageComponent1);
		entity2.addComponent(imageComponent2);
		entity1.setEntitySystem(renderSystem);
		entity2.setEntitySystem(renderSystem);
		
		saveToFile(OBJECT_FILE);
	}
	
	private void saveToFile(String file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);			
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			
			objectOut.writeObject(renderSystem);
			
			objectOut.close();
			fileOut.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadFromFile(String file) {
		try {
			FileInputStream fileIn = new FileInputStream(file);			
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			
			renderSystem = (EntitySystem)objectIn.readObject();
			
			objectIn.close();
			fileIn.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_F2)) {
			saveToFile(OBJECT_FILE);
		} else if (input.isKeyPressed(Input.KEY_F1)) {
			loadFromFile(OBJECT_FILE);
		}
		
		for (Entity entity : renderSystem.getAllEntities()) {
			if (entity instanceof SlickEntity) {
				SlickEntity slickEntity = (SlickEntity)entity;
				slickEntity.update(gc, null, delta);				
				
				float speed = 0.5f * delta;
				
				if (input.isKeyDown(Input.KEY_D)) {
					slickEntity.setGlobalX(slickEntity.getGlobalX() + speed);
				}
				
				if (input.isKeyDown(Input.KEY_A)) {
					slickEntity.setGlobalX(slickEntity.getGlobalX() - speed);
				}
				
				if (input.isKeyDown(Input.KEY_W)) {
					slickEntity.setGlobalY(slickEntity.getGlobalY() - speed);
				}
				
				if (input.isKeyDown(Input.KEY_S)) {
					slickEntity.setGlobalY(slickEntity.getGlobalY() + speed);
				}
			}
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new SerialisationExample());
		game.setDisplayMode(800, 600, false);
		game.start();
	}

}
