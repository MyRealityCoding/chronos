/**
 * 
 */
package de.myreality.chronos.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.myreality.chronos.models.Entity;
import de.myreality.chronos.models.EntitySystem;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * 
 * Test case for EntitySystem
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.5
 */
public class EntitySystemTest {
	
	private EntitySystem system;
	private static final int ENTITY_COUNT = 1000;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		system = new EntitySystem("TestSystem");
		
		for (int i = 0; i < ENTITY_COUNT; ++i) {
			system.addEntity(new Entity());
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link de.myreality.chronos.models.EntitySystem#clear()}.
	 */
	@Test
	public void testClear() {
		system.clear();
		
		assertEquals(system.size(), 0);
	}

	/**
	 * Test method for {@link de.myreality.chronos.models.EntitySystem#addEntity(de.myreality.chronos.models.Entity)}.
	 */
	@Test
	public void testAddEntity() {
		
		final int SIZE = system.size();
		
		for (int i = 0; i < ENTITY_COUNT; ++i) {
			system.addEntity(new Entity());
		}
		
		assertEquals(system.size(), SIZE + ENTITY_COUNT);
	}

	/**
	 * Test method for {@link de.myreality.chronos.models.EntitySystem#removeEntity(de.myreality.chronos.models.Entity)}.
	 */
	@Test
	public void testRemoveEntityEntity() {
		
		ArrayList<String> ids = new ArrayList<String>();
		
		for (Entity entity : system.getAllEntities()) {
			ids.add(entity.getId());
		}
		
		for (String id : ids) {
			system.removeEntity(id);
		}
		
		assertEquals(system.size(), 0);
	}

}
