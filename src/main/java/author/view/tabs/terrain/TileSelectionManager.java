package main.java.author.view.tabs.terrain;

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
	private JTabbedPane tileDisplayTab;
	
	public TileSelectionManager(Canvas canvas) {
		myCanvas = canvas;
		initTileDisplays();
		myTileEditPanel = new TileEditingPanel(this);
	}
	
	private void initTileDisplays() {
		myTileDisplays = new ArrayList<TileDisplay>();
		tileDisplayTab = new JTabbedPane();
		myBitmapBundle = ResourceBundle.getBundle("main.resources.author.images.BitmapImages");
		Enumeration <String> bitmaps = myBitmapBundle.getKeys();
		
		while (bitmaps.hasMoreElements()) {
			String bmp = bitmaps.nextElement();
			String value = myBitmapBundle.getString(bmp);
			try {
				Integer i = Integer.parseInt(value);
				TileDisplay currTileDisp = new TileDisplay(this, bmp, i);
				myTileDisplays.add(currTileDisp);
				tileDisplayTab.addTab(trimBitmapString(bmp), currTileDisp.getTileScrollPane());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private String trimBitmapString(String bitmapStr) {
		int index = bitmapStr.indexOf(".bmp");
		return (index != -1) ? bitmapStr.substring(0, index) : bitmapStr;
		
	}
	
	public JTabbedPane getTileDisplayTabs() {
		return tileDisplayTab;
	}
	
	public TileDisplay getTileDisplay() {
		int index = tileDisplayTab.getSelectedIndex();
		return myTileDisplays.get(index);
	}
	
	public TileEditingPanel getTileEditPanel() {
		return myTileEditPanel;
	}
	
	public Canvas getCanvas() {
		return myCanvas;
	}

}
