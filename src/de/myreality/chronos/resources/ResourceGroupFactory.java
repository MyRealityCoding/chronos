package de.myreality.chronos.resources;

import de.myreality.chronos.resources.data.DataNode;


/**
 * Factory which creates resource groups from data nodes
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public interface ResourceGroupFactory {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Creates a new resource group from the data node
	 * 
	 * @param node target node to create the object from
	 * @return new resource definition instance
	 * @throws ResourceException Is thrown when the node does not match to a resource definition
	 */
	ResourceGroup create(DataNode node) throws ResourceException;
}
