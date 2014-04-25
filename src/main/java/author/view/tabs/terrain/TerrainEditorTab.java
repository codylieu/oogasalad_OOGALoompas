package main.java.author.view.tabs.terrain;

import static main.java.author.util.ActionListenerUtil.actionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jgame.JGColor;
import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.TerrainController;
import main.java.author.view.AuthoringView;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.CanvasSchema;
import main.java.schema.map.GameMapSchema;
import main.java.schema.map.TileMapSchema;
import main.java.schema.map.TileSchema;

public class TerrainEditorTab extends EditorTab {
	private static final String CLEAR = "Clear Tiles";
	private static final String ADD_TILEMAP = "Import Image File";
	private static final String TERRAIN_CHOOSER = "Choose Terrain Type";
	private static final String ROW_QUERY = "Enter Row Count";
	private static final String COL_QUERY = "Enter Column Count";
	private static final String IMAGE_FILTER_DIALOGUE = ".GIF,.PNG,.BMP Images";
	private static final String MAP_SAVED = "Map Saved";
	private static final String UPDATE_CANVAS = "Change Canvas Size";
	private static final int DEFAULT_ROW_COUNT = 20;
	private static final int DEFAULT_COLUMN_COUNT = 25;
	public static final int DEFAULT_PASSABILITY_INDEX = TerrainAttribute.
			Walkable.getIndex();

	
	private int selectedPassabilityIndex;

	private JFileChooser fileChooser;
	private TileSelectionManager myTileSelectionManager;
	private Map<String, JComponent> displayOptions;

	private Canvas myCanvas;
	private JPanel myCanvasPanel;

	public TerrainEditorTab(TabController controller){
		super(controller);
		initializeTerrain();
	}

	private void initializeTerrain() {
		selectedPassabilityIndex = DEFAULT_PASSABILITY_INDEX;
		myCanvasPanel = new JPanel();
		myCanvasPanel.add(myCanvas = new Canvas(DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT, this));
		myTileSelectionManager = new TileSelectionManager(myCanvas);
		add(myTileSelectionManager.getTileDisplayTabs(), BorderLayout.WEST);
		add(myCanvasPanel, BorderLayout.CENTER);
		constructButtonDisplay();
	}

	/**
	 * Obtains information from the user about the row and column size of the 
	 * terrain map, and creates the Canvas representing the terrain map
	 * 
	 * @return true if the row and column entries contain valid information, 
	 * otherwise false
	 */
	private boolean updateCanvasSize() {
		boolean isInitialized = false;
		String numRows = queryUser(ROW_QUERY);
		String numCols = queryUser(COL_QUERY);
		try {
			int rowCount = Integer.parseInt(numRows);
			int colCount = Integer.parseInt(numCols);
			myCanvas = new Canvas(rowCount, colCount, this);
			myCanvasPanel.add(myCanvas);
			isInitialized = true;
		} catch (NumberFormatException e) {}
		return isInitialized;
	}

