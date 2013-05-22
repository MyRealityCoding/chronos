package de.myreality.chronos.resource;

import de.myreality.chronos.ChronosException;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Occurs when a target resource loader can not be found
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ResourceLoaderNotFoundException extends ChronosException {

	private static final long serialVersionUID = 6501004681180804933L;

	public ResourceLoaderNotFoundException(Class<?> classType) {
		super("Resource loader for class '" + classType.getSimpleName() + 
				   "' not found.\nCreate a new class like " +
			"'class " + classType.getSimpleName() + "Loader extends ResourceLoader<" + 
					    classType.getSimpleName() + "> { }'");
	}

}
