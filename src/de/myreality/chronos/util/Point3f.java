package de.myreality.chronos.util;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Basic point for 3 dimensions.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class Point3f extends Point2f {

	// Version id for serialization
	private static final long serialVersionUID = 553265284086101329L;
	
	/** z position */
	public float z;
	
	/**
	 * Default constructor
	 */
	public Point3f() {
		super();
		z = 0;
	}
	
	
	/**
	 * Maps the coordinates as point
	 * 
	 * @param x x position
	 * @param y y position
	 * @param z z position
	 */
	public Point3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}
	
	
	/**
	 * Maps the coordinates of the given point and the
	 * z coordinate as parameter
	 * 
	 * @param point target 2D position
	 * @param z z position
	 */
	public Point3f(Point2f point, float z) {
		this(point.x, point.y, z);
	}

	
	
	/**
	 * Calculates the distance to another point
	 * 
	 * @param other another point
	 * @return float distance
	 */
	@Override
	public float distanceTo(Point2f other) {
		return getVector(other).getLength();
	}
	
	
	
	public void rotate(Point2f center, float angleX, float angleY) {
		// TODO: Rotation stuff
	}


	/**
	 * Calculates the angle to another point
	 * 
	 * @param other another point
	 * @return float angle
	 */
	@Override
	public float getAngleTo(Point2f other) {
		return getVector(other).getAngle();
	}

	private Vector3f getVector(Point2f other) {

		if (other instanceof Point3f) {
			return new Vector3f(this, (Point3f)other);
		} else {
			return new Vector3f(this, new Point3f(other, 0));
		}
	}
}
