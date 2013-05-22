package de.myreality.chronos.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import de.myreality.chronos.scripting.Script;
import de.myreality.chronos.scripting.ScriptBuilder;
import de.myreality.chronos.scripting.Scriptable;
import de.myreality.chronos.slick.SlickEntity;
import de.myreality.chronos.util.Dimension3i;
import de.myreality.chronos.util.Point2f;
import de.myreality.chronos.util.Point3f;
import de.myreality.chronos.util.Vector3f;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Entity object. This is a basic game object.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.2
 * @version 0.7.1
 */
public class Entity extends NodeObject<Entity> implements Scriptable, Bounding {

	private static final long serialVersionUID = 1L;

	// Contained components
	protected ArrayList<Component> components;
	
	// Target render component to draw this entity
	protected RenderComponent renderComponent;
	
	// String id
	private String id;
	
	// Rotation of the entity
	private float rotation;
	
	// Scale of the entity
	private float scale;
	
	// Width, height and depth of the entity
	private Dimension3i dimension;
	
	// Position of the entity
	private Point3f position, lastPosition;
	
	// The system this entity depends on
	private EntitySystem currentSystem;
	
	// Internal counter for id naming
	private static int count;
	
	// Bounds of the entity
	protected Point2f[] bounds;
	
	// Script integration
	private HashMap<String, Script> scripts;
	
	// Script commands
	private static final String scriptCreate = "create";
	
	// Determines which position mode is entered (default: false)
	private boolean xCenterMode, yCenterMode, zCenterMode;
	
	/**
	 * Constructor to initialize the entity
	 * 
	 * @param currentSystem Current entity system
	 */
	public Entity(float x, float y, float z, int width, int height, int depth, EntitySystem currentSystem) {
        // Initialization
		id = getClass().getSimpleName() + "_" + count++; 
        components = new ArrayList<Component>();
        position = new Point3f(x, y, z);
        lastPosition = new Point3f(x, y, z);
        dimension = new Dimension3i(width, height, depth);
        scale = 1.0f;
        rotation = 0.0f;
        scripts = new HashMap<String, Script>();
        xCenterMode = false;
        yCenterMode = false;
        zCenterMode = false;
        
        if (currentSystem != null) {
        	setEntitySystem(currentSystem);
        }
		
		// Creating empty bounds
        bounds = new Point2f[4];

		for (int i = 0; i < bounds.length; ++i) {
			bounds[i] = new Point2f();
		}
		
		// Calculate the bounds
		calculateBounds();
    }
	
	
	public Entity(float x, float y, float z) {
		this(x, y, z, 0, 0, 0, null);
	}
	
	public Entity(int width, int height, int depth) {
		this(0, 0, 0, width, height, depth, null);
	}
	
	public Entity(Point3f position, Dimension3i dimension, EntitySystem system) {
		this(position.x, position.y, position.z, 
		     dimension.w, dimension.h, dimension.d, system);
	}
	
	public Entity(Point3f position, Dimension3i dimension) {
		this(position, dimension, null);
	}
	
	public Entity(Point3f position) {
		this(position.x, position.y, position.z, 0, 0, 0, null);
	}
	
	public Entity(Dimension3i dimension) {
		this(0, 0, 0, dimension.w, dimension.h, dimension.d, null);
	}
	
	public Entity(EntitySystem system) {
		this(0, 0, 0, 0, 0, 0, system);
	}
	
	public Entity() {
		this((EntitySystem)null);
	}
	
	/**
	 * @return Current entity system
	 */
	public EntitySystem getEntitySystem() {
		return currentSystem;
	}

	
	/**
	 * Change the current entity system to a new one
	 * 
	 * @param system The new target system this entity should depends on
	 */
	public void setEntitySystem(EntitySystem system) {
		if (belongsToEntitySystem()) {
			currentSystem.removeEntity(this);
			currentSystem = system;
		}
		system.addEntity(this);
	}
	
	
	/**
	 * @return True, if a entity system is currently available
	 */
	public boolean belongsToEntitySystem() {
		return currentSystem != null;
	}
	
