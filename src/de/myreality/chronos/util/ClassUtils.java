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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utility class which provides functionality for other classes
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class ClassUtils {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	@SuppressWarnings("unchecked")
	public static <T> T createObject(Class<T> clazz) {
		Constructor<?> constructor;
		try {
			constructor = clazz.getConstructor();
			return (T) constructor.newInstance(new Object[] {});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Collects all children of the given file into the given result list. The
	 * resulting string is the relative path from the given reference.
	 */
	private static void addFiles(File file, List<String> result, File reference) {
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		for (File child : file.listFiles()) {
			if (child.isDirectory()) {
				addFiles(child, result, reference);
			} else {
				String path = null;
				while (child != null && !child.equals(reference)) {
					if (path != null) {
						path = child.getName() + "/" + path;
					} else {
						path = child.getName();
					}
					child = child.getParentFile();
				}
				result.add(path);
			}
		}
	}

	/**
	 * Takes a given url and creates a list which contains all children of the
	 * given url. (Works with Files and JARs).
	 */
	public static List<String> getChildren(URL url) {
		List<String> result = new ArrayList<String>();
		if ("file".equals(url.getProtocol())) {
			File file = new File(url.getPath());
			if (!file.isDirectory()) {
				file = file.getParentFile();
			}
			addFiles(file, result, file);
		} else if ("jar".equals(url.getProtocol())) {
			try {
				JarFile jar = ((JarURLConnection) url.openConnection())
						.getJarFile();
				Enumeration<JarEntry> e = jar.entries();
				while (e.hasMoreElements()) {
					JarEntry entry = e.nextElement();
					result.add(entry.getName());
				}
			} catch (IOException e) {
				// Do nothing
			}
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Class<?>[] searchForAnnotation(Class<?> annotation) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		final String TARGET_FORMAT = ".class";
		URL[] roots = ((URLClassLoader) (Thread.currentThread()
				.getContextClassLoader())).getURLs();

		for (URL root : roots) {
			for (String relativePath : getChildren(root)) {
				
				if (relativePath.endsWith(TARGET_FORMAT)) {
					
					// Remove .class and change / to .
					String className = relativePath.substring(0,
							relativePath.length() - TARGET_FORMAT.length()).replace("/", ".");
					try {
						Class element = Class.forName(className);

						if (element.getAnnotation(annotation) != null) {
							classes.add(element);
						}
					} catch (ClassNotFoundException ex) {
						// Do nothing
					}
				}
			}
		}

		return (Class[]) classes.toArray(new Class[classes.size()]);
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
