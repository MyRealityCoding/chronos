package de.myreality.chronos.util;

import java.io.Serializable;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Basic vector for 2 dimensions.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class Vector2f implements Serializable {

	// Version id for serialization
	private static final long serialVersionUID = 1L;
	
	/** x position */
	public float x;
	
	/** y position */
	public float y;
	
	
	/**
	 * Default constructor
	 */
	public Vector2f() {
		x = 0;
		y = 0;
	}
	
	
	/**
	 * Creates a new vector from origin to the given position
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Creates a new vector between the two points
	 * 
	 * @param begin start point
	 * @param end end point
	 */
	public Vector2f(Point2f begin, Point2f end) {
		this.x = end.x - begin.x;
		this.y = end.y - begin.y;
	}
	
	
	/**
	 * Creates a new vector from origin to the given position
	 * 
	 * @param target position
	 */
	public Vector2f(Point2f target) {
		this(target.x, target.y);
	}
	
	/**
	 * Creates a new vector of the coordinates of the target
	 * 
	 * @param target vector
	 */
	public Vector2f(Vector2f target) {
		this(target.x, target.y);
	}
	
	
	/**
	 * Calculates the length of this vector
	 * 
	 * @return vector length
	 */
	public float getLength() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	
	/**
	 * Creates a new vector with length=1 (normalized)
	 * 
	 * @return normalized vector
	 */
	public Vector2f normalize() {
		Vector2f normalizedVector = new Vector2f(this);
		normalizedVector.x /= getLength();
		normalizedVector.y /= getLength();
		return normalizedVector;
	}
	
	
	/**
	 * Calculates the angle to another vector
	 * 
	 * @param other other vector
	 * @return angle
	 */
	public float getAngleTo(Vector2f other) {
		double angle = Math.acos(getScalar(other) / getLength() * other.getLength());		
		return (float) Math.toDegrees(angle);
	}
	
	/**
	 * Calculates the angle in space
	 * 
	 * @return angle
	 */
	public float getAngle() {
		Vector2f base = new Vector2f(1, 0);
		return getAngleTo(base);
	}
	
	
	/**
	 * Calculates the scalar product to another vector
	 * 
	 * @param other other vector
	 * @return scalar product
	 */
	public float getScalar(Vector2f other) {
		return x * other.x + y * other.y;
	}
	
	@Override
	public String toString() {
		return "" + x + " | " + y;
	}
	
	
}
