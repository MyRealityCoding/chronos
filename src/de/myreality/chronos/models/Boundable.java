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
package de.myreality.chronos.models;

import java.io.Serializable;

import de.myreality.chronos.util.ROVector3f;

/**
 * Represents a bounding box which can be scaled, resized or moved.
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public interface Boundable<T extends Positionable<T> > extends Serializable, Positionable<T> {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Returns the bounds of this object
	 * 
	 * @return current bounds
	 */
	Bounds getBounds();
	
	/**
	 * Sets new 2D bounds for this object
	 * 
	 * @param topLeft top left edge
	 * @param bottomRight bottom right edge
	 * @param rotation rotation factor
	 * @param scale scale factor
	 */
	void setBounds(ROVector3f topLeft, ROVector3f bottomRight, float rotation, float scale);
	void setBounds(ROVector3f topLeft, ROVector3f bottomRight, float rotation);
	void setBounds(ROVector3f topLeft, ROVector3f bottomRight);
	
	/**
	 * Apply the bounds of the other boundable
	 * 
	 * @param boundable other boundable
	 */
	void setBounds(Boundable<T> boundable);
	
	/**
	 * Set new bounds calculated from vertices
	 * 
	 * @param vertices array of vertices
	 * @param rotation rotation factor
	 * @param scale scale factor
	 */
	void setBounds(ROVector3f[] vertices, float rotation, float scale);
	void setBounds(ROVector3f[] vertices, float rotation);
	void setBounds(ROVector3f[] vertices);
}
