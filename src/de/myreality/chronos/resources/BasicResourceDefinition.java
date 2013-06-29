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

import java.util.HashMap;
import java.util.Map;

import de.myreality.chronos.util.BasicFamilyObject;

/**
 * Basic implementation of a resource definition
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicResourceDefinition extends BasicFamilyObject<ResourceDefinition> implements ResourceDefinition {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = 1L;

	// ===========================================================
	// Fields
	// ===========================================================
	
	private String type;
	
	private String id;
	
	private String value;
	
	private ResourceGroup group;
	
	private Map<String, String> attributes;
	
	private boolean deferred;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	BasicResourceDefinition(String id, String type) {
		this(id, type, false);
	}
	
	BasicResourceDefinition(String id, String type, boolean deferred) {
		this.type = type;
		this.id = id;
		this.attributes = new HashMap<String, String>();
		this.value = "";
		this.deferred = deferred;
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
	public String getValue() {
		return value;
	}

	@Override
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public ResourceGroup getGroup() {
		return group;
	}

	@Override
	public String getGroupId() {
		if (group != null) {
			return group.getId();
		} else {
			return ResourceGroup.DEFAULT_ID;
		}
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}

	@Override
	public void setGroup(ResourceGroup group) {
		if (group != null && !group.equals(this.group)) {
			if (this.group != null) {
				this.group.removeResourceDefinition(this);
			}
			
			if (!group.containsDefinition(this)) {
				group.addResourceDefinition(this);	
			}
		}
		
		this.group = group;
	}

	@Override
	public boolean isDeferred() {
		return deferred;
	}

	@Override
	public void setDeferred(boolean deferred) {
		this.deferred = deferred;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicResourceDefinition other = (BasicResourceDefinition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicResourceDefinition [type=" + type + ", id=" + id
				+ ", value=" + value + ", group=" + group + ", attributes="
				+ attributes + ", deferred=" + deferred + "]";
	}
	
	
	

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner classes
	// ===========================================================
}
