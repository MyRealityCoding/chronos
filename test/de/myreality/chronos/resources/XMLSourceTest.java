package de.myreality.chronos.resources;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
	
	ResourceDefinitionManager manager;
	
	@Before
	public void setUp() throws Exception {
		source = new XMLSource("xml/example.xml");
		manager = new BasicResourceDefinitionManager(new BasicResourceGroupManager());
		source.addListener(manager);
	}

	@Test
	public void testLoad() {
		try {
			
			
			Collection<DataNode> nodes = source.load();

			assertTrue("There should be 9 definitions instead of "
					+ nodes.size(), nodes.size() == 9);

			for (ResourceDefinition definition : manager.getAllElements()) {
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
