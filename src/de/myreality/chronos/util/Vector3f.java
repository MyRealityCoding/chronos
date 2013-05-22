package de.myreality.chronos.util;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Basic vector for 2 or 3 dimensions.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class Vector3f extends Vector2f {

	private static final long serialVersionUID = 1L;
	
	public float z;
	
	public Vector3f() {
		
	}
	
	public Vector3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}
	
	public Vector3f(Vector3f other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}
	
	public Vector3f(Point3f begin, Point3f end) {
		super(begin, end);
		z = end.z - begin.z;
	}
	
	@Override
	public float getLength() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	

	@Override
	public Vector2f normalize() {
		Vector3f normalizedVector = new Vector3f(this);
		normalizedVector.x /= getLength();
		normalizedVector.y /= getLength();
		normalizedVector.z /= getLength();
		return normalizedVector;
	}	

	@Override
	public float getScalar(Vector2f other) {
		if (other instanceof Vector3f) {
			return super.getScalar(other) + z * ((Vector3f)other).z;
		} else {
			return super.getScalar(other);
		}
	}

	@Override
	public String toString() {
		return super.toString() + " | " + z;
	}
	
	
}
