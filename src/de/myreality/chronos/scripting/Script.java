package de.myreality.chronos.scripting;


public interface Script {
	
	String getPath();
	
	/**
	* Call a Lua function inside the Lua script to insert
	* data into a Java object passed as parameter
	* @param functionName Name of Lua function.
	* @param params A list of Java object.
	*/
	void runScriptFunction(String functionName, Object[] params);
	
	/**
	* Call a Lua function inside the Lua script to insert
	* data into a Java object passed as parameter
	* @param functionName Name of Lua function.
	* @param object A Java object.
	*/
	void runScriptFunction(String functionName, Object object);
}
