package main.java.author.view.tabs.terrain;



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

public class TileSelectionManager {

	private ResourceBundle myBitmapBundle;
	private Canvas myCanvas;
	private TileEditingPanel myTileEditPanel;
	private TileDisplay myTileDisplay;
	
	public TileSelectionManager(Canvas canvas) {
		myCanvas = canvas;
		myTileDisplay = new TileDisplay(this);
		myTileEditPanel = new TileEditingPanel(this);

	}
	
	public TileDisplay getTileDisplay() {
		return myTileDisplay;
	}
	
	public TileEditingPanel getTileEditPanel() {
		return myTileEditPanel;
	}
	
	public void clearCanvasTiles(ActionEvent e) {
		myCanvas.clearTiles();
	}
	
	public Canvas getCanvas() {
		return myCanvas;
	}

}
