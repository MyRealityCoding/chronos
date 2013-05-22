/* Chronos - Game Development Toolkit for Java game developers. The
 * original source remains:
 * 
 * Copyright (c) 2013 Miguel Gonzalez http://my-reality.de
 * 
 * This source is provided under the terms of the BSD License.
 * 
 * Copyright (c) 2013, Chronos
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 *  * Redistributions of source code must retain the above 
 *    copyright notice, this list of conditions and the 
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above 
 *    copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided 
 *    with the distribution.
 *  * Neither the name of the Chronos/my Reality Development nor the names of 
 *    its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 */
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
		return scanNodeList(nodes, null, null, null);
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
	private Collection<ResourceDefinition> scanNodeList(NodeList nodes, Node parent, ResourceDefinition parentDefinition, ResourceGroup parentGroup) throws ResourceException {
		
		Collection<ResourceDefinition> definitions = new ArrayList<ResourceDefinition>();
		
		for (int index = 0; index < nodes.getLength(); ++index) {
			
			Node node = nodes.item(index);
			
			if (isDefinition(node)) {
				ResourceDefinition definition = convertNodeToDefinition(node);
				
				// Only add to node list when parent is not a definition
				if (!isDefinition(parent)) {
					definitions.add(definition);
				}
			} else if (isGroup(node)) {
				if (parent != null && !isGroup(parent)) {
					throw new ResourceException("Only groups are permitted as parent for another group");
				} else if (parent == null) {
					
				} else {
					
				}
			} else {
				throw new ResourceException("Invalid tag in resource file: <" + node.getNodeName() + ">");
			}
			
		}
		
		return definitions;
	}
	
	
	private ResourceDefinition convertNodeToDefinition(Node node) throws ResourceException {
		return null;
	}
	
	private boolean isDefinition(Node node) {
		if (node != null) {
			return node.getNodeName().equals(ResourceDefinition.RESOURCE_TAG);
		} else {
			return false;
		}
	}
	
	private boolean isGroup(Node node) {
		if (node != null) {
			return node.getNodeName().equals(ResourceGroup.RESOURCE_TAG);
		} else {
			return false;
		}
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
