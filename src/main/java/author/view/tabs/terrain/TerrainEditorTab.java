package main.java.author.view.tabs.terrain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.TerrainController;
import main.java.author.view.tabs.EditorTab;
import main.java.exceptions.engine.InvalidParameterForConcreteTypeException;
import main.java.schema.GameMap;
import main.java.schema.TileMapSchema;
import main.java.schema.TileSchema;
import static main.java.author.util.ActionListenerUtil.actionListener;

public class TerrainEditorTab extends EditorTab {
    private static final String CLEAR = "Clear Tiles";
	private static final String EDIT_TILE = "Edit Tile";
	private static final String SAVE_MAP = "Save Map";
	private static final String ADD_TILEMAP = "Import Image File";
	private static final String PIXEL_QUERY = "How many pixels are in the bitmap?";
	private static final String ROW_QUERY = "Enter Row Count";
	private static final String COL_QUERY = "Enter Column Count";
	private static final String PIXEL_RANGE = "Pixel size must be between 10 and 40";
    private static final String IMAGE_FILTER_DIALOGUE = ".GIF,.PNG,.BMP Images";
    private static final String USER_INIT_MESSAGE = "Begin Terrain Editing";

    private JFileChooser fileChooser;
	private TileSelectionManager myTileSelectionManager;
	private Map<String, JButton> buttonDisplayOptions;
	private Canvas myCanvas;
	
	public TerrainEditorTab(TabController controller){
		super(controller);
		JButton initTerrainButton = new JButton(USER_INIT_MESSAGE);
		initTerrainButton.addActionListener(actionListener(this, "initTerrainTab"));
		add(initTerrainButton);
		setPreferredSize(new Dimension(1200, 800));
	}
	
	public void initTerrainTab(ActionEvent e) {
		boolean isCanvasInitialized = initCanvas();
		if (!isCanvasInitialized) {
			return;
		}
		remove((JButton) e.getSource());
		myTileSelectionManager = new TileSelectionManager(myCanvas);
		add(myTileSelectionManager.getTileDisplayTabs(), BorderLayout.EAST);
		add(myCanvas, BorderLayout.CENTER);
		constructButtonDisplay();
	}
	
	private boolean initCanvas() {
		boolean isInitialized = false;
		String numRows = queryUser(ROW_QUERY);
		String numCols = queryUser(COL_QUERY);
		try {
			int rowCount = Integer.parseInt(numRows);
			int colCount = Integer.parseInt(numCols);
			myCanvas = new Canvas(rowCount, colCount);
			isInitialized = true;
		} catch (NumberFormatException e) {}
		return isInitialized;
	}
	
	private void constructButtonDisplay() {
		buttonDisplayOptions = new HashMap<String, JButton>();
		buttonDisplayOptions.put(CLEAR, initClearButton());
		buttonDisplayOptions.put(EDIT_TILE, initEditorButton());
		buttonDisplayOptions.put(SAVE_MAP, initSaveButton());
		buttonDisplayOptions.put(ADD_TILEMAP, initNewTileMap());
		
		JPanel buttonDisplayPanel = new JPanel();
		buttonDisplayPanel.setBackground(new Color(50, 50, 50));
		
		buttonDisplayPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		for (JButton buttonDisplay : buttonDisplayOptions.values()) {
			buttonDisplayPanel.add(buttonDisplay, c);
			c.gridy++;
		}
		
		add(buttonDisplayPanel, BorderLayout.WEST);
		revalidate();
		repaint();
	}

	private JButton initNewTileMap() {
		JButton createTileMap = new JButton(ADD_TILEMAP);
		createTileMap.addActionListener(actionListener(this, "importTileMap"));
		return createTileMap;
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
		openBGTiles.setEnabled(false);
		return openBGTiles;
	}
	
	/**
	 * Allows the user to enter a grid of images that they would like to use,
	 * which can then be parsed based on the pixel size of each image within
	 * the grid.
	 * 
	 * @param e
	 */
	public void importTileMap(ActionEvent e) {
		fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        FileFilter imageFilter = new FileNameExtensionFilter(IMAGE_FILTER_DIALOGUE,
                ".png", ".gif", ".bmp");
        fileChooser.setFileFilter(imageFilter);

		int fileReturn = fileChooser.showOpenDialog(this);
		if (fileReturn == JFileChooser.APPROVE_OPTION) {
			addTileDisplay(fileChooser.getSelectedFile());
			buttonDisplayOptions.get(EDIT_TILE).setEnabled(true);
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
		GameMap myCompletedMap = new GameMap();

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

		myCompletedMap.setTileSchemas(gameTileSchemas);
		myCompletedMap.setTileMapSchemas(gameTileMapSchemas);

		TerrainController myControl = (TerrainController) myController;
		myControl.addMap(myCompletedMap);
		
		//writeMapToFile(myCompletedMap);
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
	 * Serializes a 'GameMap' which contains multiple TileSchemas and 
	 * TileMapSchemas. 
	 * 
	 * @param map
	 */
	private void writeMapToFile(GameMap map) {
		JFileChooser saveFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SER Files", "ser");
        saveFileChooser.setFileFilter(filter);
        int returnVal = saveFileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fout = new FileOutputStream(saveFileChooser.getSelectedFile().getAbsolutePath() + ".ser");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(map);
                oos.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
	}
	
	/**
	 * Clears the canvas of all user placed Tiles
	 */
	public void clearCanvasTiles(ActionEvent e) {
		myCanvas.clearTiles();
	}
	
	private void addTileDisplay(File f) {
        String pixels = queryUser(PIXEL_QUERY);
        try {
            int pixelCount = Integer.parseInt(pixels);
            if (pixelCount < 40 && pixelCount > 10) {
                myTileSelectionManager.addTileDisplay(f, pixelCount);
            } else {
                JOptionPane.showMessageDialog(this, PIXEL_RANGE);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
	}
	
	private String queryUser(String query) {
		return JOptionPane.showInputDialog(query);
	}
	
	/**
	 * Opens a window that allows the user to edit information about
	 * the selected Tile
	 */
	public void openEditorWindow(ActionEvent e) {
		JFrame selectionFrame = new JFrame();
		selectionFrame.add(myTileSelectionManager.getTileEditPanel(), BorderLayout.CENTER);
		selectionFrame.setLocation(this.getWidth() + 25, 0);
		selectionFrame.pack();
		selectionFrame.setVisible(true);
	}
}
