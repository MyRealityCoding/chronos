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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import de.myreality.chronos.models.Entity;
import de.myreality.chronos.models.EntityChangedEvent;

/**
 * Basic script implemenation
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class BasicScript implements Script {

	
	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final String ENTITY_NAME = "entity";
	
	private static final String UPDATE_FUNCTION = "update";

	// ===========================================================
	// Fields
	// ===========================================================
	
	private File file;
	
	private ScriptEngine engine;
	
	private boolean compile;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public BasicScript(String file, ScriptEngine engine, boolean compile) {
		this.file = new File(file);
		this.engine = engine;
		this.compile = compile;
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public void onAddListener(EntityChangedEvent event) {
		Entity entity = event.getSender();
		ScriptContext context = new SimpleScriptContext();
		Bindings engineScope = context.getBindings(ScriptContext.ENGINE_SCOPE);
		engineScope.put(ENTITY_NAME, entity);
		
		try {
			engine.eval(new FileReader(file), context);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onUpdate(EntityChangedEvent event) {
		if (engine instanceof Invocable) {
			Invocable invocable = (Invocable)engine;
			Entity object = event.getSender();
			int delta = event.getFrameDelta();
			
			try {
				invocable.invokeFunction(UPDATE_FUNCTION, object, delta);
			} catch (ScriptException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getFile() {
		return file.getName();
	}

	@Override
	public boolean isCompilable() {
		return compile && engine instanceof Compilable;
	}

	@Override
	public ScriptEngine getEngine() {
		return engine;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner classes
	// ===========================================================
}
