package de.myreality.chronos.toolkit.updating;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Holds information about a file to upload
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class UpdateFile {

	// File size
	private long size;
	
	// File checksum
	private long checksum;
	
	// File path
	private String path;
	
	
	/**
	 * Constructor to initialize the file
	 * 
	 * @param path path to the file
	 * @param size size of the file
	 * @param sum checksum of the file
	 */
	public UpdateFile(String path, long size, long sum) {
		this.path = path;
		this.size = size;
		this.checksum = sum;
	}

	public Long getSize() {
		return size;
	}

	public Long getChecksum() {
		return checksum;
	}

	public String getPath() {
		return path;
	}
}
