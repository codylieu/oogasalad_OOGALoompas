package main.java.author.view.tabs.terrain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import main.java.author.controller.MainController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.terrain.types.TileObject;
import static main.java.author.controller.ActionListenerUtil.actionListener;

public class TerrainEditorTab extends EditorTab{

	private static final String CLEAR = "Clear Tiles";
	private static final String BACKGROUND_TILES = "Open Default Background Tiles";
	
	private static final String BITMAP_FILE = "BitmapImages";
	private static final String DEFAULT_BG_BITMAP_SOURCE = "blocks1.bmp";
	
	private ResourceBundle myBitmapBundle;
	
	private static final int SCALE_PIXEL_SIZE = 16;
	private Map<String, JButton> buttonDisplayOptions;
	private Canvas myCanvas;
	
	public TerrainEditorTab(MainController controller){
		super(controller);
		myBitmapBundle = getResourceBundle("main.resources.author.images.", BITMAP_FILE);
		initButtonDisplay();
		add(myCanvas = new Canvas(), BorderLayout.CENTER);
	}
	
	private void initButtonDisplay() {
		buttonDisplayOptions = new HashMap<String, JButton>();
		buttonDisplayOptions.put(CLEAR, initClearButton());
		buttonDisplayOptions.put(BACKGROUND_TILES, initDefaultBackground());
		
		JPanel buttonDisplayPanel = new JPanel();
		buttonDisplayPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		for (JButton buttonDisplay : buttonDisplayOptions.values()) {
			buttonDisplayPanel.add(buttonDisplay, c);
			c.gridy++;
		}
		
		add(buttonDisplayPanel, BorderLayout.WEST);
	}

	private JButton initClearButton() {
		JButton clearButton = new JButton(CLEAR);
		clearButton.addActionListener(actionListener(this, "clearCanvasTiles"));
		return clearButton;
	}
	
	private JButton initDefaultBackground() {
		JButton openBGTiles = new JButton(BACKGROUND_TILES);
		openBGTiles.addActionListener(actionListener(this, "displayTileOptions"));
		return openBGTiles;
	}
	
	private Image[][] readBitmap() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(Tile.DEFAULT_IMAGE_PACKAGE + DEFAULT_BG_BITMAP_SOURCE));
		    int imageWidth = img.getWidth();
		    int imageHeight = img.getHeight();
		    int pixels = Integer.parseInt(myBitmapBundle.getString(DEFAULT_BG_BITMAP_SOURCE));
		    Image [][] myImageArray = new Image[imageHeight/pixels][imageWidth/pixels];
		    
		    for (int i = 0; i < imageHeight/pixels; i++) {
		    	for (int j = 0; j < imageWidth/pixels; j++) {
		    		myImageArray[i][j] = img.getSubimage(j*pixels, i*pixels, pixels, pixels);
		    	}
		    }
		    return myImageArray;
		} catch (IOException e) {
			return null;
		}
	}
	
	public void displayTileOptions(ActionEvent e) {
		JFrame tileOptionsFrame = new JFrame();
		final Image[][] myImages = readBitmap();

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
				imgDisplayObj.addActionListener(actionListener(this, "updateCanvasSelection"));
				imgDisplayObj.setIcon(new ImageIcon(im));
				tileOptions.add(imgDisplayObj, c);
			}
		}
		JScrollPane scrollPane = new JScrollPane(tileOptions);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setPreferredSize(new Dimension(350, 350));
		tileOptionsFrame.add(scrollPane, BorderLayout.CENTER);
		tileOptionsFrame.setResizable(false);
		tileOptionsFrame.pack();
		tileOptionsFrame.setVisible(true);
	}
	
    private ResourceBundle getResourceBundle(String bundlePackage, String bundleName) {
        return ResourceBundle.getBundle(bundlePackage + bundleName);
    }
	
	public void updateCanvasSelection(ActionEvent e) {
		myCanvas.setSelectedTileObj((TileObject) e.getSource());
	}
	
	public void clearCanvasTiles(ActionEvent e) {
		myCanvas.clearTiles();
	}

}
