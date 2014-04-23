package main.java.author.view.tabs.terrain;
import javax.swing.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Canvas extends JPanel {

	private static final int TILE_SIZE = 25; // in pixels
	public static final Color DEFAULT_TILE_COLOR = Color.LIGHT_GRAY;
	public static final Color DEFAULT_BORDER_COLOR = Color.BLACK;

	private int numRows;
	private int numCols;

	private Tile[][] myTiles;
	private TileObject selectedTileObj;
	private TerrainEditorTab myTerrainTab;

	public Canvas(int rows, int cols, TerrainEditorTab terrainTab){
		numRows = rows;
		numCols = cols;
		myTerrainTab = terrainTab;
		myTiles = new Tile[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				myTiles[row][col] = new Tile(row, col);
			}
		}
		setPreferredSize(new Dimension(numCols*TILE_SIZE, numRows*TILE_SIZE)); // important for maintaining size of JPanel
		initCanvasListeners();
	}

	/**
	 * Initializes listeners for both clicking and dragging on tiles
	 */
	private void initCanvasListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updateTile(e);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e) {
				updateTile(e);
			}
		});	
	}

	/**
	 * Updates tiles within the JPanel when the 'update(getGraphics())' method is called. Sets the
	 * appropriate background and border of each tile accordingly. 
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call to super class is necessary
		g.clearRect(0, 0, getWidth(), getHeight());

		for (Tile tile : getTiles()) {
			// Upper left corner of the tile
			int x = tile.getCol() * TILE_SIZE;
			int y = tile.getRow() * TILE_SIZE;
			Image tileImage = tile.getImage();

			if (tileImage == null) {
				g.setColor(DEFAULT_TILE_COLOR);
				g.fillRect(x, y, TILE_SIZE, TILE_SIZE); // filling appropriate Tile background colors
			} else {
				g.drawImage(tileImage,x,y, TILE_SIZE, TILE_SIZE, DEFAULT_TILE_COLOR, null);
			}
			g.setColor(DEFAULT_BORDER_COLOR);
			g.drawRect(x, y, TILE_SIZE, TILE_SIZE); // drawing appropriate Tile borders
		}
	}

	/**
	 * Obtains the specified Tile within the JPanel
	 * @param x the x-coordinate of the JPanel
	 * @param y the y-coordinate of the JPanel
	 * @return the Tile for a specific location
	 */
	private Tile getTile(int x, int y) {
		int row = y/TILE_SIZE;
		int col = x/TILE_SIZE;
		boolean validRow = row >= 0 && row <= numRows - 1;
		boolean validCol = col >= 0 && col <= numCols - 1;
		if (validRow && validCol) {
			return myTiles[row][col];
		}
		return null;
	}

	protected void updateTile(Tile newTile) {
		int newTileRow = newTile.getRow();
		int newTileCol = newTile.getCol();

		if (newTileRow < numRows && newTileCol < numCols) {
			myTiles[newTileRow][newTileCol] = newTile;
		}
	}

	/**
	 * Obtains a list of tiles within the JPanel
	 * @return all tiles within the JPanel
	 */
	protected List<Tile> getTiles() {
		List<Tile> tiles = new ArrayList<Tile>();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				tiles.add(myTiles[i][j]);
			}
		}
		return tiles;
	}

	/**
	 * Updates a tile that has been clicked in the terrain map
	 * with data from the TileObject that was selected in the
	 * TileDisplay
	 */
	private void updateTile(MouseEvent e) {
		Tile tile = getTile(e.getX(), e.getY());
		if (tile == null || selectedTileObj == null) {
			return;
		}
		tile.setImage(selectedTileObj.getImage());
		tile.setPassIndex(myTerrainTab.getPassabilityIndex());
		tile.setMyMapXIndex(selectedTileObj.getMyXIndex()); // TODO: change?
		tile.setMyMapYIndex(selectedTileObj.getMyYIndex());
		tile.setMyTileMapFileName(selectedTileObj.getMyTileMapFileName());
		repaint(); // we want to keep this repaint, if we use update, it messes up on macs
	}

	/**
	 * Clears all tiles on the grid
	 */
	protected void clearTiles() {
		for (Tile tile : getTiles()) {
			tile.setImage(null);
			tile.setPassIndex(0);
			repaint();
		}
	}

	protected int getRows() {
		return numRows;
	}

	protected int getCols() {
		return numCols;
	}

	/**
	 * Updates the 'selected' TileObject so that on a mouse press,
	 * we can update the underlying Tile on the Canvas accordingly
	 */
	public void setSelectedTileObj(TileObject tObj) {
		selectedTileObj = tObj;
	}

	/**
	 * Obtains the 'selected' TileObject
	 */
	public TileObject getSelectedTileObj() {
		return selectedTileObj;
	}
}