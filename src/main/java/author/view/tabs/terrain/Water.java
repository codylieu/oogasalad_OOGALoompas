package main.java.author.view.tabs.terrain;

import java.awt.Color;

public class Water extends TileObject {

	@Override
	boolean canWalkOver() {
		return false;
	}

	@Override
	Color getBGColor() {
		return new Color(0, 0, 205);
	}

	@Override
	String getImage() {
		return "water.png";
	}

}
