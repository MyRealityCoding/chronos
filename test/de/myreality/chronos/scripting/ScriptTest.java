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
package de.myreality.chronos.scripting;

import static org.junit.Assert.*;

import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

import de.myreality.chronos.models.BasicEntity;
import de.myreality.chronos.models.Entity;
import de.myreality.chronos.resources.ResourceException;
import de.myreality.chronos.resources.ResourceManager;
import de.myreality.chronos.resources.data.XMLSource;

/**
 * Test case for basic script
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class ScriptTest {
	
	Entity entityOne, entityTwo;
	
	Script script;
	
	@Before
	public void beforeTest() throws ScriptException, ResourceException {
		entityOne = new BasicEntity();
		entityTwo = new BasicEntity();
		ResourceManager manager = ResourceManager.getInstance();
		manager.load(new XMLSource("xml/example.xml"));
		script = manager.getResource("test", Script.class);
	}
	
	
	
	@Test
	public void testAddListener() {
		
		entityOne.addListener(script);
		
		assertTrue("X should be 100", entityOne.getX() == 100f);
		assertTrue("Y should be 100", entityOne.getY() == 100f);
		assertTrue("X should be 0", entityTwo.getX() == 0f);
		assertTrue("Y should be 0", entityTwo.getY() == 0f);
		entityTwo.addListener(script);
		assertTrue("X should be 100", entityTwo.getX() == 100f);
		assertTrue("Y should be 100", entityTwo.getY() == 100f);
	}
	
	@Test
	public void testUpdateEntity() {
		entityOne.addListener(script);
		entityTwo.addListener(script);
		
		entityOne.update(15);
		
		assertTrue("EntityOne should be at X: 115", entityOne.getX() == 115f);
		assertTrue("EntityOne should be at Y: 115", entityOne.getY() == 115f);
		assertTrue("EntityTwo should be at X: 100", entityTwo.getX() == 100f);
		assertTrue("EntityTwo should be at Y: 100", entityTwo.getY() == 100f);
		
		entityTwo.update(30);
		
		assertTrue("EntityTwo should be at X: 130", entityTwo.getX() == 130f);
		assertTrue("EntityTwo should be at Y: 130", entityTwo.getY() == 130f);
		
		entityOne.update(20);
	
		assertTrue("EntityOne should be at X: 135", entityOne.getX() == 135f);
		assertTrue("EntityOne should be at Y: 135", entityOne.getY() == 135f);
	}
}
