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

import java.awt.geom.AffineTransform;

import de.myreality.chronos.models.BasicBounds;
import de.myreality.chronos.models.Bounds;

/**
 * Utility class which provides functionality to handle vectors
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class VectorUtils {

	// ===========================================================
	// Fields
	// ===========================================================

	private static final ROVector3f utilVector = new Vector3f();
	
	private static final Bounds bounds = new BasicBounds();
	
	static {
		
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Calculates the distance between two positions
	 * 
	 * @param vectorA first position vector
	 * @param vectorB second position vector
	 * @return distance between the two vectors
	 */
	public static double getDistanceBetween(ROVector3f vectorA,
			ROVector3f vectorB) {

		utilVector.setX(vectorB.getX() - vectorA.getX());
		utilVector.setY(vectorB.getY() - vectorA.getY());
		utilVector.setZ(vectorB.getZ() - vectorA.getZ());

		return utilVector.getLength();
	}

	/**
	 * Calculates the bounds of a given point array by considering the rotation.
	 * 
	 * @param points the points to calculate
	 */
	public static void calculateBounds(ROVector3f topLeft, ROVector3f bottomRight, ROVector3f[] points) {
		
		bounds.clear();

		if (points != null && points.length > 0) {
			float minX = points[0].getX(), maxX = minX;
			float minY = points[0].getY(), maxY = minY;

			for (ROVector3f point : points) {
				minX = Math.min(minX, point.getX());
				minY = Math.min(minY, point.getY());
				maxX = Math.max(maxX, point.getX());
				maxY = Math.max(maxY, point.getY());
			}
			
			topLeft.setX(minX);
			topLeft.setY(minY);
			bottomRight.setX(maxX);
			bottomRight.setY(maxY);
		}
	}
	
	/**
	 * Rotates one position around another by the given angle
	 * 
	 * @param centerX center x to rotate
	 * @param centerY center y to rotate
	 * @param target target which will be rotated
	 * @param angle angle of rotation
	 */
	public static void rotate(float centerX, float centerY, ROVector3f target, float angle) {
		// TODO: Fix precision bug with native code
        /*double radian = Math.toRadians(angle);
        double cosinus = Math.cos(radian);
        double sinus = Math.sin(radian);
        double xVector = target.getX() - centerX;
        double yVector = target.getY() - centerY;
        target.setX((float) (centerX + cosinus * xVector - sinus * yVector));
        target.setY((float) (centerY + sinus * xVector + cosinus * yVector));*/
		AffineTransform trans = AffineTransform.getRotateInstance(Math.toRadians(angle), centerX, centerY);
		double[] pt = {target.getX(), target.getY()};
		trans.transform(pt, 0, pt, 0, 1);
		target.setX((float) pt[0]);
		target.setY((float) pt[1]);
	}
}
