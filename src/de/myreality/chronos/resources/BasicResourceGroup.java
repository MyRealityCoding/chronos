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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.myreality.chronos.util.BasicFamilyObject;

/**
 * Basic implementation of a resource group
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicResourceGroup extends BasicFamilyObject<ResourceGroup>
		implements ResourceGroup {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = 1L;

	// ===========================================================
	// Fields
	// ===========================================================

	private String id;

	private Map<String, ResourceDefinition> definitions;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BasicResourceGroup() {
		this(DEFAULT_ID);
	}

	public BasicResourceGroup(String id) {
		this(id, false);
	}

	public BasicResourceGroup(String id, boolean deferred) {
		this.id = id;
		definitions = new HashMap<String, ResourceDefinition>();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean containsDefinition(ResourceDefinition definition) {
		return definitions.containsValue(definition);
	}

	@Override
	public Collection<ResourceDefinition> getDefinitions() {
		return definitions.values();
	}

	@Override
	public void addResourceDefinition(ResourceDefinition definition) {
		definitions.put(definition.getId(), definition);
	}

	@Override
	public void removeResourceDefinition(ResourceDefinition definition) {
		removeResourceDefinition(definition.getId());
	}

	@Override
	public void removeResourceDefinition(String definitionId) {
		ResourceDefinition def = definitions.get(definitionId);

		if (def != null) {
			if (equals(def.getGroup())) {
				def.setGroup(null);
			}

			definitions.remove(definitionId);
		}
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((definitions == null) ? 0 : definitions.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicResourceGroup other = (BasicResourceGroup) obj;
		if (definitions == null) {
			if (other.definitions != null)
				return false;
		} else if (!definitions.equals(other.definitions))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner classes
	// ===========================================================
}
