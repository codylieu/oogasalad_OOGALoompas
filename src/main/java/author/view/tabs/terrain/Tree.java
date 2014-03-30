package main.java.author.view.tabs.terrain;

import java.awt.Color;

public class Tree extends TileObject {

	@Override
	boolean canWalkOver() {
		return false;
	}

	@Override
	Color getBGColor() {
		return new Color(0, 100, 0);
	}

	@Override
	String getImage() {
		return "tree.png";
	}

}
