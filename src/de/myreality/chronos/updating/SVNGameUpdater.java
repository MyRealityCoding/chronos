package de.myreality.chronos.updating;

import java.io.File;
import java.io.InputStream;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * An SVN game updater to update the game by an open SVN connection.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class SVNGameUpdater extends GameUpdater {

	// SVN username
	private String username;
		
	// SVN password
	private String password;
		
	public SVNGameUpdater(String url, String repositoryURL, String username, String password) {
		super(url);
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected void downloadFile(File file) {
		
	}

	@Override
	protected void beforeDownload() {
		
	}

	@Override
	protected void afterDownload() {
		
	}

	@Override
	protected void onAbort() {
		
	}

	@Override
	protected InputStream getMetaFileInputStream() {
		return null;
	}

	@Override
	protected InputStream getXmlFilesInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
	}
}
