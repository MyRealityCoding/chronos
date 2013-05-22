package de.myreality.chronos.scripting;

import de.myreality.chronos.ChronosException;

public class ScriptNotFoundException extends ChronosException {

	private static final long serialVersionUID = 1L;

	public ScriptNotFoundException(String path) {
		super("Script not found: " + path);
	}

}
