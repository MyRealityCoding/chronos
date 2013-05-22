package de.myreality.chronos.resources;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.myreality.chronos.util.ROVector3f;

/**
 * Test case for vector loader
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class ROVectorLoaderTest {
	
	ROVectorLoader loader;

	@Before
	public void setUp() throws Exception {
		loader = new ROVectorLoader();
	}


	@Test
	public void testCreate() {
		ResourceDefinition invalidDefinition1 = new BasicResourceDefinition("my_vec1", "vector");
		invalidDefinition1.addAttribute("x", "1.5");
		invalidDefinition1.addAttribute("y", "dumpd22");
		invalidDefinition1.addAttribute("z", "928.837");
		
		ResourceDefinition invalidDefinition2 = new BasicResourceDefinition("my_vec2", "vector");
		
		ResourceDefinition validDefinition = new BasicResourceDefinition("my_vec2", "vector");
		validDefinition.addAttribute("x", "0.5");
		validDefinition.addAttribute("y", "1.5");
		validDefinition.addAttribute("z", "2.5");
		try {
			loader.create(invalidDefinition1);
			fail("The first definition is not valid");
		} catch (ResourceException e) {
			// Everything is good!
		}
		
		try {
			loader.create(invalidDefinition2);
			fail("The second definition is not valid");
		} catch (ResourceException e) {
			// Everything is good!
		}
		
		try {
			ROVector3f vector = loader.create(validDefinition);			
			assertFalse("Vector should not be null", vector == null);
			assertTrue("Vector x must be 0.5", vector.getX() == 0.5f);
			assertTrue("Vector y must be 1.5", vector.getY() == 1.5f);
			assertTrue("Vector z must be 2.5", vector.getZ() == 2.5f);
		} catch (ResourceException e) {
			fail("The third definition should be valid");
		}
	}
}
