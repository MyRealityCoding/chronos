package de.myreality.chronos.resources;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.myreality.chronos.resources.data.BasicDataNode;
import de.myreality.chronos.resources.data.DataNode;

/**
 * Test case for a definition validator
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class DefinitionValidatorTest {
	
	DefinitionValidator validator;
	
	DataNode validNode;
	
	DataNode invalidNode1, invalidNode2, invalidNode3;

	@Before
	public void setUp() throws Exception {
		validator = new DefinitionValidator();
		
		validNode = new BasicDataNode("resource");
		validNode.addAttribute("id", "id1");
		validNode.addAttribute("type", "string");		
		
		invalidNode1 = new BasicDataNode("", "");
		
		invalidNode2 = new BasicDataNode("resource");
		invalidNode2.addAttribute("id", "");
		invalidNode2.addAttribute("type", "vector");
		
		invalidNode3 = new BasicDataNode("resource");
		invalidNode3.addAttribute("id", "bla");
		invalidNode3.addAttribute("type", "");
	}

	@Test
	public void testValidate() {
		try {
			validator.validate(validNode);
		} catch (ResourceException e) {
			fail(e.getMessage());
		}
		
		validateInvalidNode(invalidNode1);
		validateInvalidNode(invalidNode2);
		validateInvalidNode(invalidNode3);
	}	
	
	private void validateInvalidNode(DataNode node) {
		try {
			validator.validate(invalidNode1);
			fail("Node " + invalidNode1 + " is invalid.");
		} catch (ResourceException e) {
			// Good!
		}
	}
}