	/**
	 * Adds a new component to the system
	 * 
	 * @param component
	 */
	public void addComponent(Component component) {
		if(RenderComponent.class.isInstance(component)) {
            renderComponent = (RenderComponent)component;
		}
        component.setOwnerEntity(this);
        components.add(component);
    }
 
	
	
	/**
	 * Returns a given component
	 * 
	 * @param id ID of the component
	 * @return Found component
	 */
    public Component getComponent(String id) {
    	
        for(Component comp : components) {
        	if (comp.getId().equalsIgnoreCase(id)) {
        		return comp;
        	}
        }
 
        return null;
    }
    
    
    
    /**
     * @return The current id of this entity
     */
    public String getId() {
    	return id;
    }
    
    
    
    /**
     * @return The current render component
     */
    public RenderComponent getRenderComponent() {
    	return renderComponent;
    }



    /**
     * @return The global rotation (Depends on parents rotation)
     */
	public float getGlobalRotation() {
		if (hasParent() && getParent() instanceof Entity) {
			Entity parent = (Entity)getParent();
			return rotation + parent.getRotation();
		} else {
			return rotation;
		}
	}


	/**
     * @return The global rotation (does not depend on parents rotation)
     */
	public float getRotation() {
		return rotation;
	}


	/**
	 * Change the current rotation to a new one
	 * 
	 * @param rotation New rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}


	/**
	 * @return Scaling of this entity instance
	 */
	public float getScale() {
		return scale;
	}
	
	
	/**
	 * Rotate this entity by the given angle
	 * 
	 * @param angle target angle
	 */
	public void rotate(float angle) {
		setRotation(getRotation() + angle);
	}	
	
	
	/**
	 * Scales the entity by the given amount
	 * 
	 * @param scale The additional scaling factor
	 */
	public void scale(float scale) {
		setScale(getScale() * scale);
	}


	/**
	 * Change the scale to a new one
	 * 
	 * @param scale the new scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
	
	/**
	 * Calculate the distance (in pixel) to another entity
	 * 
	 * @param other The other entity
	 * @return Distance to the other entity
	 */
	public float getDistanceTo(Entity other) {
		return getGlobalCenterPosition().distanceTo(other.getGlobalCenterPosition());
	}
	
	public Point3f getGlobalCenterPosition() {
		Point3f position = new Point3f();
		
		position.x = getGlobalX() + getWidth() / 2;
		position.y = getGlobalY() + getWidth() / 2;
		position.z = getGlobalZ() + getWidth() / 2;
		
		return position;
	}
	
	public float getGlobalCenterX() {
		return getGlobalCenterPosition().x;
	}
	
	public float getGlobalCenterY() {
		return getGlobalCenterPosition().y;
	}
	
	public float getGlobalCenterZ() {
		return getGlobalCenterPosition().z;
	}
	
	public Point3f getLocalCenterPosition() {
		Point3f position = new Point3f();
		
		position.x = getLocalX() + getWidth() / 2;
		position.y = getLocalY() + getWidth() / 2;
		position.z = getLocalZ() + getWidth() / 2;
		
		return position;
	}
	
	public float getLocalCenterX() {
		return getLocalCenterPosition().x;
	}
	
	public float getLocalCenterY() {
		return getLocalCenterPosition().y;
	}
	
	public float getLocalCenterZ() {
		return getLocalCenterPosition().z;
	}
	
	
	public void setLocalPosition(Vector3f position) {
		setLocalPosition(position.x, position.y, position.z);
	}
	
	public void setLocalPosition(float x, float y, float z) {
		setLocalX(x);
		setLocalY(y);
		setLocalZ(z);
	}
	
	public Point3f getLocalPosition() {
		return position;
	}
	
	public Point3f getLocalLastPosition() {
		return lastPosition;
	}
	
	public float getLocalLastX() {
		return lastPosition.x;
	}
	
	public float getLocalLastY() {
		return lastPosition.y;
	}
	
	public float getLocalLastZ() {
		return lastPosition.z;
	}
	
