package main.java.author.view.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Tile {
	
	private int myRow;    // 0 - NUM_ROWS
	private int myColumn; // 0 - NUM_COLS
	private Color myColor;
	private int myPassIndex;
	private boolean isSelected;
	private Image myImg;
	
	public static final String DEFAULT_IMAGE_PACKAGE = "src/main/resources/author/images/";

	public Tile(int row, int column, Color color) {
	    myRow = row;
	    myColumn = column;
	    myColor = color;
	}
	
	public Color getColor() {
		return myColor;
	}
	
	public void setColor(Color color) {
		myColor = color;
	}
	
	public Image getImage() {
		return myImg;
	}
	
	public int getRow() {
		return myRow;
	}
	
	public int getCol() {
		return myColumn;
	}
	
	public int getPassIndex() {
		return myPassIndex;
	}
	
	public void setPassIndex(int index) {
		myPassIndex = index;
	}
	
	public void setImage(Image img) {
		myImg = img;
	}
	
}
