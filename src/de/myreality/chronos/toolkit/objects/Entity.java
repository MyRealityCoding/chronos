package de.myreality.chronos.toolkit.objects;

import java.util.ArrayList;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Entity object. This is a basic game object.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class Entity extends NodeObject {

	// Contained components
	private ArrayList<Component> components;
	
	// TODO: Target render component
	//private RenderComponent renderComponent;
	
	// String id
	private String id;
	
	
	/**
	 * Constructor to initialize the entity
	 * 
	 * @param id A string id
	 */
	public Entity(String id) {
        this.id = id; 
        components = new ArrayList<Component>();
    }
	
	
	
	/**
	 * Adds a new component to the system
	 * 
	 * @param component
	 */
	public void addComponent(Component component) {
		/*if(RenderComponent.class.isInstance(component)) {
            renderComponent = (RenderComponent)component;
		}*/ // TODO: Implement render functionality
        component.setOwnerEntity(this);
        components.add(component);
    }
 
	
	
	/**
	 * Returns a given component
	 * 
	 * @param id ID of the component
	 * @return Found component
	 */
    public Component getComponent(String id) {
    	
        for(Component comp : components) {
        	if (comp.getId().equalsIgnoreCase(id)) {
        		return comp;
        	}
        }
 
        return null;
    }
    
    
    
    /**
     * Returns the ID
     */
    public String getId() {
    	return id;
    }
    
    
	
	
}
