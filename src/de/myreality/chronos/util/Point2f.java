package de.myreality.chronos.util;

import java.io.Serializable;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Basic point for 2 dimensions.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class Point2f implements Serializable {

	/** x position */
	public float x;
	
	/** y position */
	public float y;
	
	// Version id for serialization
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Maps the coordinates as point
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public Point2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Default constructor that initiates the coordinates
	 * with 0
	 */
	public Point2f() {
		x = 0;
		y = 0;
	}

	
	
	/**
	 * Calculates the distance to another point
	 * 
	 * @param other another point
	 * @return float distance
	 */
	public float distanceTo(Point2f other) {
		Vector2f distanceVector = new Vector2f(this, other);
		return distanceVector.getLength();
	}
	
	
	/**
	 * Calculates the angle to another point
	 * 
	 * @param other another point
	 * @return float angle
	 */
	public float getAngleTo(Point2f other) {
		Vector2f base = new Vector2f(this, other);
		return base.getAngle();
	}
	
	
	// xRot = xCenter + cos(Angle) * (x - xCenter) - sin(Angle) * (y - yCenter)
	// yRot = yCenter + sin(Angle) * (x - xCenter) + cos(Angle) * (y - yCenter)
	public void rotate(Point2f center, float angle) {
		// Initialisation
		double radian = Math.toRadians(angle);
		double cosinus = Math.cos(radian);
		double sinus = Math.sin(radian);
		float xVector = x - center.x;
		float yVector = y - center.y;
		
		// Calculation
		x = (float) (center.x + cosinus * xVector - sinus * yVector);
		y = (float) (center.y + sinus * xVector + cosinus * yVector);
	}
	
	
	/**
	 * Calculates the distance to another point
	 * 
	 * @param x x position
	 * @param y y position
	 * @return float distance
	 */
	public float distanceTo(float x, float y) {
		return distanceTo(new Point2f(x, y));
	}
}
