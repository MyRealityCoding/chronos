package de.myreality.chronos.logging;

import java.util.Date;
import java.util.logging.Level;


/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Log system in order to make chronos logs
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.7
 * @version 0.7
 */
public class ChronosLogSystem implements LogSystem {
	
	// Target logging level
	private Level level;
	
	/**
	 * Basic constructor
	 */
	public ChronosLogSystem() {
		this.level = Level.ALL;
	}
	
	
	/**
	 * Sets a new logging level
	 */
	@Override
	public void setLevel(Level level) {
		this.level = level;
	}

	
	/**
	 * Shows an error message
	 */
	@Override
	public void error(String message, Throwable e) {
		if (level.intValue() <= Level.FINE.intValue()) {
			System.out.println(getMessageHead("ERROR") + message + e);
		}

	}

	
	/**
	 * Shows an error message
	 */
	@Override
	public void error(Throwable e) {
		if (level.intValue() <= Level.FINE.intValue()) {
			System.out.println(getMessageHead("ERROR") + e);
		}
	}

	
	/**
	 * Shows an error message
	 */
	@Override
	public void error(String message) {
		if (level.intValue() <= Level.FINE.intValue()) {
			System.out.println(getMessageHead("ERROR") + message);
		}
	}

	
	/**
	 * Shows a warning message
	 */
	@Override
	public void warn(String message) {
		if (level.intValue() <= Level.WARNING.intValue()) {
			System.out.println(getMessageHead("WARN") + message);
		}
	}

	
	/**
	 * Shows an information message
	 */
	@Override
	public void info(String message) {
		if (level.intValue() <= Level.INFO.intValue()) {
			System.out.println(getMessageHead("INFO") + message);
		}
	}

	
	/**
	 * Shows a debugging message message
	 */
	@Override
	public void debug(String message) {
		if (level.intValue() <= Level.ALL.intValue()) {
			System.out.println(getMessageHead("DEBUG") + message);
		}

	}

	
	/**
	 * Shows a warning message
	 */
	@Override
	public void warn(String message, Throwable e) {
		if (level.intValue() <= Level.WARNING.intValue()) {
			System.out.println(getMessageHead("WARN") + message + e);
		}
	}
	
	
	/**
	 * Helper function to generate the current message head
	 */
	private String getMessageHead(String phase) {
		Date date = new Date();
		return date.toString() + " Chronos " + phase + ":";
	}

}
