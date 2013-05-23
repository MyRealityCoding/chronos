package de.myreality.chronos.resources;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for a xml source
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class XMLSourceTest {

	DataSource source;

	@Before
	public void setUp() throws Exception {
		source = new XMLSource("xml/example.xml");
	}

	@Test
	public void testLoad() {
		try {
			Collection<ResourceDefinition> definitions = source.load();

			assertTrue("There should be 9 definitions instead of "
					+ definitions.size(), definitions.size() == 9);

			for (ResourceDefinition definition : definitions) {
				assertFalse("ID must be set of definition: " + definition,
						definition.getId().isEmpty());
				assertFalse("Value must be set of definition: " + definition,
						definition.getValue().isEmpty());
				assertTrue("The definition " + definition
						+ " needs at least a group (root)",
						definition.getGroup() != null);

				ResourceGroup group = definition.getGroup();
				assertFalse("The group " + group + " needs children", group
						.getChildren().isEmpty());
				assertTrue("The group " + group
						+ " should have the definition " + definition
						+ " as child", group.containsDefinition(definition));
			}
		} catch (ResourceException e) {
			fail(e.getMessage());
		}
	}
}
