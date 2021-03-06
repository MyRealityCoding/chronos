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

import de.myreality.chronos.resources.data.BasicDataNode;
import de.myreality.chronos.resources.data.DataNode;
import de.myreality.chronos.resources.data.DataSourceEvent;
import de.myreality.chronos.util.BasicManager;

/**
 * Basic implementation of a resource definition manager
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicResourceDefinitionManager extends
		BasicManager<ResourceDefinition> implements ResourceDefinitionManager {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = 1L;

	// ===========================================================
	// Fields
	// ===========================================================

	private ResourceGroupManager manager;

	private ResourceDefinitionFactory factory;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BasicResourceDefinitionManager(ResourceGroupManager manager) {
		this.manager = manager;
		factory = new BasicResourceDefinitionFactory();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public void beforeLoad() {

	}

	@Override
	public void onNodeCreate(DataSourceEvent event) throws ResourceException {
		DataNode node = event.getNode();
		DataNode parentNode = event.getParent();

		if (node.getName().equals(ResourceDefinition.RESOURCE_TAG)) {

			String id = node.getAttribute(ResourceDefinition.ID);
			String parentId = parentNode.getAttribute(ResourceDefinition.ID);

			ResourceDefinition definition = getElement(id);
			definition = computeDefinition(definition, node);

			if (parentNode != null) {
				if (parentNode.getName()
						.equals(ResourceDefinition.RESOURCE_TAG)) {
					ResourceDefinition parentDefinition = getElement(parentId);
					parentDefinition = computeDefinition(parentDefinition,
							parentNode);
					parentDefinition.addChild(definition);
				} else if (parentNode.getName().equals(
						ResourceGroup.RESOURCE_TAG)) {
					ResourceGroup group = manager.getElement(parentId);
					group = manager.computeGroup(group, parentNode);
					group.addResourceDefinition(definition);
				} else {
					// Create root group
					DataNode rootNode = new BasicDataNode(
							ResourceGroup.RESOURCE_TAG);
					rootNode.addAttribute(ResourceGroup.ID,
							ResourceGroup.DEFAULT_ID);

					ResourceGroup root = manager
							.getElement(ResourceGroup.DEFAULT_ID);
					root = manager.computeGroup(root, parentNode);
					root.addResourceDefinition(definition);
				}
			}

		}
	}

	@Override
	public void afterLoad() {

	}

	@Override
	public ResourceGroupManager getGroupManager() {
		return manager;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	ResourceDefinition computeDefinition(ResourceDefinition definition,
			DataNode node) throws ResourceException {
		if (definition == null) {
			definition = factory.create(node);
			addElement(definition);
		}

		return definition;
	}

	@Override
	public void onError(DataSourceEvent event, Throwable cause) {
		// TODO Auto-generated method stub

	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
