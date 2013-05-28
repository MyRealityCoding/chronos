package de.myreality.chronos.resources;

import de.myreality.chronos.resources.data.DataNode;

/**
 * Validates a data node for a given purpose
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public interface ResourceValidator {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Validates a node for correctness. If the validation fails
	 * an exception will be thrown.
	 * 
	 * @param node target node to check
	 * @throws ResourceException Is thrown when the validation fails
	 */
	void validate(DataNode node) throws ResourceException;
}
