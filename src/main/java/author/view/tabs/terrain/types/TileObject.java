package main.java.author.view.tabs.terrain.types;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;

import main.java.author.view.tabs.terrain.Canvas;

public class TileObject extends JButton{
	
	private Image myImage;
	private Image myOriginalImage;
	private int myPassabilityIndex;
	
	public TileObject(Image img) {
		myImage = img;
		myOriginalImage = img;
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
	
	public Image getOriginalImage() {
		return myOriginalImage;
	}
	
	public void setImage(Image img) {
		myImage = img;
	}

}
