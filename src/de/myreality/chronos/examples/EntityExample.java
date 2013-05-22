package de.myreality.chronos.examples;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.myreality.chronos.ChronosException;
import de.myreality.chronos.models.Entity;
import de.myreality.chronos.models.EntityManager;
import de.myreality.chronos.models.EntitySystem;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Example for scanning the current game setup. This is necessary for updating the
 * game correctly. 
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class EntityExample {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		try {
			EntitySystem system1 = EntityManager.getInstance().addSystem("system1");
			
			Entity entity1 = new Entity();
			Entity entity2 = new Entity();
			Entity entity3 = new Entity();
			entity1.attachTo(entity2);
			entity2.attachTo(entity3);
			
			// Set new positions
			entity1.setLocalPosition(50, 50, 50);
			entity3.setLocalPosition(100, 0, 100);
			entity2.setLocalPosition(500, 500, 0);
			
			system1.addEntity(entity1);
			system1.addEntity(entity2);
			system1.addEntity(entity3);
			
			// Save all entities
			FileOutputStream fStream = new FileOutputStream("system.data");
			ObjectOutputStream objectStream = new ObjectOutputStream(fStream);
			objectStream.writeObject(system1);
			objectStream.close();
			
			// Show the old results
			for (Entity entity : system1.getAllEntities()) {
				System.out.println(entity.getId() + ": " + entity.getGlobalPosition());
			}
			system1.clear();
			System.out.println("New Setting:");
			
			// Load the entities
			FileInputStream fileIn = new FileInputStream("system.data");
			ObjectInputStream objectInStream = new ObjectInputStream(fileIn);
			system1 = (EntitySystem)objectInStream.readObject();
			objectInStream.close();
			
			for (Entity entity : system1.getAllEntities()) {
				System.out.println(entity.getId() + ": " + entity.getGlobalPosition());
			}
		} catch (ChronosException e) {
			e.printStackTrace();
		}
	}

}
