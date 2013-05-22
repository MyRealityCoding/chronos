package de.myreality.chronos.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML implementation of a datasource. Reads a XML file and converts it into a
 * collection of resource definitions.
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
		NodeList nodes = getXMLNodes(file);
		return scanNodeList(nodes, null);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	
	/**
	 * Tries to open a XML file and reads the content. Afterwards it will return a nodelist object.
	 * 
	 * @param file Path of the file
	 * @return NodeList object which contains all XML nodes
	 * @throws ResourceException Is thrown when the file is corrupt or not valid
	 */
	private NodeList getXMLNodes(String file) throws ResourceException {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(input);
			doc.getDocumentElement().normalize();
			return doc.getFirstChild().getChildNodes();
		} catch (Exception e) {
			throw new ResourceException(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new ResourceException(e);
				}
			}
		}
	}

	
	/**
	 * Scans a node list recursively and detecting groups and child definitions.
	 * 
	 * @param nodes nodes to check
	 * @param parent parent node of the list
	 * @return A collection of processed resource definitions
	 * @throws ResourceException Is thrown when a group or a definition is not valid
	 */
	private Collection<ResourceDefinition> scanNodeList(NodeList nodes, Node parent) throws ResourceException {
		
		Collection<ResourceDefinition> definitions = new ArrayList<ResourceDefinition>();
		
		for (int index = 0; index < nodes.getLength(); ++index) {
			
			//Node node = nodes.item(index);
			
			
			
		}
		
		return definitions;
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
