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
import java.util.HashMap;
import java.util.Map;

/**
 * Basic implementation of a manager
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicManager<T extends IDProvider> implements Manager<T> {

	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final long serialVersionUID = 1L;

	// ===========================================================
	// Fields
	// ===========================================================
	
	private Map<String, T> elements;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public BasicManager() {
		elements = new HashMap<String, T>();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	/* (non-Javadoc)
	 * @see de.myreality.chronos.util.Manager#addElement(de.myreality.chronos.util.IDProvider)
	 */
	@Override
	public void addElement(T element) {
		if (element != null) {
			elements.put(element.getId(), element);
		}
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.util.Manager#getAllElements()
	 */
	@Override
	public Collection<T> getAllElements() {
		return elements.values();
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.util.Manager#getElement(java.lang.String)
	 */
	@Override
	public T getElement(String id) {
		return elements.get(id);
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.util.Manager#hasElement(java.lang.String)
	 */
	@Override
	public boolean hasElement(String id) {
		return elements.containsKey(id);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
