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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
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
		if (file == null || !file.exists() || !file.isDirectory()) {
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
		URL[] roots = findClassPaths();

		for (URL root : roots) {
			for (String relativePath : getChildren(root)) {

				if (relativePath.endsWith(TARGET_FORMAT)) {

					// Remove .class and change / to .
					String className = relativePath.substring(0,
							relativePath.length() - TARGET_FORMAT.length())
							.replace("/", ".");
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

		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Find the classpath URLs for a specific classpath resource. The classpath
	 * URL is extracted from loader.getResources() using the baseResource.
	 * 
	 * @param baseResource
	 * @return
	 */
	public static URL[] findResourceBases(String baseResource,
			ClassLoader loader) {
		ArrayList<URL> list = new ArrayList<URL>();
		try {
			Enumeration<URL> urls = loader.getResources(baseResource);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				list.add(findResourceBase(url, baseResource));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return list.toArray(new URL[list.size()]);
	}

	/**
	 * Find the classpath URLs for a specific classpath resource. The classpath
	 * URL is extracted from loader.getResources() using the baseResource.
	 * 
	 * @param baseResource
	 * @return
	 */
	public static URL[] findResourceBases(String baseResource) {
		return findResourceBases(baseResource, Thread.currentThread()
				.getContextClassLoader());
	}

	private static URL findResourceBase(URL url, String baseResource) {
		String urlString = url.toString();
		int idx = urlString.lastIndexOf(baseResource);
		urlString = urlString.substring(0, idx);
		URL deployUrl = null;
		try {
			deployUrl = new URL(urlString);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return deployUrl;
	}

	/**
	 * Find the classpath URL for a specific classpath resource. The classpath
	 * URL is extracted from
	 * Thread.currentThread().getContextClassLoader().getResource() using the
	 * baseResource.
	 * 
	 * @param baseResource
	 * @return
	 */
	public static URL findResourceBase(String baseResource) {
		return findResourceBase(baseResource, Thread.currentThread()
				.getContextClassLoader());
	}

	/**
	 * Find the classpath URL for a specific classpath resource. The classpath
	 * URL is extracted from loader.getResource() using the baseResource.
	 * 
	 * @param baseResource
	 * @param loader
	 * @return
	 */
	public static URL findResourceBase(String baseResource, ClassLoader loader) {
		URL url = loader.getResource(baseResource);
		return findResourceBase(url, baseResource);
	}

	/**
	 * Find the classpath for the particular class
	 * 
	 * @param clazz
	 * @return
	 */
	public static URL findClassBase(Class<?> clazz) {
		String resource = clazz.getName().replace('.', '/') + ".class";
		return findResourceBase(resource, clazz.getClassLoader());
	}

	/**
	 * Uses the java.class.path system property to obtain a list of URLs that
	 * represent the CLASSPATH
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static URL[] findClassPaths() {
		List<URL> list = new ArrayList<URL>();
		String classpath = System.getProperty("java.class.path");
		StringTokenizer tokenizer = new StringTokenizer(classpath,
				File.pathSeparator);

		while (tokenizer.hasMoreTokens()) {
			String path = tokenizer.nextToken();
			File fp = new File(path);
			if (!fp.exists())
				throw new RuntimeException(
						"File in java.class.path does not exist: " + fp);
			try {
				list.add(fp.toURL());
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		return list.toArray(new URL[list.size()]);
	}

	/**
	 * Uses the java.class.path system property to obtain a list of URLs that
	 * represent the CLASSPATH
	 * <p/>
	 * paths is used as a filter to only include paths that have the specific
	 * relative file within it
	 * 
	 * @param paths
	 *            comma list of files that should exist in a particular path
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static URL[] findClassPaths(String... paths) {
		ArrayList<URL> list = new ArrayList<URL>();

		String classpath = System.getProperty("java.class.path");
		StringTokenizer tokenizer = new StringTokenizer(classpath,
				File.pathSeparator);
		for (int i = 0; i < paths.length; i++) {
			paths[i] = paths[i].trim();
		}

		while (tokenizer.hasMoreTokens()) {
			String path = tokenizer.nextToken().trim();
			boolean found = false;
			for (String wantedPath : paths) {
				if (path.endsWith(File.separator + wantedPath)) {
					found = true;
					break;
				}
			}
			if (!found)
				continue;
			File fp = new File(path);
			if (!fp.exists())
				throw new RuntimeException(
						"File in java.class.path does not exists: " + fp);
			try {
				list.add(fp.toURL());
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		return list.toArray(new URL[list.size()]);
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
