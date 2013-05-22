package de.myreality.chronos.slick;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Chronos Game state with internal entity logic. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class ChronosGameState extends BasicGameState {
	
	// ID that refers this game state
	private int id;
	
	// Screen buffer
	private Image screenBuffer;
	
	// Current game state
	private static ChronosGameState currentGameState;
	
	
	/**
	 * Basic constructor to initialize the object
	 * 
	 * @param id ID of this game state
	 */
	public ChronosGameState(int id) {
		this.id = id;		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		currentGameState = this;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		currentGameState = this;
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
	}

	@Override
	public int getID() {
		return id;
	}
	
	
	/** 
	 * Takes a screenshot, fileExt can be .png, .gif, .tga, .jpg or .bmp 
	 */
   public void takeScreenshot(GameContainer container, String fileExt) {

      try {
         File FileSSDir = new File("screenshots");
         if (!FileSSDir.exists()) {
            FileSSDir.mkdirs();
         }

         int number = 0;
         String screenShotFileName = "screenshots/" + "screenshot_" + number + fileExt;
         File screenShot = new File(screenShotFileName);

         while (screenShot.exists()) {
            number++;
            screenShotFileName = "screenshots/" + "screenshot_" + number + fileExt;
            screenShot = new File(screenShotFileName);
         }	         

         screenBuffer = new Image(container.getWidth(), container.getHeight());

         Graphics g = container.getGraphics();
         g.copyArea(screenBuffer, 0, 0);
         ImageOut.write(screenBuffer, screenShotFileName);

      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   
   public boolean isCurrentGameState() {
	   return this == currentGameState;
   }
   
   public static ChronosGameState getCurrentGameState() {
	   return currentGameState;
   }
}
