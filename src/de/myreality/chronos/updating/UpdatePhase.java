package de.myreality.chronos.updating;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Holds information about a update phase
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public enum UpdatePhase {
	NONE(""),
	PREPARING("Preparing"),
	LOADING("Downloading"),
	ABORTING("Aborting"),
	ABORTED("Aborted"),
	DONE("Done");
	
	private String name;
	
	private String reason;
	
	UpdatePhase(String name) {
		this.name = name;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
