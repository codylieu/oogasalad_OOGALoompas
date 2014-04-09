package main.java.author.view.tabs.terrain.types;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;

import main.java.author.view.tabs.terrain.Canvas;

/**
 * Intermediate class which represents a Tile. Class has favorable aspects as
 * we can click on JButtons that look like Tiles and set their values accordingly.
 * These values are then transferred to the actual Tile object when the user clicks
 * on the Canvas
 *
 */
public class TileObject extends JButton{
	
	private Image myImage;
	private Image myUneditedImage; // original image associated with the TileObject
	private int myPassabilityIndex; // an edited version of the TileObject's original image
	
	public TileObject(Image img) {
		myImage = img;
		myUneditedImage = img;
	}
	
	public int getPassabilityIndex() {
		return myPassabilityIndex;
	}
	
	public void setPassabilityIndex(int index) {
		myPassabilityIndex = index;
	}
	
	public Color getBGColor() {
		return null;
	}
	
	public Image getImage() {
		return myImage;
	}
	
	public Image getUneditedImage() {
		return myUneditedImage;
	}
	
	public void setImage(Image img) {
		myImage = img;
	}

}
