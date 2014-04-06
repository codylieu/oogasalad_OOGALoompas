package main.java.player;

import jgame.JGObject;

public class TowerGhost extends JGObject {
	
	public TowerGhost(double x, double y) {
		super("TowerGhost", false, x, y, 0, "SimpleTower");
		//expiry = 1;
	}
}
