package de.myreality.chronos.slick;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.shader.ShaderProgram;

import de.myreality.chronos.resource.Freeable;
import de.myreality.chronos.resource.ResourceLoader;

/**
 * Resource loader for slick music. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * <p>
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <p>
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.7alpha
 */
public class ShaderProgramLoader extends ResourceLoader<ShaderProgram> {

	// Singleton instance
	private static ShaderProgramLoader instance;
	
	static {
		instance = new ShaderProgramLoader();
	}
	
	/**
	 * Private constructor
	 */
	private ShaderProgramLoader() { }
	
	public static ShaderProgramLoader getInstance() {
		return instance;
	}
	
	
	@Override
	protected ShaderProgram loadResource(Freeable freeable, ResourceDefinition definition) {
		
		String vertexFile = definition.getAttribute("vertex");
		String fragmentFile = definition.getAttribute("fragment");
		
		try {
			return ShaderProgram.loadProgram(vertexFile, fragmentFile);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
