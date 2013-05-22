package de.myreality.chronos.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Contains helper functions to work better with files
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class FileHelper {
	
	// Configuration folder
	public static final String CONFIG_PATH = "config/";
	// Name of the backup file
	public final static String BACKUP_PATH = ".backup/";
	
	// Meta file name
	public static final String XML_FILE = "files.xml";
	
	private static FileHelper instance;
	
	static {
		instance = new FileHelper();
	}
	
	public static FileHelper getInstance() {
		return instance;
	}
	
	private FileHelper() { }
	
	/**
	 * Determines all files inside the folder tree
	 * 
	 * @param file
	 * @param all
	 */
	public void addFilesRecursively(File file, ArrayList<File> all) {
	    final File[] children = file.listFiles();
	    if (children != null) {
	        for (File child : children) {
	        	child.setReadable(true);
	        	child.setWritable(true);
	        	child.setExecutable(true);	 
	        	if (child.isFile()) {
	        		all.add(child);
	        	} else {
	        		addFilesRecursively(child, all);
	        	}
	        }
	    }
	}
	
	
	
	/**
	 * Returns the root path of a file
	 * 
	 * @param f a given file
	 * @return the root path of a given file
	 */
	public File getRootFile(File f) {
		File root = f;
		while (root != null) {
			root = root.getParentFile();
		}
		return root;
	}
	
	
	
	/**
	 * Removes all files inside the given folder
	 * 
	 * @param file target file path
	 */
	public static void removeFilesRecursively(File file) {
		if (file.isDirectory()) {
		    for (File c : file.listFiles())
		    	removeFilesRecursively(c);
		}
		
		file.delete();
	}
	
	
	
	/**
	 * @return Returns the global XML path (for the configuration meta file)
	 */
	public String getFileXMLPath() {
		return CONFIG_PATH + XML_FILE;
	}
	
	
	/**
	 * Returns an array with all files inside the folder
	 */
	public ArrayList<File> getFileList(File file) {
		ArrayList<File> files = new ArrayList<File>();
		
		addFilesRecursively(file, files);
		
		return files;
	}
	

	
	
	/**
	 * Copies a source file to another destination
	 * 
	 * @param f1 Source file
	 * @param f2 Destination file
	 * @return true on success, false on failure
	 */
	public boolean copyFile(File f1, File f2) {
		  try {			  
			  // Create subdirectories
			  if (f2.getParent() != null) {
				  File dirs = new File(f2.getParent() + "/");
			      if (!dirs.exists()) {
			         dirs.mkdirs();
			      }
		  	  }
			  
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
	 * Copies a source file to another destination (overloaded
	 * 
	 * @param filePath1 Source file
	 * @param filePath2 Destination file
	 * @return true on success, false on failure
	 */
	public boolean copyFile(String filePath1, String filePath2) {
		return copyFile(new File(filePath1), new File(filePath2));
	}
	
  
    
    
    /**
     * Scan many directories and write information to a file
     */
    public void scanDirectories(ArrayList<String> paths) {
    	// Initialize the comparison
    			ArrayList<File> AllLocalFiles = new ArrayList<File>();
    			
    			// Get all local files
    			for (String s : paths) {    	
    				if (s.isEmpty()) {
    					s = ".";
    				}
    				File tempFile = new File(s);
    				if (tempFile.isDirectory()) {
    					addFilesRecursively(tempFile, AllLocalFiles);
    				} else {
    					AllLocalFiles.add(tempFile);
    				}
    				
    			}
    			
    			FileWriter fstream;
    			try {
    				
    				StringWriter writer = new StringWriter();	
    				// Write header
    				writer.write("<?xml version=" + (char)34 + "1.0" + (char)34 + " encoding=" + (char)34 + "UTF-8" + (char)34 + " ?>");
    				writer.write("\n");
    				writer.write("<files>");
    				writer.write("\n");
    				for (File f : AllLocalFiles) {
    					
    					if (f.isFile() && !f.getPath().equals(CONFIG_PATH + XML_FILE)) {

    						String path = f.getPath().replace("./", "");
    						
    						writer.write("    <file sum=" + 
    								    (char)34 + getChecksum(f) + (char)34 + " src=" + 
    									(char)34 + path + (char)34 + " size=" + 
    									(char)34 + f.length() + (char)34 + "></file>");
    						writer.write("\n");	    						
    					}
    				}
    				
    				writer.write("</files>");
    				createConfigurationDirectory();
    				fstream = new FileWriter(CONFIG_PATH + XML_FILE);
    				BufferedWriter out = new BufferedWriter(fstream);
    				out.write(writer.toString());
    				//Close the output stream
    				out.close();	
    				fstream.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    }
    
    
    
    private void createConfigurationDirectory() {
    	File dirs = new File(CONFIG_PATH);
        if (!dirs.exists()) {
        	dirs.mkdir();
        }
    }
    
    
    /**
     * Creates a byte checksum of a given file
     */
    private byte[] createChecksum(File f) throws Exception {
	       InputStream fis =  new FileInputStream(f);

	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
	   }

    
    /**
     * Determines the checksum of a given file in path
     * 
     * @param f file to check
     * @return A MD5 checksum of the given file
     */
    public String getChecksum(File f) {
        byte[] b;
	    try {
	 		 b = createChecksum(f);
			 String result = "";
  
	 	     for (int i=0; i < b.length; i++) {
	 	         result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		     }
		     return result;
	    } catch (FileNotFoundException e) {
	    	// File does not exist, return nothing
	    	return "";
	    } catch (Exception e) {
			 e.printStackTrace();
	    }
	    return "";
    }
    
    
    
    /**
     * Create a file with the given content
     */
    public void createFile(String path, String content) {
    	try {
    	File file = new File(path);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		PrintWriter writer = new PrintWriter(stream);
		writer.write(content);
		writer.close();
		stream.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    
    // Converts a too large byte string in a shorter one
 	public String getFileSizeString(float bytes) {
 		
 		int floating = 3;
 		int level = 0;
 		
 		while (String.valueOf(Math.round(bytes)).length() > floating) {
 			bytes /= 1024.0f;
 			++level;
 		}
 		
 		return roundDecimalToString(bytes, 1) + getSizeLevelString(level);
 	}
 	
 	private String roundDecimalToString(float value, int floating) {
 		String stringValue = String.valueOf(value);
 		stringValue = removeCharsAt(stringValue, stringValue.lastIndexOf('.') + ++floating);
 		return appendNull(stringValue, floating);
 	}

 	private String removeCharsAt(String s, int pos) {
 		if (pos < s.length()) {
 			return s.substring(0, pos);
 		} else {
 			return s;
 		}
 	}
 	
 	private String appendNull(String numberString, int floating) {
 		
 		int pointIndex = numberString.lastIndexOf('.');
 		while (numberString.length() - pointIndex < floating) {
 			numberString += "0";
 		}
 		
 		return numberString;
 	}
 	
 	
 	private String getSizeLevelString(int level) {
 		switch (level) {
 			case 0:
 				return "Bytes";
 			case 1:
 				return "KB";
 			case 2:
 				return "MB";
 			case 3:
 				return "GB";
 			case 4:
 				return "TB";
 			default:
 				return "B";
 		}
 	}

}
