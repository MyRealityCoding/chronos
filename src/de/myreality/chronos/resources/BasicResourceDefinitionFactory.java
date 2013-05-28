package de.myreality.chronos.resources;

import java.util.Map.Entry;

import de.myreality.chronos.resources.data.DataNode;

/**
 * Basic implementation of a resource definition factory
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicResourceDefinitionFactory implements
		ResourceDefinitionFactory {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private ResourceValidator validator;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public BasicResourceDefinitionFactory() {
		validator = new DefinitionValidator();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public ResourceDefinition create(DataNode node) throws ResourceException {

		validator.validate(node);

		String type = node.getAttribute("type");
		String id = node.getAttribute("id");
		
		ResourceDefinition definition = new BasicResourceDefinition(id, type);
		for (Entry<String, String> entry : node.getAttributes().entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			if (!key.equals("id") && !key.equals("type")
					&& !key.equals("deferred")) {
				definition.addAttribute(key, value);
			}
		}
		
		if (node.getNumberOfChildren() == 0) {
			definition.setValue(node.getContent().trim());
		}

		return definition;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner classes
	// ===========================================================
}
