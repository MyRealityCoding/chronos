package de.myreality.chronos.updating;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.myreality.chronos.ChronosException;
import de.myreality.chronos.resource.Resource;
import de.myreality.chronos.resource.ResourceLoader;
import de.myreality.chronos.resource.ResourceManager;
import de.myreality.chronos.resource.StringLoader;
import de.myreality.chronos.util.FileHelper;

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
	protected long downloadSize;
		
	// Current download size
	protected long currentSize;

	private long tmpSize;
	
	// Current game version
	private GameVersion gameVersion;
	
	// Newer server version
	private GameVersion newerVersion;
	
	// Is true, when an update has been found
	private boolean updateFound;
	
	// Is true, when server is reachable
	private boolean serverReachable;
	
	// List of files to download
	protected ArrayList<File> downloads;
	
	// Optional server port
	private int serverPort = -1;
	
	// Default loader of meta resources
	protected ResourceLoader<String> metaResourceLoader;
	
	// Future Object for concurrency
	Future<?> future;
	
	// Meta data XML script
	private String metaFile;
	
	// Version resource id
	private String versionResourceID;
	
	// Listeners
	private HashSet<UpdatingListener> listeners;
	
	// Game timer
	private UpdatingTimer timer;
	
	/**
	 * Constructor of the game updater
	 */
	public GameUpdater(String url) {
		
		metaResourceLoader = StringLoader.getInstance();
		
		// Basic configuration
		downloads = new ArrayList<File>();
		listeners = new HashSet<UpdatingListener>();
		setPhase(UpdatePhase.NONE);
		
		// Default temporary directory
		temporaryDirectory = ".tmp";
		
		// Set the server URL
		setServerURL(url);
		
		downloadSize = 0;
		currentSize = 0;
		
		// Meta file's default
		setMetaFile("meta.xml");
				
		// Default version resource id
		versionResourceID = "APP_VERSION";
		
		timer = new UpdatingTimer();
	}
	
	
	public void addUpdatingListener(UpdatingListener listener) {
		listeners.add(listener);
	}
	
	
	/**
	 * Start the updater process by a given executor
	 */
	public void start(ExecutorService executor) {
		if (!isRunning()) {
			setPhase(UpdatePhase.PREPARING);			
			if (executor != null) {
				future = executor.submit(this);
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
		
		onStart();

		// The process can only be done by 
		// one single thread instance
		synchronized(this) {
			// Check the server connection
			checkServerConnection();
			
			if (isServerReachable()) {
				
				// Calculate the versions
				setGameVersion(getLocalVersion());
				try {
					setNewerVersion(getServerVersion());
				} catch (FileNotFoundException e1) {
					setPhase(UpdatePhase.ABORTED);
					getPhase().setReason("Meta file not found");
					return;
				}
				
				
				if (isNewVersionAvailable()) {
					timer.reset();
					setPhase(UpdatePhase.LOADING);
					try {
						beforeDownload();
						beginDownload();
						afterDownload();
					} catch (ChronosException e) {
						setPhase(UpdatePhase.ABORTED);
						getPhase().setReason(e.getMessage());
						return;
					}
	
					removeFileBackup();
					setPhase(UpdatePhase.DONE);
				} else {
					setPhase(UpdatePhase.DONE);
					getPhase().setReason("Game is up to date");
				}
			} else {
				setPhase(UpdatePhase.ABORTED);
				getPhase().setReason("Server is not reachable");
			}
		}
	}
	
	public boolean isNewVersionAvailable() {
		// Fetch the two versions in order to compare them
		GameVersion onlineVersion = getNewerVersion();		
		GameVersion localVersion = getGameVersion();
		return onlineVersion.isNewerThan(localVersion);
	}
	
	/**
	 * Start the download of the registered files
	 * 
	 * @return false on failure or abortion
	 */
	private void beginDownload() throws ChronosException {		
		
			// Download the content. Not writable files that are
			// in usage copy to a temporary folder and update them on
			// restart.
			
			// 1. Fetch all relevant server files
			ArrayList<UpdateFile> serverFiles = loadServerFiles();
			
			// 2. Make backup
			createFileBackup(serverFiles);
			
			// 3. Compare local files with server files. When differences
			// exist, download the server file. If there is no local file
			// available, download the server file as well.
			for (UpdateFile serverFile : serverFiles) {			
				boolean foundOnDisk = false;
				for (UpdateFile localFile : serverFiles) {					
					if (localFile.getPath().equals(serverFile.getPath())) {							
						if (!FileHelper.getInstance().getChecksum(localFile).equals(serverFile.getChecksum())) {
							System.out.println(FileHelper.getInstance().getChecksum(localFile) + " <-> " + serverFile.getChecksum());
							downloadSize += serverFile.getSize();	
							downloads.add(serverFile);
						}
						foundOnDisk = true;
						break;
					} 			
				}
				if (!foundOnDisk) {
					downloadSize += serverFile.getSize();
					downloads.add(serverFile);
				}
			}
			setPhase(UpdatePhase.LOADING);		
			for (File download : downloads) {	
				if (isAborting()) {
					throw new ChronosException("Corrupt download. Maybe some files contain whitespaces.");
				}
				downloadFile(download);

				for (UpdatingListener listener : listeners) {
					listener.onUpdate(download.getName(), getCurrentSize(), getDownloadSize(), getLoadingProgress(), getByteRate(), getPhase());
				}
			}
	
		
			setPhase(UpdatePhase.DONE);
		
	}
	
	
	
	private float getByteRate() {
		return (float)getCurrentSize() / (float)timer.getElapsedTime() / 1000.0f;
	}

	
	/**
	 * Download the target file
	 */
	protected abstract void downloadFile(File file);

	
	public String getServerURL() {
		return serverURL;
	}


	public void setServerURL(String url) {		
		if (!isRunning()) {		
			if (!url.isEmpty()) {
				this.serverURL = url;
				// Correct the last segment of the url
				if (this.serverURL.charAt(this.serverURL.length() - 1) != '/') {
					this.serverURL += '/';
				}
			}						
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
		if (downloadSize > 0) {
		return (float) (currentSize + tmpSize) * 100 / (float) downloadSize;
		} else {
			return 0;
		}
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
		return phase.equals(UpdatePhase.DONE);
	}
	
	public boolean isAborting() {
		return phase.equals(UpdatePhase.ABORTING);
	}
	
	public boolean isPreparing() {
		return phase.equals(UpdatePhase.PREPARING);
	}
	
	public boolean isLoading() {
		return phase.equals(UpdatePhase.LOADING);
	}
	
	
	public boolean isAborted() {
		return phase.equals(UpdatePhase.ABORTED);
	}

	
	public boolean isRunning() {
		if (future != null) {
			if (future.isDone()) {		
				return false;
			} else {
				return true;
			}
		} else {
			return phase != UpdatePhase.ABORTED && phase != UpdatePhase.NONE && !isDone();
		}
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
			onAbort();
			// Recreate the old directory
			alignFileBackup();
		}
	}
	
	
	
	/**
	 * Gets the current version of this game
	 */	
	public GameVersion getLocalVersion() {
		
		ResourceManager mgr = ResourceManager.getInstance();
		
		String versionString = "";
		
		try {
			Resource<String> resource = mgr.getResource(getVersionResourceID(), String.class);
			versionString = resource.get();
		} catch (ChronosException e) {
			// TODO: Exception handling
		}
		return new GameVersion(versionString);
	}
	
	/**
	 * @return a stream to the given meta file on the server
	 */
	protected abstract InputStream getMetaFileInputStream();
	
	/**
	 * @return A stream to the files.xml on the server
	 */
	protected abstract InputStream getXmlFilesInputStream();
	
	
	/**
	 * Abstract method in order to fetch the server URL
	 * @throws FileNotFoundException 
	 */
	protected GameVersion getServerVersion() throws FileNotFoundException {
		InputStream xmlMetaFileStream = getMetaFileInputStream();
		
		if (xmlMetaFileStream == null) {
			throw new FileNotFoundException("Meta file not found");
		}
		
		try {
			String onlineVersion = "";
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = null;
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new ChronosException("Could not load resources");
			}
			Document doc = null;
	        try {
				doc = docBuilder.parse(xmlMetaFileStream);
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
	
	        		if(type.equals(metaResourceLoader.getResourceType()) 
	        		&& resourceElement.getAttribute("id").equals(getVersionResourceID())) {        			
	        			onlineVersion = resourceElement.getTextContent();        			
	        		}   		
	        	}
	        }
	
	        xmlMetaFileStream.close();
			
			return new GameVersion(onlineVersion);
		} catch (IOException e) {
			// Can't read version because it does not exist
			// Return an empty version
			return new GameVersion("");
		}
	}
	
	
	private ArrayList<UpdateFile> loadServerFiles() {	
		InputStream xmlFilesInputStream = getXmlFilesInputStream();
		ArrayList<UpdateFile> serverFiles = new ArrayList<UpdateFile>();
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
        	
			docBuilder = docBuilderFactory.newDocumentBuilder();			
			Document doc = docBuilder.parse (xmlFilesInputStream);			
			// normalize text representation
	        doc.getDocumentElement ().normalize(); 
	        NodeList listResources = doc.getElementsByTagName("file");        
	        int totalResources = listResources.getLength();
	       
	        // Load the content
	        for(int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++){
	        	 
	        	Node resourceNode = listResources.item(resourceIdx);
	 
	        	if(resourceNode.getNodeType() == Node.ELEMENT_NODE){        		
	        		Element resourceElement = (Element)resourceNode; 
	        		String source = resourceElement.getAttribute("src");
	        		long size = Long.parseLong(resourceElement.getAttribute("size"));
	        		String sum = resourceElement.getAttribute("sum");
	        		source = source.replace('\\', '/');
	        		//if (folder.equals(source.substring(0, folder.length()))) {	
					serverFiles.add(new UpdateFile(source, size, sum));
					//}
					xmlFilesInputStream.close();
	        	}
	        }			
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return serverFiles;		
	}



	
	

	protected void setPhase(UpdatePhase phase) {
		this.phase = phase;
		
		for (UpdatingListener listener : listeners) {
			listener.onStartPhase(phase);
		}
		
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
			} else {
				setServerReachable(false);
			}
		} catch (UnknownHostException e) {
			setServerReachable(false);
		} catch (IOException e) {
			setServerReachable(false);
		}
		
	}
	
	
	
	/**
	 * Set a new meta file
	 */
	public void setMetaFile(String metaFileName) {
		metaFile = metaFileName;
		ResourceManager mgr = ResourceManager.getInstance();
		
		try {
			mgr.fromXML(metaFile);
		} catch (ChronosException e) {
			// TODO: Exception handling, file does not exist
		}
	}
	
	
	/**
	 * Returns the current meta file
	 */
	public String getMetaFile() {
		return metaFile;
	}

	public ResourceLoader<String> getMetaResourceLoader() {
		return metaResourceLoader;
	}
	
	public String getVersionResourceID() {
		return versionResourceID;
	}

	public void setVersionResourceID(String versionResourceID) {
		this.versionResourceID = versionResourceID;
	}
	
	 /**
		 * Creates a full backup file of the given files
		 * 
		 * @param files Files to copy
		 */
		protected void createFileBackup(ArrayList<? extends File> files) {
			File backup = new File(FileHelper.BACKUP_PATH);
			backup.setReadable(true);
			backup.setWritable(true);
			backup.setExecutable(true);
			if (!backup.exists()) {
				backup.mkdir();
				if (backup.getName().equals("null")) {
					backup.delete();
				}
			}
			
			for (File file : files) {
				FileHelper.getInstance().copyFile(file.getPath(), FileHelper.BACKUP_PATH + file.getPath());
			}
		}
		
		
		/**
		 * Removes the current file backup
		 */
		protected void removeFileBackup() {
			File backup = new File(FileHelper.BACKUP_PATH);
			backup.setReadable(true);
			backup.setWritable(true);
			backup.setExecutable(true);
			FileHelper.removeFilesRecursively(backup);			
		}
		
		/**
		 * Align the backup and delete it
		 */
		protected boolean alignFileBackup() {
			ArrayList<File> files = new ArrayList<File>();
			File backup = new File(FileHelper.BACKUP_PATH);
			backup.setReadable(true);
			backup.setWritable(true);
			backup.setExecutable(true);
			FileHelper.getInstance().addFilesRecursively(backup, files);
			
			// Copy all files back
			for (File f : files) {
				String targetPath = f.getPath().replace(FileHelper.BACKUP_PATH, "");
				if (!FileHelper.getInstance().copyFile(f.getPath(), targetPath)) {
					return false;
				}
			}
			removeFileBackup();
			return true;
		}
		
		
		/**
		 * Callback functions
		 */
		protected abstract void beforeDownload();
		protected abstract void afterDownload();
		protected abstract void onAbort();
		protected abstract void onStart();
		
		
		
		
		/**
		 * Updating timer
		 */
		class UpdatingTimer {
			
			private long startMilis;
			
			public UpdatingTimer() {
				reset();
			}
			
			public long getElapsedTime() {
				return System.currentTimeMillis() - startMilis;
			}
			
			public void reset() {
				startMilis = System.currentTimeMillis();
			}
		}
}
