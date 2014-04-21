package main.java.author.view.tabs.terrain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

/**
 * This class manages the interactions between the TileDisplay (where TileObjects are selected),
 * the TileEditingPanel (where TileObjects are edited), and the Canvas (where TileObjects are displayed)
 *
 */
public class TileSelectionManager {
	private Canvas myCanvas;
	private List<TileDisplay> myTileDisplays;
	private JTabbedPane myTileDisplayTab;
	
	public TileSelectionManager(Canvas canvas) {
		myCanvas = canvas;
        myTileDisplays = new ArrayList<TileDisplay>();
        myTileDisplayTab = new JTabbedPane();
	}
	
	protected void addTileDisplay(File mapFile) {
		TileDisplay currTileDisp = new TileDisplay(this, mapFile);
		myTileDisplays.add(currTileDisp);
		myTileDisplayTab.addTab(mapFile.getName(), currTileDisp.getTileScrollPane());
	}
	
	public JTabbedPane getTileDisplayTabs() {
		return myTileDisplayTab;
	}
	
	public TileDisplay getCurrentTileDisplay() {
		int index = myTileDisplayTab.getSelectedIndex();
		return myTileDisplays.get(index);
	}

    public List<TileDisplay> getAllTileDisplays() {
        return myTileDisplays; // TODO: make unmodifiable?
    }
	
    public void setCanvas(Canvas canvas) {
    	myCanvas = canvas;
    }
    
	public Canvas getCanvas() {
		return myCanvas;
	}
}
