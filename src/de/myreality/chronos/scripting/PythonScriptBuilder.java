package de.myreality.chronos.scripting;

public class PythonScriptBuilder extends BasicScriptBuilder {

	@Override
	protected String getFunctionHeader(String name, String[] params) {
		return "def " + name + "(" + getParameterList(params) + "):";
	}

	@Override
	protected String getFunctionFooter() {
		return "";
	}

	@Override
	protected String getCommentTag() {
		return "#";
	}

}
