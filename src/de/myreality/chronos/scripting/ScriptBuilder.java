package de.myreality.chronos.scripting;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * A script builder creates default content for a specific
 * script type like LUA, Javascript or Python.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.4
 */
public interface ScriptBuilder {

	/**
	 * Sets the current spacing between functions
	 * 
	 * @param spacing new spacing value
	 */
	void setSpacing(int spacing);
	
	/**
	 * Sets the current line spacing at the begin of
	 * each line in a function
	 * 
	 * @param lineSpacing new line spacing value
	 */
	void setLineSpacing(int lineSpacing);
	
	/**
	 * @return Returns the current spacing
	 */
	int getSpacing();
	
	
	/**
	 * @return Returns the current line spacing
	 */
	int getLineSpacing();
	
	/**
	 * Adds a new function to the builder
	 * 
	 * @param name Name of the function
	 * @param arg single argument of the function
	 */
	void addFunction(String name, String arg);
	
	
	
	/**
	 * Adds a new function to the builder
	 * 
	 * @param name Name of the function
	 * @param args Arguments of the function (ordered)
	 */
	void addFunction(String name, String[] args);
	
	
	/**
	 * 
	 * 
	 * @param scriptable Scripting target to build from
	 */
	void build(Scriptable scriptable);
	
	void clear();
	
	boolean isEmpty();
	
	int getLineCount();
	
	String getLine(int index);
}
