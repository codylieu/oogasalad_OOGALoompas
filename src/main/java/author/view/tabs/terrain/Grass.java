package main.java.author.view.tabs.terrain;

import java.awt.Color;

public class Grass extends TileObject {

	@Override
	boolean canWalkOver() {
		return true;
	}

	@Override
	Color getBGColor() {
		return null;
	}

	@Override
	String getImage() {
		return "grass.png";
	}

}
