package de.myreality.chronos.scripting;

import java.util.Collection;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * Is used by the script builder in order to create scripts of a specific
 * class implementation. Additionally this interface provides scripting
 * support of any scripting language.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.5
 */
public interface Scriptable {

	/**
	 * Is directly called by the script builder in order to make a 
	 * new script for the class.
	 * 
	 * @param builder ScriptBuilder instance
	 */
	void generateScript(ScriptBuilder builder);
	
	
	/**
	 * Appends a new script to the current implementation.
	 * 
	 * @param script Script to append
	 */
	void appendScript(Script script);
	
	
	/**
	 * @return A collection of all contained scripts
	 */
	Collection<Script> getScripts();
	
	
	/**
	 * Returns a script with the given path (if exists)
	 * 
	 * @param path Path ID of the script
	 * @return Script instance (on success), otherwise an 
	 * exception is thrown
	 * @exception Is thrown if the script does not exist
	 */
	Script getScript(String path) throws ScriptNotFoundException;
}
