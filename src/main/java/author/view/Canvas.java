package main.java.author.view;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class Canvas extends JPanel {

	public static final Color DEFAULT_TILE_COLOR = Color.LIGHT_GRAY;
	public static final Color DEFAULT_BORDER_COLOR = Color.BLACK;

	public static final int NUM_ROWS = 15;
	public static final int NUM_COLS = 15;
	public static final int TILE_SIZE = 40; // in pixels

	private final Tile[][] myTiles;

	public Canvas(){
		myTiles = new Tile[NUM_ROWS][NUM_COLS];
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				myTiles[row][col] = new Tile(row, col, DEFAULT_TILE_COLOR);
			}
		}
		setPreferredSize(new Dimension(NUM_COLS*TILE_SIZE, NUM_ROWS*TILE_SIZE)); // important for maintaining size of JPanel
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Tile tile = getTile(e.getX(), e.getY());
				tile.toggleSelection();
				repaint(); 
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
		
		for (Tile tile : getTiles()) {
			// Upper left corner of the tile
			int x = tile.getRow() * rectWidth;
			int y = tile.getCol() * rectHeight;
			Color tileColor = tile.getColor();
			g.setColor(tileColor);
			g.fillRect(x, y, rectWidth, rectHeight); // filling appropriate Tile background colors
			
			Graphics2D g2 = (Graphics2D) g; // g2 permits setting width of border
			g2.setColor(DEFAULT_BORDER_COLOR);
			BasicStroke borderWidth = (tile.isSelected()) ? new BasicStroke(3) : new BasicStroke(1);
			g2.setStroke(borderWidth);
			g2.drawRect(x, y, rectWidth, rectHeight); // drawing appropriate Tile borders
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
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				tiles.add(myTiles[i][j]);
			}
		}
		return tiles;
	}
}