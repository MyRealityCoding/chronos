package de.myreality.chronos.scripting;


public class LuaScriptBuilder extends BasicScriptBuilder {

	@Override
	protected String getFunctionHeader(String name, String[] params) {
		return "function " + name + "(" + getParameterList(params) + ")";
	}

	@Override
	protected String getFunctionFooter() {
		return "end";
	}

	@Override
	protected String getCommentTag() {
		return "--";
	}
}
