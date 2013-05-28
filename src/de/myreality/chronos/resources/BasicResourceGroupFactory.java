package de.myreality.chronos.resources;

import de.myreality.chronos.resources.data.DataNode;

/**
 * Basic factory implementation for resource groups
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicResourceGroupFactory implements ResourceGroupFactory {

	
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
	
	public BasicResourceGroupFactory() {
		this.validator = new GroupValidator();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================
	
	@Override
	public ResourceGroup create(DataNode node) throws ResourceException {
		
		validator.validate(node);		
		String id = node.getAttribute("id");		
		ResourceGroup group = new BasicResourceGroup(id);		
		return group;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner classes
	// ===========================================================
}
