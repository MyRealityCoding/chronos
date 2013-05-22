package de.myreality.chronos.logging;

import java.util.logging.Level;


/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Chronos logger compatible to other log systems
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.7
 * @version 0.7
 */
public class CLog {

	private static LogSystem system;
	
	static {
		system = new ChronosLogSystem();
	}
	
	/**
	 * Logging an error
	 * 
	 * @param message Message to show
	 */
	public static void error(String message) {
		system.error(message);
	}
	
	/**
	 * Logging an error
	 * 
	 * @param message Message to show
	 * @param e Throwable error
	 */
	public static void error(String message, Throwable e) {
		system.error(message, e);
	}
	
	/**
	 * Logging an error
	 * 
	 * @param e Throwable error
	 */
	public static void error(Throwable e) {
		system.error(e);
	}
	
	/**
	 * Logging a warning
	 * 
	 * @param message Message to show
	 */
	public static void warn(String message) {
		system.warn(message);
	}
	
	/**
	 * Logging a warning
	 * 
	 * @param message Message to show
	 * @param e Throwable error
	 */
	public static void warn(String message, Throwable e) {
		system.warn(message, e);
	}
	
	/**
	 * Logging an information
	 * 
	 * @param message Message to show
	 */
	public static void info(String message) {
		system.info(message);
	}
	
	/**
	 * Logging a debug test
	 * 
	 * @param message Message to show
	 */
	public static void debug(String message) {
		system.debug(message);
	}
	
	
	/**
	 * Sets a new log system
	 * 
	 * @param logSystem new log system
	 */
	public static void setLogSystem(LogSystem logSystem) {
		system = logSystem;
	}
	
	
	/**
	 * Change the level of the logging
	 * 
	 * @param level logging level
	 */
	public static void setLevel(Level level) {
		system.setLevel(level);
	}
}
