package main.java.author.view.tabs.terrain;

import static main.java.author.controller.ActionListenerUtil.actionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.java.author.view.tabs.terrain.types.TileObject;

public class TileSelectionManager extends JPanel {

	private static final String BITMAP_FILE = "BitmapImages";

	private ResourceBundle myBitmapBundle;
	private Canvas myCanvas;
	
	private TileEditingPanel myTileEditPanel;
	private TileDisplay myTileDisplay;
	
	public TileSelectionManager(Canvas canvas) {
		myCanvas = canvas;
		initResources();
	
		myTileDisplay = new TileDisplay(this, myBitmapBundle);
		
		
		add(myTileDisplay.getMyScrollPane(), BorderLayout.WEST);
		add(myTileEditPanel = new TileEditingPanel(this), BorderLayout.EAST);
	}
	
	public void makeVisible(ActionEvent e) {
		setVisible(true);
	}
	
	private void initResources() {
		myBitmapBundle = getResourceBundle("main.resources.author.images.", BITMAP_FILE);
	}
	
	private ResourceBundle getResourceBundle(String bundlePackage, String bundleName) {
		return ResourceBundle.getBundle(bundlePackage + bundleName);
	}

	public void clearCanvasTiles(ActionEvent e) {
		myCanvas.clearTiles();
	}
	
	public Canvas getCanvas() {
		return myCanvas;
	}
	
	public int getPixelSize() {
		return myTileDisplay.getPixelSize();
	}
	
	public void updateSelection(ActionEvent e) {
		TileObject selectedTile = (TileObject) e.getSource();
		getCanvas().setSelectedTileObj(selectedTile);
		myTileEditPanel.setImageAngle(0);
		myTileEditPanel.repaint();
	}

}
