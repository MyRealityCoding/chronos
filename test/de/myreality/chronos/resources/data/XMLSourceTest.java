package de.myreality.chronos.resources.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.myreality.chronos.resources.BasicResourceDefinitionManager;
import de.myreality.chronos.resources.BasicResourceGroupManager;
import de.myreality.chronos.resources.ResourceDefinition;
import de.myreality.chronos.resources.ResourceDefinitionManager;
import de.myreality.chronos.resources.ResourceException;
import de.myreality.chronos.resources.ResourceGroup;
import de.myreality.chronos.resources.data.DataNode;
import de.myreality.chronos.resources.data.XMLSource;

/**
 * Test case for a xml source
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class XMLSourceTest {

	static final String FILE = "xml/example.xml";

	XMLSource source;

	ResourceDefinitionManager manager;

	@Before
	public void setUp() throws Exception {
		source = new XMLSource(FILE);
		manager = new BasicResourceDefinitionManager(
				new BasicResourceGroupManager());
		source.addListener(manager);
	}

	@Test
	public void testLoad() {
		try {

			Collection<DataNode> nodes = source.load();

			assertTrue(
					"There should be 4 definitions instead of " + nodes.size(),
					nodes.size() == 4);

			for (ResourceDefinition definition : manager.getAllElements()) {
				assertFalse("ID must be set of definition: " + definition,
						definition.getId().isEmpty());
				assertTrue("The definition " + definition
						+ " needs at least a group (root)",
						definition.getGroup() != null);
				ResourceGroup group = definition.getGroup();
				assertFalse("The group " + group + " needs children", group
						.getDefinitions().isEmpty());
				assertTrue("The group " + group
						+ " should have the definition " + definition
						+ " as child", group.containsDefinition(definition));
			}
		} catch (ResourceException e) {
			fail(e.getMessage());
		}	
	}

	@Test
	public void testGetXMLNodes() {
		try {
			NodeList nodes = source.getXMLNodes(FILE);
			Node node = nodes.item(0);
			NodeList realList = node.getChildNodes();

			assertTrue("There has to be only one child", nodes.getLength() == 1);
			assertFalse("Node has to exists", node == null);
			assertTrue("There must be 11 items in the list instead of "
					+ realList.getLength(), realList.getLength() == 11);
			assertTrue("There must be 5 real nodes in the list",
					countRealNodes(realList) == 5);
		} catch (ResourceException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConvertToDataNode() {
		try {
			NodeList nodes = source.getXMLNodes(FILE);
			Node parent = nodes.item(0);
			NodeList realList = parent.getChildNodes();

			for (int i = 0; i < realList.getLength(); ++i) {
				Node node = realList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					DataNode dataNode = source.convertToDataNode(node);

					assertFalse("Data node must exists", dataNode == null);
					assertTrue("The node name has to be equal", dataNode
							.getName().equals(node.getNodeName()));

					// Test the attributes
					NamedNodeMap attributes = node.getAttributes();

					if (attributes != null) {
						for (int a = 0; i < attributes.getLength(); ++a) {
							Node attribute = attributes.item(a);

							if (attribute.getNodeType() == Node.ATTRIBUTE_NODE) {
								String key = attribute.getNodeName();
								String value = attribute.getNodeValue();
								String dataAttribute = dataNode
										.getAttribute(key);
								assertTrue("Attribute '" + key
										+ "' with value='" + value
										+ "' must exist in data node object",
										dataAttribute.equals(value));
								
							}
						}
					}
				}
			}
		} catch (ResourceException e) {
			fail(e.getMessage());
		}
	}

	private int countRealNodes(NodeList list) {
		int count = 0;

		for (int i = 0; i < list.getLength(); ++i) {
			Node node = list.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				++count;
			}
		}

		return count;
	}

}
