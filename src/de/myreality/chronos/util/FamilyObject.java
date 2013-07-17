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

import java.io.Serializable;
import java.util.Collection;

/**
 * Provides parent and children support to an object
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public interface FamilyObject<T extends FamilyObject<T> > extends Serializable {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Attaches this object to the parent. When the
	 * object has already a parent, it will be detached
	 * before attaching to a new one.
	 * 
	 * @param parent parent to attach to
	 */
	void attachTo(T parent);
	
	/**
	 * Detaches this object from its parent
	 * 
	 * @return True when the detaching was successful
	 */
	boolean detach();
	
	/**
	 * Adds a new child to this object
	 * 
	 * @param child new child
	 */
	void addChild(T child);
	
	
	/**
	 * Removes a child
	 */
	void removeChild(T child);
	
	/**
	 * Returns true, when the given object exists
	 * as a child
	 * 
	 * @param child target child to check
	 */
	boolean hasChild(T child);
	
	/**
	 * Returns true when this object is a parent
	 * 
	 * @return True when this is a parent
	 */
	boolean isParent();
	
	/**
	 * Returns true when this object has a parent
	 * 
	 * @return True when parent exists
	 */
	boolean hasParent();
	
	/**
	 * Returns the current parent
	 * 
	 * @return the current parent
	 */
	T getParent();
	
	/**
	 * Returns the current children
	 * 
	 * @return current children
	 */
	Collection<T> getChildren();
	
	/**
	 * Returns the number of children
	 *
	 * @return Number of children
	 */
	int getNumberOfChildren();

}
