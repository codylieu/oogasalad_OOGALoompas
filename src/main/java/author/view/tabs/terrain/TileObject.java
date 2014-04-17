package main.java.author.view.tabs.terrain;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Intermediate class which represents a Tile. Class has favorable aspects as
 * we can click on JButtons that look like Tiles and set their values accordingly.
 * These values are then transferred to the actual Tile object when the user clicks
 * on the Canvas
 *
 */
public class TileObject extends JButton {
	private Image myImage; // an edited version of the TileObject's original image
	private Image myUneditedImage; // original image associated with the TileObject
	private int myPassabilityIndex;
    private int myXIndex;
    private int myYIndex;
    private String myTileMapFileName;
	
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
	
	public Image getImage() {
		return myImage;
	}
	
	public Image getUneditedImage() {
		return myUneditedImage;
	}
	
	public void setImage(Image img) {
		myImage = img;
	}

    public int getMyXIndex() {
        return myXIndex;
    }

    public void setMyXIndex(int myXIndex) {
        this.myXIndex = myXIndex;
    }

    public int getMyYIndex() {
        return myYIndex;
    }

    public void setMyYIndex(int myYIndex) {
        this.myYIndex = myYIndex;
    }

    public String getMyTileMapFileName() {
        return myTileMapFileName;
    }

    public void setMyTileMapFileName(String myTileMapFileName) {
        this.myTileMapFileName = myTileMapFileName;
    }
}
