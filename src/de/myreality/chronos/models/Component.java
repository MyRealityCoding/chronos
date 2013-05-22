package de.myreality.chronos.models;

import java.util.Collection;
import java.util.HashMap;

import de.myreality.chronos.scripting.Script;
import de.myreality.chronos.scripting.ScriptBuilder;
import de.myreality.chronos.scripting.Scriptable;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Game component for individual game logic.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class Component extends NodeObject<Component> implements Scriptable {

	private static final long serialVersionUID = 1L;

	// String identification
	protected String id;
	
	// Target owner of the component
    protected Entity owner;    
    
    private static int count;
    
    private HashMap<String, Script> scripts;
    
    /**
     * Constructor to set the id
     */
    public Component() {
    	this.id = getClass().getSimpleName() + "_" + count++;
    	scripts = new HashMap<String, Script>();
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
    
    
    public Entity getOwner() {
    	return owner;
    }


	@Override
	public void generateScript(ScriptBuilder builder) {
		final String param = getClass().getSimpleName().toLowerCase();
		builder.addFunction("create", param);
		
	}


	@Override
	public void appendScript(Script script) {
		scripts.put(script.getPath(), script);
		script.runScriptFunction("create", this);		
	}


	@Override
	public Collection<Script> getScripts() {
		return scripts.values();
	}


	@Override
	public Script getScript(String path) {
		return scripts.get(path);
	}
}
