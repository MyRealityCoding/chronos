package de.myreality.chronos.examples;

import java.io.IOException;

import de.myreality.chronos.resource.Resource;
import de.myreality.chronos.resource.ResourceManager;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Example for showing how to use a string resource with Chronos. 
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class StringResourceExample {

	public static void main(String[] args) throws IOException {
		// Get the resource manager
		ResourceManager resourceManager = ResourceManager.getInstance();
		
		// Load a sample file into the system
		resourceManager.fromXML("res/meta.xml");
		
		// Get the resource from the manager
		Resource<String> resource = resourceManager.getResource("TXT_GAME_NAME", String.class);

		// Show me the result
		System.out.println(resource.get());
		
		resource.free();
	}
}
