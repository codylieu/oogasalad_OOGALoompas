package main.java.author.view.tabs.terrain;

import static main.java.author.util.ActionListenerUtil.actionListener;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TileDisplay extends JPanel {
	private static int PIXEL_SIZE = 32;
	private static final int SCALE_PIXEL_SIZE = 16; // pixel size for jbutton icon display
	private TileSelectionManager myTileManager;
	private Image[][] myImages;
	private int myNumXTiles;
	private int myNumYTiles;

	private String myTileMapFile;

	public TileDisplay(TileSelectionManager tileManager, File tileMapFile) {
		myTileManager = tileManager;
		myTileMapFile = tileMapFile.getName();
		initTileDisplay(tileMapFile);
		displayTiles();
		setVisible(true);
	}

	/**
	 * Initializes resources to be used
	 */
	private void initTileDisplay(File tileMap) {
		myImages = parseTileMap(tileMap);
	}

	/**
	 * Reads through a bitmap (currently used for tile backgrounds) and outputs
	 * a 2D array of image objects
	 * @param tileMap the bitmap file to load
	 * @return a 2D array of image objects within the bitmap
	 */
	private Image[][] parseTileMap(File tileMap) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(tileMap);

			myNumXTiles = img.getWidth()/PIXEL_SIZE;
			myNumYTiles = img.getHeight()/PIXEL_SIZE;
			Image [][] myImageArray = new Image[myNumYTiles][myNumXTiles];
			for (int i = 0; i < myNumYTiles; i++) {
				for (int j = 0; j < myNumXTiles; j++) {
					myImageArray[i][j] = img.getSubimage(j * PIXEL_SIZE, i * PIXEL_SIZE,
							PIXEL_SIZE, PIXEL_SIZE);
				}
			}

			return myImageArray;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Allows the user to see the tiles read in from the bitmap,
	 * displayed through a GridBagLayout on a JPanel
	 */
	public void displayTiles() {
		JPanel tileView = new JPanel();
		tileView.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		for (int i = 0; i < myImages.length; i ++) {
			for (int j = 0; j < myImages[0].length; j++) {
				if (c.gridx > 4) {
					c.gridx = 0;
					c.gridy++;
				} 
				Image im = myImages[i][j];
				Image scaledIm = im.getScaledInstance(SCALE_PIXEL_SIZE, SCALE_PIXEL_SIZE, Image.SCALE_DEFAULT); // scale down image

				TileObject imgDisplayObj = new TileObject(im);
				imgDisplayObj.setMyXIndex(j); // Set row and column of where the thing is displayed
				imgDisplayObj.setMyYIndex(i);
				imgDisplayObj.setMyTileMapFileName(myTileMapFile);
				imgDisplayObj.addActionListener(actionListener(this, "updateSelection"));
				imgDisplayObj.setIcon(new ImageIcon(scaledIm)); // place scaled image as jbutton icon
				tileView.add(imgDisplayObj, c); // grid layout of jbutton/images
				c.gridx++;
			}
		}
		add(tileView);
	}

	/**
	 * Obtains the side length of images in the bitmap, in pixels
	 */
	public int getMyPixelSize() {
		return PIXEL_SIZE;
	}

	public int getNumRows() {
		return myNumYTiles;
	}

	public int getNumCols() {
		return myNumXTiles;
	}

	public String getTileMapFile() {
		return myTileMapFile;
	}

	/**
	 * Allows for a scrollable view of the tiles
	 */
	public JScrollPane getTileScrollPane() {
		JScrollPane myScrollPane = new JScrollPane(this);
		myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		myScrollPane.setPreferredSize(new Dimension(275, 350));
		return myScrollPane;
	}

	/**
	 * Action Listener, called when a TileObject within this TileDisplay is clicked
	 * @param e an ActionEvent called by a TileObject source
	 */
	public void updateSelection(ActionEvent e) {
		TileObject selectedTile = (TileObject) e.getSource();
		myTileManager.getCanvas().setSelectedTileObj(selectedTile);
	}

}
