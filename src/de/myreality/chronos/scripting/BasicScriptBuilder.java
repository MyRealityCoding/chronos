package de.myreality.chronos.scripting;

import java.util.ArrayList;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * Basic implementation of a script builder
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.5
 */
public abstract class BasicScriptBuilder implements ScriptBuilder {

	// Spacing between functions
	private int spacing;
	
	// Spacing of a line
	private int lineSpacing;
	
	// Lines of the script
	private ArrayList<String> lines;
	
	// Lines as string content
	private String scriptContent;
	
	/**
	 * Basic constructor of the basic script builder class
	 */
	public BasicScriptBuilder() {
		lines = new ArrayList<String>();
		setSpacing(2);
		setLineSpacing(3);
	}
	
	/**
	 * Setter for spacing
	 */
	@Override
	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}
	
	protected abstract String getFunctionHeader(String name, String[] params);
	
	protected abstract String getFunctionFooter();
	
	protected abstract String getCommentTag();
	
	protected String getParameterList(String[] params) {
		String paramString = "";
		
		for (int i = 0; i < params.length; ++i) {
			if (i != 0) {
				paramString += ", ";
			}
			paramString += params[i];
		}
		
		return paramString;
	}

	/**
	 * Setter for line spacing
	 */
	@Override
	public void setLineSpacing(int lineSpacing) {
		this.lineSpacing = lineSpacing;
		
	}

	/**
	 * Returns the current spacing amount
	 */
	@Override
	public int getSpacing() {
		return spacing;
	}

	
	/**
	 * Returns the current line spacing amount
	 */
	@Override
	public int getLineSpacing() {
		return lineSpacing;
	}
	
	private String addLineSpacing() {
		String content = "";
		for (int i = 0; i < getLineSpacing(); ++i) {
			content += " ";
		}
		return content;
	}
	
	private void addSpacing() {
		for (int i = 0; i < getSpacing(); ++i) {
			addLine("");
		}
	}

	
	/**
	 * Add a new function to the script build
	 */
	@Override
	public void addFunction(String name, String arg) {
		String[] args = {arg};
		addFunction(name, args);
		
	}

	/**
	 * Add a new function to the script build 
	 * (with multiple parameters)
	 */
	@Override
	public void addFunction(String name, String[] args) {
		addLine(getFunctionHeader(name, args));
		addLine(addLineSpacing());
		addLine(addLineSpacing() + getCommentTag() + " content goes here");
		addLine(addLineSpacing());
		addLine(getFunctionFooter());
		addSpacing();
		
	}
	
	private void addLine(String line) {
		lines.add(line);
	}

	
	/**
	 * Builds a script as string content inside the builder. The built
	 * script can be fetched by calling the toString() method.
	 */
	@Override
	public void build(Scriptable scriptable) {
		clear();
		scriptContent = "";
		scriptable.generateScript(this);	
		for (String line : lines) {
			scriptContent += line + "\n";
		}
	}

	
	/**
	 * Clears the current building state
	 */
	@Override
	public void clear() {
		lines.clear();		
	}

	
	/**
	 * @return True, if no content exists
	 */
	@Override
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	
	/**
	 * @return Current line count
	 */
	@Override
	public int getLineCount() {
		return lines.size();
	}

	
	/**
	 * @return Current script content
	 */
	@Override
	public String toString() {
		return scriptContent;
	}
	
	
	/**
	 * @return Line at a specific index position
	 */
	public String getLine(int index) {
		return lines.get(index);
	}
	
	
}
