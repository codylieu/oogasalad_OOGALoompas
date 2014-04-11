package main.java.author.view.tabs.terrain;

import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;

public class Tile implements Serializable {
	private int myRow;          // 0 - NUM_ROWS
	private int myColumn;       // 0 - NUM_COLS
    private int myMapXIndex;    // image x index in tilemap
    private int myMapYIndex;    // image y index in tilemap
    private String myTileMapFileName; // tilemap name
	private Color myColor;
	private int myPassIndex;
	private transient Image myImg;

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

    public String getMyTileMapFileName() {
        return myTileMapFileName;
    }

    public void setMyTileMapFileName(String myTileMapFileName) {
        this.myTileMapFileName = myTileMapFileName;
    }
}
