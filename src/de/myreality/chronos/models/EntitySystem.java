package de.myreality.chronos.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * Entity system that contains a list of entities
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class EntitySystem implements Serializable {

	private static final long serialVersionUID = 1L;

	// List of entities
	private ConcurrentHashMap<String, Entity> entities;
	
	private String id;
	
	/**
	 * Default constructor
	 */
	public EntitySystem(String id) {
		entities = new ConcurrentHashMap<String, Entity>();
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void clear() {
		entities.clear();
	}
	
	
	public void addEntity(Entity entity) {
		if (!entities.contains(entity)) {
			entities.put(entity.getId(), entity);
		}
	}
	
	public void removeEntity(Entity entity) {
		removeEntity(entity.getId());
	}
	
	public void removeEntity(String id) {
		entities.remove(id);
	}
	
	public Collection<Entity> getAllEntities() {
		return entities.values();
	}
	
	public boolean isEmpty() {
		return entities.isEmpty();
	}
	
	public boolean contains(Entity entity) {
		return contains(entity.getId());
	}
	
	public boolean contains(String id) {
		return entities.containsKey(id);
	}
	
	public int size() {
		return entities.size();
	}
	
	public Entity getEntity(String id) {
		return entities.get(id);
	}
}