	public Point3f getGlobalLastPosition() {
		Point3f globalLastPosition = new Point3f();
		
		globalLastPosition.x = getGlobalLastX();
		globalLastPosition.y = getGlobalLastY();
		globalLastPosition.z = getGlobalLastZ();
		
		return globalLastPosition;
	}
	
	public float getGlobalLastX() {
		float value = getLocalLastX();
		if (hasParent()) {
			Entity entity = getParent();
			value += entity.getGlobalLastX();
		}
		return value;		
	}
	
	public float getGlobalLastY() {
		float value = getLocalLastY();
		if (hasParent()) {
			Entity entity = getParent();
			value += entity.getGlobalLastY();
		}
		return value;		
	}
	
	public float getGlobalLastZ() {
		float value = getLocalLastZ();
		if (hasParent()) {
			Entity entity = getParent();
			value += entity.getGlobalLastZ();
		}
		return value;		
	}
	
	public float getLocalX() {
		return position.x;
	}
	
	public float getLocalY() {
		return position.y;
	}
	
	public float getLocalZ() {
		return position.z;
	}

    public void setLocalX(float x) {
    	lastPosition.x = position.x;
    	position.x = x;
    	setXCenterMode(false);

    }
    
    public void setLocalY(float y) {
    	lastPosition.y = position.y;
    	position.y = y;
    	setYCenterMode(false);
    }
    
    public void setLocalZ(float z) {
    	lastPosition.z = position.z;
    	position.z = z;
    	setZCenterMode(false);
    }
    
    public void setGlobalPosition(Point3f position2) {
    	setGlobalPosition(position2.x, position2.y, position2.z);
    }
    
    public void setGlobalPosition(float x, float y, float z) {
    	setGlobalX(x);
    	setGlobalY(y);
    	setGlobalZ(z);
    	setCenterMode(false);
    }
    
    public void setGlobalX(float x) {
    	if (hasParent()) {
    		Entity parent = getParent();
    		position.x = x - parent.getGlobalX();
    	} else {
    		setLocalX(x);
    	}
    	setXCenterMode(false);
    }
    
    public void setGlobalY(float y) {
    	if (hasParent()) {
    		Entity parent = getParent();
    		position.y = y - parent.getGlobalY();
    	} else {
    		setLocalY(y);
    	}
    	setYCenterMode(false);
    }
    
    public void setGlobalZ(float z) {
    	if (hasParent()) {
    		Entity parent = getParent();
    		position.z = z - parent.getGlobalZ();
    	} else {
    		setLocalZ(z);
    	}
    	setZCenterMode(false);
    }
    
    public float getGlobalX() {
    	if (hasParent()) {
    		Entity parent = getParent();
    		return parent.getGlobalX() + getLocalX();
    	} else {
    		return getLocalX();
    	}
    }
    
    public float getGlobalY() {
    	if (hasParent()) {
    		Entity parent = getParent();
    		return parent.getGlobalY() + getLocalY();
    	} else {
    		return getLocalY();
    	}
    }
    
    public float getGlobalZ() {
    	if (hasParent()) {
    		Entity parent = getParent();
    		return parent.getGlobalZ() + getLocalZ();
    	} else {
    		return getLocalZ();
    	}
    }
    
    public Point3f getGlobalPosition() {
    	return new Point3f(getGlobalX(), getGlobalY(), getGlobalZ());
    }
    
    @Override
    public int getWidth() {
		return dimension.w;
	}
	
    @Override
	public int getHeight() {
		return dimension.h;
	}
	
	public int getDepth() {
		return dimension.d;
	}
	
	public float getRealWidth() {
		return getWidth() * (1.0f / getScale());
	}
	
	public float getRealHeight() {
		return getHeight() * (1.0f / getScale());
	}
	
	public float getRealDepth() {
		return getDepth() * (1.0f / getScale());
	}

	public void setWidth(int width) {
		dimension.w = width;
		recalculateXPosition();
	}

	public void setHeight(int height) {
		dimension.h = height;
		recalculateYPosition();
	}
	
	public void setDepth(int depth) {
		dimension.d = depth;
		recalculateZPosition();
	}
	
	public void setDimension(Dimension3i dimension) {
		setDimension(dimension.w, dimension.h, dimension.d);
	}
	
