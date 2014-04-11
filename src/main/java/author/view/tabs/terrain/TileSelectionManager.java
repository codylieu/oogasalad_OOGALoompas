package main.java.author.view.tabs.terrain;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JTabbedPane;

/**
 * This class manages the interactions between the TileDisplay (where TileObjects are selected),
 * the TileEditingPanel (where TileObjects are edited), and the Canvas (where TileObjects are displayed)
 *
 */
public class TileSelectionManager {

	private Canvas myCanvas;
	private ResourceBundle myBitmapBundle;
	private TileEditingPanel myTileEditPanel;
	private List<TileDisplay> myTileDisplays;
	private JTabbedPane myTileDisplayTab;
	
	public TileSelectionManager(Canvas canvas) {
		myCanvas = canvas;
        myTileDisplays = new ArrayList<TileDisplay>();
        myTileDisplayTab = new JTabbedPane();
		myTileEditPanel = new TileEditingPanel(this);
	}
	
	protected void addTileDisplay(File bmpFile, int pixels) {
		TileDisplay currTileDisp = new TileDisplay(this, bmpFile, pixels);
		myTileDisplays.add(currTileDisp);
		myTileDisplayTab.addTab(trimBitmapString(bmpFile.getName()), currTileDisp.getTileScrollPane());
	}
	
	private String trimBitmapString(String bitmapStr) {
		int index = bitmapStr.indexOf(".bmp");
		return (index != -1) ? bitmapStr.substring(0, index) : bitmapStr;
	}
	
	public JTabbedPane getTileDisplayTabs() {
		return myTileDisplayTab;
	}
	
	public TileDisplay getTileDisplay() {
		int index = myTileDisplayTab.getSelectedIndex();
		return myTileDisplays.get(index);
	}
	
	public TileEditingPanel getTileEditPanel() {
		return myTileEditPanel;
	}
	
	public Canvas getCanvas() {
		return myCanvas;
	}

}
