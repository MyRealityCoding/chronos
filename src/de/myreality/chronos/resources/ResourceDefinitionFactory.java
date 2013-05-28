package de.myreality.chronos.resources;

import de.myreality.chronos.resources.data.DataNode;


/**
 * Factory which creates resource definitions from data nodes
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public interface ResourceDefinitionFactory {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Creates a new resource definition from the data node
	 * 
	 * @param node target node to create the object from
	 * @return new resource definition instance
	 * @throws ResourceException Is thrown when the node does not match to a resource definition
	 */
	ResourceDefinition create(DataNode node) throws ResourceException;
}
