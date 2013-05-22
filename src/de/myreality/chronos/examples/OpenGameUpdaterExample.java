package de.myreality.chronos.examples;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.myreality.chronos.updating.OpenGameUpdater;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Example for updating the game from an opened server (in this case an open
 * google SVN server without authentification)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class OpenGameUpdaterExample {

	public static void main(String[] args) throws FileNotFoundException, MalformedURLException {		
	
		// We need an executor service in order to updating
		// the game "in background"
		ExecutorService executor = Executors.newFixedThreadPool(1);

		// Create a new open game updater with the target URL
		OpenGameUpdater openUpdater = new OpenGameUpdater("https://little-wars.googlecode.com/svn/trunk/");

		// Lets begin with the download
		System.out.print("Starting download... ");			
		
		openUpdater.start(executor);

		while (openUpdater.isRunning()) {
			// Do something other here like showing an information of the
			// progress of download
		}
		
		// After the download we can check some information
		// Was the download successful?
		System.out.println(openUpdater.getPhase() + ".");
		
		// Show the reason if it fails:
		if (openUpdater.isAborted()) {
			System.out.println("Reason: " + openUpdater.getPhase().getReason());
		}
		
		// What is the local version?
		System.out.println("Local version: " + openUpdater.getLocalVersion().toFullLabeledVersion());
		
		// Everything is done -> shut the executor down
		executor.shutdown();
	}

}
