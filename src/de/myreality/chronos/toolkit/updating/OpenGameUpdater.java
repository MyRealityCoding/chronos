package de.myreality.chronos.toolkit.updating;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * An open game updater to update the game by an open and unsafety connection 
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class OpenGameUpdater extends GameUpdater {
	
	// Repository URL
	private String repositoryURL;

	public OpenGameUpdater(String url, String repositoryURL) {
		super(url);
		this.repositoryURL = repositoryURL;
	}

	@Override
	public boolean beginDownload() {

		
		
		return false;
	}
	
	public String getRepositoryURL() {
		return repositoryURL;
	}

}
