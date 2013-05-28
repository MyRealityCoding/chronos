package de.myreality.chronos.resources;

import de.myreality.chronos.resources.data.DataNode;

/**
 * Validates for a resource definition
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class DefinitionValidator implements ResourceValidator {

	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public void validate(DataNode node) throws ResourceException {
		
		String name = node.getName();
		String id = node.getAttribute(ResourceDefinition.ID);
		String type = node.getAttribute(ResourceDefinition.TYPE);
		
		assertString(name, node, "name");
		assertString(id, node, "id");
		assertString(type, node, "type");
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	void assertString(String string, DataNode node, String name) throws ResourceException {
		if (string == null || string.isEmpty()) {
			throw new ResourceException("Validation error: " + name + " '" + string + "' of " + node + " is not valid");
		}
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
