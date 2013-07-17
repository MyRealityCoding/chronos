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
import de.myreality.chronos.util.Vector3f;

/**
 * Provides basic functionality for a positionable object
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicPositionable<T extends Positionable<T>> extends
		BasicFamilyObject<T> implements Positionable<T> {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = 1L;

	private static final Coordinate DEFAULT_COORD = Coordinate.GLOBAL;

	// ===========================================================
	// Fields
	// ===========================================================

	private ROVector3f position, lastPosition;
	
	private Coordinate coordX, coordY, coordZ, lastCoordX, lastCoordY, lastCoordZ;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BasicPositionable() {
		this(0f, 0f);		
	}

	public BasicPositionable(float x, float y, float z) {
		position = new Vector3f(x, y, z);
		lastPosition = new Vector3f(x, y, z);
		coordX = coordY = coordZ = DEFAULT_COORD;
		lastCoordX = lastCoordY = lastCoordZ = DEFAULT_COORD;
	}

	public BasicPositionable(float x, float y) {
		this(x, y, 0f);
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public ROVector3f getPosition(Coordinate coord) {
		
		ROVector3f result = new Vector3f();
		
		result.setX(getX(coord));
		result.setY(getY(coord));
		result.setZ(getZ(coord));
		
		return result;
	}	

	@Override
	public ROVector3f getLastPosition(Coordinate coord) {
		
		ROVector3f result = new Vector3f();
		
		result.setX(getLastX(coord));
		result.setY(getLastY(coord));
		result.setZ(getLastZ(coord));
		
		return result;
	}

	@Override
	public float getX(Coordinate coord) {		
		return calculatePositionValue(PositionType.X, position, coordX, coord);
	}

	@Override
	public float getY(Coordinate coord) {
		return calculatePositionValue(PositionType.Y, position, coordY, coord);
	}

	@Override
	public float getZ(Coordinate coord) {
		return calculatePositionValue(PositionType.Z, position, coordZ, coord);
	}
	
	@Override
	public float getLastX(Coordinate coord) {
		return calculatePositionValue(PositionType.X, lastPosition, lastCoordX, coord);
	}

	@Override
	public float getLastY(Coordinate coord) {
		return calculatePositionValue(PositionType.Y, lastPosition, lastCoordY, coord);
	}

	@Override
	public float getLastZ(Coordinate coord) {
		return calculatePositionValue(PositionType.Z, lastPosition, lastCoordZ, coord);
	}

	@Override
	public ROVector3f getPosition() {
		return getPosition(DEFAULT_COORD);
	}

	@Override
	public float getX() {
		return getX(DEFAULT_COORD);
	}

	@Override
	public float getY() {
		return getY(DEFAULT_COORD);
	}

	@Override
	public float getZ() {
		return getZ(DEFAULT_COORD);
	}

	@Override
	public void setX(float x) {
		setX(x, DEFAULT_COORD);
	}

	@Override
	public void setY(float y) {
		setY(y, DEFAULT_COORD);
	}

	@Override
	public void setZ(float z) {
		setZ(z, DEFAULT_COORD);
	}

	@Override
	public ROVector3f getLastPosition() {
		return getLastPosition(DEFAULT_COORD);
	}
	

	@Override
	public float getLastX() {
		return getLastX(DEFAULT_COORD);
	}

	@Override
	public float getLastY() {
		return getLastY(DEFAULT_COORD);
	}

	@Override
	public float getLastZ() {
		return getLastZ(DEFAULT_COORD);
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setPosition(float, float, float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setPosition(float x, float y, float z, Coordinate coord) {
		setX(x, coord);
		setY(y, coord);
		setZ(z, coord);
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setPosition(float, float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setPosition(float x, float y, Coordinate coord) {
		setPosition(x, y, 0f, coord);
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setX(float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setX(float x, Coordinate coord) {
		lastCoordX = coordX;
		lastPosition.setX(getX());
		position.setX(x);
		coordX = coord;
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setY(float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setY(float y, Coordinate coord) {
		lastCoordY = coordY;
		lastPosition.setY(getY());
		position.setY(y);
		coordY = coord;
	}

	/* (non-Javadoc)
	 * @see de.myreality.chronos.models.Positionable#setZ(float, de.myreality.chronos.models.Coordinate)
	 */
	@Override
	public void setZ(float z, Coordinate coord) {
		lastCoordZ = coordZ;
		lastPosition.setZ(getZ());
		position.setZ(z);
		coordZ = coord;
	}
	

	@Override
	public void setPosition(float x, float y) {
		setPosition(x, y, 0f);
	}

	@Override
	public void setPosition(float x, float y, float z) {
		setPosition(x, y, z, DEFAULT_COORD);
	}

	@Override
	public boolean detach() {
		if (hasParent()) {
			ROVector3f pos = getPosition();
			setPosition(pos.getX(), pos.getY(), pos.getZ(), Coordinate.LOCAL);
		}
		return super.detach();		
	}


	// ===========================================================
	// Methods
	// ===========================================================


	private float calculatePositionValue(PositionType type, ROVector3f target, Coordinate currentCoordinate, Coordinate coordinate) {
		
		float position = filterPosition(target, type);
		boolean isInSameSystem = currentCoordinate.equals(coordinate);
		
		if (hasParent() && !isInSameSystem) {
			
			float parentValue = filterPosition(getParent().getPosition(), type);
			
			switch (coordinate) {
				case GLOBAL:
					position += parentValue;
					break;
				case LOCAL:
					position -= parentValue;
					break;
			}
		}
		
		return position;
	}
	
	private float filterPosition(ROVector3f position, PositionType type) {
		switch (type) {
		case X:
			return position.getX();
		case Y:
			return position.getY();
		case Z:
			return position.getZ();
		}
		return 0f;
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
	
	enum PositionType {
		X,
		Y,
		Z
	}

}
