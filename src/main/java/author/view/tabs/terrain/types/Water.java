package main.java.author.view.tabs.terrain.types;

import java.awt.Color;

public class Water extends TileObject {

	@Override
	public boolean canWalkOver() {
		return false;
	}

	@Override
	public Color getBGColor() {
		return new Color(0, 0, 205);
	}

	@Override
	public String getImage() {
		return "water.png";
	}

}
