package de.myreality.chronos.models;

import java.io.Serializable;

import de.myreality.chronos.util.ROVector3f;

/**
 * Represents bounds of a specific boundable
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public interface Bounds extends Serializable, Iterable<ROVector3f>, Positionable<Bounds> {
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	// Number of total edges
	public static final int COUNT = 4;

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Returns the bound of the edge
	 * 
	 * @param edge edge of the bound
	 * @return target bound
	 */
	ROVector3f get(Edge edge);
	ROVector3f get(int index);
	
	/**
	 * Returns the bound of the edge
	 * 
	 * @param edge edge of the bound
	 * @param rotated take rotation into account
	 * @return target bound
	 */
	ROVector3f get(Edge edge, boolean rotated);	
	ROVector3f get(int index, boolean rotated);
	
	/**
	 * Clears the current bounds
	 */
	void clear();
	
	/**
	 * Aligns the new bounds from the target
	 */
	void copy(Bounds original);
	
	/**
	 * Sets new bounds
	 * 
	 * @param topLeftX top left X
	 * @param topLeftY top left y
	 * @param bottomRightX bottom right x
	 * @param bottomRightY bottom right y
	 */
	void set(float topLeftX, float topLeftY, float bottomRightX, float bottomRightY);
	void set(float topLeftX, float topLeftY, float bottomRightX, float bottomRightY, float rotation);
	void set(float topLeftX, float topLeftY, float bottomRightX, float bottomRightY, float rotation, float scale);
	
	/**
	 * Sets new bounds
	 * 
	 * @param topLeft top left position
	 * @param bottomRight bottom right position
	 */
	void set(ROVector3f topLeft, ROVector3f bottomRight);
	void set(ROVector3f topLeft, ROVector3f bottomRight, float rotation);
	void set(ROVector3f topLeft, ROVector3f bottomRight, float rotation, float scale);
	
	/**
	 * Sets new bounds.
	 * 
	 * @param vertices vertices which are inside of the bounds
	 * @param rotation initial rotation
	 * @param scale initial scale
	 */
	void set(ROVector3f[] vertices, float rotation, float scale);
	void set(ROVector3f[] vertices, float rotation);
	void set(ROVector3f[] vertices);
	
	/**
	 * Returns the width of this bounds
	 * 
	 * @return width of the bounds
	 */
	float getWidth();
	
	/**
	 * Returns the height of this bounds
	 * 
	 * @return height of the bounds
	 */
	float getHeight();
	
	/**
	 * Returns the rotation
	 * 
	 * @return current rotation
	 */
	float getRotation();
	
	/**
	 * Sets a new rotation
	 * 
	 * @param rotation new rotation
	 */
	void setRotation(float rotation);
	
	/**
	 * Rotates the bounds by a given amount
	 * 
	 * @param angle
	 */
	void rotate(float angle);
	
	/**
	 * Returns the current scale factor
	 * 
	 * @return current scale factor
	 */
	float getScale();
	
	/**
	 * Sets a new scale factor
	 * 
	 * @param scale new scale factor
	 */
	void setScale(float scale);
	
	/**
	 * Scales the bounds by the given amount
	 * 
	 * @param factor scale factor
	 */
	void scale(float factor);
	
	/**
	 * Returns the current center x position
	 * 
	 * @param coord coordinate system
	 * @return center position
	 */
	float getCenterX(Coordinate coord);
	
	
	/**
	 * Returns the current global center x position
	 * 
	 * @return global center position
	 */
	float getCenterX();
	
	/**
	 * Returns the current center y position
	 * 
	 * @param coord coordinate system
	 * @return center position
	 */
	float getCenterY(Coordinate coord);
	
	
	/**
	 * Returns the current global center y position
	 * 
	 * @return global center position
	 */
	float getCenterY();
}
