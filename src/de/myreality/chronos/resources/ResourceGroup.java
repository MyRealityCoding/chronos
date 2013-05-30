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

import java.io.Serializable;
import java.util.Collection;

import de.myreality.chronos.util.FamilyObject;
import de.myreality.chronos.util.IDProvider;

/**
 * A resource group contains all direct definitions as children. Furthermore it
 * provides functionality for global deferred resource loading. Also a resource
 * group can have other resource groups as children.
 * <p>
 * If the player has not defined an own resource group, Chronos will create a
 * "root" group for it.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public interface ResourceGroup extends FamilyObject<ResourceGroup>,
		Serializable, IDProvider {

	// ===========================================================
	// Constants
	// ===========================================================

	static final String DEFAULT_ID = "root";

	// Resource element tag
	public static final String RESOURCE_TAG = "group";

	// Resource element tag
	public static final String ID = "id";

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Returns the group id of this group.
	 * 
	 * @return current group id
	 */
	@Override
	String getId();

	/**
	 * Contains true when it contains a specific definition
	 * 
	 * @param definition
	 *            target definition to check
	 * @return True when found
	 */
	boolean containsDefinition(ResourceDefinition definition);

	/**
	 * Returns all containing definitions
	 * 
	 * @return collection of resource definitions
	 */
	Collection<ResourceDefinition> getDefinitions();

	/**
	 * Adds a new resource definition to the group
	 * 
	 * @param definition
	 *            definition to add
	 */
	void addResourceDefinition(ResourceDefinition definition);

	/**
	 * Removes an existing definition from the group
	 * 
	 * @param definition
	 *            definition in the list
	 */
	void removeResourceDefinition(ResourceDefinition definition);

	/**
	 * Removes an existing definition by id
	 * 
	 * @param definitionId
	 *            definition id
	 */
	void removeResourceDefinition(String definitionId);

	/**
	 * Sets a new id to the resource group
	 * 
	 * @param id
	 */
	void setId(String id);
}
