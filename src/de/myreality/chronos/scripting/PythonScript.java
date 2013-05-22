package de.myreality.chronos.scripting;

public class PythonScript implements Script {
	
	private String path;
	
	public PythonScript(String path) {
		this.path = path;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void runScriptFunction(String functionName, Object[] params) {
		
	}

	@Override
	public void runScriptFunction(String functionName, Object object) {
		
	}

}
