package de.myreality.chronos.models;

import de.myreality.chronos.util.Point2f;

public interface Bounding {


	/**
	 * Returns the current bounds of the lighting target
	 */
	Point2f[] getBounds();
	
	
	float getGlobalTop();
	float getGlobalBottom();
	float getGlobalLeft();
	float getGlobalRight();
	float getLocalTop();
	float getLocalBottom();
	float getLocalLeft();
	float getLocalRight();
	
	int getWidth();
	int getHeight();
	
	boolean collidateWith(Bounding other);
}
