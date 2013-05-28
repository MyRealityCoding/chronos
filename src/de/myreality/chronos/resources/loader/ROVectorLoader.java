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
package de.myreality.chronos.resources.loader;

import de.myreality.chronos.resources.ResourceDefinition;
import de.myreality.chronos.resources.ResourceException;
import de.myreality.chronos.resources.ResourceType;
import de.myreality.chronos.util.ROVector3f;
import de.myreality.chronos.util.Vector3f;

/**
 * Creates string resources from a resource definition
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
@ResourceType("vector")
public class ROVectorLoader extends AbstractResourceLoader<ROVector3f> {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================
	
	@Override
	public ROVector3f create(ResourceDefinition definition)
			throws ResourceException {
		String xValue = definition.getAttribute("x");
		String yValue = definition.getAttribute("y");
		String zValue = definition.getAttribute("z");
		
		float x = convertFromValue(xValue, definition);
		float y = convertFromValue(yValue, definition);
		
		ROVector3f vector = new Vector3f(x, y);		
		
		// z value is not necessary!
		if (zValue != null) {			
			float z = convertFromValue(zValue, definition);
			vector.setZ(z);		
		}
		
		return vector;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	private float convertFromValue(String value, ResourceDefinition definition) throws ResourceException {
		if (value != null) {
			try {
				return Float.valueOf(value);
			} catch (NumberFormatException e) {
				throw new ResourceException("Value in " + definition.getType() + " resource with id '" + definition.getId() + "' is not valid: ");
			}
		} else {
			throw new ResourceException("Missing value in " + definition.getType() + " resource with id '" + definition.getId() + "'");
		}
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
