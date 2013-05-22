package de.myreality.chronos.resource;

import de.myreality.chronos.ChronosException;
import de.myreality.chronos.scripting.JsScript;
import de.myreality.chronos.scripting.LuaScript;
import de.myreality.chronos.scripting.PythonScript;
import de.myreality.chronos.scripting.Script;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Default resource loader for string types
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ScriptLoader extends ResourceLoader<Script> {
	
	private static ScriptLoader instance;
	
	static {		
		instance = new ScriptLoader();
	}
	
	// Singleton is always private
	private ScriptLoader() { }
	
	public static ScriptLoader getInstance() {
		return instance;
	}

	@Override
	protected Script loadResource(Freeable freeable, ResourceDefinition definition) {
		
		String scriptExtension = definition.getAttribute("type");
		
		if (scriptExtension == null) {
			throw new ChronosException("Missing type argument in script XML");
		}
		
		String path = definition.getContent();
		
		Script script = null;
		
		if (scriptExtension.equalsIgnoreCase("lua")) {
			script = new LuaScript(path);
		} else if (scriptExtension.equalsIgnoreCase("js")) {
			script = new JsScript(path);
		} else if (scriptExtension.equalsIgnoreCase("python")) {
			script = new PythonScript(path);
		}
		
		return script;
	}
}
