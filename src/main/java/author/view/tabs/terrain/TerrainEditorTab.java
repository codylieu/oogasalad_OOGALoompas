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
import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.terrain.types.TileObject;
import static main.java.author.util.ActionListenerUtil.actionListener;

public class TerrainEditorTab extends EditorTab{

	private static final String CLEAR = "Clear Tiles";
	private static final String EDIT_TILE = "Edit Tile";
	
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
