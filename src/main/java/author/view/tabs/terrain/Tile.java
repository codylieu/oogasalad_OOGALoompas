package main.java.author.view.tabs.terrain;

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

public class Tile extends JPanel {
	
	private int myRow;    // 0 - NUM_ROWS
	private int myColumn; // 0 - NUM_COLS
	private Color myColor;
	private boolean isSelected;
	private BufferedImage myImg;
	
	static final String DEFAULT_IMAGE_PACKAGE = "src/main/resources/author/images/";

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
	
	public boolean isSelected() {
		return isSelected;
	}
	
	protected void setImage(String imgName) {
		if (imgName == null) {
			return;
		}
		try {
			File file = new File(DEFAULT_IMAGE_PACKAGE + imgName);
			myImg = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
