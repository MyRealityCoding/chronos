package de.myreality.chronos.slick;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;

import de.myreality.chronos.resource.Freeable;
import de.myreality.chronos.resource.ResourceLoader;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Resource loader for slick fonts. Can only be used in combination with
 * Slick (http://slick.cokeandcode.com)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class FontLoader extends ResourceLoader<Font> {

	@Override
	protected Font loadResource(Freeable freeable, ResourceDefinition definition) {
		Font font = null;
		try {
			String path = definition.getContent();
			// Image-Pfad erstellen
			String imagePath = path.substring( 0, path.length() - 3);
			StringBuilder builder = new StringBuilder();
			builder.append(imagePath);
			builder.append("png");
			font = new AngelCodeFont(path, builder.toString());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return font;
	}

}