	private JComboBox constructTerrainTypes() {
		TerrainAttribute [] terrainAttributeTypes = TerrainAttribute.values();
		
		List<String> terrainAttributeInfo = new ArrayList<String>();
	
		for (TerrainAttribute terrainAttr : terrainAttributeTypes) {
			Color tileBorderColor = terrainAttr.getColor();
			int r = tileBorderColor.getRed();
			int g = tileBorderColor.getGreen();
			int b = tileBorderColor.getBlue();
			String colorHex = String.format("%02x%02x%02x", r, g, b);
			String terrainInfo = terrainAttr.toString();
			String coloredTerrainInfo = "<html><font color=#" + colorHex + ">" + terrainInfo + "</font>";
			terrainAttributeInfo.add(coloredTerrainInfo);
		}

		JComboBox scrollableTerrainTypes = new JComboBox(terrainAttributeInfo.toArray());
		((JLabel) scrollableTerrainTypes.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		scrollableTerrainTypes.addActionListener(actionListener(this, "updatePassabilityIndex"));
		scrollableTerrainTypes.setEnabled(false);
		return scrollableTerrainTypes;
	}

	public void updatePassabilityIndex(ActionEvent e) {
		String terrainTypeStr = String.valueOf(((JComboBox) e.getSource()).
				getSelectedItem()).replaceAll("\\<[^>]*>","");
		selectedPassabilityIndex = TerrainAttribute.valueOf(terrainTypeStr).getIndex();
	}

	public int getPassabilityIndex() {
		return selectedPassabilityIndex;
	}

	/**
	 * Initializes different buttons that give the user various options
	 * when constructing the terrain map. These include clearing the canvas,
	 * editing tiles, saving the current map, and adding new images for future
	 * use.
	 */
	private void constructButtonDisplay() {
		displayOptions = new LinkedHashMap<String, JComponent>();
		displayOptions.put(TERRAIN_CHOOSER, constructTerrainTypes());
		displayOptions.put(ADD_TILEMAP, initNewTileMap());
		displayOptions.put(CLEAR, initClearButton());
		displayOptions.put(UPDATE_CANVAS, initNewCanvas());

		JPanel optionDisplayPanel = new JPanel();
		optionDisplayPanel.setBackground(new Color(50, 50, 50));

		optionDisplayPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridy = 0;
		for (JComponent displayOption : displayOptions.values()) {
			optionDisplayPanel.add(displayOption, c);
			c.gridy++;
		}

		add(optionDisplayPanel, BorderLayout.EAST);
		revalidate();
		repaint();
	}


	/**
	 * Constructs a JButton that allows the user to enter a image map,
	 * which can then be parsed into smaller images
	 */
	private JButton initNewTileMap() {
		JButton createTileMap = new JButton(ADD_TILEMAP);
		createTileMap.addActionListener(actionListener(this, "importTileMap"));
		return createTileMap;
	}

	/**
	 * Constructs a JButton that allows the user to clear the terrain map
	 */
	private JButton initClearButton() {
		JButton clearButton = new JButton(CLEAR);
		clearButton.addActionListener(actionListener(this, "clearCanvasTiles"));
		return clearButton;
	}

	private JButton initNewCanvas() {
		JButton createNewCanvas = new JButton(UPDATE_CANVAS);
		createNewCanvas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tile> oldTiles = myCanvas.getTiles();
				myCanvasPanel.remove(myCanvas);
				updateCanvasSize();
				updateCanvas(oldTiles);
				myTileSelectionManager.setCanvas(myCanvas);
				TerrainEditorTab.this.revalidate();
				TerrainEditorTab.this.repaint();
				SwingUtilities.getWindowAncestor(TerrainEditorTab.this).pack();
			}

		});
		return createNewCanvas;
	}
	
	private void updateCanvas(List<Tile> oldTiles) {
		for (Tile t : oldTiles) {
			myCanvas.updateTile(t);;
		}
	}

	/**
	 * Allows the user to enter a grid of images that they would like to use,
	 * which can then be parsed based on the pixel size of each image within
	 * the grid.
	 * 
	 * @param e
	 */
	public void importTileMap(ActionEvent e) {
		fileChooser = new JFileChooser(new File(AuthoringView.DEFAULT_RESOURCES_DIR));
		FileFilter imageFilter = new FileNameExtensionFilter(IMAGE_FILTER_DIALOGUE,
				"png", "gif", "bmp");
		fileChooser.setFileFilter(imageFilter);

		int fileReturn = fileChooser.showOpenDialog(this);
		if (fileReturn == JFileChooser.APPROVE_OPTION) {
			importTileDisplay(fileChooser.getSelectedFile());
			displayOptions.get(TERRAIN_CHOOSER).setEnabled(true);
			revalidate();
			repaint();
		} 
	}

	/**
	 * Allows the current tiles on the canvas to be saved. TileSchemas represent
	 * the information behind Tile objects, while TileMapSchemas represent
	 * information about an image file imported by the user. Both are necessary
	 * in transferring information to the engine team. 
	 * 
	 * @param e
	 */
	public void saveMap(ActionEvent e) {
		JOptionPane.showMessageDialog(this, MAP_SAVED);
		saveTabData();
	}

	@Override
	public void saveTabData() {
		GameMapSchema myCompletedMap = new GameMapSchema();

		List<Tile> gameTiles = myCanvas.getTiles();
		List<TileSchema> gameTileSchemas = new ArrayList<TileSchema>();        
		for (Tile tile : gameTiles) {
			TileSchema tileSchema = new TileSchema();
			populateTileSchema(tileSchema, tile);
			gameTileSchemas.add(tileSchema);
		}

		List<TileDisplay> tileDisplays = myTileSelectionManager.getAllTileDisplays();
		List<TileMapSchema> gameTileMapSchemas = new ArrayList<TileMapSchema>();
		for (TileDisplay tileDisp : tileDisplays) {
			TileMapSchema tileMapSchema = new TileMapSchema();
			populateTileMapSchema(tileMapSchema, tileDisp);
			gameTileMapSchemas.add(tileMapSchema);
		}
		
		CanvasSchema canvasSchema = new CanvasSchema();
		canvasSchema.addAttribute(CanvasSchema.Y_TILES, myCanvas.getRows());
		canvasSchema.addAttribute(CanvasSchema.X_TILES, myCanvas.getCols());
		canvasSchema.addAttribute(CanvasSchema.ENTRY_ROW, myCanvas.getEntryRow());
		canvasSchema.addAttribute(CanvasSchema.ENTRY_COL, myCanvas.getEntryCol());
		canvasSchema.addAttribute(CanvasSchema.EXIT_ROW, myCanvas.getExitRow());
		canvasSchema.addAttribute(CanvasSchema.EXIT_COL, myCanvas.getExitCol());
		

		myCompletedMap.addAttribute(GameMapSchema.MY_TILES, (Serializable) gameTileSchemas);
		myCompletedMap.addAttribute(GameMapSchema.MY_TILEMAPS, (Serializable) gameTileMapSchemas);
		myCompletedMap.addAttribute(GameMapSchema.MY_CANVAS_ATTRIBUTES, (Serializable) canvasSchema);

		TerrainController myTerrainController = (TerrainController) myController;
		myTerrainController.addMaps(myCompletedMap);
	}

	/**
	 * Transfers information about a Tile into a TileSchema object
	 * 
	 * @param tileSchema 
	 * @param tile
	 */
	private void populateTileSchema(TileSchema tileSchema, Tile tile) {
		tileSchema.addAttribute(TileSchema.CANVAS_ROW, tile.getRow());
		tileSchema.addAttribute(TileSchema.CANVAS_COL, tile.getCol());
		tileSchema.addAttribute(TileSchema.TILEMAP_ROW, tile.getMyMapYIndex());
		tileSchema.addAttribute(TileSchema.TILEMAP_COL, tile.getMyMapXIndex());
		tileSchema.addAttribute(TileSchema.TILEMAP_FILE_NAME, tile.getMyTileMapFileName());
		tileSchema.addAttribute(TileSchema.TILE_CID, tile.getPassIndex());
	}

	/**
	 * Transfers information about a TileDisplay into a TileMapSchema object
	 * @param tileMapSchema
	 * @param tileDisp
	 */
	private void populateTileMapSchema(TileMapSchema tileMapSchema, TileDisplay tileDisp) {
		tileMapSchema.addAttribute(TileMapSchema.NUM_ROWS, tileDisp.getNumRows());
		tileMapSchema.addAttribute(TileMapSchema.NUM_COLS, tileDisp.getNumCols());
		tileMapSchema.addAttribute(TileMapSchema.PIXEL_SIZE, tileDisp.getMyPixelSize());
		tileMapSchema.addAttribute(TileMapSchema.TILEMAP_FILE_NAME, tileDisp.getTileMapFile());
	}

	/**
	 * Clears the canvas of all user placed Tiles
	 */
	public void clearCanvasTiles(ActionEvent e) {
		myCanvas.clearTiles();
	}

	/**
	 * Asks the user for information that can help parse the provided image, then
	 * calls upon myTileSelectionManager to add the tile display
	 */
	private void importTileDisplay(File f) {
		myTileSelectionManager.addTileDisplay(f);
	}

	private String queryUser(String query) {
		return JOptionPane.showInputDialog(query);
	}

}
