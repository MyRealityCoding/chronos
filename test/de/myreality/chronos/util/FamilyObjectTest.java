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
package de.myreality.chronos.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for family object
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class FamilyObjectTest {
	
	FamilyObjectImpl familyObject;
	FamilyObjectImpl parent;
	
	@Before
	public void beforeTest() {
		familyObject = new FamilyObjectImpl();
		parent = new FamilyObjectImpl();
	}

	@Test
	public void testAttachTo() {		
		int oldChildCount = parent.getNumberOfChildren();
		familyObject.attachTo(parent);
		int newChildCount = parent.getNumberOfChildren();
		assertTrue("Family object should have a parent", familyObject.hasParent());
		assertTrue("Parent child count should have been increased by 1", (newChildCount - oldChildCount) == 1);		
		assertTrue("Parent should have a child", parent.hasChild(familyObject));
	}

	@Test
	public void testDetach() {
		familyObject.attachTo(parent);
		assertTrue("Parent should have a child", parent.hasChild(familyObject));
		familyObject.detach();
		assertFalse("Family object should not have a parent", familyObject.hasParent());		
		assertFalse("Parent should not have a child", parent.hasChild(familyObject));
	}

	@Test
	public void testAddChild() {
		int oldChildCount = parent.getNumberOfChildren();
		parent.addChild(familyObject);
		int newChildCount = parent.getNumberOfChildren();
		assertTrue("Family object should have a parent", familyObject.hasParent());
		assertTrue("Parent child count should have been increased by 1", (newChildCount - oldChildCount) == 1);		
		assertTrue("Parent should have a child", parent.hasChild(familyObject));
	}
	
	@Test
	public void testRemoveChild() {
		int oldChildCount = parent.getNumberOfChildren();
		parent.addChild(familyObject);
		parent.removeChild(familyObject);
		int newChildCount = parent.getNumberOfChildren();
		assertFalse("Family object should not have a parent", familyObject.hasParent());
		assertTrue("Parent child count should have been resetted", (newChildCount - oldChildCount) == 0);		
		assertFalse("Parent should not have a child", parent.hasChild(familyObject));
	}
	
	
	class FamilyObjectImpl extends BasicFamilyObject<FamilyObjectImpl> {

		private static final long serialVersionUID = 1L;
		
	}
}
