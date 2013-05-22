package de.myreality.chronos.resources;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for a string loader
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class StringLoaderTest {
	
	StringLoader loader;
	
	ResourceDefinition definition;
	
	static final String VALUE = "Hello World!";

	@Before
	public void setUp() throws Exception {
		loader = new StringLoader();
		definition = new BasicResourceDefinition("my_string", "string");
		definition.setValue(VALUE);
	}

	@Test
	public void testCreate() {
		try {
			String result = loader.create(definition);
			assertTrue("The presetted value should be valid", result.equals(VALUE));
		} catch (ResourceException e) {
			fail("Resource definition should be valid");
		}
	}
}
