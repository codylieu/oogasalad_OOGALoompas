package main.java.player.util;

import jgame.JGObject;

public class TowerGhost extends JGObject {
	
	public TowerGhost(double x, double y, String imageName) {
		super("TowerGhost", false, x, y, 0, imageName);
		//expiry = 1;
	}
}
