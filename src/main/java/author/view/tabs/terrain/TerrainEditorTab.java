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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.TerrainController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.author.view.tabs.terrain.types.TileObject;
import main.java.schema.GameMap;
import static main.java.author.util.ActionListenerUtil.actionListener;

public class TerrainEditorTab extends EditorTab{

	private static final String CLEAR = "Clear Tiles";
	private static final String EDIT_TILE = "Edit Tile";
	private static final String SAVE_MAP = "Save Map";
	private static final String ADD_BITMAP = "Add Bitmap File";
	private static final String PIXEL_QUERY = "How many pixels are in the bitmap?";
	private static final String PIXEL_RANGE = "Pixel size must be between 10 and 40";
	
	private JFileChooser fileChooser;
	private TileSelectionManager myTileSelectionManager;
	private Map<String, JButton> buttonDisplayOptions;

	private Canvas myCanvas;
	
	public TerrainEditorTab(TabController controller){
		super(controller);
		myCanvas = new Canvas();
		myTileSelectionManager = new TileSelectionManager(myCanvas);
		add(myCanvas, BorderLayout.CENTER);
		add(myTileSelectionManager.getTileDisplayTabs(), BorderLayout.EAST);
		constructButtonDisplay();
	}
	
	private void constructButtonDisplay() {
		buttonDisplayOptions = new HashMap<String, JButton>();
		buttonDisplayOptions.put(CLEAR, initClearButton());
		buttonDisplayOptions.put(EDIT_TILE, initEditorButton());
		buttonDisplayOptions.put(SAVE_MAP, initSaveButton());
		buttonDisplayOptions.put(ADD_BITMAP, initNewBitmap());
		
		JPanel buttonDisplayPanel = new JPanel();
		buttonDisplayPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		for (JButton buttonDisplay : buttonDisplayOptions.values()) {
			buttonDisplayPanel.add(buttonDisplay, c);
			c.gridy++;
		}
		add(buttonDisplayPanel, BorderLayout.SOUTH);
	}

	private JButton initNewBitmap() {
		JButton createBitmap = new JButton(ADD_BITMAP);
		createBitmap.addActionListener(actionListener(this, "importBitmap"));
		return createBitmap;
	}
	
	private JButton initSaveButton() {
		JButton saveButton = new JButton(SAVE_MAP);
		saveButton.addActionListener(actionListener(this, "saveMap"));
		return saveButton;
	}
	
	private JButton initClearButton() {
		JButton clearButton = new JButton(CLEAR);
		clearButton.addActionListener(actionListener(this, "clearCanvasTiles"));
		return clearButton;
	}
	
	private JButton initEditorButton() {
		JButton openBGTiles = new JButton(EDIT_TILE);
		openBGTiles.addActionListener(actionListener(this, "openEditorWindow"));
		return openBGTiles;
	}
	
	public void saveMap(ActionEvent e) {
		GameMap myCompletedMap = new GameMap(myCanvas.getTileArray());
		TerrainController control = (TerrainController) myController;
		control.addMap(myCompletedMap);
		System.out.println("Map Saved");
	}
	
	public void importBitmap(ActionEvent e) {
		fileChooser = new JFileChooser();
		int fileReturn = fileChooser.showOpenDialog(this);
		if (fileReturn == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			addTileDisplay(file);
		}
	}
	
	private void addTileDisplay(File f) {
		if (f.getPath().endsWith(".bmp")) {
			String pixels = queryUser(PIXEL_QUERY);
			try {
				int pixelCount = Integer.parseInt(pixels);
				if (pixelCount < 40 && pixelCount > 10) {
					myTileSelectionManager.addTileDisplay(f, pixelCount);
				} else {
					JOptionPane.showMessageDialog(this, PIXEL_RANGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String queryUser(String query) {
		return JOptionPane.showInputDialog(query);
	}
	
	public void clearCanvasTiles(ActionEvent e) {
		myCanvas.clearTiles();
	}
	
	public void openEditorWindow(ActionEvent e) {
		JFrame selectionFrame = new JFrame();
		selectionFrame.add(myTileSelectionManager.getTileEditPanel(), BorderLayout.CENTER);
		selectionFrame.setLocation(this.getWidth() + 25, 0);
		selectionFrame.pack();
		selectionFrame.setVisible(true);
	}
}
