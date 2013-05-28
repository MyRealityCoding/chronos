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
package de.myreality.chronos.resources.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.myreality.chronos.resources.ResourceException;

/**
 * XML implementation of a datasource. Reads a XML file and converts it into a
 * collection of resource definitions.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class XMLSource extends AbstractDataSource {

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
	protected void startLoading() throws ResourceException {
		NodeList nodes = getXMLNodes(file);

		for (int i = 0; i < nodes.getLength(); ++i) {
			Node node = nodes.item(i);
			NodeList items = node.getChildNodes();
			computeNodes(items, node);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Tries to open a XML file and reads the content. Afterwards it will return
	 * a nodelist object.
	 * 
	 * @param file
	 *            Path of the file
	 * @return NodeList object which contains all XML nodes
	 * @throws ResourceException
	 *             Is thrown when the file is corrupt or not valid
	 */
	NodeList getXMLNodes(String file) throws ResourceException {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(input);
			doc.getDocumentElement().normalize();
			return doc.getElementsByTagName("resources");
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
	 * Computes recursively the nodes and injecting them into the parent data
	 * source.
	 * <p>
	 * The nodes are converted into a {@link DataNode} object.
	 * 
	 * @param nodes
	 *            nodes to calculate the children
	 * @param parent
	 *            parent node of the node list
	 */
	void computeNodes(NodeList nodes, Node parent) {

		for (int i = 0; i < nodes.getLength(); ++i) {
			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				DataNode dataNode = convertToDataNode(node);
				DataNode parentNode = convertToDataNode(parent);

				addNode(dataNode, parentNode);

				NodeList children = node.getChildNodes();
				if (children.getLength() > 0) {
					computeNodes(children, node);
				}
			}
		}
	}

	DataNode convertToDataNode(Node node) {
		if (node != null) {
			DataNode dataNode = new BasicDataNode(node.getNodeName(),
					node.getTextContent());
			
			if (node.getChildNodes().getLength() > 0) {
				dataNode.setContent("");
			}

			NamedNodeMap attributes = node.getAttributes();

			if (attributes != null) {
				for (int i = 0; i < attributes.getLength(); ++i) {
					Node attribute = attributes.item(i);

					if (attribute.getNodeType() == Node.ATTRIBUTE_NODE) {
						dataNode.addAttribute(attribute.getNodeName(),
								attribute.getNodeValue());
					}
				}
			}

			return dataNode;
		} else {
			return null;
		}
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
