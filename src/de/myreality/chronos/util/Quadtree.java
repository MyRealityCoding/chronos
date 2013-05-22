package de.myreality.chronos.util;

import java.util.ArrayList;
import java.util.List;

import de.myreality.chronos.models.Bounding;
import de.myreality.chronos.models.Entity;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Quadtree implementation for Chronos entities
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.7
 */
public class Quadtree {
	 
	  private int MAX_OBJECTS = 20;
	  private int MAX_LEVELS = 10;
	 
	  private int level;
	  private List<Bounding> objects;
	  private Bounding bounds;
	  private Quadtree[] nodes;
	 
	 /*
	  * Constructor
	  */
	  public Quadtree(int pLevel, Bounding pBounds) {
	   level = pLevel;
	   objects = new ArrayList<Bounding>();
	   bounds = pBounds;
	   nodes = new Quadtree[4];
	  }
	  
	  
	  /*
	   * Clears the quadtree
	   */
	   public void clear() {
	     objects.clear();
	   
	     for (int i = 0; i < nodes.length; i++) {
	       if (nodes[i] != null) {
	         nodes[i].clear();
	         nodes[i] = null;
	       }
	     }
	   }
	   
	   
	   
	   /*
	    * Splits the node into 4 subnodes
	    */
	    private void split() {
	      int subWidth = (int)((bounds.getGlobalRight() - bounds.getGlobalLeft()) / 2);
	      int subHeight = (int)((bounds.getGlobalBottom() - bounds.getGlobalTop()) / 2);
	      float x = bounds.getGlobalLeft();
	      float y = bounds.getGlobalTop();
	    
	      nodes[0] = new Quadtree(level+1, new Entity(x + subWidth, y, 0, subWidth, subHeight, 0, null));
	      nodes[1] = new Quadtree(level+1, new Entity(x, y, 0, subWidth, subHeight, 0, null));
	      nodes[2] = new Quadtree(level+1, new Entity(x, y + subHeight, 0, subWidth, subHeight, 0, null));
	      nodes[3] = new Quadtree(level+1, new Entity(x + subWidth, y + subHeight, 0, subWidth, subHeight, 0, null));
	    }
	    
	    
	    
	    
	    /*
	     * Determine which node the object belongs to. -1 means
	     * object cannot completely fit within a child node and is part
	     * of the parent node
	     */
	     private int getIndex(Bounding pRect) {
	       int index = -1;
	       double verticalMidpoint = bounds.getGlobalLeft() + (bounds.getWidth() / 2);
	       double horizontalMidpoint = bounds.getGlobalTop() + (bounds.getHeight() / 2);
	     
	       // Object can completely fit within the top quadrants
	       boolean topQuadrant = (pRect.getGlobalTop() < horizontalMidpoint && pRect.getGlobalBottom() < horizontalMidpoint);
	       // Object can completely fit within the bottom quadrants
	       boolean bottomQuadrant = (pRect.getGlobalTop() > horizontalMidpoint);
	     
	       // Object can completely fit within the left quadrants
	       if (pRect.getGlobalLeft() < verticalMidpoint && pRect.getGlobalRight() < verticalMidpoint) {
	          if (topQuadrant) {
	            index = 1;
	          }
	          else if (bottomQuadrant) {
	            index = 2;
	          }
	        }
	        // Object can completely fit within the right quadrants
	        else if (pRect.getGlobalLeft() > verticalMidpoint) {
	         if (topQuadrant) {
	           index = 0;
	         }
	         else if (bottomQuadrant) {
	           index = 3;
	         }
	       }
	     
	       return index;
	     }
	     
	     
	     
	     
	     /*
	      * Insert the object into the quadtree. If the node
	      * exceeds the capacity, it will split and add all
	      * objects to their corresponding nodes.
	      */
	      public void insert(Bounding pRect) {
	        if (nodes[0] != null) {
	          int index = getIndex(pRect);
	      
	          if (index != -1) {
	            nodes[index].insert(pRect);
	      
	            return;
	          }
	        }
	      
	        objects.add(pRect);
	      
	        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
	           if (nodes[0] == null) { 
	              split(); 
	           }
	      
	          int i = 0;
	          while (i < objects.size()) {
	            int index = getIndex(objects.get(i));
	            if (index != -1) {
	              nodes[index].insert(objects.remove(i));
	            }
	            else {
	              i++;
	            }
	          }
	        }
	      }
	      
	      
	      
	      
	      /*
	       * Return all objects that could collide with the given object
	       */
	       public List<Bounding> retrieve(List<Bounding> returnObjects, Bounding pRect) {
	         int index = getIndex(pRect);
	         if (index != -1 && nodes[0] != null) {
	           nodes[index].retrieve(returnObjects, pRect);
	         }
	       
	         returnObjects.addAll(objects);
	       
	         return returnObjects;
	       }

}
