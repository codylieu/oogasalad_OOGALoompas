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

public class Tile {
	private int myRow;          // 0 - NUM_ROWS
	private int myColumn;       // 0 - NUM_COLS
    private int myMapXIndex;    // image x index in tilemap
    private int myMapYIndex;    // image y index in tilemap
    private String myTileMapFile; // tilemap name
	private Color myColor;
	private int myPassIndex;
	private Image myImg;

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

    public void setImage(Image img) {
        myImg = img;
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

    public int getMyMapXIndex() {
        return myMapXIndex;
    }

    public void setMyMapXIndex(int myMapXIndex) {
        this.myMapXIndex = myMapXIndex;
    }

    public int getMyMapYIndex() {
        return myMapYIndex;
    }

    public void setMyMapYIndex(int myMapYIndex) {
        this.myMapYIndex = myMapYIndex;
    }

    public String getMyTileMapFile() {
        return myTileMapFile;
    }

    public void setMyTileMapFile(String myTileMapFile) {
        this.myTileMapFile = myTileMapFile;
    }
}
