package main.java.author.view.tabs.terrain;

import static main.java.author.controller.ActionListenerUtil.actionListener;

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
	
	private static final String DEFAULT_BG_BITMAP_SOURCE = "blocks1.bmp";
	private static final int SCALE_PIXEL_SIZE = 16; // pixel size for jbutton icon display
	private TileSelectionManager myTileManager;
	private ResourceBundle myBitmapBundle;
	private Image[][] myImages; 
	private int pixelSize;
	
	public TileDisplay(TileSelectionManager tileManager, ResourceBundle bitmapBundle) {
		myTileManager = tileManager;
		myBitmapBundle = bitmapBundle;
		myImages = parseBitmap(Tile.DEFAULT_IMAGE_PACKAGE, DEFAULT_BG_BITMAP_SOURCE);

		constructTileOptions();
		setVisible(true);
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
	
	/**
	 * 
	 * @param e
	 */
	public void constructTileOptions() {
		JPanel tileOptions = new JPanel();
		tileOptions.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		for (int i = 0; i < myImages.length; i ++) {
			for (int j = 0; j < myImages[0].length; j++) {
				c.gridy = i;
				c.gridx = j;
				Image im = myImages[i][j];
				im = im.getScaledInstance(SCALE_PIXEL_SIZE, SCALE_PIXEL_SIZE, Image.SCALE_DEFAULT);

				TileObject imgDisplayObj = new TileObject(myImages[i][j]);
				imgDisplayObj.addActionListener(actionListener(myTileManager, "updateSelection"));
				imgDisplayObj.setIcon(new ImageIcon(im));
				tileOptions.add(imgDisplayObj, c);
			}
		}
		add(tileOptions);
	}
	
	public TileSelectionManager getTileSelectionManager() {
		return ((TileSelectionManager) this.getParent());
	}
	
	public Canvas getCanvas() {
		return getTileSelectionManager().getCanvas();
	}
	
	public int getPixelSize() {
		return pixelSize;
	}
	
	public JScrollPane getMyScrollPane() {
		JScrollPane myScrollPane = new JScrollPane(this);
		myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		myScrollPane.setPreferredSize(new Dimension(275, 350));
		return myScrollPane;
	}
	
	
	
}
