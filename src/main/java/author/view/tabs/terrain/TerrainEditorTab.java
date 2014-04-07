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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.java.author.controller.MainController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.terrain.types.TileObject;
import static main.java.author.util.ActionListenerUtil.actionListener;

public class TerrainEditorTab extends EditorTab{

	private static final String CLEAR = "Clear Tiles";
	private static final String BACKGROUND_TILES = "Open Default Background Tiles";
	
	private TileSelectionManager myTileSelectionManager;
	private Map<String, JButton> buttonDisplayOptions;

	private Canvas myCanvas;
	
	public TerrainEditorTab(MainController controller){
		super(controller);
		myCanvas = new Canvas();
		myTileSelectionManager = new TileSelectionManager(myCanvas);
		add(myCanvas, BorderLayout.CENTER);
		initButtonDisplay();
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
		clearButton.addActionListener(actionListener(myTileSelectionManager, "clearCanvasTiles"));
		return clearButton;
	}
	
	private JButton initDefaultBackground() {
		JButton openBGTiles = new JButton(BACKGROUND_TILES);
		openBGTiles.addActionListener(actionListener(this, "displayTileSelectionManager"));
		return openBGTiles;
	}
	
	public void displayTileSelectionManager(ActionEvent e) {
		JFrame selectionFrame = new JFrame();
		selectionFrame.add(myTileSelectionManager, BorderLayout.CENTER);
		selectionFrame.setLocation(this.getWidth() + 25, 0);
		selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectionFrame.pack();
		selectionFrame.setVisible(true);
	}
}
