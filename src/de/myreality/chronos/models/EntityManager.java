package de.myreality.chronos.models;

import java.util.HashMap;

import de.myreality.chronos.ChronosException;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * Singleton entity manager that manages multiple entity systems.
 * You are able to move or copy one entity to another system on a specific
 * condition.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class EntityManager {
	
	// Singleton object
	private static EntityManager instance;
	
	// Entity systems
	private HashMap<String, EntitySystem> systems;
	
	static {
		instance = new EntityManager();
	}
	
	private EntityManager() {
		systems = new HashMap<String, EntitySystem>();
	}
	
	
	/**
	 * Add a new entity system to the manager
	 * 
	 * @param id custom id of the system
	 */
	public EntitySystem addSystem(String id) throws ChronosException {
		if (!systems.containsKey(id)) {
			EntitySystem system = new EntitySystem(id);
			systems.put(id,  system);
			return system;
		} else throw new ChronosException("Entity system with id=" + id + "does already exist");
	}
	
	/**
	 * Removes a given entity system
	 * 
	 * @param id custom id of the system
	 */	
	public void removeSystem(String id) {
		systems.remove(id);
	}
	
	
	/**
	 * Returns a given entity system
	 * 
	 * @return A valid entity system
	 */
	public EntitySystem getSystem(String id) {
		return systems.get(id);
	}
	
	public static EntityManager getInstance() {
		return instance;
	}

}
