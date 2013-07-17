/* Chronos - Game Development Toolkit for Java game developers. The
 * original source remains:
 * 
 * Copyright (c) 2013 Miguel Gonzalez http://my-reality.de
 * 
 * This source is provided under the terms of the BSD License.
 * 
 * Copyright (c) 2013, Chronos
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 *  * Redistributions of source code must retain the above 
 *    copyright notice, this list of conditions and the 
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above 
 *    copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided 
 *    with the distribution.
 *  * Neither the name of the Chronos/my Reality Development nor the names of 
 *    its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 */
package de.myreality.chronos.models;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.myreality.chronos.util.Vector3f;

/**
 * Test case for a positionable
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class PositionableTest {
	
	PositionableImpl obj;
	PositionableImpl parent;

	@Before
	public void setUp() throws Exception {
		obj = new PositionableImpl();
		parent = new PositionableImpl();
		parent.setPosition(100f, 100f, 100f);
		obj.setPosition(20f, 20f, 20f);
		obj.attachTo(parent);
	}

	@Test
	public void testPositionable() {		
		
		// Check: Local and global position
		assertTrue("The global position should not have changed.", obj.getPosition().equals(new Vector3f(20f, 20f, 20f)));
		assertTrue("The local position is wrong.", obj.getPosition(Coordinate.LOCAL).equals(new Vector3f(-80f, -80f, -80f)));
	
		obj.setPosition(10f, 10f, 10f, Coordinate.LOCAL);
		
		// Check: Last positions
		assertTrue("The local last position is wrong.", obj.getLastPosition(Coordinate.LOCAL).equals(new Vector3f(-80f, -80f, -80f)));
		assertTrue("The global last position is wrong.", obj.getLastPosition(Coordinate.GLOBAL).equals(new Vector3f(20f, 20f, 20f)));
		
		assertTrue("The global position should not have changed.", obj.getPosition().equals(new Vector3f(110f, 110f, 110f)));
		assertTrue("The local position is wrong.", obj.getPosition(Coordinate.LOCAL).equals(new Vector3f(10f, 10f, 10f)));
		
		parent.setPosition(200f, 200f, 200f);
		parent.removeChild(obj);
		
		assertTrue("The global position should not have changed.", obj.getPosition().equals(new Vector3f(210f, 210f, 210f)));
		assertTrue("The local position is wrong.", obj.getPosition(Coordinate.LOCAL).equals(new Vector3f(210f, 210f, 210f)));
		
		obj.setPosition(20f, 20f, 20f, Coordinate.LOCAL);
		obj.attachTo(parent);
		
		assertTrue("The global position should not have changed.", obj.getPosition().equals(new Vector3f(220f, 220f, 220f)));
		assertTrue("The local position is wrong.", obj.getPosition(Coordinate.LOCAL).equals(new Vector3f(20f, 20f, 20f)));
		
		parent.setX(-100f, Coordinate.LOCAL);		
		assertTrue("Last local X is wrong. Should be 210f instead of" + obj.getLastX(Coordinate.LOCAL), obj.getLastX(Coordinate.LOCAL) == 210f);
		assertTrue("Last global X is wrong. Should be 110f instead of" + obj.getLastX(Coordinate.GLOBAL), obj.getLastX(Coordinate.GLOBAL) == 110f);
		
		parent.setY(-50f, Coordinate.LOCAL);
		assertTrue("Last local Y is wrong. Should be 210f instead of" + obj.getLastY(Coordinate.LOCAL), obj.getLastY(Coordinate.LOCAL) == 210f);
		assertTrue("Last global Y is wrong. Should be 160f instead of" + obj.getLastY(Coordinate.GLOBAL), obj.getLastY(Coordinate.GLOBAL) == 160f);
		
		parent.setZ(-10f, Coordinate.LOCAL);
		assertTrue("Last local Z is wrong. Should be 210f instead of" + obj.getLastZ(Coordinate.LOCAL), obj.getLastZ(Coordinate.LOCAL) == 210f);
		assertTrue("Last global Z is wrong. Should be 200f instead of" + obj.getLastZ(Coordinate.GLOBAL), obj.getLastZ(Coordinate.GLOBAL) == 200);
		
		assertTrue("The local X is wrong.", obj.getX(Coordinate.LOCAL) == 20f);
		assertTrue("The local Y is wrong.", obj.getY(Coordinate.LOCAL) == 20f);
		assertTrue("The local Z is wrong.", obj.getZ(Coordinate.LOCAL) == 20f);
		assertTrue("The global X is wrong.", obj.getX(Coordinate.GLOBAL) == -80f);
		assertTrue("The global Y is wrong.", obj.getY(Coordinate.GLOBAL) == -30f);
		assertTrue("The global Z is wrong.", obj.getZ(Coordinate.GLOBAL) == 10f);
	}
	
	class PositionableImpl extends BasicPositionable<PositionableImpl> {

		private static final long serialVersionUID = 1L;
		
	}
}