package de.myreality.chronos.resources;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for resource loader
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class ResourceLoaderTest {
	
	private StringLoader loader;
	
	@Before
	public void setUp() throws Exception {
		loader = new StringLoader();
		loader.loadResource(new BasicResourceDefinition("s1", "string"));
		loader.loadResource(new BasicResourceDefinition("s2", "string"));
	}

	@Test
	public void testGetResource() {
		assertTrue("There should be the first string resource inside the loader", loader.getResource("s1") != null);
		assertTrue("There should be the first string resource inside the loader", loader.getResource("s2") != null);
	}

	@Test
	public void testContainsResource() {
		assertTrue("There should be the first string resource inside the loader", loader.containsResource("s1"));
		assertTrue("There should be the first string resource inside the loader", loader.containsResource("s2"));
	}

	@Test
	public void testGetResourceClass() {
		assertTrue("The resource class should be java.lang.String", loader.getResourceClass().equals(String.class));
	}
}
