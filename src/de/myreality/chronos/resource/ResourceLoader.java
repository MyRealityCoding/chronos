package de.myreality.chronos.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import de.myreality.chronos.ChronosException;
import de.myreality.chronos.logging.CLog;
import de.myreality.chronos.util.ReflectionTemplate;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit). Chronos is
 * licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3) <br />
 * <br />
 * For more information visit http://dev.my-reality.de/chronos <br />
 * <br />
 * Basic resource loader with a given resource type. Loads a given resource
 * loader into the resource manager automatically. This class can be extended to
 * register new resource types in order to define them in XML files.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class ResourceLoader<Type> extends ReflectionTemplate<Type> {

	// The stored resources
	private Map<String, RenewableResource<Type>> resources;

	private Map<String, ResourceDefinition> definitions;

	// The XML resource type
	private String resourceType;

	/**
	 * Basic constructor of the Resource loader
	 */
	protected ResourceLoader() {
		
		resources = new TreeMap<String, RenewableResource<Type>>();
		definitions = new TreeMap<String, ResourceDefinition>();

		// Set the resource type
		try {
			resourceType = getInnerClass().getSimpleName().toLowerCase();

		} catch (NullPointerException e) {
			resourceType = "unknown-resource";
		}
	}

	/**
	 * Returns the given resource identified by an unique ID
	 */
	public RenewableResource<Type> getResource(String id, boolean deferred)
			throws ResourceNotFoundException {
		RenewableResource<Type> resource = resources.get(id);
		if (resource != null) {
			resource.allocate();
			return resource;
		} else if (deferred) {
			// Fetch the definition and load it afterwards
			try {
				loadInternalResourceByID(id);
				resource = resources.get(id);
				resource.allocate();
				return resource;
			} catch (ChronosException e) {
				throw new ResourceNotFoundException(
						getInnerClass(), id);
			}

		} else {
			throw new ResourceNotFoundException(
					getInnerClass(), id);
		}
	}

	/**
	 * Returns a contained resource
	 * 
	 * @param id
	 *            resource ID
	 * @return Resource instance
	 * @throws ResourceNotFoundException
	 *             Is thrown when the resource can not be found
	 */
	public RenewableResource<Type> getResource(String id)
			throws ResourceNotFoundException {
		return getResource(id, true);
	}

	/**
	 * Free a contained resource (for internal usage only!)
	 * 
	 * @param id
	 *            resource ID
	 */
	public void freeResource(String id) {
		RenewableResource<Type> resource = resources.get(id);

		if (resource != null) {
			
			if (resource.getUseCount() <= 1) {
				CLog.info("Removed " + getResourceType() + " resource with id = " + id);
				resources.remove(id);
			}
		}
	}

	/**
	 * Returns the current resource type
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * Determines if the id exists
	 */
	public boolean containsResourceID(String id) {
		return resources.get(id) != null;
	}

	/**
	 * Calls a resource query by the manager
	 */
	public void addResource(Element resourceElement) throws ChronosException {
		
		// Fetch the ID
		String resourceID = resourceElement.getAttribute("id");
		resourceElement.removeAttribute("id");

		// Fetch the Type
		String resourceType = resourceElement.getNodeName();
		
		CLog.info("Add " + getResourceType() + " resource with id = " + resourceID);

		// Deferred checking
		boolean deferred = false;
		if (resourceElement.getAttribute("deferred") != null) {
			deferred = resourceElement.getAttribute("deferred").equals("true");
		}

		// Define the attributes
		NamedNodeMap attributes = resourceElement.getAttributes();
		ResourceDefinition definition = new ResourceDefinition();

		for (int i = 0; i < attributes.getLength(); ++i) {
			Node node = attributes.item(i);
			definition.addAttribute(node.getNodeName(), node.getNodeValue());
		}

		// Set the content
		definition.setContent(resourceElement.getTextContent());

		// Set the type
		definition.setType(resourceType);

		// Set the ID
		definition.setID(resourceID);

		// Renew the resource definition in the list
		definitions.put(resourceID, definition);

		// Load resources immediately, when deferred is OFF
		if (!deferred) {
			loadInternalResourceByDefinition(definition);
		}
	}

	private void loadInternalResourceByDefinition(ResourceDefinition definition) {
		
		RenewableResource<Type> resourceWrapper = resources.get(definition
				.getID());
		
		if (resourceWrapper == null) {
			resourceWrapper = new Resource<Type>(definition.getID(),
					this);
			resources.put(definition.getID(), resourceWrapper);
		} else {
			resourceWrapper.free();
		}
		
		Type resource = loadResource(resourceWrapper, definition);
		
		if (resource != null) {
			resourceWrapper.setResourceContent(resource);
		} else {
			throw new ChronosException("Can't load resource '" + definition.getContent() + "'");
		}
	}

	private void loadInternalResourceByID(String id) throws ChronosException {
		ResourceDefinition definition = definitions.get(id);

		if (definition != null) {
			loadInternalResourceByDefinition(definition);
		} else {
			throw new ChronosException("Resource definition with id " + id
					+ " does not exist.");
		}
	}

	/**
	 * Delivers resource data in order to load them
	 */
	protected abstract Type loadResource(Freeable freeable, ResourceDefinition definition);

	protected class ResourceDefinition {

		private Map<String, String> resourceAttributes;

		private String content;

		private String type;

		private String ID;

		public ResourceDefinition() {
			resourceAttributes = new HashMap<String, String>();
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public void addAttribute(String name, String value) {
			resourceAttributes.put(name, value);
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getContent() {
			return content;
		}

		public String getAttribute(String name) {
			return resourceAttributes.get(name);
		}

	}

}
