package de.myreality.chronos.toolkit.resource;

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

import de.myreality.chronos.toolkit.ChronosException;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
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
		loaders = new HashMap<String, ResourceLoader<?> >();
		addDefaultLoaders();
	}
	
	
	
	/**
	 * Loads resources by the given resource loaders
	 */
	public void loadFromXML(String url) throws ChronosException {
		InputStream input;
		try {
			input = new FileInputStream(url);		
		
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
	        doc.getDocumentElement ().normalize ();
	 
	        NodeList listResources = doc.getElementsByTagName("resource");
	 
	        int totalResources = listResources.getLength();
	
	        for(int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++){
	 
	        	Node resourceNode = listResources.item(resourceIdx);
	 
	        	if(resourceNode.getNodeType() == Node.ELEMENT_NODE){
	        		Element resourceElement = (Element)resourceNode;
	 
	        		String type = resourceElement.getAttribute("type");
	        		
	        		ResourceLoader<?> loader = loaders.get(type);
       		
	        		if (loader != null) {
	        			String id = resourceElement.getAttribute("id");
		        		loader.addResource(url, id, resourceElement.getTextContent());	        			
	        		} else {
	        			throw new ChronosException("Unknown resource type '" + type + 
	        									   "' in file '" + url + "'");
	        		}
	        	}
	        }
        
        	input.close();
		} catch (FileNotFoundException e1) {
			throw new ChronosException("Could not load resources");
		} catch (IOException e) {
			throw new ChronosException("Could not load resources");
		}
	}
	
	
	
	/**
	 * Register a new resource loader
	 */
	public void addResourceLoader(ResourceLoader<?> loader) {
		loaders.put(loader.getInnerClass().getSimpleName().toLowerCase(), loader);
	}
	
	
	
	
	/**
	 * Returns an existing resource
	 * 
	 * @param id ID of the resource
	 * @param classType class of the resource
	 * @return An existing resource
	 * @throws ChronosException Occurs when the resource can not be found
	 */
	public Object getResource(String id, Class<?> classType) throws ChronosException {

		try {
			return loaders.get(classType.getSimpleName().toLowerCase()).getResource(id);
		} catch (NullPointerException e) {
			throw new ResourceLoaderNotFoundException(classType);
		}
	}
	

	
	/**
	 * Add default loaders
	 */
	private void addDefaultLoaders() {
		addResourceLoader(new StringLoader());
	}
	
}
