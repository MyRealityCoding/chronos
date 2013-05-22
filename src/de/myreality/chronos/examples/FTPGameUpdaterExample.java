package de.myreality.chronos.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.myreality.chronos.updating.FTPGameUpdater;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Example for updating the game from a FTP server. Connects with FTP
 * and try to download all new files.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class FTPGameUpdaterExample {

	public static void main(String[] args) {

		// We need an executor service in order to updating
		// the game "in background"
		ExecutorService executor = Executors.newFixedThreadPool(1);
		
		// Create a new FTP game updater with the target URL, username and password
		FTPGameUpdater ftpUpdater = new FTPGameUpdater("ftp.server.com", "games/test/", "user", "password");
		
		// Lets begin with the download
		System.out.print("Starting download... ");	
		ftpUpdater.start(executor);
		
		while (ftpUpdater.isRunning()) {
			// Do something other here like showing an information of the
			// progress of download
		}		
		
		// After the download we can check some information
		// Was the download successful?
		System.out.println(ftpUpdater.getPhase() + ".");
		
		// Show the reason if it fails:
		if (ftpUpdater.isAborted()) {
			System.out.println("Reason: " + ftpUpdater.getPhase().getReason());
		}
		
		// What is the local version?
		System.out.println("Local version: " + ftpUpdater.getLocalVersion().toFullLabeledVersion());
		
		// Everything is done -> shut the executor down
		executor.shutdown();
	}

}
