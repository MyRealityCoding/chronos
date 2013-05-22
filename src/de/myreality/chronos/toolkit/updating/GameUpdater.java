package de.myreality.chronos.toolkit.updating;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;

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
 * Basic game updater to fetch the newest files from server 
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class GameUpdater implements Runnable {
	
	// Current phase
	private UpdatePhase phase;
	
	// Server URL
	private String serverURL;
	
	// Temporary directory
	private String temporaryDirectory;
	
	// Download size in bytes
	private long downloadSize;
		
	// Current download size
	private long currentSize, tmpSize;
	
	// Current game version
	private GameVersion gameVersion, newerVersion;
	
	// Is true, when an update has been found
	private boolean updateFound;
	
	// Is true, when server is reachable
	private boolean serverReachable;
	
	// Folder to download all content
	private ArrayList<String> downloadFolders;
	
	// Optional server port
	private int serverPort = -1;

	
	/**
	 * Constructor of the game updater
	 */
	public GameUpdater(String url) {
		
		// Basic configuration
		downloadFolders = new ArrayList<String>();
		setPhase(UpdatePhase.NONE);
		
		// Default temporary directory
		temporaryDirectory = ".tmp";
		
		downloadSize = 0;
		currentSize = 0;
	}
	
	
	/**
	 * Start the updater process by a default thread
	 */
	public void start() throws ChronosException {
		start(null);
	}
	
	/**
	 * Start the updater process by a given executor
	 */
	public void start(Executor executor) {
		if (!isRunning()) {
			if (executor != null) {
				executor.execute(this);
			} else {
				Thread thread = new Thread(this);
				thread.start();
			}
		} else {
			throw new ChronosException("Can not start the updating process again. It has been already started.");
		}
	}
	

	/**
	 * Run the download of the prepared configuration
	 */
	@Override
	public void run() {
		
		// The process can only be done by 
		// one single thread instance
		synchronized(this) {
			if (hasDownloadFolders()) {
				setPhase(UpdatePhase.PREPARING);
				
				// Check the server connection
				checkServerConnection();
				
				if (isServerReachable()) {
					if (!beginDownload()) {
						setPhase(UpdatePhase.ABORTED);
						return;
					}
					setPhase(UpdatePhase.DONE);
				} else {
					setPhase(UpdatePhase.ABORTED);
				}
				
				
			}	
		}
	}
	
	/**
	 * Start the download of the registered files
	 * 
	 * @return false on failure or abortion
	 */
	public abstract boolean beginDownload();
	
	
	/**
	 * Add a new download folder
	 */
	public void addDownloadFolder(String folder) throws ChronosException {
		if (!isRunning()) {
			downloadFolders.add(folder);
		} else {
			throw new ChronosException("Error while adding a download folder. Game is currently updating.");
		}
	}
	
	
	public boolean hasDownloadFolders() {
		return !downloadFolders.isEmpty();
	}
	
	public String getServerURL() {
		return serverURL;
	}


	public void setServerURL(String serverURL) {		
		if (!isRunning()) {
			this.serverURL = serverURL;
		} else {
			throw new ChronosException("Error while changing the server url. Game is currently updating.");
		}
	}


	public String getTemporaryDirectory() {
		return temporaryDirectory;
	}


	public void setTemporaryDirectory(String temporaryDirectory) {		
		if (!isRunning()) {
			this.temporaryDirectory = temporaryDirectory;
		} else {
			throw new ChronosException("Error while changing the temporary url. Game is currently updating.");
		}
	}
	
	public void setServerPort(int port) {		
		if (!isRunning()) {
			serverPort = port;
		} else {
			throw new ChronosException("Error while changing the server port. Game is currently updating.");
		}
	}

	public int getServerPort() {
		return serverPort;
	}
	
	public float getLoadingProgress() {
		return (float) (currentSize + tmpSize) * 100 / (float) downloadSize;
	}

	public UpdatePhase getPhase() {
		return phase;
	}


	public long getDownloadSize() {
		return downloadSize;
	}


	public long getCurrentSize() {
		return currentSize;
	}


	public long getTmpSize() {
		return tmpSize;
	}


	public boolean isUpdateFound() {
		return updateFound;
	}


	public boolean isServerReachable() {
		return serverReachable;
	}
	
	public boolean isDone() {
		return phase == UpdatePhase.DONE;
	}
	
	public boolean isAborting() {
		return phase == UpdatePhase.ABORTING;
	}
	
	public boolean isPreparing() {
		return phase == UpdatePhase.PREPARING;
	}
	
	public boolean isLoading() {
		return phase == UpdatePhase.LOADING;
	}

	
	public boolean isRunning() {
		return phase != UpdatePhase.NONE && !isDone();
	}
	
	
	public GameVersion getGameVersion() {
		return gameVersion;
	}


	public GameVersion getNewerVersion() {
		return newerVersion;
	}


	/**
	 * Aborts a running updating process
	 */
	public void abort() {
		if (isRunning()) {
			setPhase(UpdatePhase.ABORTING);
		}
	}
	
	
	
	/**
	 * Protected setters
	 */
	
	
	
	
	
	/**
	 * Helper function to determine the version inside of the given file
	 * 
	 * @param metaXMLFile XML file URL
	 * @param versionID ID of the version string
	 * @return A converted game version object
	 * @throws ChronosException occurs when the resources can not be loaded successfully
	 * @throws IOException occurs if the stream is broken
	 */
	protected GameVersion getOnlineVersion(URL metaXMLFile, String versionID) throws ChronosException, IOException {
		String onlineVersion = "";
		InputStream is;
		is = metaXMLFile.openStream();
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ChronosException("Could not load resources");
		}
		Document doc = null;
        try {
			doc = docBuilder.parse(is);
		} catch (SAXException e) {
			throw new ChronosException("Could not load resources");
		} catch (IOException e) {
			throw new ChronosException("Could not load resources");
		}
 
		// normalize text representation
        doc.getDocumentElement ().normalize ();
 
        NodeList listResources = doc.getElementsByTagName("resource");
 
        int totalResources = listResources.getLength();
        
        for(int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++) {
 
        	Node resourceNode = listResources.item(resourceIdx);
 
        	if(resourceNode.getNodeType() == Node.ELEMENT_NODE){
        		Element resourceElement = (Element)resourceNode;
 
        		String type = resourceElement.getAttribute("type");

        		if(type.equals("string") && resourceElement.getAttribute("id").equals(versionID)) {        			
        			onlineVersion = resourceElement.getTextContent();
        		}   		
        	}
        }

        is.close();	
		
		return new GameVersion(onlineVersion);
	}
	
	
	
	protected ArrayList<String> getDownloadFolders() {
		return downloadFolders;
	}


	protected void setPhase(UpdatePhase phase) {
		this.phase = phase;
	}


	protected void setDownloadSize(long downloadSize) {
		this.downloadSize = downloadSize;
	}


	protected void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}


	protected void setTmpSize(long tmpSize) {
		this.tmpSize = tmpSize;
	}


	protected void setGameVersion(GameVersion gameVersion) {
		this.gameVersion = gameVersion;
	}


	protected void setNewerVersion(GameVersion newerVersion) {
		this.newerVersion = newerVersion;
	}


	protected void setUpdateFound(boolean updateFound) {
		this.updateFound = updateFound;
	}


	protected void setServerReachable(boolean serverReachable) {
		this.serverReachable = serverReachable;
	}


	
	/**
	 * Check, if the current server is reachable
	 */
	public void checkServerConnection() {		

		try {
			URL u = new URL (getServerURL()); 
			HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection(); 
			huc.setDoOutput(true);
			huc.setRequestMethod ("GET"); 
			huc.connect(); 		 
			int code = huc.getResponseCode();
			if (code == 200) {
				setServerReachable(true);
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}

		setServerReachable(false);
	}
	
	
	
	/**
	 * Update phases of the game updater
	 * 
	 * @author Miguel Gonzalez
	 */
	enum UpdatePhase {
		NONE,
		PREPARING,
		LOADING,
		ABORTING,
		ABORTED,
		DONE
	}

}
