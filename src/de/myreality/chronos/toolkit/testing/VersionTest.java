package de.myreality.chronos.toolkit.testing;

import de.myreality.chronos.toolkit.updating.GameVersion;

public class VersionTest {

	public static void main(String[] args) {
		
		GameVersion version1 = new GameVersion("2.1.1.6");
		GameVersion version2 = new GameVersion("2.2.1.5");
		
		System.out.println(version1.isNewerThan(version2));
	}
}
