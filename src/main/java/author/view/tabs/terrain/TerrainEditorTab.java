package main.java.author.view.tabs.terrain;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.map.GameMap;
import static main.java.author.util.ActionListenerUtil.actionListener;

public class TerrainEditorTab extends EditorTab {
    private static final String CLEAR = "Clear Tiles";
	private static final String EDIT_TILE = "Edit Tile";
	private static final String SAVE_MAP = "Save Map";
	private static final String ADD_TILEMAP = "Add Bitmap File";
	private static final String PIXEL_QUERY = "How many pixels are in the bitmap?";
	private static final String PIXEL_RANGE = "Pixel size must be between 10 and 40";
    public static final String IMAGE_FILTER_DIALOGUE = ".GIF and .PNG Images";

    private JFileChooser fileChooser;
	private TileSelectionManager myTileSelectionManager;
	private Map<String, JButton> buttonDisplayOptions;

	private Canvas myCanvas;
	
	public TerrainEditorTab(TabController controller){
		super(controller);
		myCanvas = new Canvas();
		myTileSelectionManager = new TileSelectionManager(myCanvas);
		add(myCanvas, BorderLayout.WEST);
		add(myTileSelectionManager.getTileDisplayTabs(), BorderLayout.EAST);
		constructButtonDisplay();
	}
	
	private void constructButtonDisplay() {
		buttonDisplayOptions = new HashMap<String, JButton>();
		buttonDisplayOptions.put(CLEAR, initClearButton());
		buttonDisplayOptions.put(EDIT_TILE, initEditorButton());
		buttonDisplayOptions.put(SAVE_MAP, initSaveButton());
		buttonDisplayOptions.put(ADD_TILEMAP, initNewTileMap());
		
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
		return openBGTiles;
	}

    /**
     * Temporary test file, saving will be in another component TODO: remove - jordan
     * @param e
     */
	public void saveMap(ActionEvent e) {
		GameMap myCompletedMap = new GameMap();
        Tile[][] tiles = myCanvas.getTileArray();
        myCompletedMap.setMyTiles(tiles);

        List<TileDisplay> tileDisplays = myTileSelectionManager.getAllTileDisplays();
        List<TileMap> tileMaps = new ArrayList<TileMap>();
        for (TileDisplay d : tileDisplays) {
            tileMaps.add(d.getTileMap());
        }
        myCompletedMap.setMyTileMaps(tileMaps);

        JFileChooser saveFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SER Files", "ser");
        saveFileChooser.setFileFilter(filter);
        int returnVal = saveFileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fout = new FileOutputStream(saveFileChooser.getSelectedFile().getAbsolutePath() + ".ser");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(myCompletedMap);
                oos.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
	
	public void importTileMap(ActionEvent e) {
		fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        FileFilter imageFilter = new FileNameExtensionFilter(IMAGE_FILTER_DIALOGUE,
                ".png", ".gif");
        fileChooser.setFileFilter(imageFilter);

		int fileReturn = fileChooser.showOpenDialog(this);
		if (fileReturn == JFileChooser.APPROVE_OPTION) {
			addTileDisplay(fileChooser.getSelectedFile());
		} else {
            System.out.println("unacceptable file format"); //TODO: throw exception
        }
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
        } catch (Exception e) {
            e.printStackTrace();
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
