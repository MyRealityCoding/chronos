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

import de.myreality.chronos.util.BasicFamilyObject;
import de.myreality.chronos.util.ROVector3f;

/**
 * Basic implementation of a boundable
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicBoundable<T extends Positionable<T> & Boundable<T> > extends BasicFamilyObject<T> implements Boundable<T> {

	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final long serialVersionUID = 1L;
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private Bounds bounds;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public BasicBoundable() {
		this(0f, 0f);
	}
	
	public BasicBoundable(float x, float y) {
		this(x, y, 0, 0);
	}
	
	public BasicBoundable(float x, float y, float width, float height) {
		bounds = new BasicBounds(x, y, x + width, y + height);
	}
	
	public BasicBoundable(ROVector3f ... vertices) {
		bounds = new BasicBounds(vertices);
	}
	
	public BasicBoundable(ROVector3f topLeft, ROVector3f bottomRight) {
		bounds = new BasicBounds(topLeft, bottomRight);
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	

	@Override
	public Bounds getBounds() {
		return bounds;
	}

	@Override
	public void setBounds(ROVector3f topLeft, ROVector3f bottomRight,
			float rotation, float scale) {
		bounds.set(topLeft, bottomRight, rotation, scale);
	}

	@Override
	public void setBounds(ROVector3f topLeft, ROVector3f bottomRight,
			float rotation) {
		bounds.set(topLeft, bottomRight, rotation);
	}

	@Override
	public void setBounds(ROVector3f topLeft, ROVector3f bottomRight) {
		bounds.set(topLeft, bottomRight);
	}

	@Override
	public void setBounds(Boundable<T> boundable) {
		Bounds otherBounds = boundable.getBounds();
		bounds.set(otherBounds.get(Edge.TOP_LEFT), otherBounds.get(Edge.BOTTOM_RIGHT), otherBounds.getRotation(), otherBounds.getScale());
	}

	@Override
	public void setBounds(ROVector3f[] vertices, float rotation, float scale) {
		bounds.set(vertices, rotation, scale);
	}

	@Override
	public void setBounds(ROVector3f[] vertices, float rotation) {
		bounds.set(vertices, rotation);		
	}

	@Override
	public void setBounds(ROVector3f[] vertices) {
		bounds.set(vertices);
	}

	@Override
	public void setPosition(float x, float y) {
		bounds.setPosition(x, y);
	}

	@Override
	public void setPosition(float x, float y, float z) {
		bounds.setPosition(x, y, z);
	}

	@Override
	public ROVector3f getPosition(Coordinate coord) {
		return bounds.getPosition(coord);
	}

	@Override
	public ROVector3f getPosition() {
		return bounds.getPosition();
	}

	@Override
	public float getX(Coordinate coord) {
		return bounds.getY(coord);
	}

	@Override
	public float getX() {
		return bounds.getX();
	}

	@Override
	public float getY(Coordinate coord) {
		return bounds.getY(coord);
	}

	@Override
	public float getY() {
		return bounds.getY();
	}

	@Override
	public float getZ(Coordinate coord) {
		return bounds.getZ(coord);
	}

	@Override
	public float getZ() {
		return bounds.getZ();
	}

	@Override
	public void setX(float x) {
		bounds.setX(x);
	}

	@Override
	public void setY(float y) {
		bounds.setY(y);
	}

	@Override
	public void setZ(float z) {
		bounds.setZ(z);
	}

	@Override
	public void attachTo(T parent) {		
		super.attachTo(parent);
		bounds.attachTo(parent.getBounds());
	}

	@Override
	public boolean detach() {		
		return super.detach() && bounds.detach();
	}

	@Override
	public void addChild(T child) {
		super.addChild(child);
		bounds.addChild(child.getBounds());
	}

	@Override
	public void removeChild(T child) {
		super.removeChild(child);
		bounds.removeChild(child.getBounds());
	}

	@Override
	public ROVector3f getLastPosition(Coordinate coord) {
		return bounds.getLastPosition(coord);
	}

	@Override
	public ROVector3f getLastPosition() {
		return bounds.getLastPosition();
	}

	@Override
	public float getLastX(Coordinate coord) {
		return bounds.getLastX(coord);
	}

	@Override
	public float getLastX() {
		return bounds.getLastX();
	}

	@Override
	public float getLastY(Coordinate coord) {
		return bounds.getLastY(coord);
	}

	@Override
	public float getLastY() {
		return bounds.getLastY();
	}

	@Override
	public float getLastZ(Coordinate coord) {
		return bounds.getLastZ(coord);
	}

	@Override
	public float getLastZ() {
		return bounds.getLastZ();
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setPosition(float, float, float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setPosition(float x, float y, float z, Coordinate coord) {
		bounds.setPosition(x, y, z, coord);
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setPosition(float, float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setPosition(float x, float y, Coordinate coord) {
		bounds.setPosition(x, y, coord);
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setX(float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setX(float x, Coordinate coord) {
		bounds.setX(x, coord);
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setY(float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setY(float y, Coordinate coord) {
		bounds.setY(y, coord);
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setZ(float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setZ(float z, Coordinate coord) {
		bounds.setZ(z, coord);
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner classes
	// ===========================================================
}
