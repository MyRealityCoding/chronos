package de.myreality.chronos.slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface OnUpdateComponent {

	void onUpdate(GameContainer gc, StateBasedGame sbg, int delta);
}
