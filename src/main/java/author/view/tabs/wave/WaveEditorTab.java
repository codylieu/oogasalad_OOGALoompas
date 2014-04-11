package main.java.author.view.tabs.wave;

import java.awt.BorderLayout;

import javax.swing.JButton;

import main.java.author.controller.TabController;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.terrain.TileSelectionManager;

public class WaveEditorTab extends EditorTab{
	
	private static final String CLEAR = "Clear Tiles";
	private static final String SAVE = "Save";
	
	private TileSelectionManager myTileSelectionManager;
	
	private Canvas myCanvas;
	
	public WaveEditorTab(TabController controller){
		super(controller);
		myCanvas = new Canvas();
		add(myCanvas, BorderLayout.CENTER);
		add(myTileSelectionManager.getTileDisplayTabs(), BorderLayout.EAST);
		
	}
	
	public void update() {
		
	}
	
	private JButton makeClearButton() {
		JButton result = new JButton(CLEAR);
		
		return result;
	}
	
	private JButton makeSaveButton() {
		return null;
	}
	
}
