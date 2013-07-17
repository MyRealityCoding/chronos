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
package de.myreality.chronos.resources;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for resource definitions
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class ResourceDefinitionTest {
	
	ResourceDefinition resourceDefinition;
	
	ResourceGroup group;
	
	static final String VALUE = "Hello World!";
	static final String ID = "def1";
	static final String TYPE = "String";

	@Before
	public void setUp() throws Exception {
		resourceDefinition = new BasicResourceDefinition(ID, TYPE);
		resourceDefinition.setValue(VALUE);
		resourceDefinition.addAttribute("mama", "stella");
		group = new BasicResourceGroup("strings");
		resourceDefinition.setGroup(group);	
	}

	@Test
	public void testGetId() {
		assertTrue("ID has to be " + ID, resourceDefinition.getId().equals(ID));
	}

	@Test
	public void testGetValue() {
		assertTrue("Value has to be " + VALUE, resourceDefinition.getValue().equals(VALUE));
	}

	@Test
	public void testGetAttribute() {
		assertTrue("Attribute 'mama' must exists", resourceDefinition.getAttribute("mama") != null);
		assertTrue("Attribute 'mama' has to be 'stella'", resourceDefinition.getAttribute("mama").equals("stella"));
	}

	@Test
	public void testGetType() {
		assertTrue("Type has to be " + TYPE, resourceDefinition.getType().equals(TYPE));
	}

	@Test
	public void testGetGroup() {
		assertTrue("Group ID should match", group.equals(resourceDefinition.getGroup()));
	}

	@Test
	public void testGetGroupId() {
		assertTrue("Group ID should be strings", resourceDefinition.getGroupId().equals("strings"));
	}
}
