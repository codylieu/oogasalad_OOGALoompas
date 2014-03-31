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

	public static final int NUM_ROWS = 10;
	public static final int NUM_COLS = 15;
	public static final int TILE_SIZE = 32; // in pixels

	private final Tile[][] myTiles;
	private static TileObject selectedTileObj;

	public Canvas(){
		myTiles = new Tile[NUM_COLS][NUM_ROWS];
		for (int col = 0; col < NUM_COLS; col++) {
			for (int row = 0; row < NUM_ROWS; row++) {
				myTiles[col][row] = new Tile(row, col, DEFAULT_TILE_COLOR);
			}
		}
		setPreferredSize(new Dimension(NUM_COLS*TILE_SIZE, NUM_ROWS*TILE_SIZE)); // important for maintaining size of JPanel
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updateTileImage(e);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e) {
				updateTileImage(e);
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
			BufferedImage tileImage = (BufferedImage) tile.getImage();

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
		return myTiles[x/TILE_SIZE][y/TILE_SIZE];
	}

	/**
	 * Obtains a list of tiles within the JPanel
	 * @return all tiles within the JPanel
	 */
	private List<Tile> getTiles() {
		List<Tile> tiles = new ArrayList<Tile>();
		for (int i = 0; i < NUM_COLS; i++) {
			for (int j = 0; j < NUM_ROWS; j++) {
				tiles.add(myTiles[i][j]);
			}
		}
		return tiles;
	}

	private void updateTileImage(MouseEvent e) {
		Tile tile = getTile(e.getX(), e.getY());
		tile.setImage((selectedTileObj == null) ? null : selectedTileObj.getImage());
		tile.setColor((selectedTileObj == null) ? DEFAULT_TILE_COLOR : selectedTileObj.getBGColor());
		repaint(); 
	}

	public static void setSelectedTileObj(TileObject tObj) {
		selectedTileObj = tObj;
	}
}