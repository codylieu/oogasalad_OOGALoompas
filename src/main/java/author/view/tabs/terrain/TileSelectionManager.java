package main.java.author.view.tabs.terrain;

/**
 * This class manages the interactions between the TileDisplay (where TileObjects are selected),
 * the TileEditingPanel (where TileObjects are edited), and the Canvas (where TileObjects are displayed)
 *
 */
public class TileSelectionManager {

	private Canvas myCanvas;
	private TileEditingPanel myTileEditPanel;
	private TileDisplay myTileDisplay;
	
	public TileSelectionManager(Canvas canvas) {
		myCanvas = canvas;
		myTileDisplay = new TileDisplay(this);
		myTileEditPanel = new TileEditingPanel(this);
	}
	
	public TileDisplay getTileDisplay() {
		return myTileDisplay;
	}
	
	public TileEditingPanel getTileEditPanel() {
		return myTileEditPanel;
	}
	
	public Canvas getCanvas() {
		return myCanvas;
	}

}
