package de.myreality.chronos.slick;

import java.io.IOException;

import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

import de.myreality.chronos.resource.Freeable;
import de.myreality.chronos.resource.ResourceLoader;

public class ConfigurableEmitterLoader extends ResourceLoader<ConfigurableEmitter> {

	private static ConfigurableEmitterLoader instance;
	
	static {
		instance = new ConfigurableEmitterLoader();
	}
	
	// Singleton is always private
	private ConfigurableEmitterLoader() { }

	@Override
	protected ConfigurableEmitter loadResource(Freeable freeable, ResourceDefinition definition) {
		try {
			return ParticleIO.loadEmitter(definition.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ConfigurableEmitterLoader getInstance() {
		return instance;
	}

}
