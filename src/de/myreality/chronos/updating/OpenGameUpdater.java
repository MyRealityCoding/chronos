package de.myreality.chronos.updating;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.myreality.chronos.util.FileHelper;

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
	
	public OpenGameUpdater(String repositoryURL) {
		super("");
		this.repositoryURL = repositoryURL;
		
		// Set the server url without a svn path
		URL url;
		try {
			url = new URL(repositoryURL);

			// Filter the path
			String tempURL = repositoryURL.replace(url.getPath(), "");
			
			// Filter the port
			setServerURL(tempURL.replace(":" + url.getPort(), ""));
			setServerPort(url.getPort());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}

	
	
	public String getRepositoryURL() {
		return repositoryURL;
	}
	
	@Override
	protected void downloadFile(File download) {

		String sourceurl = getRepositoryURL() + download.getPath();
		String dest = download.getPath();
		
		// Check, if path contains whitespaces
		if (dest.contains(" ")) {
			abort();
			return;
		}

		// Make dirs
		File file = new File(dest);		
		File dirs = new File(file.getParent() + System.getProperty("file.separator"));
        if (!dirs.exists()) {
        	dirs.mkdirs();
        }
		
        URL url;        

        try {
            url = new URL(sourceurl);
            HttpURLConnection hConnection = (HttpURLConnection) url
                    .openConnection();
            HttpURLConnection.setFollowRedirects(true);
            if (HttpURLConnection.HTTP_OK == hConnection.getResponseCode()) {
                InputStream in = hConnection.getInputStream();
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
                byte[] buffer = new byte[4096];
                int numRead;
                long numWritten = 0;
                while ((numRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, numRead);
                    numWritten += numRead;                
                    setTmpSize(numWritten);
                }
                setTmpSize(0);
                setCurrentSize(getCurrentSize() + numWritten);            	
                out.close();
                in.close();
            }
        } catch(Exception e) {

            // Save the file temporary
        	File tmpDirs = new File(getTemporaryDirectory() + "\\" + file.getParent() + "\\");
            if (!tmpDirs.exists()) {
            	tmpDirs.mkdirs();
            }            
        }
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
		URL metaXMLFile;
		try {
			metaXMLFile = new URL(getRepositoryURL() + getMetaFile());
			return metaXMLFile.openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	@Override
	protected InputStream getXmlFilesInputStream() {
		URL url;
		try {
			url = new URL(getRepositoryURL() + FileHelper.getInstance().getFileXMLPath());
			return url.openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}



	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
	}
	
}
