package de.myreality.chronos.models;

import java.io.Serializable;

import de.myreality.chronos.util.FamilyObject;
import de.myreality.chronos.util.ROVector3f;

/**
 * Provides position methods to move the object
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public interface Positionable<T extends Positionable<T>> extends
		FamilyObject<T>, Serializable {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * Sets a new position. The position will be the same as the global position
	 * as long as this entity is not attached to another one.
	 * 
	 * @param x
	 *            new x position
	 * @param y
	 *            new y position
	 * @param z
	 *            new z position
	 */
	void setPosition(float x, float y, float z);
	void setPosition(float x, float y);
	
	/**
	 * Sets a new position. The position will be the same as the global position
	 * as long as this entity is not attached to another one.
	 * 
	 * @param x
	 *            new x position
	 * @param y
	 *            new y position
	 * @param z
	 *            new z position
	 */
	void setPosition(float x, float y, float z, Coordinate coord);
	void setPosition(float x, float y, Coordinate coord);
	
	/**
	 * Returns the current position
	 * 
	 * @return current position
	 */
	ROVector3f getPosition(Coordinate coord);
	ROVector3f getPosition();

	/**
	 * Returns the x position
	 * 
	 * @return x position
	 */
	float getX(Coordinate coord);
	float getX();

	/**
	 * Returns the y position
	 * 
	 * @return y position
	 */
	float getY(Coordinate coord);
	float getY();

	/**
	 * Returns the z position
	 * 
	 * @return z position
	 */
	float getZ(Coordinate coord);
	float getZ();

	/**
	 * Sets a new x position
	 * 
	 * @param x
	 *            new x position
	 */
	void setX(float x);

	/**
	 * Sets a new y position
	 * 
	 * @param y
	 *            new y position
	 */
	void setY(float y);

	/**
	 * Sets a new z position
	 * 
	 * @param z
	 *            new z position
	 */
	void setZ(float z);
	
	/**
	 * Sets a new x position
	 * 
	 * @param x
	 *            new x position
	 */
	void setX(float x, Coordinate coord);

	/**
	 * Sets a new y position
	 * 
	 * @param y
	 *            new y position
	 */
	void setY(float y, Coordinate coord);

	/**
	 * Sets a new z position
	 * 
	 * @param z
	 *            new z position
	 */
	void setZ(float z, Coordinate coord);
	
	
	/**
	 * Returns the last position of this entity
	 * 
	 * @param coord coordinate system
	 * 
	 * @return last position vector
	 */
	ROVector3f getLastPosition(Coordinate coord);
	ROVector3f getLastPosition();
	
	/**
	 * Returns the last x value
	 * 
	 * @param coord coordinate system
	 * @return last x value
	 */
	float getLastX(Coordinate coord);
	float getLastX();

	/**
	 * Returns the last y value
	 * 
	 * @param coord coordinate system
	 * @return last y value
	 */
	float getLastY(Coordinate coord);
	float getLastY();
	
	/**
	 * Returns the last z value
	 * 
	 * @param coord coordinate system
	 * @return last z value
	 */
	float getLastZ(Coordinate coord);
	float getLastZ();

	
}