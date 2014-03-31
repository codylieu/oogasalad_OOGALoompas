package main.java.author.view.tabs.terrain.types;

import java.awt.Color;

public class Grass extends TileObject {

	@Override
	
	public boolean canWalkOver() {
		return true;
	}

	@Override
	public Color getBGColor() {
		return null;
	}

	@Override
	public String getImage() {
		return "grass.png";
	}

}
