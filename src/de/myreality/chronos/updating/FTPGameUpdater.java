package de.myreality.chronos.updating;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.rmi.ConnectException;

import org.apache.commons.net.ftp.FTPClient;

import de.myreality.chronos.util.FileHelper;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * An FTP game updater to update the game by an open FTP connection.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class FTPGameUpdater extends GameUpdater {
	
	// FTP username
	private String username;
	
	// FTP password
	private String password;
	
	// FTP Client
	private FTPClient client;
	
	// Directory on Server on Server
	private String directory;

	/**
	 * Creates a new FTPGameUpdater instance
	 * 
	 * @param url server address
	 * @param directory directory on server where the game exists
	 * @param username FTP user name
	 * @param password FTP user password
	 */
	public FTPGameUpdater(String url, String directory, String username, String password) {
		super(url);
		this.username = username;
		this.password = password;
		this.directory = directory;
		client = new FTPClient();
		// Default port for FTP (21)
		setServerPort(21);
	}
	
	
	/**
	 * Creates a new FTPGameUpdater instance
	 * 
	 * @param url server address
	 * @param username FTP user name
	 * @param password FTP user password
	 */
	public FTPGameUpdater(String url, String username, String password) {
		this(url, "", username, password);
	}
	
	
	/**
	 * Creates a new FTPGameUpdater instance
	 * 
	 * @param url server address
	 * @param username FTP user name
	 */
	public FTPGameUpdater(String url, String username) {
		this(url, "", username, "");
	}

	
	/**
	 * @return Current FTP user name
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDirectory() {
		return directory;
	}

	/**
	 * @return Current FTP user password
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the current game version fetched from the server
	 */
	@Override
	public GameVersion getServerVersion() {
		return null;
	}
	
	@Override
	protected void downloadFile(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			client.retrieveFile(getDirectory() + file.getPath(), fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void beforeDownload() {
		
	}

	@Override
	protected void afterDownload() {
		try {
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onAbort() {
		try {
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected InputStream getMetaFileInputStream() {
		try {
			return client.retrieveFileStream(getDirectory() + getMetaFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected InputStream getXmlFilesInputStream() {
		try {
			return client.retrieveFileStream(getDirectory() + FileHelper.getInstance().getFileXMLPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Check the FTP connection
	 */
	@Override
	public void checkServerConnection() {
		boolean reachable = client.isConnected();
		setServerReachable(reachable);
	}


	@Override
	protected void onStart() {
		try {
			client.connect(getServerURL(), getServerPort());
			client.login(username, password);
			client.enterLocalPassiveMode();
		} catch (SocketException e) {
			abort();
		} catch (ConnectException e) {
			abort();
		}  catch (IOException e) {
			abort();
		} 
	}
	
	
	
	
	
}
