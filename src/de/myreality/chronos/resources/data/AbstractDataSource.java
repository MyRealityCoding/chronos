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
package de.myreality.chronos.resources.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.myreality.chronos.resources.ResourceException;
import de.myreality.chronos.util.BasicObserver;

/**
 * Abstract implementation of a data source
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public abstract class AbstractDataSource extends BasicObserver<DataSourceListener>implements DataSource {

	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private List<DataNode> nodes;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public AbstractDataSource() {
		nodes = new ArrayList<DataNode>();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================
	
	@Override
	public final Collection<DataNode> load() throws ResourceException {
		for (DataSourceListener listener : getListeners()) {
			listener.beforeLoad();
		}
		nodes.clear();
		startLoading();
		for (DataSourceListener listener : getListeners()) {
			listener.afterLoad();
		}
		return nodes;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	protected abstract void startLoading() throws ResourceException;
	
	protected void addNode(DataNode node, DataNode parent) {
		for (DataSourceListener listener : getListeners()) {
			DataSourceEvent event = new BasicDataSourceEvent(this, node, parent);
			try {
				listener.onNodeCreate(event);
			} catch (ResourceException e) {
				listener.onError(event, e);
			}
		}
		
		if (parent != null) {
			if (nodes.contains(parent)) {
				DataNode realParent = nodes.get(nodes.indexOf(parent));
				realParent.addChild(node);
			} else {
				parent.addChild(node);
				nodes.add(parent);
			}
		} else if (!nodes.contains(node)){
			nodes.add(node);
		}
	}
	
	

	// ===========================================================
	// Inner classes
	// ===========================================================
}
