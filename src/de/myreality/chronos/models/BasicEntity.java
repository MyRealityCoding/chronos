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
/**
 * 
 */
package de.myreality.chronos.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.myreality.chronos.util.BasicGameObject;
import de.myreality.chronos.util.BasicObserver;
import de.myreality.chronos.util.GameObject;
import de.myreality.chronos.util.Observer;
import de.myreality.chronos.util.ROVector3f;

/**
 * Provides basic functionality of an entity
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicEntity extends BasicBoundable<Entity> implements Entity {
	
	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = 1L;
	
	// ===========================================================
	// Fields
	// ===========================================================\
	
	private GameObject gameObject;
	
	private Map<String, Component> components;
	
	private Observer<EntityListener> observer;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public BasicEntity() {
		this(0, 0);
	}
	
	public BasicEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
		initializeFields();
	}



	public BasicEntity(float x, float y) {
		this(x, y, 0f, 0f);
	}



	public BasicEntity(ROVector3f topLeft, ROVector3f bottomRight) {
		super(topLeft, bottomRight);
		initializeFields();
	}



	public BasicEntity(ROVector3f... vertices) {
		super(vertices);
		initializeFields();
	}


	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public String getId() {
		return gameObject.getId();
	}

	@Override
	public void addComponent(Component component) {
		String id = component.getId();
		
		if (!components.containsKey(id) && !components.containsValue(component)) {
			components.put(id, component);
			addListener(component);
		}
	}

	@Override
	public Collection<Component> getComponents() {
		return components.values();
	}

	@Override
	public int getNumberOfComponents() {
		return components.size();
	}

	@Override
	public void update(int delta) {
		
		EntityChangedEvent event = new BasicEntityChangedEvent(this, delta);
		
		for (EntityListener listener : getListeners()) {
			listener.onUpdate(event);
		}
	}

	@Override
	public Component getComponent(String componentId) {
		return components.get(componentId);
	}

	@Override
	public Collection<EntityListener> getListeners() {
		return observer.getListeners();
	}

	@Override
	public void addListener(EntityListener listener) {
		
		if (!observer.hasListener(listener)) {
			EntityChangedEvent event = new BasicEntityChangedEvent(this, 0);
			listener.onAddListener(event);
		}
		
		observer.addListener(listener);
	}

	@Override
	public void removeListener(EntityListener listener) {
		observer.removeListener(listener);
		EntityChangedEvent event = new BasicEntityChangedEvent(this, 0);
		listener.onRemoveListener(event);
	}

	@Override
	public boolean hasListener(EntityListener listener) {
		return observer.hasListener(listener);
	}

	@Override
	public int getNumberOfListeners() {
		return observer.getNumberOfListeners();
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	
	private void initializeFields() {
		gameObject = new BasicGameObject();
		components = new HashMap<String, Component>();
		observer = new BasicObserver<EntityListener>();
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
