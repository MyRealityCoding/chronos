package de.myreality.chronos.toolkit.updating;

import java.util.regex.Pattern;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * 
 * For more information visit http://dev.my-reality.de/chronos
 * 
 * Game version that is comparable with other versions
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class GameVersion implements Comparable<GameVersion> {
	
	// String representation of the version
	private String version;
	
	/**
	 * Constructor of the game version
	 */
	public GameVersion(String version) {
		this.version = version;
	}

	@Override
	public int compareTo(GameVersion other) {
		String s1 = normalisedVersion(this);
        String s2 = normalisedVersion(other);
        int cmp = s1.compareTo(s2);
        return cmp;
	}
	
	
	/**
	 * Overriden equals method. Compares (if given) this version instance
	 * with the other one.
	 */
	@Override
	public boolean equals(Object other) {		
		if (other instanceof GameVersion) {
			GameVersion otherVersion = (GameVersion)other;
			return compareTo(otherVersion) == 0;
		} else {
			return super.equals(other);
		}
	}
	
	
	/**
	 * Checks, if the given version is newer
	 */
	public boolean isNewerThan(GameVersion version) {
		return compareTo(version) > 0;
	}
	
	/**
	 * Checks, if the given version is newer
	 */
	public boolean isOlderThan(GameVersion version) {
		return compareTo(version) < 0;
	}
	

	@Override
	public String toString() {
		return version;
	}
	
	/**
	 * @return A labeled version
	 */
	public String toLabeledVersion() {
		return "v. " + this;
	}
	
	/**
	 * @return A full labeled version
	 */
	public String toFullLabeledVersion() {
		return "Version " + this;
	}
	
	private String normalisedVersion(GameVersion version) {
        return normalisedVersion(version, ".", 4);
    }

	private String normalisedVersion(GameVersion version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version.toString());
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }
}
