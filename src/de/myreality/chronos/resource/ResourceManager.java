package de.myreality.chronos.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.myreality.chronos.ChronosException;
import de.myreality.chronos.logging.CLog;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Resource manager to load individual resources that are defined by
 * the user.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ResourceManager {

	// Singleton object
	private static ResourceManager _instance;
	
	// List of all Loaders
	private Map<String, ResourceLoader<?> > loaders;
	
	static {
		// Initialize the singleton object
		_instance = new ResourceManager();
	}
	
	/**
	 * Returns the singleton object
	 * 
	 * @return Singleton of ResourceManager
	 */
	public static ResourceManager getInstance() {
		return _instance;
	}
	
	
	/**
	 * Basic constructor of the resource manager
	 */
	private ResourceManager() {
		CLog.info("Initiating resource manager...");
		loaders = new HashMap<String, ResourceLoader<?> >();	
		initializeDefaultLoaders();
	}
	
	
	
	/**
	 * Loads resources by the given resource loaders
	 */
	public synchronized void fromXML(String url) throws ChronosException {
		InputStream input;
		try {
			input = new FileInputStream(url);		
			synchronized (input) {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder docBuilder = null;
				try {
					docBuilder = docBuilderFactory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {
					input.close();
					throw new ChronosException("Could not load resources");
				}
				Document doc = null;
		        try {
					doc = docBuilder.parse (input);
				} catch (SAXException e) {
					throw new ChronosException("Could not load resources");
				} catch (IOException e) {
					throw new ChronosException("Could not load resources");
				}
		 
				// normalize text representation
		        doc.getDocumentElement ().normalize();
		 
		        // Iterate through all elements
		        NodeList nodeList = doc.getElementsByTagName("resources");
	
		        for (int index = 0; index < nodeList.getLength(); ++index) {
		        	
		        	Node basicNode = nodeList.item(index);
		        	
		        	NodeList childNodes = basicNode.getChildNodes();
		        	
		        	for (int childIndex = 0; childIndex < childNodes.getLength(); ++childIndex) {
	
		        		Node node = childNodes.item(childIndex);
	
			        	if(node.getNodeType() == Node.ELEMENT_NODE) {
	
				        	Element resourceElement = (Element)node;
				        	
				        	String resourceType = node.getNodeName();
				        	ResourceLoader<?> loader = loaders.get(resourceType);
				       		
			        		if (loader != null) {
				        		loader.addResource(resourceElement);	        			
			        		} else {
			        			throw new ChronosException("Unknown resource type '" + resourceType + 
			        									   "' in file '" + url + "'");
			        		}
			        	}
		        	}
		        }
		        
	        	input.close();
				}
		} catch (FileNotFoundException e1) {
			throw new ChronosException("File " + url + " not found.");
		} catch (IOException e) {
			throw new ChronosException("Could not load resources");
		}
	}
	
	
	
	/**
	 * Register a new resource loader
	 */
	public void addResourceLoader(ResourceLoader<?> loader) {
		CLog.info("Add resource " + loader.getResourceType() + " loader as default");
		loaders.put(loader.getResourceType(), loader);
	}
	
	
	
	/**
	 * Initializes the default loaders into the manager
	 */
	private void initializeDefaultLoaders() {		
		addResourceLoader(StringLoader.getInstance());
		addResourceLoader(ScriptLoader.getInstance());
	}
	
	
	/**
	 * Returns an existing resource
	 * 
	 * @param id ID of the resource
	 * @param classType class of the resource
	 * @return An existing resource
	 * @throws ChronosException Occurs when the resource can not be found
	 */
	public <Type> Resource<Type> getResource(String id, Class<Type> classType, boolean deferred) throws ChronosException {

		try {
			// A type cast error can occur
			@SuppressWarnings("unchecked")
			Resource<Type> resource = (Resource<Type>) loaders.get(classType.getSimpleName().toLowerCase()).getResource(id, deferred);
			return resource;
		} catch (NullPointerException e) {
			throw new ResourceLoaderNotFoundException(classType);
		}
	}
	
	public <Type> Resource<Type> getResource(String id, Class<Type> classType) throws ChronosException {
		return getResource(id, classType, true);
	}
}
