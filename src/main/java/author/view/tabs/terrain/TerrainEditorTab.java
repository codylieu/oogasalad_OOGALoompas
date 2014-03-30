package main.java.author.view.tabs.terrain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.java.author.controller.MainController;
import main.java.author.view.tabs.EditorTab;

public class TerrainEditorTab extends EditorTab{

	private List<TileObject> availableTiles;

	public TerrainEditorTab(MainController controller){
		super(controller);
		availableTiles = new ArrayList<TileObject>();
		availableTiles.add(new Ground());
		availableTiles.add(new Grass());
		add(new Canvas(), BorderLayout.CENTER);
		add(getTileList(), BorderLayout.EAST);
	}

	private JPanel getTileList() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 1;
		JPanel panel = new JPanel();
		for (TileObject tObj : availableTiles) {
			try {
				Image img = ImageIO.read(new File(Tile.DEFAULT_IMAGE_PACKAGE + tObj.getImage()));
				tObj.setIcon(new ImageIcon(img));
				tObj.setPreferredSize(new Dimension(35,35));
			} catch (IOException ex) {}
			panel.add(tObj, c);
			c.gridy++;
		}
		panel.setPreferredSize(new Dimension(50,200)); // important for maintaining size of JPanel
		return panel;
	}

}
