package de.myreality.chronos.updating;

/**
 * Listens to an updater
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @version 0.7.2alpha
 * @since 0.7.2alpha
 */
public interface UpdatingListener {

	/**
	 * Is called every updating moment
	 */
	void onUpdate(String file, long currentSize, long totalSize, float percentage, float bytesPerSecond, UpdatePhase phase);
	
	/**
	 * Is called when the download has been started
	 */
	void onStartPhase(UpdatePhase phase);
}
