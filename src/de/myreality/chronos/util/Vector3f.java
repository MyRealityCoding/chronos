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

/**
 * Basic implementation of a vector
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class Vector3f implements ROVector3f {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = -8640895131191880904L;

	// ===========================================================
	// Fields
	// ===========================================================

	// Using double to increase precision during calculations
	private double x, y, z;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Default constructor
	 */
	public Vector3f() {
		this(0f, 0f, 0f);
	}

	/**
	 * Creates a new vector object
	 * 
	 * @param x x-position of the vector
	 * @param y y-position of the vector
	 */
	public Vector3f(float x, float y) {
		this(x, y, 0f);
	}

	/**
	 * Creates a new vector object
	 * 
	 * @param x x-position of the vector
	 * @param y y-position of the vector
	 * @param z z-position of the vector
	 */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a new vector object
	 * 
	 * @param original original vector to copy
	 */
	public Vector3f(ROVector3f original) {
		this(original.getX(), original.getY(), original.getZ());
	}

	/**
	 * Creates a new vector object
	 * 
	 * @param start starting point
	 * @param end ending point
	 */
	public Vector3f(ROVector3f start, ROVector3f end) {
		this(end.getX() - start.getX(), 
			 end.getY() - start.getY(), 
			 end.getZ() - start.getZ());
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	@Override
	public double getLength() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	@Override
	public float getScalar(ROVector3f other) {
		return (float) (x * other.getX() + y * other.getY() + z * other.getZ());
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public ROVector3f normalize() {
		Vector3f normalized = new Vector3f(this);
		normalized.x /= getLength();
		normalized.y /= getLength();
		normalized.z /= getLength();
		return normalized;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.myreality.chronos.util.ROVector3f#getX()
	 */
	@Override
	public float getX() {
		return (float) x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.myreality.chronos.util.ROVector3f#getY()
	 */
	@Override
	public float getY() {
		return (float) y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.myreality.chronos.util.ROVector3f#getZ()
	 */
	@Override
	public float getZ() {
		return (float) z;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.myreality.chronos.util.ROVector3f#setX(float)
	 */
	@Override
	public void setX(float x) {
		this.x = x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.myreality.chronos.util.ROVector3f#setY(float)
	 */
	@Override
	public void setY(float y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.myreality.chronos.util.ROVector3f#setZ(float)
	 */
	@Override
	public void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ROVector3f) {
			ROVector3f other = (ROVector3f)o;
			return getX() == other.getX() &&
				   getY() == other.getY() &&
				   getZ() == other.getZ();
		} else {
			return super.equals(o);
		}
	}
	
	@Override
	public String toString() {
		return "vec[" + x + "|" + y + "|" + z + "]";
	}

	@Override
	public ROVector3f copy() {
		return new Vector3f(this);
	}

	@Override
	public void scale(float factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
