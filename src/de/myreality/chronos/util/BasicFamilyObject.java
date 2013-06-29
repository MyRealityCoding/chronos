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
package de.myreality.chronos.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Basic implementation of a family object
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicFamilyObject<T extends FamilyObject<T> > implements FamilyObject<T> {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = 1L;
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	// Parent of this object
	private T parent;
	
	// Contains all children
	private Set<T> children;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public BasicFamilyObject() {
		children = new HashSet<T>();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@SuppressWarnings("unchecked")
	@Override
	public void attachTo(T parent) {
		if (!hasParent() || (hasParent() && !getParent().equals(parent))) {
			detach();
			this.parent = parent;	
			parent.addChild((T) this);		
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean detach() {
		if (hasParent()) {
			parent.removeChild((T)this);
			parent = null;
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addChild(T child) {
		if (!hasChild(child)) {
			children.add(child);
			child.attachTo((T)this);
		}
	}

	@Override
	public boolean hasChild(T child) {
		return children.contains(child);
	}

	@Override
	public void removeChild(T child) {
		if (hasChild(child)) {
			children.remove(child);
			child.detach();
		}
	}

	@Override
	public boolean isParent() {
		return getNumberOfChildren() > 0;
	}

	@Override
	public boolean hasParent() {
		return parent != null;
	}

	@Override
	public T getParent() {
		return parent;
	}

	@Override
	public Collection<T> getChildren() {
		return children;
	}

	@Override
	public int getNumberOfChildren() {
		return children.size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicFamilyObject other = (BasicFamilyObject) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
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
