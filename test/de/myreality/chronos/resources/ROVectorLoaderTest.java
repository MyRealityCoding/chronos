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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.myreality.chronos.resources.loader.ROVectorLoader;
import de.myreality.chronos.util.ROVector3f;

/**
 * Test case for vector loader
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class ROVectorLoaderTest {
	
	ROVectorLoader loader;

	@Before
	public void setUp() throws Exception {
		loader = new ROVectorLoader();
	}


	@Test
	public void testCreate() {
		ResourceDefinition invalidDefinition1 = new BasicResourceDefinition("my_vec1", "vector");
		invalidDefinition1.addAttribute("x", "1.5");
		invalidDefinition1.addAttribute("y", "dumpd22");
		invalidDefinition1.addAttribute("z", "928.837");
		
		ResourceDefinition invalidDefinition2 = new BasicResourceDefinition("my_vec2", "vector");
		
		ResourceDefinition validDefinition = new BasicResourceDefinition("my_vec2", "vector");
		validDefinition.addAttribute("x", "0.5");
		validDefinition.addAttribute("y", "1.5");
		validDefinition.addAttribute("z", "2.5");
		try {
			loader.create(invalidDefinition1);
			fail("The first definition is not valid");
		} catch (ResourceException e) {
			// Everything is good!
		}
		
		try {
			loader.create(invalidDefinition2);
			fail("The second definition is not valid");
		} catch (ResourceException e) {
			// Everything is good!
		}
		
		try {
			ROVector3f vector = loader.create(validDefinition);			
			assertFalse("Vector should not be null", vector == null);
			assertTrue("Vector x must be 0.5", vector.getX() == 0.5f);
			assertTrue("Vector y must be 1.5", vector.getY() == 1.5f);
			assertTrue("Vector z must be 2.5", vector.getZ() == 2.5f);
		} catch (ResourceException e) {
			fail("The third definition should be valid");
		}
	}
}