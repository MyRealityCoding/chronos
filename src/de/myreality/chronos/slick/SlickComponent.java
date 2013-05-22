package de.myreality.chronos.slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.chronos.models.Component;
import de.myreality.chronos.scripting.Script;
import de.myreality.chronos.scripting.ScriptBuilder;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Basic click component. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public abstract class SlickComponent extends Component {
	
	private static final long serialVersionUID = 1L;

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		for (Component child : getChildren()) {
			if (child instanceof SlickComponent) {
				((SlickComponent) child).update(gc, sbg, delta);
			}
		}
		for (Script script : getScripts()) {
	        Object[] params = {this, gc, (Integer)delta};
	        script.runScriptFunction("update", params);	        
        }
	}

	@Override
	public void generateScript(ScriptBuilder builder) {
		final String updateMethod = "update";
		final String ownerName = getOwner().getClass().getSimpleName().toLowerCase();
		final String[] params = {ownerName, "container", "delta"};
		
		builder.addFunction(updateMethod, params);
	}
	
	
	
}
