package main.java.author.view.tabs.terrain;

import static main.java.author.util.ActionListenerUtil.actionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.author.view.tabs.terrain.types.TileObject;

public class TileDisplay extends JPanel {

	private static final String BITMAP_FILE = "BitmapImages";
	private static final String DEFAULT_BG_BITMAP_SOURCE = "pokemon2.bmp";
	private static final int SCALE_PIXEL_SIZE = 16; // pixel size for jbutton icon display
	private TileSelectionManager myTileManager;
	private ResourceBundle myBitmapBundle;
	private Image[][] myImages; 
	private int pixelSize;
	
	public TileDisplay(TileSelectionManager tileManager) {
		myTileManager = tileManager;
		initResources();
		constructTileOptions();
		setVisible(true);
	}
	
	private void initResources() {
		myBitmapBundle = getResourceBundle("main.resources.author.images.", BITMAP_FILE);
		myImages = parseBitmap(Tile.DEFAULT_IMAGE_PACKAGE, DEFAULT_BG_BITMAP_SOURCE);
	}
	
	/**
	 * 
	 * @param bitmapPckg
	 * @param bitmapName
	 * @return
	 */
	private Image[][] parseBitmap(String bitmapPckg, String bitmapName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(bitmapPckg + bitmapName));
			int imageWidth = img.getWidth();
			int imageHeight = img.getHeight();
			pixelSize = Integer.parseInt(myBitmapBundle.getString(bitmapName));
			Image [][] myImageArray = new Image[imageHeight/pixelSize][imageWidth/pixelSize];

			for (int i = 0; i < imageHeight/pixelSize; i++) {
				for (int j = 0; j < imageWidth/pixelSize; j++) {
					myImageArray[i][j] = img.getSubimage(j*pixelSize, i*pixelSize, pixelSize, pixelSize);
				}
			}
			return myImageArray;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void constructTileOptions() {
		JPanel tileOptions = new JPanel();
		tileOptions.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		for (int i = 0; i < myImages.length; i ++) {
			for (int j = 0; j < myImages[0].length; j++) {
				if (c.gridx > 8) {
					c.gridy++;
					c.gridx = 0;
				} 
				Image im = myImages[i][j];
				Image scaledIm = im.getScaledInstance(SCALE_PIXEL_SIZE, SCALE_PIXEL_SIZE, Image.SCALE_DEFAULT);

				TileObject imgDisplayObj = new TileObject(im);
				imgDisplayObj.addActionListener(actionListener(this, "updateSelection"));
				imgDisplayObj.setIcon(new ImageIcon(scaledIm)); // place scaled image as jbutton icon
				tileOptions.add(imgDisplayObj, c); // grid layout of jbutton/images
				c.gridx++;
			}
		}
		add(tileOptions);
	}
	
	private ResourceBundle getResourceBundle(String bundlePackage, String bundleName) {
		return ResourceBundle.getBundle(bundlePackage + bundleName);
	}
	
	public int getPixelSize() {
		return pixelSize;
	}
	
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
		myTileManager.getTileEditPanel().setImageAngle(0);
		myTileManager.getTileEditPanel().update(myTileManager.getTileEditPanel().getGraphics());
	}
	
}
