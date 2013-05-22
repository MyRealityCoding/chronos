package de.myreality.chronos.logging;

import java.util.logging.Level;


/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Log system in order to make logging outputs
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.7
 * @version 0.7
 */
public interface LogSystem {

	/**
	 * Shows an error message and throws an error
	 */
	void error(String message, Throwable e);
	
	/**
	 * Shows an error
	 */
	void error(Throwable e);
	
	/**
	 * Shows an error message
	 */
	void error(String message);
	
	/**
	 * Shows a warn message
	 */
	void warn(String message);
	
	/**
	 * Shows an info message
	 */
	void info(String message);
	
	/**
	 * Shows a debug message
	 */
	void debug(String message);
	
	/**
	 * Shows a warning with throwable message
	 */
	void warn(String message, Throwable e);
	
	
	/**
	 * Sets a new logging level
	 * 
	 * @param level new logging level
	 */
	void setLevel(Level level);
}
