package de.myreality.chronos.toolkit.updating;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Contains helper functions to work better with files
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class FileHelper {
	
	
	
	/**
	 * Determines all files inside the folder tree
	 * 
	 * @param file
	 * @param all
	 */
	private static void addFilesRecursively(File file, ArrayList<File> all) {
	    final File[] children = file.listFiles();
	    if (children != null) {
	        for (File child : children) {
	        	child.setReadable(true);
	        	child.setWritable(true);
	        	child.setExecutable(true);	        	
	            all.add(child);
	            addFilesRecursively(child, all);
	        }
	    }
	}
	
	
	
	
	
	/**
	 * Returns an array with all files inside the folder
	 */
	public static ArrayList<File> getFileList(File file) {
		ArrayList<File> files = new ArrayList<File>();
		
		addFilesRecursively(file, files);
		
		return files;
	}
	
	
	
	
	
	/**
	 * Copies a source file to another destination
	 * 
	 * @param srFile Source file
	 * @param dtFile Destination file
	 * @return true on success, false on failure
	 */
	public static boolean copyFile(String srFile, String dtFile) {
		  try{
			  File f1 = new File(srFile);
			  File f2 = new File(dtFile);
			  InputStream in = new FileInputStream(f1); 
		
			  //For Overwrite the file.
			  OutputStream out = new FileOutputStream(f2);
		
			  byte[] buf = new byte[1024];
			  int len;
			  while ((len = in.read(buf)) > 0){
				  out.write(buf, 0, len);
			  }
			  in.close();
			  out.close();
			  return true;
		  }
		  catch(FileNotFoundException ex) {
			  return false;
		  }
		  catch(IOException e) {
			  return false;
		  }
	}  
    
	
	
	
	
	/**
	 * Returns the checksum of the file
	 * 
	 * @param path Path to the file to check
	 * @return A checksum of the file content
	 */
    public static long getFileChecksum(String path) {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    String content = Charset.defaultCharset().decode(bb).toString();
		    long checksum = 0;
			for (int i = 0; i < content.length(); ++i) {
				checksum += content.charAt(i);
			}
			return checksum;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			  try {
				stream.close();
			  } catch (IOException e) {
					e.printStackTrace();
			  }
		}		
		return -1;
	}
    
    
    
    /**
     * Scan many directories and write information to a file
     */
    public static void scanDirectories(ArrayList<String> directories, String targetFile) {
    	
    }
    
    /**
     * Scan many directories and write information to a file
     */
    public static void scanDirectories(ArrayList<String> directories) {
    	scanDirectories(directories, "config/files.xml");
    }

}
