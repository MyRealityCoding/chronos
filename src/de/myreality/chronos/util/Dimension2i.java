package de.myreality.chronos.util;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Basic 2D dimension
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class Dimension2i {

	// Width of the dimension
	public int w;
	
	// Height of the dimension
	public int h;
	
	public Dimension2i(int width, int height) {
		this.w = width;
		this.h = height;
	}
	
	public Dimension2i(int size) {
		this(size, size);
	}
}
