package de.myreality.chronos.util;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Basic 3D dimension
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class Dimension3i extends Dimension2i {
	
	// Depth of the dimension
	public int d;

	public Dimension3i(int width, int height, int depth) {
		super(width, height);
		this.d = depth;
	}
	
	public Dimension3i(int size) {
		this(size, size, size);
	}
}
