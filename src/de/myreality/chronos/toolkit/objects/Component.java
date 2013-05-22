package de.myreality.chronos.toolkit.objects;


/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Game component for individual game logic.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class Component {

	// String identification
	protected String id;
	
	// Target owner of the component
    protected Entity owner;
    
    
    /**
     * Basic constructor
     */
    public Component() {
    	owner = null;
    	id = toString();
    }
    
    
    /**
     * Constructor to set the id
     * 
     * @param id component id
     */
    public Component(String id) {
    	this.id = id;
    }
 
    
    /**
     * Returns the current id
     * 
     * @return The current id
     */
    public String getId()
    {
        return id;
    }
 
    
    /**
     * Change the owner of the component
     * 
     * @param owner Current Owner
     */
    public void setOwnerEntity(Entity owner)
    {
    	this.owner = owner;
    }
    
    
    /**
     * Determines if an owner exists
     */
    public boolean hasOwner() {
    	return owner != null;
    }

}
