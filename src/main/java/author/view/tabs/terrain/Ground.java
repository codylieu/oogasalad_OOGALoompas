package main.java.author.view.tabs.terrain;

import java.awt.Color;

import javax.swing.JButton;

public class Ground extends TileObject {

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
		return "ground.png";
	}

}
