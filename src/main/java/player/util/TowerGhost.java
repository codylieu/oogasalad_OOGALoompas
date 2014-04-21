package main.java.player.util;

import jgame.JGObject;

/**
 * A "tower ghost" that allows the player to 
 * visualize where they are about to place a tower
 * 
 * This JGObject does not interact with anything else
 * in the JGEngine.
 * @author Kevin
 *
 */
public class TowerGhost extends JGObject {
	
	public TowerGhost(double x, double y, String imageName) {
		super("TowerGhost", false, x, y, 0, imageName);
		//expiry = 1;
	}
}
