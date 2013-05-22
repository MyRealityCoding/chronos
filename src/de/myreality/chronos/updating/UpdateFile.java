package de.myreality.chronos.updating;

import java.io.File;

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
public class UpdateFile extends File {

	private static final long serialVersionUID = 1L;

	// File size
	private long size;
	
	// File checksum
	private String checksum;
	
	/**
	 * Constructor to initialize the file
	 * 
	 * @param path path to the file
	 * @param size size of the file
	 * @param sum checksum of the file
	 */
	public UpdateFile(String path, long size, String sum) {
		super(path);
		this.size = size;
		this.checksum = sum;
	}

	public Long getSize() {
		return size;
	}

	public String getChecksum() {
		return checksum;
	}
}