	public void setDimension(int width, int height, int depth) {
		setWidth(width);
		setHeight(height);
		setDepth(depth);
	}
	
	public void setBounds(float x, float y, float z, int width, int height, int depth) {
		setGlobalPosition(x, y, z);
		setDimension(width, height, depth);
	}
	
	public void setBounds(Point3f position, Dimension3i dimension) {
		setDimension(dimension);
		setGlobalPosition(position);
	}
	
	
	public void setLocalCenterX(float x) {
		boolean yMode = isYCenterMode();
		boolean zMode = isZCenterMode();
		setLocalCenterPosition(x, getLocalCenterY(), getLocalCenterZ());
		setXCenterMode(true);
		setYCenterMode(yMode);
		setZCenterMode(zMode);
	}
	
	public void setLocalCenterY(float y) {
		boolean xMode = isXCenterMode();
		boolean zMode = isZCenterMode();
		setLocalCenterPosition(getLocalCenterX(), y, getLocalCenterZ());
		setXCenterMode(xMode);
		setYCenterMode(true);
		setZCenterMode(zMode);
	}
	
	public void setLocalCenterZ(float z) {
		boolean xMode = isXCenterMode();
		boolean yMode = isYCenterMode();
		setLocalCenterPosition(getLocalCenterX(), getLocalCenterY(), z);
		setXCenterMode(xMode);
		setYCenterMode(yMode);
		setZCenterMode(true);
	}
	
	public void setLocalCenterPosition(Point3f pos) {
		setLocalCenterPosition(pos.x, pos.y, pos.z);
	}
	
	public void setLocalCenterPosition(float x, float y, float z) {
		setLocalPosition(x - getWidth() / 2, y - getHeight() / 2, z - getDepth() / 2);
		// Enable the center position mode
		setCenterMode(true);
	}
	
	public void setGlobalCenterX(float x) {
		boolean yMode = isYCenterMode();
		boolean zMode = isZCenterMode();
		setLocalCenterPosition(x, getLocalCenterY(), getLocalCenterZ());
		setXCenterMode(true);
		setYCenterMode(yMode);
		setZCenterMode(zMode);
	}
	
	public void setGlobalCenterY(float y) {
		boolean xMode = isXCenterMode();
		boolean zMode = isZCenterMode();
		setLocalCenterPosition(getLocalCenterX(), y, getLocalCenterZ());
		setXCenterMode(xMode);
		setYCenterMode(true);
		setZCenterMode(zMode);
	}
	
	public void setGlobalCenterZ(float z) {
		boolean xMode = isXCenterMode();
		boolean yMode = isYCenterMode();
		setLocalCenterPosition(getLocalCenterX(), getLocalCenterY(), z);
		setXCenterMode(xMode);
		setYCenterMode(yMode);
		setZCenterMode(true);
	}
	
	public void setGlobalCenterPosition(Point3f pos) {
		setGlobalCenterPosition(pos.x, pos.y, pos.z);
	}
	
	public void setGlobalCenterPosition(float x, float y, float z) {
		if (hasParent()) {
			setGlobalPosition(x - getWidth() / 2, y - getHeight() / 2, z - getDepth() / 2);
		} else {
			setLocalCenterPosition(x, y, z);
		}
		
		// Enable the center position mode
		setCenterMode(true);
	}
	
	
	
	/**
	 * When this entity belongs to a parent entity, the current global position
	 * will be set to the position of the parent.
	 */
	public void alignToParentPosition() {
		if (hasParent()) {
			setLocalPosition(0, 0, 0);
		}
	}
	
	
	
	/**
	 * When this entity belongs to a parent entity, the current center position
	 * will be set to the center position of the parent
	 */
	public void alignToParentCenterPosition() {
		if (hasParent()) {
			Entity parent = getParent();
			setLocalPosition(parent.getWidth() / 2, parent.getHeight() / 2, parent.getDepth() / 2);
		}
	}

	public boolean contains(int x, int y) {
		return false;
	}
	
	public boolean contains(SlickEntity other) {
		return false;
	}

