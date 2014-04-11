package main.java.author.view.tabs.terrain;
import javax.swing.*;

import main.java.author.view.tabs.terrain.types.TileObject;

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
	public static final Color DEFAULT_TILE_COLOR = Color.LIGHT_GRAY;
	public static final Color DEFAULT_BORDER_COLOR = Color.BLACK;

	public static final int NUM_ROWS = 20;
	public static final int NUM_COLS = 25;
	public static final int TILE_SIZE = 40; // in pixels

	private final Tile[][] myTiles;
	private TileObject selectedTileObj;

	public Canvas(){
		myTiles = new Tile[NUM_ROWS][NUM_COLS];
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				myTiles[row][col] = new Tile(row, col, DEFAULT_TILE_COLOR);
			}
		}
		setPreferredSize(new Dimension(NUM_COLS*TILE_SIZE, NUM_ROWS*TILE_SIZE)); // important for maintaining size of JPanel
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
	 * Updates tiles within the JPanel when the 'repaint()' method is called. Sets the
	 * appropriate background and border of each tile accordingly. 
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call to super class is necessary
		g.clearRect(0, 0, getWidth(), getHeight());

		int rectWidth = getWidth() / NUM_COLS;
		int rectHeight = getHeight() / NUM_ROWS;

		int index = 0;
		for (Tile tile : getTiles()) {
			// Upper left corner of the tile
			int x = tile.getCol() * rectWidth;
			int y = tile.getRow() * rectHeight;
			Color tileColor = tile.getColor();
			Image tileImage = tile.getImage();

			if (tileImage == null) {
				g.setColor(tileColor);
				g.fillRect(x, y, rectWidth, rectHeight); // filling appropriate Tile background colors
			} else {
				g.drawImage(tileImage,x,y, rectWidth, rectHeight, tileColor, null);
			}
			g.setColor(DEFAULT_BORDER_COLOR);
			g.drawRect(x, y, rectWidth, rectHeight); // drawing appropriate Tile borders
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
		boolean validRow = row >= 0 && row <= NUM_ROWS - 1;
		boolean validCol = col >= 0 && col <= NUM_COLS - 1;
		if (validRow && validCol) {
			return myTiles[row][col];
		}
		return null;
	}

	/**
	 * Obtains a list of tiles within the JPanel
	 * @return all tiles within the JPanel
	 */
	private List<Tile> getTiles() {
		List<Tile> tiles = new ArrayList<Tile>();
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				tiles.add(myTiles[i][j]);
			}
		}
		return tiles;
	}

	private void updateTile(MouseEvent e) {
		Tile tile = getTile(e.getX(), e.getY());
		if (tile == null) {
			return;
		}
		tile.setImage((selectedTileObj == null) ? null : selectedTileObj.getImage());
		tile.setColor((selectedTileObj == null) ? DEFAULT_TILE_COLOR : selectedTileObj.getBGColor());
		tile.setPassIndex((selectedTileObj == null) ? 0 : selectedTileObj.getPassabilityIndex());
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
			tile.setColor(DEFAULT_TILE_COLOR);
			tile.setPassIndex(0);
			repaint();
		}
	}
	
	protected Tile[][] getTileArray() {
		return myTiles;
	}

	public void setSelectedTileObj(TileObject tObj) {
		selectedTileObj = tObj;
	}
	
	public TileObject getSelectedTileObj() {
		return selectedTileObj;
	}
}