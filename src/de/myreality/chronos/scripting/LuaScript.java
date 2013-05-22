package de.myreality.chronos.scripting;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaScript implements Script {
	
	private String path;
	private LuaValue _G;
	
	public LuaScript(String path) {
		this.path = path;
		_G = JsePlatform.standardGlobals();
		_G.get("dofile").call( LuaValue.valueOf(path));
	}

	@Override
	public void runScriptFunction(String functionName,
			Object[] params) {
		LuaFunction function = (LuaFunction) _G.get(functionName);

		// Converting the objects into lua values
		LuaValue[] luaParams = new LuaValue[params.length];
		
		for (int i = 0; i < luaParams.length; i++) {
			luaParams[i] = CoerceJavaToLua.coerce(params[i]);
		}
		
		function.invoke(LuaValue.varargsOf(luaParams));
	}

	@Override
	public void runScriptFunction(String functionName, Object object) {
		Object[] params = {object};
		runScriptFunction(functionName, params);

	}

	@Override
	public String getPath() {
		return path;
	}

}
