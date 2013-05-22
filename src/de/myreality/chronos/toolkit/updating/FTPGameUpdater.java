package de.myreality.chronos.toolkit.updating;

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

	public FTPGameUpdater(String url, String username, String password) {
		super(url);
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean beginDownload() {

		return false;
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
}
