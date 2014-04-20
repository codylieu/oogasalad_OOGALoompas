package main.java.author.view.tabs.terrain;

import java.awt.Image;
import java.io.Serializable;

public class Tile implements Serializable {
	private int myRow;          // 0 - NUM_ROWS
	private int myColumn;       // 0 - NUM_COLS
	private int myMapXIndex;    // image x (column) index in tilemap
	private int myMapYIndex;    // image y (row) index in tilemap
	private String myTileMapFileName; // tilemap name
	private int myPassIndex; // passability index to specify what can traverse the Tile

	public static final String DEFAULT_IMAGE_PACKAGE = "src/main/resources/author/images/";

	private transient Image myImg;

	public Tile(int row, int column) {
		myRow = row;
		myColumn = column;
	}

	/**
	 * Specifies the image of the tile on the canvas
	 * @return the image representing the tile
	 */
	public Image getImage() {
		return myImg;
	}

	/**
	 * Sets the image of the current tile
	 */
	public void setImage(Image img) {
		myImg = img;
	}

	/**
	 * Specifies the row of the the tile on the canvas
	 * @return the row of the tile on the canvas
	 */
	public int getRow() {
		return myRow;
	}

	/**
	 * Specifies the column of the tile on the canvas
	 * @return the column of the tile on the canvas
	 */
	public int getCol() {
		return myColumn;
	}

	/**
	 * Specifies the passability index of the tile, different
	 * options are specified in TerrainAttribute.java
	 * @return an index representing the method of available travel
	 *         on the tile
	 */
	public int getPassIndex() {
		return myPassIndex;
	}

	/**
	 * Sets the passability index of the tile, different options are
	 * specified in TerrainAttribute.java
	 */
	public void setPassIndex(int index) {
		myPassIndex = index;
	}

	/**
	 * Obtains the column index of the image that is being used
	 * to represent this Tile within the map of the TileDisplay
	 */
	public int getMyMapXIndex() {
		return myMapXIndex;
	}

	/**
	 * Sets the column index of the image that is being used
	 * to represent this Tile within the map of the TileDisplay
	 */
	public void setMyMapXIndex(int myMapXIndex) {
		this.myMapXIndex = myMapXIndex;
	}

	/**
	 * Obtains the row index of the image that is being used
	 * to represent this Tile within the map of the TileDisplay
	 */
	public int getMyMapYIndex() {
		return myMapYIndex;
	}

	/**
	 * Sets the row index of the image that is being used
	 * to represent this Tile within the map of the TileDisplay
	 */
	public void setMyMapYIndex(int myMapYIndex) {
		this.myMapYIndex = myMapYIndex;
	}

	/**
	 * Obtains the resource that holds the image being used to represent
	 * the Tile
	 */
	public String getMyTileMapFileName() {
		return myTileMapFileName;
	}

	/**
	 * Sets the resource that holds the image being used to represent the
	 * Tile
	 */
	public void setMyTileMapFileName(String myTileMapFileName) {
		this.myTileMapFileName = myTileMapFileName;
	}
}
