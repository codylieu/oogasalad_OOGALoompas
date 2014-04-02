package main.java.author.view.tabs.terrain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.author.controller.MainController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.terrain.types.Grass;
import main.java.author.view.tabs.terrain.types.Ground;
import main.java.author.view.tabs.terrain.types.TileObject;
import main.java.author.view.tabs.terrain.types.Tree;
import main.java.author.view.tabs.terrain.types.Water;

import static main.java.author.controller.ActionListenerUtil.actionListener;

public class TerrainEditorTab extends EditorTab{

	private List<TileObject> availableTiles;
	private static final String TERRAIN_TYPE_PCKG = "main.java.author.view.tabs.terrain.types.";
	private static final String CLEAR = "Clear Tiles";
	private String [] terrainTypes = { "Ground", "Grass", "Water", "Tree" };
	private TileObject mySelectedTile;
	private Canvas myCanvas;
	
	public TerrainEditorTab(MainController controller){
		super(controller);
		initTerrainTypes();
		add(myCanvas = new Canvas(), BorderLayout.CENTER);
		add(getTileList(), BorderLayout.EAST);
		add(initClearButton(), BorderLayout.SOUTH);
	}
	
	private void initTerrainTypes() {
		availableTiles = new ArrayList<TileObject>();		
		for (String terrainType : terrainTypes) {
			try {
				TileObject tileObj = (TileObject) Class.forName(TERRAIN_TYPE_PCKG + terrainType).newInstance();
				tileObj.addActionListener(actionListener(this, "updateCanvasSelection"));
				availableTiles.add(tileObj);
			} catch (Exception e) { e.printStackTrace(); } 
		}
	}
	
	private JButton initClearButton() {
		JButton clearButton = new JButton(CLEAR);
		clearButton.addActionListener(actionListener(this, "clearCanvasTiles"));
		return clearButton;
	}
	
	private JPanel getTileList() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 1;
		JPanel panel = new JPanel();
		for (TileObject tObj : availableTiles) {
			try {
				Image img = ImageIO.read(new File(Tile.DEFAULT_IMAGE_PACKAGE + tObj.getImage()));
				tObj.setIcon(new ImageIcon(img));
				tObj.setBackground(tObj.getBGColor());
				tObj.setOpaque(true);
				tObj.setPreferredSize(new Dimension(35,35));
			} catch (IOException ex) {}
			panel.add(tObj, c);
			c.gridy++;
		}
		panel.setPreferredSize(new Dimension(50,200)); // important for maintaining size of JPanel
		return panel;
	}

	public void updateCanvasSelection(ActionEvent e) {
		myCanvas.setSelectedTileObj((TileObject) e.getSource());
	}
	
	public void clearCanvasTiles(ActionEvent e) {
		myCanvas.clearTiles();
	}

}
