package de.myreality.chronos.slick;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.chronos.models.Bounding;
import de.myreality.chronos.models.Component;
import de.myreality.chronos.models.Entity;
import de.myreality.chronos.models.EntitySystem;
import de.myreality.chronos.scripting.Script;
import de.myreality.chronos.scripting.ScriptBuilder;
import de.myreality.chronos.util.Dimension2i;
import de.myreality.chronos.util.Point2f;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Entity especially for slick games. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class SlickEntity extends Entity {

	private static final long serialVersionUID = 1L;

	// Bounding box for collision detection
	private Shape boundingBox;
	
	// Color of the entity
	private Color color;
	
	private ArrayList<BeforeUpdateComponent> beforeUpdateComponents;
	private ArrayList<AfterUpdateComponent> afterUpdateComponents;

	public SlickEntity(float x, float y, int width, int height, EntitySystem system) {
		super(x, y, 0, width, height, 0, system);
		boundingBox = new Rectangle(0, 0, 0, 0);
		beforeUpdateComponents = new ArrayList<BeforeUpdateComponent>();
		afterUpdateComponents = new ArrayList<AfterUpdateComponent>();
		color = new Color(255, 255, 255);
	}
	
	public SlickEntity(float x, float y, int width, int height) {
		this(x, y, width, height, null);
	}
	
	public SlickEntity(float x, float y) {
		this(x, y, 0, 0);
	}
	
	public SlickEntity(int width, int height) {
		this(0, 0, width, height);
	}
	
	public SlickEntity(Point2f position, Dimension2i dimension, EntitySystem system) {
		this(position.x, position.y, dimension.w, dimension.h, system);
	}
	
	public SlickEntity(Point2f position, Dimension2i dimension) {
		this(position, dimension, null);
	}
	
	public SlickEntity(EntitySystem system) {
		this(0, 0, 0, 0, system);
	}
	
	public SlickEntity() {
		this(0.0f, 0.0f);
	}

	private void addBeforeUpdateComponent(BeforeUpdateComponent component) {
		beforeUpdateComponents.add(component);
	}
	
	private void addAfterUpdateComponent(AfterUpdateComponent component) {
		afterUpdateComponents.add(component);
	}
	
	@Override
	public void addComponent(Component component) {
		super.addComponent(component);
		if (component instanceof BeforeUpdateComponent) {
			BeforeUpdateComponent before = (BeforeUpdateComponent)component;
			addBeforeUpdateComponent(before);
		} else if (component instanceof AfterUpdateComponent) {
			AfterUpdateComponent after = (AfterUpdateComponent)component;
			addAfterUpdateComponent(after);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        for(BeforeUpdateComponent component : beforeUpdateComponents) {
        	component.beforeUpdate(gc, sbg, delta);
        }
        
        for(Component component : components) {
        	if (component instanceof SlickComponent) {
        		SlickComponent slickComponent = (SlickComponent)component;
        		slickComponent.update(gc, sbg, delta);
        	}
        }
        
        for (Script script : getScripts()) {
	        Object[] params = {this, gc, (Integer)delta};
	        script.runScriptFunction("update", params);
	        
        }
        
        for(AfterUpdateComponent component : afterUpdateComponents) {
        	component.afterUpdate(gc, sbg, delta);
        }
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        if(renderComponent != null && renderComponent instanceof SlickRenderComponent) {
        	SlickRenderComponent slickRenderComponent = (SlickRenderComponent)renderComponent;
        	slickRenderComponent.render(gc, sbg, g);
        }
        
        for (Script script : getScripts()) {
	        Object[] params = {this, gc, g};
	        script.runScriptFunction("render", params);	        
        }
    }

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public boolean collidateWith(Bounding other) {
		if (other instanceof SlickEntity) {
		return boundingBox.intersects(((SlickEntity)other).boundingBox);
		} else {
			return super.collidateWith(other);
		}
	}
	
	
	@Override
	public boolean contains(int x, int y) {
		return boundingBox.contains(x, y);
	}
	
	
	@Override
	public boolean contains(SlickEntity other) {
		return boundingBox.contains(other.boundingBox);
	}

	@Override
	public void setScale(float scale) {
		super.setScale(scale);
		setDimension((int)getRealWidth(), (int)getRealHeight(), 0);
	}

	@Override
	public void setLocalX(float x) {
		super.setLocalX(x);
		boundingBox.setX(getGlobalX());
	}

	@Override
	public void setLocalY(float y) {
		super.setLocalY(y);
		boundingBox.setY(getGlobalY());
	}

	@Override
	public void setGlobalX(float x) {
		super.setGlobalX(x);
		boundingBox.setX(getGlobalX());
	}

	@Override
	public void setGlobalY(float y) {
		super.setGlobalY(y);
		boundingBox.setY(getGlobalY());
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		boundingBox = new Rectangle(getGlobalX(), getGlobalY(), width, getHeight());
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		boundingBox = new Rectangle(getGlobalX(), getGlobalY(), getWidth(), height);
	}
	

	public void setLocalPosition(Point2f position) {
		super.setLocalPosition(position.x, position.y, 0);
	}


	public void setLocalPosition(float x, float y) {
		super.setLocalPosition(x, y, 0);
	}

	public void setGlobalPosition(Point2f position) {
		super.setGlobalPosition(position.x, position.y, 0);
	}

	public void setGlobalPosition(float x, float y) {
		super.setGlobalPosition(x, y, 0);
	}
	
	public void setGlobalCenterPosition(float x, float y) {
		super.setGlobalCenterPosition(x, y, 0);
	}
	
	public void setLocalCenterPosition(float x, float y) {
		super.setLocalCenterPosition(x, y, 0);
	}
	
	public void setGlobalCenterPosition(Point2f position) {
		setGlobalCenterPosition(position.x, position.y);
	}
	
	public void setLocalCenterPosition(Point2f position) {
		setLocalCenterPosition(position.x, position.y);
	}

	public void setBounds(float x, float y, int width, int height) {
		super.setBounds(x, y, 0, width, height, 0);
	}
	
	public void setDimension(int width, int height) {
		super.setDimension(width, height, 0);
	}

	@Override
	public void generateScript(ScriptBuilder builder) {
		super.generateScript(builder);
		final String updateMethod = "update";
		final String ownerName = getClass().getSimpleName().toLowerCase();
		final String[] params1 = {ownerName, "container", "delta"};
		
		builder.addFunction(updateMethod, params1);
		
		final String renderMethod = "render";
		
		final String [] params2 = {ownerName, "container", "graphics"};
		
		builder.addFunction(renderMethod, params2);
	}
	
	
	

}
