package de.myreality.chronos.resources;

import java.util.Collection;

/**
 * XML implementation of a datasource. Reads a XML file and converts it into
 * a collection of resource definitions.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class XMLSource implements DataSource {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private String file;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public XMLSource(String file) {
		this.file = file;
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public Collection<ResourceDefinition> load() throws ResourceException {
		return null;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner classes
	// ===========================================================
}