	@Override
	public void generateScript(ScriptBuilder builder) {
		final String param = getClass().getSimpleName().toLowerCase();
		builder.addFunction(scriptCreate, param);
	}


	@Override
	public void appendScript(Script script) {
		scripts.put(script.getPath(), script);
		script.runScriptFunction("create", this);
	}


	@Override
	public Collection<Script> getScripts() {
		return scripts.values();
	}


	@Override
	public Script getScript(String path) {
		return scripts.get(path);
	}

	@Override
	public Point2f[] getBounds() {
		calculateBounds();
		return bounds;
	}

	@Override
	public float getGlobalTop() {
		return getGlobalY();
	}

	@Override
	public float getGlobalBottom() {
		return getGlobalTop() + getHeight();
	}

	@Override
	public float getGlobalLeft() {
		return getGlobalX();
	}

	@Override
	public float getGlobalRight() {
		return getGlobalLeft() + getWidth();
	}

	@Override
	public float getLocalTop() {
		return getLocalY();
	}

	@Override
	public float getLocalBottom() {
		return getLocalTop() + getHeight();
	}

	@Override
	public float getLocalLeft() {
		return getLocalX();
	}

	@Override
	public float getLocalRight() {
		return getLocalLeft() + getWidth();
	}
	
	
	
	protected void calculateBounds() {
		// Bound: Top Left
		bounds[0].x = getGlobalLeft();
		bounds[0].y = getGlobalTop();
		
		// Bound: Top Right
		bounds[1].x = getGlobalRight();
		bounds[1].y = getGlobalTop();
		
		// Bound: Bottom Right
		bounds[2].x = getGlobalRight();
		bounds[2].y = getGlobalBottom();
		
		// Bound: Bottom Left
		bounds[3].x = getGlobalLeft();
		bounds[3].y = getGlobalBottom();
		
		// Normalize the bounds (caused by rotation)
		for (Point2f bound : bounds) {
			bound.rotate(getGlobalCenterPosition(), getRotation());
		}
	}

	@Override
	public boolean collidateWith(Bounding other) {
		boolean leftCollision = getGlobalLeft()  >= other.getGlobalLeft() &&
				                getGlobalLeft() <= other.getGlobalRight();
		boolean topCollision = getGlobalTop() >= other.getGlobalTop() &&
				               getGlobalTop() <= other.getGlobalBottom();
		boolean rightCollision = getGlobalRight() <= other.getGlobalRight() &&
				                 getGlobalRight() >= other.getGlobalLeft();
		boolean bottomCollision = getGlobalBottom() <= other.getGlobalBottom() &&
				                  getGlobalBottom() >= other.getGlobalTop();
				                
		return leftCollision && topCollision || 
			   rightCollision && topCollision ||
			   leftCollision && bottomCollision ||
			   rightCollision && bottomCollision;
	}
	
	private void recalculateXPosition() {
		if (isXCenterMode()) {
			setGlobalX(getGlobalX() - getWidth() / 2);
			setXCenterMode(true);
		}
	}
	
	private void recalculateYPosition() {
		if (isYCenterMode()) {
			setGlobalY(getGlobalY() - getHeight() / 2);
			setYCenterMode(true);
		}
	}
	
	private void recalculateZPosition() {
		if (isZCenterMode()) {
			setGlobalZ(getGlobalZ() - getDepth() / 2);
			setZCenterMode(true);
		}
	}
	
	protected void setXCenterMode(boolean mode) {
		this.xCenterMode = mode;
	}
	
	protected void setYCenterMode(boolean mode) {
		this.yCenterMode = mode;
	}
	
	protected void setZCenterMode(boolean mode) {
		this.zCenterMode = mode;
	}
	
	protected boolean isXCenterMode() {
		return xCenterMode;
	}
	
	protected boolean isYCenterMode() {
		return yCenterMode;
	}
	
	protected boolean isZCenterMode() {
		return zCenterMode;
	}
	
	protected void setCenterMode(boolean mode) {
		setXCenterMode(mode);
		setYCenterMode(mode);
		setZCenterMode(mode);
	}
}
