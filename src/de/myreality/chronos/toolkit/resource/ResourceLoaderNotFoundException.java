package de.myreality.chronos.toolkit.resource;

import de.myreality.chronos.toolkit.ChronosException;

public class ResourceLoaderNotFoundException extends ChronosException {

	private static final long serialVersionUID = 6501004681180804933L;

	public ResourceLoaderNotFoundException(Class<?> classType) {
		super("Resource loader for class '" + classType.getSimpleName() + 
				   "' not found.\nCreate a new class like " +
			"'class " + classType.getSimpleName() + "Loader extends ResourceLoader<" + 
					    classType.getSimpleName() + "> { }'");
	}

}
