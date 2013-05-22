package de.myreality.chronos.slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface AfterUpdateComponent {

	void afterUpdate(GameContainer gc, StateBasedGame sbg, int delta);
}
